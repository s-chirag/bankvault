package functional.programming;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.*;
/**
 * STREAMS PRACTICE — forEach, map, filter, reduce, distinct, sorted, collect
 * Using objects (Employee) — basic → intermediate → hard
 *
 * Rules:
 * - Solve each method below using a single stream pipeline
 * - No for loops
 * - Paste your solutions and I will review them one by one
 * - Do NOT look at solutions online — paste your attempt even if incomplete
 */
public class StreamsPractice {

    record Employee(String name, String department, int salary, int age, String city) {}

    static List<Employee> employees = List.of(
            new Employee("Alice",   "Engineering",  95000, 30, "Bangalore"),
            new Employee("Bob",     "Marketing",    55000, 45, "Mumbai"),
            new Employee("Carol",   "Engineering",  85000, 28, "Bangalore"),
            new Employee("Dave",    "HR",           48000, 35, "Delhi"),
            new Employee("Eve",     "Engineering",  110000, 32, "Hyderabad"),
            new Employee("Frank",   "Marketing",    62000, 29, "Mumbai"),
            new Employee("Grace",   "Engineering",  95000, 27, "Bangalore"),
            new Employee("Heidi",   "HR",           52000, 40, "Delhi"),
            new Employee("Ivan",    "Marketing",    70000, 33, "Bangalore"),
            new Employee("Judy",    "Engineering",  120000, 36, "Hyderabad"),
            new Employee("Alice",   "HR",           48000, 31, "Mumbai"),   // duplicate name
            new Employee("Bob",     "Engineering",  90000, 26, "Hyderabad") // duplicate name
    );

    public static void main(String[] args) {
        // Uncomment each line as you solve the method

         System.out.println("1: " + getNamesOfAllEmployees());
         System.out.println("2: " + getEmployeesInEngineering());
         System.out.println("3: " + getTotalSalaryBill());
         System.out.println("4: " + getUniqueEmployeeNames());
         System.out.println("5: " + getEmployeesSortedBySalaryDesc());
         System.out.println("6: " + getHighSalaryEmployeeNames());
         System.out.println("7: " + getTotalSalaryOfEngineering());
         System.out.println("8: " + getAverageSalary());
         System.out.println("9: " + getNameAndSalaryStrings());
         System.out.println("10: " + getEmployeesSortedByDeptThenSalaryDesc());
  //       System.out.println("11: " + getHighestPaidEmployee());
         System.out.println("12: " + getDistinctCities());
//        System.out.println("13a: " + areAllWellPaid());
        System.out.println("13b: " + hasAnyoneOverForty());
        System.out.println("15: " + salarySUmOfbangalore());

    }

    private static int salarySUmOfbangalore() {

        return employees.stream()
                .filter(Emp -> Emp.city.equals("Bangalore"))
                .map(Employee::salary)
                .reduce(0,Integer::sum);
    }


    /**
     * Return true if EVERY employee earns more than 40000.
     * Look at the data — the lowest salaries are 48000 (Dave, Alice),
     * so the answer should be true.
     */
//    private static boolean areAllWellPaid() {
//
//        employees.stream()
//                .map(Employee::salary)
//                .allMatch(employee -> employee.sax >40000);
//
//        return false;
//    }

    /**
     * Return true if AT LEAST ONE employee is older than 40.
     * Bob is 45 and Heidi is 40 — so the answer should be true.
     */
    private static boolean hasAnyoneOverForty() {
        // use anyMatch
        return false;
    }






    private static List<String> getDistinctCities() {

        return employees.stream()
                .map(Employee::city)
                .distinct()
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());
    }

