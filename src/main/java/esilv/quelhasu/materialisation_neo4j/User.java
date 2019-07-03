/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esilv.quelhasu.materialisation_neo4j;

import org.neo4j.driver.v1.Record;

/**
 * User class
 * 
 * @author esilv
 */
public class User {

    public String id;
    public String nationality;
    public String age;

    public String getId() {
        return id;
    }

    public String getNationality() {
        return nationality;
    }

    public String getAge() {
        return age;
    }
    
    public User(String id, String nationality, String age){
        this.id = id;
        this.nationality = nationality;
        this.age = age;
    }

    public User(){}
    
    public static User processRecord(Record record) {
        User u = new User();
        try {
            u.id = record.get("id").asString();
        } catch (Exception e) {
        }
        try {
            u.nationality = record.get("country").asString();
        } catch (Exception e) {
        }
        try {
            u.age = record.get("age").asString();
        } catch (Exception e) {
        }
        return u;
    }

    @Override
    public String toString() {
        return id + " " + nationality + " " + age;
    }

}
