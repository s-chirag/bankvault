 package functional.programming;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * COLLECTORS PRACTICE — flatMap, joining, groupingBy, partitioningBy
 * Domain: restaurants and their menus (nested data — that's the point)
 *
 * ─────────────────────────────────────────────────────────────────
 * QUICK REFERENCE
 *
 * flatMap — your lambda must return a STREAM. flatMap merges all those
 *           streams into one, removing a level of nesting.
 *
 *     restaurants.stream().map(r -> r.menu().stream())      // Stream<Stream<MenuItem>>
 *     restaurants.stream().flatMap(r -> r.menu().stream())  // Stream<MenuItem>
 *
 *     map     = one output per input
 *     flatMap = many outputs per input, all merged
 *
 * joining — only works on a Stream<String>. Three forms:
 *
 *     Collectors.joining()                    "abc"
 *     Collectors.joining(", ")                "a, b, c"
 *     Collectors.joining(", ", "[", "]")      "[a, b, c]"
 *                          ^delim  ^prefix ^suffix
 *
 * groupingBy — splits into a Map keyed by whatever your function returns.
 *
 *     Collectors.groupingBy(MenuItem::category)
 *         -> Map<String, List<MenuItem>>
 *
 *     With a DOWNSTREAM collector, you control what each group becomes:
 *
 *     Collectors.groupingBy(MenuItem::category, Collectors.counting())
 *         -> Map<String, Long>
 *     Collectors.groupingBy(MenuItem::category, Collectors.averagingDouble(MenuItem::price))
 *         -> Map<String, Double>
 *     Collectors.groupingBy(MenuItem::category, Collectors.mapping(MenuItem::name, Collectors.toList()))
 *         -> Map<String, List<String>>
 *
 * partitioningBy — groupingBy with a Predicate. ALWAYS exactly two keys,
 *                  true and false, even when one side is empty.
 *
 *     Collectors.partitioningBy(MenuItem::vegetarian)
 *         -> Map<Boolean, List<MenuItem>>
 *
 * Note: groupingBy returns a HashMap, so key order is not guaranteed.
 * Don't worry if your Map prints in a different order to the expected output.
 * ─────────────────────────────────────────────────────────────────
 */
public class MenuPractice {

    record MenuItem(String name, String category, double price,
                    boolean vegetarian, int prepMinutes) {}

    record Restaurant(String name, String area, double rating, List<MenuItem> menu) {}

    static List<Restaurant> restaurants = List.of(
            new Restaurant("Tea Trails", "Indiranagar", 4.3, List.of(
                    new MenuItem("Masala Chai",   "Beverages",  60, true,  5),
                    new MenuItem("Filter Coffee", "Beverages",  50, true,  5),
                    new MenuItem("Veg Puff",      "Snacks",     40, true,  3)
            )),
            new Restaurant("Brew Room", "Koramangala", 4.6, List.of(
                    new MenuItem("Cappuccino",        "Beverages", 180, true,   7),
                    new MenuItem("Chicken Sandwich",  "Snacks",    220, false, 12),
                    new MenuItem("Brownie",           "Desserts",  150, true,   2)
            )),
            new Restaurant("Spice Corner", "Indiranagar", 4.1, List.of(
                    new MenuItem("Chicken Biryani",        "Mains",     320, false, 25),
                    new MenuItem("Paneer Butter Masala",   "Mains",     280, true,  20),
                    new MenuItem("Gulab Jamun",            "Desserts",   90, true,   2),
                    new MenuItem("Masala Chai",            "Beverages",  40, true,   5)
            )),
            new Restaurant("Green Bowl", "HSR Layout", 4.4, List.of(
                    new MenuItem("Quinoa Salad",    "Mains",     260, true,  10),
                    new MenuItem("Grilled Chicken", "Mains",     340, false, 18),
                    new MenuItem("Fresh Lime",      "Beverages",  70, true,   3)
            ))
    );

    public static void main(String[] args) {
        System.out.println("F1: " + getAllItemNames());
        System.out.println("F2: " + getDistinctItemNamesSorted());
        System.out.println("F3: " + getVegetarianItemNamesSorted());
        System.out.println("F4: " + getItemsUnder100());
        System.out.println();
        System.out.println("J1: " + getRestaurantNamesCsv());
        System.out.println("J2: " + getBeverageNamesBracketed());
        System.out.println();
        System.out.println("G1: " + getRestaurantNamesByArea());
        System.out.println("G2: " + getItemCountByCategory());
        System.out.println("G3: " + getItemNamesByCategory());
        System.out.println("G4: " + getAveragePriceByCategory());
        System.out.println();
        System.out.println("P1: " + getItemNamesByVegStatus());
        System.out.println("P2: " + getRestaurantsByRating());
    }

    // ══════════════════ flatMap ══════════════════

    /**
     * F1 — Return the names of EVERY menu item across ALL restaurants,
     *      in order. Duplicates stay in.
     * Expected: 13 names, starting [Masala Chai, Filter Coffee, Veg Puff, Cappuccino, ...]
     */
    static List<String> getAllItemNames() {

        return restaurants.stream()
                .map(Restaurant::menu)
                .flatMap(menuItems -> menuItems.stream().map(m -> m.name()))
                .collect(Collectors.toList());

    }

    /**
     * F2 — Return DISTINCT item names across all restaurants, sorted A-Z.
     *      "Masala Chai" appears on two menus.
     * Expected: 12 names
     */
    static List<String> getDistinctItemNamesSorted() {

        return restaurants.stream()

          //      .sorted(Comparator.comparing(Restaurant::name))
                .flatMap(r -> r.menu().stream())
                .map(MenuItem::name)
                .distinct()
                .collect(Collectors.toList());


    }

    /**
     * F3 — Return the names of all VEGETARIAN items across all restaurants,
     *      sorted A-Z. Duplicates stay in.
     * Expected: 10 names
     */

    static Predicate<MenuItem> isVeg = menu -> menu.vegetarian==true;
    static List<String> getVegetarianItemNamesSorted() {

        return restaurants.stream()
                .flatMap(r -> r.menu().stream())   // Stream<MenuItem>
                .filter(isVeg)                      // still Stream<MenuItem>
                .map(MenuItem::name)                // now Stream<String>
                .sorted()
                .toList();
    }

    /**
     * F4 — Return the names of all items priced UNDER 100, sorted by price
     *      ASCENDING.
     * Expected: [Masala Chai, Veg Puff, Filter Coffee, Masala Chai, Fresh Lime, Gulab Jamun]
     */
    static List<String> getItemsUnder100() {

        return restaurants.stream()
                .flatMap(r -> r.menu().stream())
            //    .map(MenuItem::price)
                .filter(m ->m.price<100)
                .sorted(Comparator.comparing(MenuItem::price))
                .map(MenuItem::name)
                .collect(Collectors.toList());
    }

    // ══════════════════ joining ══════════════════

    /**
     * J1 — Return all restaurant names as a single comma-separated string.
     * Expected: Tea Trails, Brew Room, Spice Corner, Green Bowl
     */
    static String getRestaurantNamesCsv() {

        return restaurants.stream()
                .map(Restaurant::name)
                .collect(Collectors.joining(","));

    }

    /**
     * J2 — Return the names of all items in the "Beverages" category as a
     *      single string, comma-separated, wrapped in square brackets.
     * Expected: [Masala Chai, Filter Coffee, Cappuccino, Masala Chai, Fresh Lime]
     */
    static String getBeverageNamesBracketed() {

        return restaurants.stream()
                .flatMap(c -> c.menu().stream())
                .filter( m -> m.category().equals("Beverages"))
                .map(MenuItem::name)
                .collect(Collectors.joining(",","[", "]"));

    }

    // ══════════════════ groupingBy ══════════════════

    /**
     * G1 — Group restaurant NAMES by area.
     *      Hint: groupingBy with a mapping(...) downstream collector.
     * Expected: {HSR Layout=[Green Bowl], Indiranagar=[Tea Trails, Spice Corner],
     *            Koramangala=[Brew Room]}
     */
    static Map<String, List<String>> getRestaurantNamesByArea() {

        return restaurants.stream()
                .collect(Collectors.groupingBy(Restaurant::area, Collectors.mapping(Restaurant::name, Collectors.toList())));
    }

    /**
     * G2 — Count how many menu items exist in each category, across all
     *      restaurants.
     *      Hint: flatMap first, then groupingBy with counting().
     * Expected: {Beverages=5, Desserts=2, Mains=4, Snacks=2}
     */
    static Map<String, Long> getItemCountByCategory() {

        return restaurants.stream()
                .flatMap(r -> r.menu().stream())
                .collect(Collectors.groupingBy(MenuItem::category, Collectors.counting()));


    }

    /**
     * G3 — Group item NAMES by category, across all restaurants.
     * Expected: {Beverages=[Masala Chai, Filter Coffee, Cappuccino, Masala Chai, Fresh Lime],
     *            Desserts=[Brownie, Gulab Jamun],
     *            Mains=[Chicken Biryani, Paneer Butter Masala, Quinoa Salad, Grilled Chicken],
     *            Snacks=[Veg Puff, Chicken Sandwich]}
     */
    static Map<String, List<String>> getItemNamesByCategory() {

        return restaurants.stream()
                .flatMap(r -> r.menu().stream())
                .collect(Collectors.groupingBy(MenuItem::category, Collectors.mapping(MenuItem::name, Collectors.toList())));


    }

    /**
     * G4 — Return the average price of items in each category.
     *      Hint: averagingDouble(...) as the downstream collector.
     * Expected: {Beverages=80.0, Desserts=120.0, Mains=300.0, Snacks=130.0}
     */
    static Map<String, Double> getAveragePriceByCategory() {

        return restaurants.stream()
                .flatMap(r -> r.menu().stream())
                .collect(Collectors.groupingBy(MenuItem::category, Collectors.averagingDouble(MenuItem::price)));


    }

    // ══════════════════ partitioningBy ══════════════════

    /**
     * P1 — Partition all menu item NAMES into vegetarian (true) and
     *      non-vegetarian (false).
     * Expected: {false=[Chicken Sandwich, Chicken Biryani, Grilled Chicken],
     *            true=[Masala Chai, Filter Coffee, Veg Puff, Cappuccino, Brownie,
     *                  Paneer Butter Masala, Gulab Jamun, Masala Chai, Quinoa Salad,
     *                  Fresh Lime]}
     */
    static Map<Boolean, List<String>> getItemNamesByVegStatus() {

        return restaurants.stream()
                .flatMap(r -> r.menu().stream())
                .collect(Collectors.partitioningBy(MenuItem::vegetarian, Collectors.mapping(MenuItem::name, Collectors.toList())));


    }

    /**
     * P2 — Partition restaurant NAMES by whether their rating is 4.4 or higher.
     * Expected: {false=[Tea Trails, Spice Corner], true=[Brew Room, Green Bowl]}
     */
    static Predicate<Restaurant> ratingHigher4pont4 = r -> r.rating()>4.4;
    static Map<Boolean, List<String>> getRestaurantsByRating() {

        return restaurants.stream()
                .collect(Collectors.partitioningBy(ratingHigher4pont4, Collectors.mapping(Restaurant::name, Collectors.toList())));



    }
}