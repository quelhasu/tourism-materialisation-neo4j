/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esilv.quelhasu.materialisation_neo4j;

import java.util.Date;
import org.neo4j.driver.v1.Record;
import java.util.function.Function;

/**
 * Review class
 *
 * @author esilv
 */
public class Review {

    public String name_0 = null, name_1 = null,
            name_2 = null, name_3 = null, name_4 = null;
    public String gid_0 = null, gid_1 = null,
            gid_2 = null, gid_3 = null, gid_4 = null;
    public long date_visit = -1, date_review = -1;
    public int shape_gid = -1;
    public int year = -1, month = -1;
    public String userCountry = null;
    private String idPlace = null;
    private String idAuteur = null;
    private String note = null;
    private String langue = null;
    private String jDate_visit = null;
    private String jDate_review = null;

    public Review() {

    }

    public Review(String idplace, String idauteur, String note, String dreview, String dvisit, String langue) {
        this.idPlace = idplace;
        this.idAuteur = idauteur;
        this.note = note;
        this.jDate_visit = dvisit;
        this.jDate_review = dreview;
        this.langue = langue;
    }

    public String getIdPlace() {
        return idPlace;
    }

    public String getIdAuteur() {
        return idAuteur;
    }

    public String getLangue() {
        return langue;
    }

    public String getNote() {
        return note;
    }

    public String getjDate_visit() {
        return jDate_visit;
    }

    public String getjDate_review() {
        return jDate_review;
    }
    
    
    
    

    public static Review processRecord(Record record) {
        Review r = new Review();
        try {
            r.name_0 = record.get("name_0").asString();
        } catch (Exception e) {
        }
        try {
            r.name_1 = record.get("name_1").asString();
        } catch (Exception e) {
        }
        try {
            r.name_2 = record.get("name_2").asString();
        } catch (Exception e) {
        }
        try {
            r.name_3 = record.get("name_3").asString();
        } catch (Exception e) {
        }
        try {
            r.name_4 = record.get("name_4").asString();
        } catch (Exception e) {
        }
        try {
            r.gid_0 = record.get("gid_0").asString();
        } catch (Exception e) {
        }
        try {
            r.gid_1 = record.get("gid_1").asString();
        } catch (Exception e) {
        }
        try {
            r.gid_2 = record.get("gid_2").asString();
        } catch (Exception e) {
        }
        try {
            r.gid_3 = record.get("gid_3").asString();
        } catch (Exception e) {
        }
        try {
            r.gid_4 = record.get("gid_4").asString();
        } catch (Exception e) {
        }
        try {
            r.date_visit = record.get("date_visit").asLong();
        } catch (Exception e) {
        }
        try {
            r.date_review = record.get("date_review").asLong();
        } catch (Exception e) {
        }
        try {
            r.shape_gid = record.get("shape_gid").asInt();
        } catch (Exception e) {
        }
        try {
            r.year = record.get("year").asInt();
        } catch (Exception e) {
        }
        try {
            r.month = record.get("month").asInt();
        } catch (Exception e) {
        }
        try {
            r.userCountry = record.get("user").asString();
        } catch (Exception e) {
        }
        return r;
    }

    /**
     * Path returning all reviews of the database.
     */
    public static Function<String, Review> mapToReview = (String line) -> {
        String[] p = line.split("\\t");// a CSV has comma separated lines
        Review item = new Review(p[0], p[1], p[2], p[3], p[4], p[5]);
        return item;
    };

//    @Override
//    public String toString() {
//        return year + "-" + month + " " + " |\t "
//                + gid_4 + " / " + gid_3 + " / " + gid_2 + " / " + gid_1 + " / " + gid_0
//                + name_4 + " / " + name_3 + " / " + name_2 + " / " + name_1 + " / " + name_0 + " (" + shape_gid + ")";
//    }

    @Override
    public String toString() {
        return "Review{" + "idPlace=" + idPlace + ", idAuteur=" + idAuteur + ", note=" + note + ", langue=" + langue + ", jDate_visit=" + jDate_visit + ", jDate_review=" + jDate_review + '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (o instanceof Review) {
            Review r = (Review) o;
            if (this.date_review == r.date_review && this.date_visit == r.date_visit) {
                return true;
            }
        }
        return false;
    }
}
