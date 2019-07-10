/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esilv.quelhasu.materialisation_neo4j;

import java.util.List;
import java.util.stream.Collectors;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author esilv
 */
public class LocationTest {

    /**
     * Test String line with multiple user with the mapToUser function.
     */
    @Test
    public void testMapToLocation() {
        String line = "72177	The Hotel Captain Cook	4	61.217945	-149.900635	H	323625\n"
                + "72179	Hotel Finial	5	33.665604	-85.827644	H	323563\n"
                + "72180	Crenshaw Guest House	5	32.61232	-85.482117	H	323596\n"
                + "72185	Limestone House Bed & Breakfast	5	34.80693	-87.67681	H	323594";
        Location expResult = new Location("72179", "Hotel Finial", "5", "33.665604", "-85.827644", "H", "323563");
        List<Location> locations = line.lines().map(Location.mapToLocation).collect(Collectors.toList());
        Location result = locations.get(1);
        System.out.println("Expected: "+ expResult);
        System.out.println("Result: "+ result);
        assertEquals(expResult, result);
    }
}
