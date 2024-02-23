package modernjavainaction.chap04;

import static java.util.stream.Collectors.toList;
import static modernjavainaction.chap04.Dish.menu;

import java.util.List;
import java.util.stream.Stream;

public class HighCaloriesNames {

  public static void main(String[] args) {
    List<String> names = menu.stream()
        .filter(dish -> {
          System.out.println("filtering " + dish.getName());
          return dish.getCalories() > 300;
        })
        .map(dish -> {
          System.out.println("mapping " + dish.getName());
          return dish.getName();
        })
        .limit(3)
        .collect(toList());
    System.out.println(names);


      System.out.println(" ================ ");
      Stream<Dish> dishStream = menu.stream()
              .filter(dish -> {
                  return dish.getCalories() > 300;
              });

      List<Dish> collect = dishStream.limit(3).collect(toList());

      Stream<String> stringStream = dishStream.map(dish -> {
          return dish.getName();
      });

      List<String> collect1 = stringStream.limit(3).collect(toList());
  }

}
