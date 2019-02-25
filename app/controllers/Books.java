/**
 * @author Ericka Bermudez
 * @date 21/02/2019
 */
package controllers;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import models.*;

/* Imports for JDOM */
import org.jdom2.*;
import org.jdom2.Content.CType;
import org.jdom2.filter.Filters;
import org.jdom2.input.*;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.util.IteratorIterable;
import play.mvc.*;

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
        // System.out.println("id = " + id);
        long idLong = Long.valueOf(id);
        // System.out.println("id: " + idLong);
        Book book = new Book();
        book = Book.findById(idLong);
        //System.out.println("Name: " + book.name);
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
                Thread.sleep(2000);
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

    public static void delete(Long id) {
        Book book;
        book = Book.findById(id);
        book.delete();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            java.util.logging.Logger.getLogger(Books.class.getName()).log(Level.SEVERE, null, ex);
        }
        bookList();
    }

    public static void generateXML(String[] args) {
        List<Book> books = Book.findAll();
        try {
            Element list = new Element("list");
            Document doc = new Document(list);

            for (int i = 0; i < books.size(); i++) {
                Book currentBook = books.get(i);
                Element book = new Element("book");
                book.setAttribute(new Attribute("id", Long.toString(currentBook.id)));
                book.addContent(new Element("isbn").setText(currentBook.ISBN));
                book.addContent(new Element("name").setText(currentBook.name));
                book.addContent(new Element("author").setText(currentBook.author));
                doc.getRootElement().addContent(book);
            }

            // new xmloutputter
            XMLOutputter xmlOutput = new XMLOutputter();

            // display nice nice
            xmlOutput.setFormat(Format.getPrettyFormat());
            xmlOutput.output(doc, new FileWriter("c:\\books.xml"));

            System.out.println("File saved!");
        }catch(IOException io){
            System.out.println(io.getMessage());
        }

    }

    public static void readXML(String[] args) {
        String fileName = "books.xml";
        Document document = getSAXParsedDocument(fileName);

        Element rootNode = document.getRootElement();
        System.out.println("Root Element :: " + rootNode.getName());

    }

    private static Document getSAXParsedDocument(final String fileName) {
        SAXBuilder builder = new SAXBuilder();
        Document document = null;
        try {
            document = builder.build(fileName);
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }

}
