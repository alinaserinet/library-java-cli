package com.company;

import util.FileService;
import util.ResultType;
import util.StringLength;

import java.io.IOException;
import java.util.ArrayList;

public class Book {
    private final FileService file;
    private long seek = 0;
    private long id;
    private String name;
    private String authors;
    private int availableCount;

    public Book(FileService file, String name, int availableCount, String authors) throws IOException {
        this.file = file;
        this.id = file.length() / dataLength() + 1;
        this.name = name;
        this.authors = authors;
        this.availableCount = availableCount;
    }

    public Book(FileService file) {
        this.file = file;
    }

    public Book(Book book) {
        this.file = book.file;
        this.id = book.id;
        this.name = book.name;
        this.availableCount = book.availableCount;
        this.authors = book.authors;
        this.seek = book.seek;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAvailableCount() {
        return availableCount;
    }

    public String getAuthors() {
        return authors;
    }

    public void write() throws IOException {
        file.write(id);
        file.write(name, StringLength.bookName);
        file.write(authors, StringLength.bookAuthors);
        file.write(availableCount);
    }

    public ArrayList<Book> getAll() throws IOException {
        ArrayList<Book> books = new ArrayList<>();
        for (int i = 1; i <= file.length() / dataLength(); i++) {
            searchById(i);
            books.add(new Book(this));
        }
        return books;
    }

    public ResultType<Boolean, Book> searchById(long id) throws IOException {
        long seek = this.seek = (id - 1) * dataLength();
        if (seek >= file.length() || seek < 0)
            return new ResultType<>(false, null);
        this.id = file.readLong(seek);
        seek += 8;
        this.name = file.readString(seek, StringLength.bookName);
        seek += StringLength.bookName * 2;
        this.authors = file.readString(seek, StringLength.bookAuthors);
        seek += StringLength.bookAuthors * 2;
        this.availableCount = file.readInt(seek);
        return new ResultType<>(true, this);
    }

    public void updateAvailableCount(long bookId, int count) throws IOException {
        ResultType<Boolean, Book> search = searchById(bookId);
        if (!search.getValue1()) {
            new ResultType<>(false, "book by id " + bookId + " not found!");
            return;
        }
        this.availableCount += count;
        file.seek(seek);
        write();
        new ResultType<>(true, "successful, new available count: " + this.availableCount);
    }

    public ResultType<Boolean, String> update(long bookId, String name, String authors, int availableCount) throws IOException {
        ResultType<Boolean, Book> search = searchById(bookId);
        if (!search.getValue1())
            return new ResultType<>(false, "book by id " + bookId + " not found!");
        this.name = name == null || name.equals("") || name.equals(" ") ? this.name : name;
        this.authors = name == null || authors.equals("") || authors.equals(" ") ? this.authors : authors;
        this.availableCount = availableCount == -1 ? this.availableCount : availableCount;
        file.seek(seek);
        write();
        return new ResultType<>(true, "book by id " + bookId + " updated.");
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                "authors=" + authors +
                ", availableCount=" + availableCount +
                '}';
    }

    private long dataLength() {
        return 8 + StringLength.bookName * 2 + StringLength.bookAuthors * 2 + 4;
    }
}
