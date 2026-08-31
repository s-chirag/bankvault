package functional.programming.first.fp;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class FunctionalProgramming {

    public static void main(String[] args) {

        List<Integer> arrayNumberList = List.of(1,23,45,65,23,2345,342,23,45,64,72,80);
        List<String> courses =List.of("Spring", "Spring boot", "API","Microservices", "AWS", "PCF", "Azure", "Docker", "Kubernates", "Bart");


    //    printAllCourses(courses);
     //   printCoursesContainingWordSpring(courses);
    //    printCoursesWhosNamesHasAtleastFourLetters(courses);
    //    printCubesofAllNumbers(arrayNumberList);
    //    printNumberOfCharactersinEachCourse(courses);
        System.out.println(printSumOfTheList(arrayNumberList));

    }

    private static int printSumOfTheList(List<Integer> arrayNumberList) {

        return arrayNumberList.stream()
                .reduce(0, Integer::sum);
    //    return summer;x
    }

    private static void printNumberOfCharactersinEachCourse(List<String> courses) {

        System.out.println("Print number of characters in each courses");
//        courses.stream()
//                .forEach(s -> System.out.println(s.length()));

        courses.stream()
                .map(course -> course + "::" + course.length())
                .forEach(System.out::println);

    }

    private static void printCubesofAllNumbers(List<Integer> arrayNumberList) {

        System.out.println("The list of odd numbers withtheir cubes ");
        arrayNumberList.stream()
                .filter(nums -> nums%2==1)
                .map(nums -> nums*nums*nums)
                .forEach(System.out::println);
    }


    private static void printCoursesWhosNamesHasAtleastFourLetters(List<String> courses) {
        System.out.println("This is list of all courses containing atleast 4 letters:: ");
        courses.stream()
                .filter(course -> course.length()>3)
                .forEach(System.out::println);

    }

    private static void printAllCourses(List<String> courses) {
        System.out.println("This is list of all courses>");
        courses.stream().forEach(System.out::println);

    }

    private static void printCoursesContainingWordSpring(List<String> courses) {
        System.out.println("This is list of all courses containing the word spring:: ");
        courses.stream()
                .filter(course -> course.contains("Spring"))
                .forEach(System.out::println);

    }

    private static void printALlNumbersLambda(List<Integer> integers) {

        integers.stream()
                .filter(number -> number%2==0)
                .forEach(System.out::print);
    }


    private static void printOnlyOddNumbers(List<Integer> integers) {

        integers.stream()
                .filter(number -> number%2!=0)
                .forEach(System.out::println);
    }






}
