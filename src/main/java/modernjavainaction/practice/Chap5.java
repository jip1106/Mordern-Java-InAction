package modernjavainaction.practice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import modernjavainaction.chap04.Dish;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static modernjavainaction.chap04.Dish.menu;

public class Chap5 {

    public static void main(String[] args) {
        // 5-1. 스트림을 이용해서 처음 등장하는 두 고기 요리를 필터링 하시오
        System.out.println("5-1.");
        List<Dish> dishes = menu.stream()
                .filter(d -> d.getType() == Dish.Type.MEAT)
                .limit(2)
                .collect(Collectors.toList());

        System.out.println("dishes = " + dishes);
        
        
        
        //5-2_1 숫자 리스트가 주어졌을 때 각 숫자의 제곱근으로 이루어진 리스트를 반환하시오.
        System.out.println("\n5-2_1.");
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);

        List<Integer> rtnList = list.stream().map(i -> i * i)
                .collect(Collectors.toList());

        System.out.println("rtnList = " + rtnList);

        //5-2_2 두개의 숫자 리스트가 있을 때 모든 숫자 쌍의 리스트를 반환하시오.
        //예를 들어 두개의 리스트 [1,2,3] [3,4] 가 주어지면
        //[(1,3) , (1,4) , (2,3) , (2,4), (3,3) , (3,4)] 를 반환해야 한다.
        System.out.println("\n5-2_1.");

        List<Integer> list1 = Arrays.asList(1, 2, 3);
        List<Integer> list2 = Arrays.asList(3, 4);


        List<int[]> rtnList2 = list1.stream().flatMap(i -> list2.stream()
                        .map(j -> new int[]{i, j}))
                        .collect(Collectors.toList());

        for (int[] ints : rtnList2) {
            for (int anInt : ints) {
                System.out.print(anInt + " ");
            }
            System.out.println();
        }

        //5-2_2 두개의 숫자 리스트가 있을 때 모든 숫자 쌍의 리스트 중 숫자 쌍의 합이 3으로 나누어 떨어지는 쌍만 반환 하시오
        List<int[]> rtnList3 = list1.stream()
                .flatMap(i -> list2.stream()
                        .filter(j -> (i + j) % 3 == 0)
                        .map(j -> new int[]{i, j}))
                .collect(Collectors.toList());

        System.out.println("\n5-3.");
        //5-3 리듀스
        //map과 reduce 메서드를 이용해서 스트림의 요리 개수를 계산하시오
        int size = menu.stream()
                .map(d -> 1)
                .reduce(0, (a,b) -> a+b);

        System.out.println(size);


        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 100),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );

        //5-6장 실전연습
        //1. 2011년에 일어난 모든 트랜잭션을 찾아 값을 오름차순으로 정리하시오.
        List<Transaction> qList1 = transactions.stream()
                .filter(y -> y.getYear() == 2011)
                .sorted(Comparator.comparing(Transaction :: getValue))      //트랜잭션 값으로 요소 정렬
                .collect(Collectors.toList());
        System.out.println("1====================");
        System.out.println(qList1);

        //2. 거래자가 근무하는 모든 도시를 중복 없이 나열하시오.
        List<String> qList2 = transactions.stream()
                    .map(t -> t.trader.getCity()).distinct()
                    .collect(Collectors.toList());

        qList2 = transactions.stream()
                        .map(t -> t.getTrader().getCity())
                        .distinct().collect(Collectors.toList());
        System.out.println("2====================");
        System.out.println(qList2);


        //3. 케임브리지(Cambridge)에서 근무하는 모든 거래자를 찾아서 이름순으로 정렬하시오.

        List<Trader> qList3 = transactions.stream()
                .map(Transaction::getTrader)
                .filter(t -> t.getCity().equals("Cambridge"))
                .distinct()
                .sorted(Comparator.comparing(Trader::getName))
                .collect(Collectors.toList());

        System.out.println("3====================");
        System.out.println("qList3 = " + qList3);



        //4. 모든 거래자의 이름을 알파벳 순으로 정렬해서 반환하시오.
        String qList4 = transactions.stream()
                        .map(transaction -> transaction.getTrader().getName())
                                .distinct()
                                        .sorted()
                                                .reduce("",(n1,n2) -> n1+n2);
        System.out.println("4====================");
        System.out.println(qList4);

        System.out.println("5===================");
        //5. 밀라노에 거래자가 있는가?
        boolean isMilan = transactions.stream()
                .anyMatch(t -> t.getTrader().getCity().equals("Milan"));
        System.out.println(isMilan);

        System.out.println("6===================");
        //6. 케임브리지에 거주하는 거래자의 모든 트랜잭션값을 출력하시오.
        transactions.stream()
                .filter(t -> t.getTrader().getCity().equals("Cambridge"))
                .map(Transaction::getValue)
                .forEach(System.out::println);


        System.out.println("7===================");
        //7. 전체 트랜잭션 중 최댓값은 얼마인가?
        Optional<Integer> max = transactions.stream()
                .map(Transaction::getValue)
                .reduce(Integer::max);

        //8. 전체 트랜잭션 중 최솟값은 얼마인가?
        Optional<Integer> min = transactions.stream()
                .map(Transaction::getValue)
                .reduce(Integer::min);

        System.out.println(max);
        System.out.println(min);

    }


    @Getter
    @AllArgsConstructor
    @ToString
    static class Trader{
        private final String name;
        private final String city;

    }

    @Getter
    @AllArgsConstructor
    @ToString
    static class Transaction{
        private final Trader trader;
        private final int year;
        private final int value;

    }





}
