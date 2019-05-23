/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esilv.quelhasu.materialisation_neo4j;

import org.neo4j.driver.v1.Record;

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

    public Review() {

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
        return r;
    }

    @Override
    public String toString() {
        return year + "-" + month + " " + date_review + " - " + date_visit + " \n| " 
                + gid_4 + " / " + gid_3 + " / " + gid_2 + " / " + gid_1 + " / " + gid_0
               + name_4 + " / " + name_3 + " / " + name_2 + " / " + name_1 + " / " + name_0 + " (" + shape_gid + ")";
    }
}
