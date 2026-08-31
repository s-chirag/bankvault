package io.bankvault.auth.collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArrayListMain {


//    public static void main(String args[]){
//
//
//        Book janeEyre = new Book(1, "Jane Eyre", "Charlotte Brontë", 1847);
//        Book kiteRunner = new Book(2, "Kite Runner", "Khaled Hosseini", 2010);
//        Book theBookThief = new Book(3, "The Book Thief", "Markus Dfus", 1988);
//        Book theLostSymbol = new Book(4, "The lost symbol", "Dan Brown",   2005);
//
//        ArrayList<Book> bookList = new ArrayList<Book>();
//
//        bookList.add(janeEyre);
//        bookList.add(kiteRunner);
//        bookList.add(theBookThief);
//        bookList.add(theBookThief);
//
//        System.out.println(" This is book size :"+ bookList.size());
//
//
//
//
//        for(Book b:bookList){
//            System.out.println("This is the list of books");
//            System.out.println(b);
//        }
//
//
//        System.out.println("THis is books after removing duplicates :"+removeDuplicates(bookList));
//
//        // 1. insert a book at position 0
//        bookList.add(0, theLostSymbol);
//
//// 2. replace whatever is at index 2
//        bookList.set(2,janeEyre);
//
//
//// 3. get the book at index 1 and print just its author
//
//      // Book one = new Book();
//        Book one =  bookList.get(1);
//       System.out.println("This is answer 3 " + one.getAuthor());
//
//// 4. find the index of kiteRunner
//        for(int i=0; i< bookList.size(); i++){
//
//            Book three = bookList.get(i);
//            if(three.getBookName().equalsIgnoreCase("Kite runner")){
//                System.out.println("This is answer 4" + i);
//
//            }
//
//        }
//
//        System.out.println("This is answer 4" +    bookList.indexOf(kiteRunner));
//
//
//// 5. remove the book at index 3
//
//        bookList.remove(3);
//
//// 6. remove kiteRunner by object reference
//
//        bookList.remove(kiteRunner);
//
//// 7. remove every book published before 1990
////    (use removeIf)
//
//   //     bookList.removeIf(Predicate.not( int year>1990));
//
//// 8. print size, and whether the list is empty
//
//        System.out.println("This is answer 8size" + bookList.size());
//        System.out.println("This is answer 8 is empty " + bookList.isEmpty());
//
//// 9. make a sublist of the first two books and print it
//
//        ArrayList<Book> subList = new ArrayList();
//        subList.addAll(bookList.subList(1,2));
//
//        System.out.println("This is answer 9  " + subList);
//
//// 10. clear the list, print size
//
//        bookList.removeAll(bookList);
//        System.out.println("This is answer 10  " + bookList.isEmpty());
//
//
//    }

    public static void main(String[] args) {

        Book janeEyre = new Book(1, "Jane Eyre", "Charlotte Brontë", 1847);
        Book kiteRunner = new Book(2, "Kite Runner", "Khaled Hosseini", 2010);
        Book theBookThief = new Book(3, "The Book Thief", "Markus Dfus", 1988);
        Book theLostSymbol = new Book(4, "The lost symbol", "Dan Brown",   2005);
        Book ramayana = new Book(5, "Ramayana", "Valmiki",   -5000);

        ArrayList<Book> bookList = new ArrayList<Book>();

        bookList.add(janeEyre);
        bookList.add(kiteRunner);
        bookList.add(theBookThief);
        bookList.add(theLostSymbol);
        bookList.add(ramayana);


        for(Book e: bookList){
            System.out.println("this is unsorted Book:"+ e.getBookName() + " year"+ e.getYear() + " id" + e.getId());
        }
     //   bookList.sort();
        Collections.sort(bookList);

        for(Book e: bookList){
            System.out.println("this is sorted Book:"+ e.getBookName()+ " year"+ e.getYear() + " id" + e.getId());
        }

    }

    public static List<Book> removeDuplicates(List<Book> books){

        List<Book> uniqueBooks = new ArrayList();

        for(Book b: books){
            if(!uniqueBooks.contains(b)){
                uniqueBooks.add(b);
            }

        }

       return uniqueBooks;
    }


}
