/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.List;
import javax.persistence.*;
import play.data.validation.*;
import play.db.jpa.*;

/**
 *
 * @author dsi
 */
@Entity
public class Book extends Model {

    @Required
    public String ISBN;

    @Required
    public String name;

    @Required
    public String author;

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    
    public List<Book> getCurrentBooks(){
        List<Book> books; 
        books = Book.findAll();
        return books;
    }
    
    public void deleteBooks(){
        Book.deleteAll();
    }

}
