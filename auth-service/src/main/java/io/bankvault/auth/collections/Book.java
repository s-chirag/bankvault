package io.bankvault.auth.collections;

public class Book implements Comparable<Book>{

    private int id;
    private String bookName;
    private String author;
    private int year;


    public Book(int id, String bookName, String author, int year) {
        this.id = id;
        this.bookName = bookName;
        this.author = author;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", bookName='" + bookName + '\'' +
                ", author='" + author + '\'' +
                ", year=" + year +
                '}';
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

//    @Override
//    public int compareTo(Book o) {
//        if (this.year<o.year){
//            return 1;
//        }
//        else {
//            return -1;
//        }
//    }

    @Override
    public int compareTo(Book o) {
        return this.bookName.compareTo(o.bookName);
    }
}
