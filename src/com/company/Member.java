package com.company;

import util.FileService;
import util.ResultType;
import util.StringLength;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Member {
    private final FileService file;
    private long seek = 0;
    private long id;
    private String name;
    private String family;
    private String password;
    private int role; // 0 : user, 1: admin
    private long[] books = new long[3];

    public Member(FileService file) {
        this.file = file;
    }

    public Member(FileService file, String name, String family, String password) throws IOException {
        this.file = file;
        id = file.length() / dataLength() + 1;
        this.name = name;
        this.family = family;
        this.password = password;
        role = 0;
        file.seek(file.length());
        write();
    }

    public Member(Member member) {
        this.file = member.file;
        this.seek = member.seek;
        this.id = member.id;
        this.name = member.name;
        this.family = member.family;
        this.password = member.password;
        this.role = member.role;
        this.books = new long[member.books.length];
        for(int i = 0; i < this.books.length; i++)
            this.books[i] = member.books[i];
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFamily() {
        return family;
    }

    public String getRole() {
        return role == 1 ? "admin" : "user";
    }

    public long[] getBooks() {
        return books;
    }

    public ArrayList<Member> getAll() throws IOException {
        ArrayList<Member> members = new ArrayList<>();
        for (int i = 1; i <= file.length() / dataLength(); i++) {
            searchById(i);
            members.add(new Member(this));
        }
        return members;
    }

    public void write() throws IOException {
        file.write(id);
        file.write(name, StringLength.userName);
        file.write(family, StringLength.userFamily);
        file.write(password, StringLength.userPassword);
        file.write(role);
        for (long bookId : books)
            file.write(bookId);
    }

    public boolean searchById(long id) throws IOException {
        long seek = this.seek = (id - 1) * dataLength();
        if (seek >= file.length() || seek < 0)
            return false;
        this.id = file.readLong(seek);
        seek += 8;
        this.name = file.readString(seek, StringLength.userName);
        seek += StringLength.userName * 2;
        this.family = file.readString(seek, StringLength.userFamily);
        seek += StringLength.userFamily * 2;
        this.password = file.readString(seek, StringLength.userPassword);
        seek += StringLength.userPassword * 2;
        this.role = file.readInt(seek);
        seek += 4;
        for (int i = 0; i < books.length; i++) {
            books[i] = file.readLong(seek);
            seek += 8;
        }
        return true;
    }

    public ArrayList<Member> allMembersByBookId(long bookId) throws IOException {
        Set<Member> members = new HashSet<>();
        for(int i = 1; i <= file.length() / dataLength(); i++) {
            searchById(i);
            for(long book : this.books) {
                if (book == bookId)
                    members.add(new Member(this));
            }
        }
        return new ArrayList<>(members);
    }

    public ResultType<Boolean, String> addBook(long userId, long bookId) throws IOException {
        if(!searchById(userId))
            return new ResultType<>(false, "user by id " + userId + " not found!");
        int i = 0;
        for(; i < books.length; i++) {
            if(books[i] == 0) {
                books[i] = bookId;
                break;
            }
        }
        if(i == books.length)
            return new ResultType<>(false, "you can't get more book!");
        file.seek(seek);
        write();
        return new ResultType<>(true, "book by id " + bookId + " was received.");
    }

    public ResultType<Boolean, String> removeBook(long userId, long bookId) throws IOException {
        if(!searchById(userId))
            return new ResultType<>(false, "user by id " + userId + " not found!");
        int i = 0;
        for(; i < books.length; i++) {
            if(books[i] == bookId) {
                books[i] = 0;
                break;
            }
        }
        if(i == books.length)
            return new ResultType<>(false, "This book is not in your list of books");
        file.seek(seek);
        write();
        return new ResultType<>(true, "book by id " + bookId + " was returned.");
    }

    public String booksId() {
        StringBuilder items = new StringBuilder();
        for(long book : books) {
            if(book != 0)
                items.append(book).append(" , ");
        }
        return items.toString();
    }

    public boolean comparePassword(String password) {
        return this.password.equals(password);
    }

    private long dataLength() {
        return 8 + StringLength.userName * 2 + StringLength.userFamily * 2
                + StringLength.userPassword * 2 + 4 + 8L * books.length;
    }

    @Override
    public String toString() {
        return String.format(
                "%s %s\t -id: %d",
                name, family, id
        );
    }
}
