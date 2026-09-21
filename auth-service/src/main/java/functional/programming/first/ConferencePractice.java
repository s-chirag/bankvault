package functional.programming.first;

import java.util.*;
import java.util.stream.Collectors;

/**
 * STREAMS PRACTICE — S RANK
 * Domain: a tech conference
 *
 * This is the hard set. It uses things you have NOT practised:
 *   Collectors.toMap with a merge function
 *   nested groupingBy
 *   collectingAndThen
 *   summarizingDouble / summarizingInt
 *   groupingBy with mapping + counting downstream
 *   finding the max entry of a Map
 *
 * Plus the comparator chaining you have got wrong five times.
 *
 * No hints on which collector to use. Work it out from the return type.
 */
public class ConferencePractice {

    record Speaker(String name, String company, String country) {}

    record Session(String title, String track, Speaker speaker,
                   int durationMin, int roomCapacity,
                   List<String> tags, double rating) {}

    static Speaker anita  = new Speaker("Anita Rao",     "Google",    "India");
    static Speaker ben    = new Speaker("Ben Carter",    "Netflix",   "USA");
    static Speaker chen   = new Speaker("Chen Wei",      "Alibaba",   "China");
    static Speaker diana  = new Speaker("Diana Lopez",   "Spotify",   "Sweden");
    static Speaker ethan  = new Speaker("Ethan Brooks",  "Google",    "USA");
    static Speaker fatima = new Speaker("Fatima Khan",   "Microsoft", "India");

    static List<Session> sessions = List.of(
        new Session("Virtual Threads Deep Dive", "Java",         anita,  45, 200, List.of("java","concurrency","jdk21"),        4.8),
        new Session("Scaling Kafka",             "Data",         ben,    60, 350, List.of("kafka","streaming","scale"),         4.5),
        new Session("Reactive Patterns",         "Java",         chen,   45, 200, List.of("java","reactive","async"),           4.2),
        new Session("ML at Scale",               "Data",         diana,  90, 500, List.of("ml","python","scale"),               4.9),
        new Session("JVM Tuning",                "Java",         ethan,  60, 150, List.of("java","jvm","performance"),          4.6),
        new Session("Event Sourcing",            "Architecture", fatima, 45, 250, List.of("architecture","events","cqrs"),      4.3),
        new Session("GraphQL in Practice",       "API",          ben,    30, 180, List.of("graphql","api","rest"),              3.9),
        new Session("Distributed Tracing",       "Architecture", anita,  60, 250, List.of("observability","architecture","tracing"), 4.7),
        new Session("Stream Processing",         "Data",         chen,   45, 350, List.of("streaming","kafka","java"),          4.4),
        new Session("API Gateway Patterns",      "API",          diana,  30, 180, List.of("api","gateway","architecture"),      4.1),
        new Session("Records and Sealed Types",  "Java",         ethan,  30, 150, List.of("java","jdk21","language"),           4.5),
        new Session("Chaos Engineering",         "Architecture", fatima, 60, 300, List.of("resilience","architecture","testing"), 4.6)
    );

    public static void main(String[] args) {
  //       System.out.println("S1:  " + getTopSessionPerTrack());
        // System.out.println("S2:  " + getTitlesByTrackThenCompany());
         System.out.println("S3:  " + getTagFrequency());
        // System.out.println("S4:  " + getTitlesByTrackThenRatingDesc());
        // System.out.println("S5:  " + getImmutableTitlesByTrack());
        // System.out.println("S6a: " + getBestSessionInTrack("Java"));
        // System.out.println("S6b: " + getBestSessionInTrack("Mobile"));
        // System.out.println("S7:  " + countByLength());
        // System.out.println("S8:  " + getCapacityStats());
        // System.out.println("S9:  " + getAllDistinctTags());
        // System.out.println("S10: " + getTrackWithHighestAverageRating());
        // System.out.println("S11: " + getSpeakerCountByCountry());
        // System.out.println("S12: " + getSessionCountByTrackAndCompany());
    }

    /**
     * S1 — For each track, the TITLE of its highest-rated session.
     *
     *      Use Collectors.toMap. Every track has several sessions, so you
     *      will hit duplicate keys. Work out what to do about that.
     *
     * Expected: {Java=Virtual Threads Deep Dive, Data=ML at Scale,
     *            Architecture=Distributed Tracing, API=API Gateway Patterns}
     */
    static Map<String, String> getTopSessionPerTrack() {

            return sessions.stream()
                //    .sorted(Comparator.comparing(Session::rating).reversed())
                    .collect(Collectors.toMap(
                            Session::track,Session::title));
    }

