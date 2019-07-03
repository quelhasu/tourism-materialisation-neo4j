/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esilv.quelhasu.materialisation_neo4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Main class to launch materialization
 * @author esilv
 */
public class Main {
    Database db = new Database();

    public Main() {
        //start("Nouvelle-Aquitaine");
        System.out.println("Test de la nouvelle interface client!");
        test();
    }
    
    /**
     * Test function
     *
     */
    private void test() {
        List<User> users;
        users = db.getUsers();
        List<Review> reviews = db.getReviews(users.get(58).id);
        System.out.println(reviews);
    }
    
    /**
     * Materialization procedures
     *
     */
    private void start(String region) {
        List<User> users;
        users = db.getUsers();
        int nbArea4 = 0, nbArea0 = 0,nbArea1 = 0, nbArea2 = 0, nbArea3 = 0;
        int i = 0;
        for (User u : users) {
            List<Review> reviews = db.getReviews(u.id);
            Review old = null;
            nbArea4 = 0;
            nbArea2 = 0;
            nbArea3 = 0;
            nbArea0 = 0;
            nbArea1 = 0;
            for (Review r : reviews) {
                if (old != null && old.shape_gid != r.shape_gid
                        && r.date_review - old.date_review < 1000 * 3600 * 24 * 7) {
                    if (old.name_1.compareTo(region) == 0
                            && r.name_1.compareTo(region) == 0) {
//                        System.out.println("addArea4Link between\n\t" + old + "\n\t" + r);
//                        addArea4Link(u, old, r);
                        nbArea4++;
                    }
                    if (!old.name_2.equals(r.name_2)
                            && r.name_0.compareTo("France") == 0
                            && old.name_0.compareTo("France") == 0) {
//                        System.out.println("addArea2Link between\n\t" + old + "\n\t" + r);
//                        addArea2Link(u, old, r);
                        nbArea2++;

                    }
                    if (!old.name_3.equals(r.name_3)
                            && r.name_0.compareTo("France") == 0
                            && old.name_0.compareTo("France") == 0) {
//                        System.out.println("addArea3Link between\n\t" + old + "\n\t" + r);
//                        addArea3Link(u, old, r);
                        nbArea3++;

                    }
                    if (!old.name_1.equals(r.name_1) 
                            && r.name_0.compareTo("France") == 0
                            && old.name_0.compareTo("France") == 0){
                            addArea1Link(u, old, r);
                        }
                    if (!old.name_0.equals(r.name_0) && !old.name_0.equals("null")
                            && !r.name_0.equals("null")) {
//                        System.out.println("addArea0Link between\n\t" + old + "\n\t" + r);
//                        addArea0Link(u, old, r);
                        nbArea0++;
                    }
                }
                old = r;
            }
//            if (nbArea0 > 0 || nbArea2 > 0 || nbArea3 > 0 || nbArea4 > 0) {
//                System.out.println(u.nationality + ", a_0:" + nbArea0 + ", a_2:" + nbArea2 + ", a_3:" + nbArea3 + ", a_4:" + nbArea4);
//            }
            i++;
        }

    }
    
    public static void main(String[] args) {
        new Main();
    }
    
    /**
     * Creates trip relationship between two Area_0.
     *
     */
    private void addArea0Link(User u, Review old, Review r) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("age", u.age);
        parameters.put("nat", u.nationality);
        parameters.put("gid_01", old.gid_0);
        parameters.put("gid_02", r.gid_0);
        parameters.put("year", old.year + "");
        parameters.put("month", old.month + "");
        db.addArea0Link(parameters);
        parameters.clear();
    }
    
    /**
     * Creates trip relationship between two Area_1.
     *
     */
    private void addArea1Link(User u, Review old, Review r) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("age", u.age);
        parameters.put("nat", u.nationality);
        parameters.put("gid_11", old.gid_1);
        parameters.put("gid_12", r.gid_1);
        parameters.put("year", old.year + "");
        parameters.put("month", old.month + "");
        db.addArea1Link(parameters);
        parameters.clear();
    }

    /**
     * Creates trip relationship between two Area_2.
     *
     */
    private void addArea2Link(User u, Review old, Review r) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("age", u.age);
        parameters.put("nat", u.nationality);
        parameters.put("gid_21", old.gid_2);
        parameters.put("gid_22", r.gid_2);
        parameters.put("year", old.year + "");
        parameters.put("month", old.month + "");
        db.addArea2Link(parameters);
        parameters.clear();
    }

    /**
     * Creates trip relationship between two Area_3.
     *
     */
    private void addArea3Link(User u, Review old, Review r) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("age", u.age);
        parameters.put("nat", u.nationality);
        parameters.put("gid_31", old.gid_3);
        parameters.put("gid_32", r.gid_3);
        parameters.put("year", old.year + "");
        parameters.put("month", old.month + "");
        db.addArea3Link(parameters);
        parameters.clear();
    }

    /**
     * Creates trip relationship between two Area_4.
     *
     */
    private void addArea4Link(User u, Review old, Review r) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("age", u.age);
        parameters.put("nat", u.nationality);
        parameters.put("gid_41", old.gid_4);
        parameters.put("gid_42", r.gid_4);
        parameters.put("year", old.year + "");
        parameters.put("month", old.month + "");
        db.addArea4Link(parameters);
        parameters.clear();
    }
}
