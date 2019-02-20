/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models;

import javax.persistence.*;
import play.data.validation.*;
import play.db.jpa.Model;

/**
 *
 * @author dsi
 */

public class Book extends Model{
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
    @OneToMany
    public Author author;
    
    @Required
    public int genre; 
    
}
