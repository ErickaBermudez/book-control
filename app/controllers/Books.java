/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.util.*;
import java.util.logging.Level;

import models.*;
import play.*;
import play.db.jpa.Blob;
import play.mvc.*;

/**
 *
 * @author Ericka Bermudez
 * @date 21/02/2019
 */
public class Books extends Controller {

    public static void newBook() {
        render();
    }

    public static void edit(Long id) {
        Book book = new Book();
        book = Book.findById(id);
        render(book);
    }

    public static void saveChanges(String id, String isbn, String name, String author) {
        System.out.println("id = " + id);
        long idLong = Long.valueOf(id);
        System.out.println("id: " + idLong);
        Book book = new Book();
        book = Book.findById(idLong);
        System.out.println("Name: " + book.name);
        book.setISBN(isbn);
        book.setName(name);
        book.setAuthor(author);
        book.save();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            java.util.logging.Logger.getLogger(Books.class.getName()).log(Level.SEVERE, null, ex);
        }
        bookList();
    }

    public static void addBook(String isbn, String name, String author) {
        validation.required(isbn);
        validation.required(name);
        validation.required(author);

        // Check for errors server-side
        if (validation.hasErrors()) {
            for (play.data.validation.Error error : validation.errors()) {
                System.out.println(error.message());
            }
            params.flash();
            validation.keep();
            newBook();
        } else {
            /* // Prints on console saved book
             System.out.println("--------------------------");
             System.out.println("ISBN: " + isbn);
             System.out.println("Name: " + name);
             System.out.println("Author: " + author);
             System.out.println("--------------------------");
             */
            Book book = new Book();
            book.setISBN(isbn);
            book.setName(name);
            book.setAuthor(author);
            book.save();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                java.util.logging.Logger.getLogger(Books.class.getName()).log(Level.SEVERE, null, ex);
            }
            newBook();
        }

    }

    public static void bookList() {
        List<Book> books = Book.findAll();
        render(books);
    }

    public static void delete(int id) {

    }
}
