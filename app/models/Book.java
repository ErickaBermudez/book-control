/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models;

import javax.persistence.*;
import play.data.validation.*;
import play.db.jpa.*;

/**
 *
 * @author dsi
 */
@Entity 
public class Book extends Model{

    public int getISBN() {
        return ISBN;
    }

    public void setISBN(int ISBN) {
        this.ISBN = ISBN;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Blob getExtract() {
        return extract;
    }

    public void setExtract(Blob extract) {
        this.extract = extract;
    }
    /***** genres ******/
    final int COMEDY = 0; 
    final int FANTASY = 1; 
    final int ROMANCE = 2;
    final int HORROR = 3; 
    /*******************/
    
    @Required
    public int ISBN; 
    
    @Required
    public String name; 
    
    @Required
    public String author;
    
    public Blob extract;
    
}
