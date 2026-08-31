package functional.programming;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * STREAMS PRACTICE — SET 2
 * Domain: an online store's orders
 *
 * All problems use only what you've covered: forEach, map, mapToInt,
 * filter, reduce, distinct, sorted, limit, skip, collect.
 *
 * No Optional, no allMatch/anyMatch, no groupingBy — those come later.
 *
 * Rules:
 * - One stream pipeline per method
 * - No for loops
 * - Read the spec twice before you write
 */
public class OrdersPractice {

    record Order(String orderId, String customer, String product,
                 String category, int quantity, double unitPrice, String status) {

        double total() {
            return quantity * unitPrice;
        }
    }

    static List<Order> orders = List.of(
            new Order("ORD-001", "Ravi",   "Laptop",       "Electronics", 1, 74999.00, "DELIVERED"),
            new Order("ORD-002", "Priya",  "Headphones",   "Electronics", 2,  2499.00, "SHIPPED"),
            new Order("ORD-003", "Ravi",   "Coffee Beans", "Grocery",     3,   899.00, "DELIVERED"),
            new Order("ORD-004", "Anil",   "Monitor",      "Electronics", 2, 18999.00, "CANCELLED"),
            new Order("ORD-005", "Priya",  "Yoga Mat",     "Fitness",     1,  1799.00, "DELIVERED"),
            new Order("ORD-006", "Sneha",  "Protein Powder","Fitness",    2,  3299.00, "SHIPPED"),
            new Order("ORD-007", "Anil",   "Keyboard",     "Electronics", 1,  4999.00, "DELIVERED"),
            new Order("ORD-008", "Ravi",   "Dumbbells",    "Fitness",     4,  1250.00, "PENDING"),
            new Order("ORD-009", "Sneha",  "Olive Oil",    "Grocery",     2,   749.00, "DELIVERED"),
            new Order("ORD-010", "Priya",  "Laptop",       "Electronics", 1, 74999.00, "CANCELLED"),
            new Order("ORD-011", "Vikram", "Rice 10kg",    "Grocery",     1,  1150.00, "DELIVERED"),
            new Order("ORD-012", "Anil",   "Headphones",   "Electronics", 1,  2499.00, "SHIPPED")
    );

    public static void main(String[] args) {
         System.out.println("1: " + getAllProductNames());
         System.out.println("2: " + getDeliveredOrders());
         System.out.println("3: " + getDistinctCustomers());
         System.out.println("4: " + getTotalRevenue());
         System.out.println("5: " + getTotalItemsSold());
         System.out.println("6: " + getElectronicsOrdersSortedByTotalDesc());
         System.out.println("7: " + getCustomersWhoOrderedFitness());
         System.out.println("8: " + getTopThreeOrdersByValue());
        // System.out.println("9: " + getOrderSummaryLines());
         System.out.println("10: " + getAverageOrderValue());
         System.out.println("11: " + getOrdersSortedByCategoryThenTotalDesc());
         System.out.println("12: " + getOrdersRankedFourthToSixth());
        System.out.println("13: " + getSortedNamesQuantityMorethanOne());
        System.out.println("14: " + getTotalQuantityInGrocery());
        System.out.println("15: " + getDistinctCategory());
      //  System.out.println("16: " + getOrderIdMoreThanFivek());




    }

//    private static List<String> getOrderIdMoreThanFivek() {
//
//        return orders.stream()
//                .filter(ord -> ord.unitPrice()>5000)
//                .map(Order::total)
//                .sorted(Comparator.naturalOrder())
//                .map(Order::orderId)
////                .map(Order::orderId)
//                .collect(Collectors.toList());
//
//
//
//    }

    private static List<String> getDistinctCategory() {
        return
                orders.stream()
                        .map(Order::category)
                        .distinct()
                        .sorted(Comparator.reverseOrder())
                        .collect(Collectors.toList());
    }

    private static int getTotalQuantityInGrocery() {
        return orders.stream()
                .filter(ord -> ord.category.equals("Grocery"))
                .map(Order::quantity)
                .reduce(0,Integer::sum);
    }

    private static List<String> getSortedNamesQuantityMorethanOne() {
       return orders.stream()
                .filter(ord -> ord.quantity>1)
                .map(Order::customer)
                .sorted()
                .collect(Collectors.toList());
    }



    /**
     * 1 — Return a List<String> of every product name, in original order.
     *     Duplicates stay in.
     * Expected: 12 entries, starting [Laptop, Headphones, Coffee Beans, ...]
     */
    static List<String> getAllProductNames() {

        return orders.stream()
                .map(Order::product)
                .collect(Collectors.toList());
    }

