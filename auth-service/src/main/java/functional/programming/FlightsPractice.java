package functional.programming;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * STREAMS PRACTICE — SET 3  (interview level)
 * Domain: flight search results
 *
 * Everything here is solvable with what you've covered through video 34:
 *   map, mapToInt, mapToDouble, filter, reduce, distinct, sorted,
 *   limit, skip, takeWhile, dropWhile, allMatch, anyMatch, noneMatch,
 *   min, max, findFirst, findAny, count, collect
 *
 * ─────────────────────────────────────────────────────────────────
 * READ THIS BEFORE YOU START
 *
 * Your recurring mistakes, in order of frequency:
 *   1. Not reading the spec properly  (cost you 5+ problems so far)
 *   2. .reversed() placed on the whole comparator chain instead of one key
 *   3. distinct() / map() in the wrong order
 *   4. map(...) + reduce(...) where mapToInt(...).sum() is cleaner
 *   5. Record accessors written without parentheses
 *
 * Several problems below are built specifically to punish those.
 *
 * Before writing ANY pipeline, say the requirement out loud in pieces.
 * "Direct flights. With seats. Under 5000. Sorted by price ascending."
 * That's four things. Four operations.
 * ─────────────────────────────────────────────────────────────────
 */
public class FlightsPractice {

    record Flight(String flightNo, String airline, String from, String to,
                  int priceInr, int durationMin, int seatsAvailable,
                  boolean direct, int departureHour) {

        String route() {
            return from + "-" + to;
        }
    }

    static List<Flight> flights = List.of(
            new Flight("AI-101", "Air India", "BLR", "DEL", 5400, 165, 12, true,   6),
            new Flight("6E-202", "IndiGo",    "BLR", "DEL", 4800, 155,  3, true,   8),
            new Flight("UK-303", "Vistara",   "BLR", "DEL", 6200, 160,  0, true,  11),
            new Flight("6E-404", "IndiGo",    "BLR", "BOM", 3900, 105, 25, true,   7),
            new Flight("AI-505", "Air India", "BLR", "BOM", 4500, 110,  8, true,  14),
            new Flight("SG-606", "SpiceJet",  "BLR", "BOM", 3200, 115, 40, true,  20),
            new Flight("UK-707", "Vistara",   "BLR", "CCU", 7800, 230,  5, true,   9),
            new Flight("6E-808", "IndiGo",    "BLR", "CCU", 6900, 320, 18, false,  5),
            new Flight("AI-909", "Air India", "DEL", "BOM", 5100, 130,  0, true,  16),
            new Flight("SG-010", "SpiceJet",  "DEL", "BOM", 4200, 135, 22, true,   6),
            new Flight("6E-111", "IndiGo",    "DEL", "CCU", 5600, 140, 14, true,  19),
            new Flight("UK-212", "Vistara",   "BOM", "CCU", 8400, 175,  2, true,  12),
            new Flight("SG-313", "SpiceJet",  "BLR", "DEL", 4100, 170,  0, false, 22),
            new Flight("AI-414", "Air India", "BOM", "CCU", 9200, 165,  7, true,  10)
    );

    public static void main(String[] args) {
         System.out.println("1:  " + getBookableBudgetFlights());
         System.out.println("2:  " + getCheapestBookableBlrToDel());
         System.out.println("3:  " + getTotalIndiGoSeats());
         System.out.println("4:  " + getAirlinesFromBlrDescending());
         System.out.println("5:  " + getAverageDurationOfConnectingFlights());
         System.out.println("6:  " + areAllBudgetFlightsSpiceJet());
         System.out.println("7:  " + getFlightsByAirlineThenPriceDesc());
         System.out.println("8:  " + getSecondAndThirdMostExpensive());
         System.out.println("9:  " + getMorningFlightSummaries());
         System.out.println("10: " + getBudgetRunFromCheapest());
         System.out.println("11: " + getLongestFlightFromBlr());
         System.out.println("12: " + countDistinctRoutes());
    }

    /**
     * 1 — Return the flight numbers of every flight that is DIRECT, has AT LEAST
     *     ONE seat available, and costs UNDER 5000. Sort by price ASCENDING.
     *
     * Expected: [SG-606, 6E-404, SG-010, AI-505, 6E-202]
     */

 //   Predicate<Integer> flightPrices = flights -> flight.priceInr()
    static List<String> getBookableBudgetFlights() {

        return flights.stream()
                .filter(fli -> fli.direct()==true)
                .filter(fli -> fli.priceInr()<5000)
               .filter(fli -> fli.seatsAvailable()>0)
                .sorted(Comparator.comparing(Flight::priceInr))
                .map(Flight::flightNo)
                .collect(Collectors.toList());

    }

    /**
     * 2 — Find the CHEAPEST flight from BLR to DEL that is direct AND has seats
     *     available. Return its flight number, or "NONE" if there isn't one.
     *
     *     Hint: min(...) gives you an Optional<Flight>.
     *     Chain .map(Flight::flightNo) then .orElse("NONE") onto it.
     *
     * Expected: 6E-202
     */
    static String getCheapestBookableBlrToDel() {
        return flights.stream()
                .filter(fli -> fli.from().equals("BLR"))
                .filter(fli -> fli.to().equalsIgnoreCase("DEL"))
                .filter(fli -> fli.direct()==true)
                .filter(fli -> fli.seatsAvailable() >0)
                .min(Comparator.comparing(Flight::priceInr))
                .map(Flight::flightNo)
                .orElse("NONE");

    }

    /**
     * 3 — Return the total number of seats available across all IndiGo flights.
     *     Use a primitive stream, not map + reduce.
     *
     * Expected: 60
     */
    static int getTotalIndiGoSeats() {

        return flights.stream()
                .filter(fli -> fli.airline().equals("IndiGo"))
                .mapToInt(Flight::seatsAvailable)
                .sum();

    }

