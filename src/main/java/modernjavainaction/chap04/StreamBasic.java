package modernjavainaction.chap04;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StreamBasic {

  public static void main(String... args) {
    // 자바 7
    getLowCaloricDishesNamesInJava7(Dish.menu).forEach(System.out::println);

    System.out.println("---");

    // 자바 8
    getLowCaloricDishesNamesInJava8(Dish.menu).forEach(System.out::println);
    System.out.println("---");
    getLowCaloricDishesNamesInJava8Version2(Dish.menu).forEach(System.out::println);
    System.out.println("-------");
    getLowCaloricDishesNamesInJava8Version3(Dish.menu);
  }

  public static List<String> getLowCaloricDishesNamesInJava7(List<Dish> dishes) {
    List<Dish> lowCaloricDishes = new ArrayList<>();
    for (Dish d : dishes) {
      if (d.getCalories() < 400) {
        lowCaloricDishes.add(d);
      }
    }
    List<String> lowCaloricDishesName = new ArrayList<>();    //가비지 변수
    Collections.sort(lowCaloricDishes, new Comparator<Dish>() {
      @Override
      public int compare(Dish d1, Dish d2) {
        return Integer.compare(d1.getCalories(), d2.getCalories());
      }
    });
    for (Dish d : lowCaloricDishes) {
      lowCaloricDishesName.add(d.getName());
    }
    return lowCaloricDishesName;
  }

  public static List<String> getLowCaloricDishesNamesInJava8(List<Dish> dishes) {
    return dishes.stream()                //스트림을 얻음
        .filter(d -> d.getCalories() < 400) //중간연산
        .sorted(comparing(Dish::getCalories))//중간연산
        .map(Dish::getName)
        .collect(toList());
  }

  public static List<String> getLowCaloricDishesNamesInJava8Version2(List<Dish> dishes){
    return dishes.stream()
            .filter(d -> d.getCalories() < 400)
            .sorted(Comparator.comparing(d -> d.getCalories()))
            .map(d -> d.getName())
            .collect(Collectors.toList())
            ;
  }

  public static List<String> getLowCaloricDishesNamesInJava8Version3(List<Dish> dishes){

    List<String> nameList =
            dishes.stream()
                    .filter(dish -> {
                      System.out.println("filtering :: " + dish.getName());
                      return dish.getCalories() > 300;
                    })
                    .map(dish -> {
                      System.out.println("mapping :: " + dish.getName());
                      return dish.getName();
                    })
                    .collect(Collectors.toList());


    return nameList;
  }

}
