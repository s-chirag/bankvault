package functional.programming;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

/**
 * MIN / MAX PRACTICE
 *
 * Every problem here is the same three-step shape:
 *
 *     .min(Comparator.comparingInt(Flight::field))   // -> Optional<Flight>
 *     .map(f -> something)                            // -> Optional<X>
 *     .orElse(fallback)                               // -> X
 *
 * min and max are TERMINAL operations. Nothing comes after them
 * except Optional methods. No collect().
 *
 * Get this shape automatic — it has caught you out three times.
 */
public class MinMaxPractice {

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
        System.out.println("M1: " + getCheapestFlightNo());
        System.out.println("M2: " + getMostExpensiveFlightNo());
        System.out.println("M3: " + getShortestDuration());
        System.out.println("M4: " + getFlightWithMostSeats());
        System.out.println("M5: " + getCheapestVistaraFlightNo());
        System.out.println("M6: " + getCheapestFlightToMaa());
        System.out.println("M7: " + getEarliestBookableDirectFromBlr());
        System.out.println("M8: " + getPriciestShortHaul());
    }

    /**
     * M1 — Flight number of the cheapest flight overall.
     *      Return "NONE" if the list is empty.
     * Expected: SG-606
     */
    static String getCheapestFlightNo() {

        return flights.stream()
                .min(Comparator.comparing(Flight::priceInr))
                        .map(Flight::flightNo)
                .orElse("NONE");


    }

    /**
     * M2 — Flight number of the most expensive flight.
     *      Return "NONE" if the list is empty.
     * Expected: AI-414
     */
    static String getMostExpensiveFlightNo() {


        return flights.stream()
                .max(Comparator.comparing(Flight::priceInr))
                .map(Flight::flightNo)
                .orElse("NONE");
    }

    /**
     * M3 — The duration of the shortest flight, as an int. Return 0 if none.
     *      Note the return type — the Optional holds an Integer here, not a String.
     * Expected: 105
     */
    static int getShortestDuration() {

        return flights.stream()
                .min(Comparator.comparing(Flight::durationMin))
                        .map(Flight::durationMin)
                .orElse(0);

    }

    /**
     * M4 — The flight with the most seats available, formatted as:
     *          "SG-606 has 40 seats"
     *      or "NONE".
     * Expected: SG-606 has 40 seats
     */
    static String getFlightWithMostSeats() {

        return flights.stream()
                .max(Comparator.comparing(Flight::seatsAvailable))
                .map(f -> f.flightNo() +" has "+f.seatsAvailable() + " seats")
                .orElse("NONE");

    }

    /**
     * M5 — Flight number of the cheapest Vistara flight.
     * Expected: UK-303
     */
    static Predicate<Flight> isVIstara = f -> f.airline().equals("Vistara");
    static String getCheapestVistaraFlightNo() {

        return flights.stream()
                .filter(isVIstara)
                .min(Comparator.comparing(Flight::priceInr))
                .map(Flight::flightNo)
                .orElse("NONE");
    }

    /**
     * M6 — Flight number of the cheapest flight to "MAA".
     *      There are no MAA flights — this one proves your fallback works.
     * Expected: NONE
     */
    static Predicate<Flight> isToMaa= f -> f.to().equals("MAA");

    static String getCheapestFlightToMaa() {

       return  flights.stream()
                .filter(isToMaa)
                .min(Comparator.comparing(Flight::priceInr))
                .map(Flight::flightNo)
                .orElse("NONE");


    }

    /**
     * M7 — Among flights departing FROM BLR that are DIRECT and have
     *      SEATS AVAILABLE, find the one departing EARLIEST.
     *      Return its flight number, or "NONE".
     * Expected: AI-101
     */
    static Predicate<Flight> isDepartureBlr= f -> f.from().equals("BLR");
    static Predicate<Flight> isSeatsAvailable= f -> f.seatsAvailable()>0;
    static Predicate<Flight> isDirect= f -> f.direct()==true;

    static String getEarliestBookableDirectFromBlr() {

        return flights.stream()
                .filter(isDepartureBlr)
                .filter(isSeatsAvailable)
                .filter(isDirect)
                .min(Comparator.comparing(Flight::departureHour))
                .map(Flight::flightNo)
                .orElse("NONE");

    }

    /**
     * M8 — Among flights UNDER 300 minutes duration, find the MOST EXPENSIVE.
     *      Format as "AI-414 @ 9200", or "NONE".
     * Expected: AI-414 @ 9200
     */

    static Predicate<Flight> isFlightUnder300= f -> f.durationMin()<300;

    static String getPriciestShortHaul() {


        return flights.stream()
                .filter(isFlightUnder300)
                .max(Comparator.comparing(Flight::priceInr))
                .map(f -> f.flightNo() +" @ "+ f.priceInr())
                .orElse("NONE");

    }
}