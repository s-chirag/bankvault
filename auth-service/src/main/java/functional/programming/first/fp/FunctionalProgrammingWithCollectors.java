package functional.programming.first.fp;

import java.util.List;
import java.util.stream.Collectors;

public class FunctionalProgrammingWithCollectors {

    public static void main(String[] args) {


        List<Integer> numbers = List.of(12,9,3,4,5,634,5564,3,2,43,23,232,2345,34);
        List<String> courses =List.of("Spring", "Spring boot", "API","Microservices", "AWS", "PCF", "Azure", "Docker", "Kubernates", "Bart");

        List<Integer> evenNumbers = evenNumbersList(numbers);
        evenNumbers.stream().forEach(System.out::println);

        System.out.println("This is couse length::");
        List<Integer> courseLength = courseLength(courses);
        courseLength.stream().forEach(System.out::println);
    }

    private static List<Integer> courseLength(List<String> courses) {
        return courses.stream().map(course -> course.length())
                .collect(Collectors.toList());
    }

    private static List<Integer> evenNumbersList(List<Integer> numbers) {

        return numbers.stream()
                .filter(num -> num%2==0)
                .collect(Collectors.toList());
    }
}