    /**
     * 4 — Return the distinct airlines that operate flights DEPARTING FROM BLR,
     *     sorted Z-A (reverse alphabetical).
     *
     *     Careful with the order of your operations here.
     *
     * Expected: [Vistara, SpiceJet, IndiGo, Air India]
     */
    static List<String> getAirlinesFromBlrDescending() {
        return flights.stream()
                .filter(fli -> fli.from().equals("BLR"))
                .map(Flight::airline)
                .distinct()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

    }

    /**
     * 5 — Return the average duration (in minutes) of CONNECTING flights only —
     *     that is, flights where direct is false. Return it as a double.
     *
     * Expected: 245.0
     */
    static double getAverageDurationOfConnectingFlights() {

        return flights.stream()
                .filter(fli -> fli.direct() == false)
                .mapToDouble(Flight::durationMin)
                .average()
                .orElse(0);


    }

    /**
     * 6 — Return true if EVERY flight priced under 4000 is operated by SpiceJet.
     *
     *     Think about how to express this. There are two flights under 4000.
     *
     * Expected: false
     */

    static Predicate<Flight> flightsSpice = flight -> flight.airline().equals("Spicejet");

    static boolean areAllBudgetFlightsSpiceJet() {

     return flights.stream()
                .filter(fli -> fli.priceInr()<4000)

             .allMatch(fli ->  fli.airline().equals("Spicejet"));

    }

    /**
     * 7 — Return ALL flight numbers, sorted by airline name A-Z, and within each
     *     airline by price DESCENDING.
     *
     *     This is the .reversed() trap. Get it wrong and the airlines come out Z-A.
     *
     * Expected: [AI-414, AI-101, AI-909, AI-505,
     *            6E-808, 6E-111, 6E-202, 6E-404,
     *            SG-010, SG-313, SG-606,
     *            UK-212, UK-707, UK-303]
     */
    static List<String> getFlightsByAirlineThenPriceDesc() {

        return flights.stream()
                .sorted(Comparator.comparing(Flight::airline).
                        thenComparing(Comparator.comparing(Flight::priceInr).reversed()))
                                .map(Flight::flightNo)
                .collect(Collectors.toList());
          //      .collect(Collectors.toList();

    }

    /**
     * 8 — Return the flight numbers of the SECOND and THIRD most expensive
     *     flights, in that order.
     *
     *     Not the top three. The 2nd and 3rd.
     *
     * Expected: [UK-212, UK-707]
     */
    static List<String> getSecondAndThirdMostExpensive() {


        return flights.stream()
                .sorted(Comparator.comparing(Flight::priceInr).reversed())
                .map(Flight::flightNo)
                .skip(1)
                .limit(2)
                .collect(Collectors.toList());

    }

    /**
     * 9 — For every flight departing BEFORE hour 12, produce a summary string:
     *         "AI-101 6h BLR-DEL"
     *     (flightNo, space, departureHour, "h", space, route)
     *     Sort the results by departure hour ASCENDING.
     *
     *     Flight has a route() method — use it.
     *
     *     Note: AI-101 and SG-010 both depart at hour 6. Which comes first,
     *     and why? Think about whether sorted() is stable.
     *
     * Expected: [6E-808 5h BLR-CCU, AI-101 6h BLR-DEL, SG-010 6h DEL-BOM,
     *            6E-404 7h BLR-BOM, 6E-202 8h BLR-DEL, UK-707 9h BLR-CCU,
     *            AI-414 10h BOM-CCU, UK-303 11h BLR-DEL]
     */
    static List<String> getMorningFlightSummaries() {

        return flights.stream()
                .filter(fli -> fli.departureHour()<12)
                .sorted(Comparator.comparing(Flight::departureHour))
                .map(fli -> fli.flightNo + " " + fli.departureHour() + "h "+ fli.route())
                .collect(Collectors.toList());
    }

    /**
     * 10 — Sort all flights by price ASCENDING, then collect flight numbers
     *      while the price stays under 5000 — stopping at the first flight
     *      that isn't.
     *
     *      Use takeWhile, not filter. Then work out why the answer would be
     *      the same either way here, and construct a case where it wouldn't be.
     *
     * Expected: [SG-606, 6E-404, SG-313, SG-010, AI-505, 6E-202]
     */

    static Predicate<Flight> under5k = f -> f.priceInr()<5000;

    static List<String> getBudgetRunFromCheapest() {


        return flights.stream()
                .sorted(Comparator.comparing(Flight::priceInr))
                .takeWhile(under5k)
                .map(Flight::flightNo)
                .collect(Collectors.toList());


    }

    /**
     * 11 — Find the LONGEST flight departing from BLR. Return a string:
     *          "6E-808 (320 min)"
     *      or "NONE" if there are no BLR departures.
     *
     * Expected: 6E-808 (320 min)
     */
    static String getLongestFlightFromBlr() {
        return flights.stream()
                .filter( f -> f.from().equals("BLR"))
                .max(Comparator.comparingInt(Flight::durationMin))
                .map(f -> f.flightNo() +" ("+f.durationMin() +" min)")
                .orElse("NONE");

    }

    /**
     * 12 — Return how many DISTINCT routes exist in the list.
     *      A route is the from-to pair, e.g. "BLR-DEL".
     *
     *      Flight has a route() method. count() returns a long — the method
     *      signature says long, so no cast needed.
     *
     * Expected: 6
     */
    static long countDistinctRoutes() {

        return flights.stream()
                .map(f -> f.from()+"-"+f.to())
                .distinct()
                .count();

    }
}