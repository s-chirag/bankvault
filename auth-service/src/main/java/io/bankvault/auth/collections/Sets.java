package io.bankvault.auth.collections;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public class Sets {
    public static void main(String[] args) {

        Set<Book> hash   = new HashSet<>();
        Set<Book> linked = new LinkedHashSet<>();


        Book janeEyre = new Book(1, "Jane Eyre", "Charlotte Brontë", 1847);
        Book kiteRunner = new Book(2, "Kite Runner", "Khaled Hosseini", 2010);
        Book theBookThief = new Book(3, "The Book Thief", "Markus Dfus", 1988);
        Book theLostSymbol = new Book(4, "The lost symbol", "Dan Brown", 2005);
// add the same 4 books to both, same order

        hash.add(janeEyre);
        hash.add(kiteRunner);
        hash.add(theBookThief);
        hash.add(theLostSymbol);

        linked.add(janeEyre);
        linked.add(kiteRunner);
        linked.add(theBookThief);
        linked.add(theLostSymbol);

        System.out.println("This is size for hash before ::"+hash.size());
        System.out.println("This is size for hash  before ::"+linked.size());
// print both — compare the iteration order

        System.out.println("--- insertion order ---");
        System.out.println(janeEyre.getBookName() + ", " + kiteRunner.getBookName()
                + ", " + theBookThief.getBookName() + ", " + theLostSymbol.getBookName());

        System.out.println("\n--- HashSet ---");
        for (Book b : hash) {
            System.out.println(b.getBookName());
        }

        System.out.println("\n--- LinkedHashSet ---");
        for (Book b : linked) {
            System.out.println(b.getBookName());
        }


// 1. add a duplicate. What does add() return?
        System.out.println(hash.add(theLostSymbol));
        System.out.println(linked.add(theLostSymbol));


// 2. did size change?

        System.out.println("This is size for hash after ::"+hash.size());
        System.out.println("This is size for hash  after ::"+linked.size());


        // 3. contains() on a book that's in there

        System.out.println("This is contains for hash ::"+ hash.contains(janeEyre));
        System.out.println("This is contains for linked ::"+ linked.contains(theLostSymbol));

// 4. remove one, print size

        hash.remove(theBookThief);
        linked.remove(theLostSymbol);

        System.out.println("This is size for hash last ::"+hash.size());
        System.out.println("This is size for hash  last ::"+linked.size());
    }

}
