package functional.programming.first;

import java.util.Random;
import java.util.function.*;

public class InternalsOFStreams {

    public static void main(String[] args) {


        Predicate<Integer> predicate = integer -> integer % 2 == 0;
        // predicate is for filters and returns booleam
        System.out.println("Thsi is predicate >>"+predicate.test(50));

        Function<Integer, String> func = num -> num*num + " yess";
        // function does mathematical operations like likw what maps do it converts the data

        System.out.println("This is function "+ func.apply(10));

        // consumer it accepts a single arguement and returns no result. Just like for each it will just fo for loop wiht the data

        Consumer<Integer> consumer = x -> System.out.println("thsi is consumer output >>"+x);
        consumer.accept(44);
        //consumer accepts one date and returns nothing just  like forEach


        BinaryOperator<Integer> binaryOperator = (x,y) -> x*y;
        //binary operator accepts 2 parameters and converts into 1
        System.out.println("The output of binary operator >>" +binaryOperator.apply(30,23));

        Supplier<Integer> supp = () -> {
            Random inte = new Random();
            return inte.nextInt(5000);
        };

        System.out.println("The output of Supplier >>" +supp.get());


        UnaryOperator<Integer> unaryOperator = x -> x*3;
        System.out.println("This is unary operation >>" + unaryOperator.apply(10));

    }

}
