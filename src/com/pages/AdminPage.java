package com.pages;

import com.company.Book;
import com.company.Main;
import com.company.Member;
import util.FileService;
import util.Input;
import util.ResultType;
import util.TableDrawer;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class AdminPage extends MemberPage {
    private static Member members;

    static {
        try {
            FileService membersFile = new FileService(new RandomAccessFile("Members.txt", "rw"));
            members = new Member(membersFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void menu() throws IOException {
        System.out.println("---------- Admin Menu ----------");
        System.out.println("add a book to library : Enter 1");
        System.out.println("show all library books : Enter 2");
        System.out.println("show members list : Enter 3");
        System.out.println("Logout from account : Enter 0");
        System.out.println("-------------------------------");
        int key = Input.nextInt("Enter menu key: ");
        switch (key) {
            case 0:
                Main.session = null;
                return;
            case 1:
                addBook();
                break;
            case 2:
                booksList(book.getAll());
                bookMenu();
                break;
            case 3:
                membersList(members.getAll());
                break;
            default:
                System.err.println("menu not valid!");
        }
        menu();
    }

    private static void bookMenu() throws IOException {
        System.out.println("---------- books list menu ----------");
        System.out.println("show members list that borrow a book: Enter book id");
        System.out.println("edit a book : Enter `e`");
        System.out.println("return to previous menu : Enter 0");
        System.out.println("-------------------------------------");
        String key = Input.next("Enter menu key:");
        switch (key) {
            case "0":
                return;
            case "e":
                editBook();
                booksList(book.getAll());
                break;
            default:
                long bookId = strToLong(key);
                System.out.println("all members that borrow book by id: " + bookId);
                membersList(members.allMembersByBookId(bookId));
        }
        bookMenu();
    }

    private static void addBook() throws IOException {
        String name = Input.nextLine("Enter name of book:");
        String authors = Input.nextLine("Enter authors of book:");
        int availableCount = Input.nextInt("Enter available count of book:");
        Book book = new Book(booksFile, name, availableCount, authors);
        book.write();
        System.out.printf("Book added and id is: %d \n", book.getId());
    }

    private static void editBook() throws IOException {
        long id = Input.nextLong("Enter book id to edit it:");
        String name = Input.nextLine("Enter name of book: (press enter key for skip)");
        String authors = Input.nextLine("Enter authors of book: (press enter key for skip)");
        String availableCountStr = Input.nextLine("Enter available count of book: (press enter key for skip)");
        int availableCount = (int) strToLong(availableCountStr);
        ResultType<Boolean, String> result = book.update(id, name, authors, availableCount);
        if (!result.getValue1()) {
            System.err.println(result.getValue2());
            return;
        }
        System.out.println(result.getValue2());
    }

    private static void membersList(ArrayList<Member> members) {
        TableDrawer.memberHead();
        for (Member item : members) {
            TableDrawer.memberPreview(
                    item.getId(),
                    item.getName() + " " + item.getFamily(),
                    item.getRole(), item.booksId()
            );
        }
        TableDrawer.memberFooter();
    }

    private static long strToLong(String string) {
        try {
            return Integer.parseInt(string);
        }
        catch (NumberFormatException e){
            return  -1;
        }
    }
}
