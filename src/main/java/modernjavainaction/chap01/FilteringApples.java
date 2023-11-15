package modernjavainaction.chap01;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class FilteringApples {
/**
 * 메서드 (코드) 를 인수로 전달할 수 있다.
 * */

  public static void main(String... args) {
    List<Apple> inventory = Arrays.asList(
        new Apple(80, "green"),
        new Apple(100, "green"),
        new Apple(180, "green"),
        new Apple(155, "red"),
        new Apple(70, "green"),
        new Apple(180, "red")
    );

    System.out.println("inventory = " + inventory);

    // [Apple{color='green', weight=80}, Apple{color='green', weight=155}]
    // 코드 (isGreenApple) 를 인수로 넘겨줄 수 있음
    List<Apple> greenApples = filterApples(inventory, FilteringApples::isGreenApple);
    System.out.println("greenApples :: " + greenApples);


    // [Apple{color='green', weight=155}]
    // 코드 (isHeavyApple) 를 인수로 넘겨줄 수 있음
    List<Apple> heavyApples = filterApples(inventory, FilteringApples::isHeavyApple);
    System.out.println("heavyApples :: " + heavyApples);

    // [Apple{color='green', weight=80}, Apple{color='green', weight=155}]
    /*
    isGreenApple, isHeavyApple 등 한두 번만 사용할 메서드를 매번 정의한다...??
     -> 람다 개념을 도입
     */
    List<Apple> greenApples2 = filterApples(inventory, (Apple a) -> "green".equals(a.getColor()));
    System.out.println("greenApples2 :: " + greenApples2);

    // [Apple{color='green', weight=155}]
    List<Apple> heavyApples2 = filterApples(inventory, (Apple a) -> a.getWeight() > 150);
    System.out.println("heavyApples2 :: " + heavyApples2);

    // []
    List<Apple> weirdApples = filterApples(inventory, (Apple a) -> a.getWeight() < 80 || "brown".equals(a.getColor()));
    System.out.println("weirdApples :: " + weirdApples);
  }

  //Java8 이전 녹색 사과를 선택해서 리스트를 반환
  public static List<Apple> filterGreenApples(List<Apple> inventory) {
    List<Apple> result = new ArrayList<>();
    for (Apple apple : inventory) {
      if ("green".equals(apple.getColor())) {
        result.add(apple);
      }
    }
    return result;
  }

  //Java8 이전 무게가 150그램 이상인 사과를 선택해서 리스트를 반환
  public static List<Apple> filterHeavyApples(List<Apple> inventory) {
    List<Apple> result = new ArrayList<>();
    for (Apple apple : inventory) {
      if (apple.getWeight() >= 150) {
        result.add(apple);
      }
    }
    return result;
  }

  public static boolean isGreenApple(Apple apple) {
    return "green".equals(apple.getColor());
  }

  public static boolean isHeavyApple(Apple apple) {
    return apple.getWeight() > 150;
  }


  public static List<Apple> filterApples(List<Apple> inventory, Predicate<Apple> p) {
    List<Apple> result = new ArrayList<>();
    for (Apple apple : inventory) {
      if (p.test(apple)) {
        result.add(apple);
      }
    }
    return result;
  }

  @Getter
  @Setter
  public static class Apple {

    private int weight = 0;
    private String color = "";

    public Apple(int weight, String color) {
      this.weight = weight;
      this.color = color;
    }

    @SuppressWarnings("boxing")
    @Override
    public String toString() {
      return String.format("Apple{color='%s', weight=%d}", color, weight);
    }

  }

}