    /**
     * S2 — Session titles grouped by track, then by the speaker's company
     *      within each track.
     *
     * Expected shape: {Java={Google=[Virtual Threads Deep Dive, JVM Tuning,
     *                              Records and Sealed Types],
     *                        Alibaba=[Reactive Patterns]}, ...}
     */
    static Map<String, Map<String, List<String>>> getTitlesByTrackThenCompany() {
        return null;
    }

    /**
     * S3 — How many sessions carry each tag, across the whole conference.
     *
     * Expected includes: java=5, architecture=4, jdk21=2, api=2,
     *                    kafka=2, streaming=2, scale=2, and many at 1
     */
    static Map<String, Long> getTagFrequency() {
        return sessions.stream()
                .flatMap(s -> s.tags.stream())
                .collect(Collectors.groupingBy(tag -> tag, Collectors.counting()));
    }

    /**
     * S4 — All session titles, sorted by track name A-Z, and within each
     *      track by rating DESCENDING.
     *
     *      This is the one you have got wrong five times. Think carefully
     *      about where .reversed() goes.
     *
     * Expected: [Distributed Tracing, Chaos Engineering, Event Sourcing,
     *            API Gateway Patterns, GraphQL in Practice,
     *            ML at Scale, Scaling Kafka, Stream Processing,
     *            Virtual Threads Deep Dive, JVM Tuning,
     *            Records and Sealed Types, Reactive Patterns]
     */
    static List<String> getTitlesByTrackThenRatingDesc() {
        return null;
    }

    /**
     * S5 — Session titles grouped by track, but each group must be an
     *      UNMODIFIABLE list.
     *
     *      Use collectingAndThen. Prove it works by trying to add to one
     *      of the returned lists — it should throw.
     */
    static Map<String, List<String>> getImmutableTitlesByTrack() {
        return null;
    }

    /**
     * S6 — The highest-rated session in a given track, formatted as
     *          "Virtual Threads Deep Dive (4.8)"
     *      or "no sessions in that track" if the track does not exist.
     *
     * Expected S6a ("Java"):   Virtual Threads Deep Dive (4.8)
     * Expected S6b ("Mobile"): no sessions in that track
     */
    static String getBestSessionInTrack(String track) {
        return null;
    }

    /**
     * S7 — Partition sessions into long (more than 45 minutes) and short,
     *      and return the COUNT of each rather than the sessions.
     *
     * Expected: {false=7, true=5}
     */
    static Map<Boolean, Long> countByLength() {
        return null;
    }

    /**
     * S8 — Statistics on room capacity: count, sum, min, max, average,
     *      all in one pass.
     *
     *      Return the IntSummaryStatistics object itself — its toString
     *      prints everything.
     *
     * Expected: count=12, sum=3060, min=150, average=255.0, max=500
     */
    static IntSummaryStatistics getCapacityStats() {
        return null;
    }

    /**
     * S9 — Every distinct tag used anywhere in the conference, sorted A-Z.
     *
     * Expected starts: [api, architecture, async, concurrency, cqrs, ...]
     */
    static List<String> getAllDistinctTags() {
        return null;
    }

    /**
     * S10 — Which track has the highest AVERAGE rating? Return the track name.
     *
     *       Two steps: build the averages, then find the max entry.
     *       Map.Entry.comparingByValue() is useful for the second part.
     *
     * Expected: Data   (average 4.6)
     */
    static String getTrackWithHighestAverageRating() {
        return null;
    }

    /**
     * S11 — How many DISTINCT speakers come from each country?
     *       Note a speaker may have several sessions — count each person once.
     *       Return it sorted by country name.
     *
     * Expected: {China=1, India=2, Sweden=1, USA=2}
     */
    static Map<String, Long> getSpeakerCountByCountry() {
        return null;
    }

    /**
     * S12 — For each track, how many sessions does each company have?
     *
     * Expected shape: {Java={Google=2, Alibaba=1, ...}, ...}
     *       Note Google has 3 Java sessions (Anita 1, Ethan 2).
     */
    static Map<String, Map<String, Long>> getSessionCountByTrackAndCompany() {
        return null;
    }
}