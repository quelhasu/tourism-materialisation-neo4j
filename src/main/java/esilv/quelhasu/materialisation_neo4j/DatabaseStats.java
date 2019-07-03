/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esilv.quelhasu.materialisation_neo4j;

import org.neo4j.driver.v1.Record;

/**
 *
 * @author esilv
 */
public class DatabaseStats {

    private Integer nbUsers;
    private Integer nbReviews;
    private Integer nbLocations;

    public Integer getNbUsers() {
        return nbUsers;
    }

    public Integer getNbReviews() {
        return nbReviews;
    }

    public Integer getNbLocations() {
        return nbLocations;
    }

    void setParams(Record record) {
        try {
            this.nbUsers = record.get("nbUsers").asInt();
        } catch (Exception e) {
        }
        try {
            this.nbReviews = record.get("nbReviews").asInt();
        } catch (Exception e) {
        }
        try {
            this.nbLocations = record.get("nbLocations").asInt();
        } catch (Exception e) {
        }
    }
    
    @Override
    public String toString() {
        return nbLocations + " " + nbReviews + " " + nbUsers;
    }
}
