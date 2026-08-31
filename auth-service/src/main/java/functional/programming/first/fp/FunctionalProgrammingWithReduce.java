package functional.programming.first.fp;

import java.util.List;

public class FunctionalProgrammingWithReduce {

    public static void main(String[] args) {

        List<Integer> arrayNumberList = List.of(1,23,45,65,23,2345,342,23,45,64,72,80);
        List<String> courses =List.of("Spring", "Spring boot", "API","Microservices", "AWS", "PCF", "Azure", "Docker", "Kubernates", "Bart");

        System.out.println("this is sum of squares " +printSquareEveryNumberAndSum(arrayNumberList));
        System.out.println("this is sum of cubes "+printCubeEveryNumberAndSum(arrayNumberList));
        System.out.println("this is sum of odd numbers  "+printSumOfOddNumbers(arrayNumberList));


    }

    private static int printSumOfOddNumbers(List<Integer> arrayNumberList) {
       return arrayNumberList.stream()
                .filter(num -> num%2==1)
                .reduce(0,Integer::sum);
    }

    private static int printCubeEveryNumberAndSum(List<Integer> arrayNumberList) {
        return arrayNumberList.stream().map(num -> num*num*num)
                .reduce(0, Integer::sum);
    }

    private static int printSquareEveryNumberAndSum(List<Integer> arrayNumberList) {

       return arrayNumberList.stream().map(num -> num*num)
                .reduce(0, Integer::sum);

    }
}
