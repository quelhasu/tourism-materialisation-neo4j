/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esilv.quelhasu.materialisation_neo4j;

import java.util.function.Function;
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

    public User(String id, String nationality, String age) {
        this.id = id;
        this.nationality = nationality;
        this.age = age;
    }

    public User() {
    }

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

    /**
     * Path returning all users of the database.
     */
    public static Function<String, User> mapToUser = (String line) -> {
        String[] p = line.split("\\t");// a CSV has comma separated lines
        User item = new User(p[0], p[2], p[4]);
//        
        System.out.println(p[0]);
//        item.setItemNumber(p[0]);//<-- this is the first column in the csv file
//
//        if (p[3] != null && p[3].trim().length() > 0) {
//            item.setSomeProeprty(p[3]);
//        }
        //more initialization goes here
        return item;
    };

    @Override
    public String toString() {
        return id + " " + nationality + " " + age;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof User) {
            User u = (User) o;
            if (this.id.equals(u.id) && this.nationality.equals(u.nationality)
                    && this.age.equals(u.age)) {
                return true;
            }
        }
        return false;
    }

}
