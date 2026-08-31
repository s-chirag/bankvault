package io.bankvault.auth.collections;

import java.sql.SQLOutput;
import java.util.LinkedList;

public class LinkedListMain {


    public static void main(String args[]) {
        Book janeEyre = new Book(1, "Jane Eyre", "Charlotte Brontë", 1847);
        Book kiteRunner = new Book(2, "Kite Runner", "Khaled Hosseini", 2010);
        Book theBookThief = new Book(3, "The Book Thief", "Markus Dfus", 1988);
        Book theLostSymbol = new Book(4, "The lost symbol", "Dan Brown", 2005);

        Book ramayana = new Book(5, "Ramayana", "Valmiki", -5000);


        LinkedList<Book> books = new LinkedList<>();
        books.add(janeEyre);
        books.add(kiteRunner);
        books.add(theBookThief);

// 1. add a book at the front

        books.addFirst( theLostSymbol);


// 2. add a book at the end (two different ways)

        books.addLast(ramayana);

// 3. get the first book without removing it

        System.out.println(books.peekFirst());

// 4. get the last book without removing it

        System.out.println(books.peekLast());

// 5. remove and return the first book
        System.out.println(books.removeFirst());

// 6. remove and return the last book

        System.out.println(books.removeLast());
// 7. peek at the head — what does it return on an empty list?

        System.out.println(books.peek());

// 8. poll the head — how is this different from remove()?

        System.out.println(books.pollFirst());


        // idk explain claude
// 9. use it as a stack: push three books, then pop one

        books.push(janeEyre);
        books.push(ramayana);
        books.push(theLostSymbol);
        books.pop();


// 10. iterate it backwards

        System.out.println("thisis from 10th answer ");
        for(int i= books.size()-1; i>=0; i--){
            System.out.println(
                    books.get(i)
            );
        }

    }
}
