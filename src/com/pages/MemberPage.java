package com.pages;

import com.company.Book;
import com.company.Main;
import util.FileService;
import util.Input;
import util.ResultType;
import util.TableDrawer;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class MemberPage {
    protected static FileService booksFile;
    protected static Book book;

    static {
        try {
            booksFile = new FileService(new RandomAccessFile("Books.txt", "rw"));
            book = new Book(booksFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void menu() throws IOException {
        System.out.println("---------- Member Menu ----------");
        System.out.println("Get a book : Enter 1");
        System.out.println("Return a book : Enter 2");
        System.out.println("my books list  : Enter 3");
        System.out.println("all library books list : Enter 4");
        System.out.println("Logout from account : Enter 0");
        System.out.println("-------------------------------");
        int key = Input.nextInt("Enter menu key: ");
        switch (key) {
            case 0:
                Main.session = null;
                return;
            case 1:
                getBook();
                break;
            case 2:
                returnBook();
                break;
            case 3:
                myBooks();
                break;
            case 4:
                booksList(book.getAll());
                break;
            default:
                System.err.println("menu not valid!");
        }
        menu();
    }

    private static void getBook() throws IOException {
        long id = Input.nextLong("Enter book id to get it:");
        ResultType<Boolean, Book> search = book.searchById(id);
        if(!search.getValue1()) {
            System.err.println("book by id " + id + " not found!");
            return;
        }
        else if (search.getValue2().getAvailableCount() == 0) {
            System.err.println("a book with id " + id + " is not available now");
            return;
        }

        ResultType<Boolean, String> memberResult = Main.session.addBook(Main.session.getId(), id);
        if (!memberResult.getValue1()) {
            System.err.println(memberResult.getValue2());
            return;
        }
        book.updateAvailableCount(id, -1);
        System.out.println(memberResult.getValue2());
    }

    private static void returnBook() throws IOException {
        long id = Input.nextLong("Enter book id to get it:");
        ResultType<Boolean, Book> search = book.searchById(id);

        if(!search.getValue1()) {
            System.err.println("book by id " + id + " not found!");
            return;
        }

        ResultType<Boolean, String> memberResult = Main.session.removeBook(Main.session.getId(), id);
        if(!memberResult.getValue1()) {
            System.err.println(memberResult.getValue2());
            return;
        }

        book.updateAvailableCount(id, 1);
        System.out.println(memberResult.getValue2());
    }

    private static void myBooks() throws IOException {
        long[] myBooksId = Main.session.getBooks();
        ArrayList<Book> myBooks = new ArrayList<>();
        for(long id : myBooksId) {
            if(id != 0)
                myBooks.add(new Book(book.searchById(id).getValue2()));
        }
        booksList(myBooks);
    }

    protected static void booksList(ArrayList<Book> books) {
        TableDrawer.booksHead();
        for (Book item : books){
            TableDrawer.bookPreview(
                    item.getId(), item.getName(),
                    item.getAuthors(), item.getAvailableCount()
            );
        }
        TableDrawer.booksFooter();
    }
}
