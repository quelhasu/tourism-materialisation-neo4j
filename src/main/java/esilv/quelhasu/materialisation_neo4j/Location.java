/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esilv.quelhasu.materialisation_neo4j;

import java.util.function.Function;

/**
 *
 * @author esilv
 */

//Location{id:toInteger(l.id), nom:l.nom, rating:toFloat(l.rating), 
//latitude:toFloat(l.latitude), longitude:toFloat(l.longitude), type:l.type, 
//typeR:l.typeR, gadm36:toInteger(l.gadm36_gid), country:l.country, region:l.region}

public class Location {
    private String id;
    private String nom;
    private String rating;
    private String latitude;
    private String longitude;
    private String typeR;
    private String gadm36;

    public Location(String id, String nom, String rating, String latitude, String longitude, String typeR, String gadm36) {
        this.id = id;
        this.nom = nom;
        this.rating = rating;
        this.latitude = latitude;
        this.longitude = longitude;
        this.typeR = typeR;
        this.gadm36 = gadm36;
    }
    
    public Location(){
        
    }

    public String getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getRating() {
        return rating;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getTypeR() {
        return typeR;
    }

    public String getGadm36() {
        return gadm36;
    }
    
    
    
    /**
     * Path returning all users of the database.
     */
    public static Function<String, Location> mapToLocation = (String line) -> {
        String[] p = line.split("\\t");// a CSV has comma separated lines
        Location item = new Location(p[0], p[1], p[2], p[3], p[4], p[5], p[6]);
        return item;
    };

    @Override
    public String toString() {
        return "Location{" + "id=" + id + ", nom=" + nom + ", rating=" + rating + ", typeR=" + typeR + ", gadm36=" + gadm36 + '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (o instanceof Location) {
            Location l = (Location) o;
            if (this.id.equals(l.id)) {
                return true;
            }
        }
        return false;
    }
}
