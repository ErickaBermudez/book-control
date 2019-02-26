/**
 * @author Ericka Bermudez
 * @date 21/02/2019
 */
package controllers;

import java.io.*;
import java.util.*;
import java.util.logging.Level;

import models.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

/* Imports for JDOM */
import org.jdom2.*;
import org.jdom2.input.*;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import play.mvc.*;

/* Imports for Apache PDFBox */
import utils.*;

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
            String route = FileChooser.getRute();
            System.out.println("ROUTE: " + route);
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
            File dir = new File(route);
            dir.mkdirs();
            File file = new File(dir, "books.xml");
            xmlOutput.output(doc, new FileWriter(file));
            //System.out.println("File saved!");
            bookList();
        } catch (IOException io) {
            System.out.println(io.getMessage());
        }
    }

    public static void generatePDF(String[] args) throws IOException {
        PDDocument document = new PDDocument();
        PDPage my_page = new PDPage();
        document.addPage(my_page);
        String route = FileChooser.getRute();
        // For tests
        //route = "/home/dsi/Escritorio";

        // Document information 
        PDDocumentInformation pdd = document.getDocumentInformation();
        pdd.setAuthor("Ericka Bermudez");
        pdd.setTitle("My Books");

        // Content of the document
        PDPageContentStream contentStream = new PDPageContentStream(document, my_page);
        contentStream.beginText();
        contentStream.newLineAtOffset(25, 700);
        contentStream.setFont(PDType1Font.COURIER_BOLD, 40);
        contentStream.setLeading(20f);
        String text = "List of MyBooks";
        contentStream.showText(text);
        contentStream.newLine();
        contentStream.newLine();
        contentStream.setFont(PDType1Font.COURIER_BOLD, 20);
        // We add the books to the text 
        List<Book> books = Book.findAll();
        text = "";
        for (int i = 0; i < books.size(); i++) {
            Book currentBook = books.get(i);
            text = i+1 + ". " + currentBook.name + " by "
                    + currentBook.author;
            contentStream.showText(text);
            contentStream.newLine();
        }
        contentStream.endText();
        contentStream.close();
        document.save(route + "/books.pdf");
        document.close();
        bookList();
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