    /**
     * 2 — Return a List<Order> of orders whose status is "DELIVERED".
     * Expected: 6 orders — ORD-001, 003, 005, 007, 009, 011
     */
    static List<Order> getDeliveredOrders() {
        return orders.stream()
                .filter(ord -> ord.status().equalsIgnoreCase("Delivered"))
                .collect(Collectors.toList());

    }

    /**
     * 3 — Return a List<String> of distinct customer names, sorted A-Z.
     * Expected: [Anil, Priya, Ravi, Sneha, Vikram]
     */
    static List<String> getDistinctCustomers() {

        return orders.stream()
                .map(Order::customer)
                .distinct()
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());


    }

    /**
     * 4 — Return total revenue as a double: the sum of every order's total(),
     *     EXCLUDING cancelled orders.
     *     Note: Order has a total() method — use it.
     * Expected: 111986.0
     */
    static double getTotalRevenue() {

        return orders.stream()
                .filter(ord -> !ord.status().equals("CANCELLED"))
                .map(Order::total)
                .reduce(0.00, Double::sum);

    }

    /**
     * 5 — Return the total number of individual items sold across ALL orders
     *     (sum of quantity), including cancelled ones.
     * Expected: 21
     */
    static int getTotalItemsSold() {

        return orders.stream()
                .map(Order::quantity)
                .reduce(0,Integer::sum);
    }

    /**
     * 6 — Return a List<Order> of Electronics orders only, sorted by
     *     order total DESCENDING.
     * Expected first: ORD-001 (74999.0), then ORD-010 (74999.0), then ORD-004 (37998.0)
     */
    static List<Order> getElectronicsOrdersSortedByTotalDesc() {

        return orders.stream()
                .filter(ord -> ord.category.equals("Electronics"))
                .sorted(Comparator.comparing(Order::orderId).reversed())
                .collect(Collectors.toList());
    }

    /**
     * 7 — Return a List<String> of distinct customer names who ordered
     *     anything in the "Fitness" category, sorted A-Z.
     * Expected: [Priya, Ravi, Sneha]
     */
    static List<String> getCustomersWhoOrderedFitness() {

        return orders.stream()
                .filter(ord -> ord.category().equals("Fitness"))
                .map(Order::customer)
                .distinct()
                .sorted()
                .collect(Collectors.toList());

    }

    /**
     * 8 — Return a List<String> of the orderIds of the 3 highest-value orders,
     *     highest first. Cancelled orders count here.
     * Expected: [ORD-001, ORD-010, ORD-004]
     */
    static List<String> getTopThreeOrdersByValue() {

       return  orders.stream()
          //      .map(Order::total)
                .sorted(Comparator.comparing(Order::total).reversed())
                .map(Order::orderId)
               .limit(3)
                .collect(Collectors.toList());



    }

    /**
     * 9 — Return a List<String> where each entry reads:
     *     "ORD-001 | Ravi | Laptop x1 | Rs.74999.0"
     *     for every order, in original order.
     *     Format exactly: orderId, space, pipe, space, customer, space, pipe,
     *     space, product, space, x, quantity, space, pipe, space, "Rs.", total
     * Expected: 12 entries
     */
//    static List<String> getOrderSummaryLines() {
//
//        return orders.stream()
//                .sorted(Comparator.comparing(Order::orderId))
//                .collect(Collectors.toList());
//
//    }

    /**
     * 10 — Return the average order value (using total()) across all orders
     *      as a double.
     *      Hint: mapToDouble(...).average().orElse(0)
     * Expected: 15665.5
     */
    static double getAverageOrderValue() {

        return orders.stream()
                .mapToDouble(Order::total)
                .average().orElse(0);


    }

    /**
     * 11 — Return a List<Order> sorted by category name A-Z, and within each
     *      category by order total DESCENDING.
     * Expected: Electronics first (highest total first), then Fitness, then Grocery
     */
    static List<Order> getOrdersSortedByCategoryThenTotalDesc() {
        return orders.stream()
                .sorted(Comparator.comparing(Order::category)
                        .thenComparing(Comparator.comparingDouble(Order::total).reversed()))
                .collect(Collectors.toList());
    }
    /**
     * 12 — Return a List<String> of the orderIds ranked 4th, 5th and 6th
     *      by order total (highest first). So skip the top 3, then take 3.
     *      Hint: skip() and limit()
     * Expected: [ORD-006, ORD-008, ORD-007]
     */
    static List<String> getOrdersRankedFourthToSixth() {

        return orders.stream()
                .sorted(Comparator.comparing(Order::total).reversed())
                .skip(3)
                .limit(3)
                .map(Order::orderId)
                .collect(Collectors.toList());



    }
}