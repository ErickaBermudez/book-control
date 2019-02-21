/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.util.*;

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

    public static void addBook(Integer isbn, String name, String author, Blob extract) {
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
        }else{
            
            System.out.println("Okay!");
        }
        
        
        
        
    }
}
