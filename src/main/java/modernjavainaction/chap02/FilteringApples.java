package modernjavainaction.chap02;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FilteringApples {

  public static void main(String... args) {
    List<Apple> inventory = Arrays.asList(
        new Apple(80, Color.GREEN),
        new Apple(155, Color.GREEN),
        new Apple(120, Color.RED));

    //java8 이전 st
    System.out.println("java8 이전 st ");
    filterGreenApples(inventory);


    System.out.println("java8 이전 end ");
    //java8 이전 end




    // [Apple{color=GREEN, weight=80}, Apple{color=GREEN, weight=155}]
    List<Apple> greenApples = filterApplesByColor(inventory, Color.GREEN);
    System.out.println("greenApples :: " + greenApples);

    // [Apple{color=RED, weight=120}]
    List<Apple> redApples = filterApplesByColor(inventory, Color.RED);
    System.out.println("redApples :: " + redApples);

    // [Apple{color=GREEN, weight=80}, Apple{color=GREEN, weight=155}]
    List<Apple> greenApples2 = filter(inventory, new AppleColorPredicate());
    System.out.println(greenApples2);

    // [Apple{color=GREEN, weight=155}]
    List<Apple> heavyApples = filter(inventory, new AppleWeightPredicate());
    System.out.println(heavyApples);

    // []
    List<Apple> redAndHeavyApples = filter(inventory, new AppleRedAndHeavyPredicate());
    System.out.println(redAndHeavyApples);

    // [Apple{color=RED, weight=120}]
    List<Apple> redApples2 = filter(inventory, new ApplePredicate() {
      @Override
      public boolean test(Apple a) {
        return a.getColor() == Color.RED;
      }
    });
    System.out.println(redApples2);


  }

  public static List<Apple> filterGreenApples(List<Apple> inventory) {
    List<Apple> result = new ArrayList<>();

    System.out.println(Arrays.toString(Color.values()));

    for (Apple apple : inventory) {
      if (apple.getColor() == Color.GREEN) {
        result.add(apple);
      }
    }
    return result;
  }

  //메서드에 파라미터를 추가
  public static List<Apple> filterApplesByColor(List<Apple> inventory, Color color) {
    List<Apple> result = new ArrayList<>();
    for (Apple apple : inventory) {
      if (apple.getColor() == color) {
        result.add(apple);
      }
    }
    return result;
  }

  public static List<Apple> filterApplesByWeight(List<Apple> inventory, int weight) {
    List<Apple> result = new ArrayList<>();
    for (Apple apple : inventory) {
      if (apple.getWeight() > weight) {
        result.add(apple);
      }
    }
    return result;
  }

  public static List<Apple> filter(List<Apple> inventory, ApplePredicate p) {
    List<Apple> result = new ArrayList<>();
    for (Apple apple : inventory) {
      if (p.test(apple)) {
        result.add(apple);
      }
    }
    return result;
  }

  private static boolean test(Integer i) {
    return i % 2 == 0;
  }

  enum Color {
    RED("빨간색"),
    GREEN("초록색");

    private String name;

    Color(String name){
      this.name = name;
    }

    public String getName(){
      return name;
    }
  }

  @Getter
  @Setter
  public static class Apple {

    private int weight = 0;
    private Color color;

    public Apple(int weight, Color color) {
      this.weight = weight;
      this.color = color;
    }


    @SuppressWarnings("boxing")
    @Override
    public String toString() {
      return String.format("Apple{color=%s, weight=%d}", color, weight);
    }

  }


  interface ApplePredicate {

    boolean test(Apple a);

  }

  static class AppleWeightPredicate implements ApplePredicate {

    @Override
    public boolean test(Apple apple) {
      return apple.getWeight() > 150;
    }

  }

  static class AppleColorPredicate implements ApplePredicate {

    @Override
    public boolean test(Apple apple) {
      return apple.getColor() == Color.GREEN;
    }

  }

  static class AppleRedAndHeavyPredicate implements ApplePredicate {

    @Override
    public boolean test(Apple apple) {
      return apple.getColor() == Color.RED && apple.getWeight() > 150;
    }

  }

}
