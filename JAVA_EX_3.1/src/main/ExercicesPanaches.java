
package main;

import domaine.Trader;
import domaine.Transaction;

import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ExercicesPanaches {
    public static void main(String[] args) {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );

        ExercicesPanaches main = new ExercicesPanaches(transactions);
        main.run();
    }

    private final List<Transaction> transactions;


    public ExercicesPanaches(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void run() {
        // Complete the methods below based on the exercise descriptions
        exercice1();
        exercice2();
        exercice3();
        exercice4();
        exercice5();
        exercice6();
    }

    private void exercice1() {
        // TODO: Filter transactions of Cambridge, map to their values, and find max.
        int t = transactions.stream()
                .filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
                .map(Transaction::getValue)
                .reduce(Integer.MIN_VALUE, Integer::max);
        System.out.println(t);

    }

    private void exercice2() {
        // TODO: Filter transactions for traders in Cambridge, group by trader, and count their transactions.

        Map<Trader,Long> count = transactions.stream()
                .filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
                .collect(Collectors.groupingBy(Transaction::getTrader,Collectors.counting()));

        System.out.println(count);
    }

    private void exercice3() {
        // TODO: Filter transactions over 500, map trader names, sort by name length, find the longest.

        transactions.stream()
                .filter(transaction -> transaction.getValue() > 500)
                .map(transaction -> transaction.getTrader().getName())
                .sorted(Comparator.comparingInt(String::length))
                .reduce((s1, s2) -> s1.length() > s2.length() ? s1 : s2)
                .ifPresentOrElse(System.out::println,() -> {
                    System.out.println("Not found");
                });
    }

    private void exercice4() {
        // TODO: Group transactions by city, map to transaction values, and compute the average.
       Map<String,Double> l = transactions.stream()
               .collect(Collectors.groupingBy(transaction -> transaction.getTrader().getCity(),
                       Collectors.averagingInt(Transaction::getValue)));

        System.out.println(l);
    }

    private void exercice5() {
        // TODO: Filter transactions in Milan, map to values, find the min, and handle empty results.
        transactions.stream()
                .filter(transaction -> transaction.getTrader().getCity().equals("Milan"))
                .mapToInt(Transaction::getValue)
                .min()
                .ifPresentOrElse(System.out::println,() -> {
                    System.out.println("Not present");
                });
        Supplier<List<String>> genTest = () -> new ArrayList<>();

    }
    private void exercice6() {
        // TODO: group transaction by year
        var t = transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getYear));

        System.out.println(t);
    }
}