//    private static String getHighestPaidEmployee() {
//
//        employees.stream()
//                .map(employee -> employee.salary)
//                .reduce(0,Integer::max);
//    }
//    private static String getHighestPaidEmployee() {
//
//        employees.stream().max(Comparator.comparing(Employee::salary));
//
//    }

    // ─────────────────────────────────────────────────────────────────
    // BASIC (map, filter, forEach, reduce)
    // ─────────────────────────────────────────────────────────────────

    /**
     * Problem 1 — BASIC
     * Return a List<String> of the names of ALL employees.
     * Expected: [Alice, Bob, Carol, Dave, Eve, Frank, Grace, Heidi, Ivan, Judy, Alice, Bob]
     */
    static List<String> getNamesOfAllEmployees() {
        // your code here
       return employees.stream()
                .map(Employee::name)
                .collect(Collectors.toList());
    }

    /**
     * Problem 2 — BASIC
     * Return a List<Employee> of all employees in the "Engineering" department.
     * Expected: Alice, Carol, Eve, Grace, Judy, Bob (the second one)
     */
    static List<Employee> getEmployeesInEngineering() {
        // your code here

        return employees.stream()
                .filter(emp -> emp.department().equals("Engineering"))
                .collect(Collectors.toList());
    }

    /**
     * Problem 3 — BASIC
     * Return the total salary bill (sum of all salaries) as an int.
     * Hint: use reduce or mapToInt + sum
     * Expected: 930000
     */
    static int getTotalSalaryBill() {
        // your code here

        return employees.stream()
                .map(Employee::salary)
                .reduce(0, Integer::sum);
    }

    // ─────────────────────────────────────────────────────────────────
    // INTERMEDIATE (distinct, sorted, collect, multi-step pipelines)
    // ─────────────────────────────────────────────────────────────────

    /**
     * Problem 4 — INTERMEDIATE
     * Return a List<String> of UNIQUE employee names, in alphabetical order.
     * The list has two "Alice" and two "Bob" — deduplicate, then sort.
     * Expected: [Alice, Bob, Carol, Dave, Eve, Frank, Grace, Heidi, Ivan, Judy]
     */

    static List<String> getUniqueEmployeeNames() {

        return employees.stream().
                map(Employee::name)
                .distinct().sorted().collect(Collectors.toList());

        // your code here
    }

    /**
     * Problem 5 — INTERMEDIATE
     * Return a List<Employee> of ALL employees sorted by salary descending
     * (highest first).
     * Hint: sorted() with a Comparator
     * Expected first: Judy (120000), Eve (110000), Alice (95000)...
     */
    static List<Employee> getEmployeesSortedBySalaryDesc() {

        return employees.stream()
                .sorted(Comparator.comparingInt(Employee::salary).reversed())
                .collect(Collectors.toList());
    }

    /**
     * Problem 6 — INTERMEDIATE
     * Return a List<String> of names of employees earning MORE than 80000,
     * sorted by name A-Z.
     * Expected: [Alice, Bob, Carol, Eve, Grace, Ivan, Judy]
     */
    static List<String> getHighSalaryEmployeeNames() {
        // your code here
        return employees.stream()
                .filter(emp -> emp.salary()>80000)
                .map(Employee::name)
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());
    }

    /**
     * Problem 7 — INTERMEDIATE
     * Return the total salary of all Engineering employees only.
     * Expected: 595000
     */
    static int getTotalSalaryOfEngineering() {

        return employees.stream()
                .filter(employee -> employee.department.equals("Engineering"))
                .map(employee -> employee.salary)
                .reduce(0, Integer::sum);
    }

    // ─────────────────────────────────────────────────────────────────
    // HARDER (chaining multiple ops, transformation + aggregation)
    // ─────────────────────────────────────────────────────────────────

    /**
     * Problem 8 — HARDER
     * Return the average salary across ALL employees as a double.
     * Hint: mapToInt(...).average() returns an OptionalDouble.
     *       Call .orElse(0) on it to get a plain double.
     * Expected: 77500.0
     */
    static double getAverageSalary() {

        return employees.stream()
                .mapToInt(Employee::salary).average().orElse(0);

    }

    /**
     * Problem 9 — HARDER
     * Return a List<String> where each entry is:
     *   "Name -> $salary"
     * for every employee, in the original order.
     * Example entry: "Alice -> $95000"
     * Expected: 12 entries, one per employee
     */
    static List<String> getNameAndSalaryStrings() {

        return employees.stream()
                .map(emp -> emp.name() +" -> $"+ emp.salary())
                .collect(Collectors.toList());

    }

    /**
     * Problem 10 — HARDER
     * Return a List<Employee> sorted first by department name A-Z,
     * then within each department by salary descending.
     * Hint: Comparator.comparing(...).thenComparing(...)
     * Expected first entries: Engineering dept (highest salary first), then HR, then Marketing
     */
    static List<Employee> getEmployeesSortedByDeptThenSalaryDesc() {

        return employees.stream()
                .sorted(Comparator.comparing(Employee::department).thenComparing(Employee::salary).reversed())
                .collect(Collectors.toList());


    }
}