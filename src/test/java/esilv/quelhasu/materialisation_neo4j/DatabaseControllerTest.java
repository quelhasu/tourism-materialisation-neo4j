/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esilv.quelhasu.materialisation_neo4j;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.mock.web.MockMultipartFile;

/**
 *
 * @author esilv
 */
public class DatabaseControllerTest {

    public DatabaseControllerTest() {
    }

    /**
     * Test the upload of a wrong file name.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testWrongFilename() throws Exception {
        MockMultipartFile file = new MockMultipartFile("foo", "foo.properties", "text/plain", "bar".getBytes());
        DatabaseController instance = new DatabaseController();
        int expResult = -1;
        assertEquals(expResult, instance.addUsers(file));
        assertEquals(expResult, instance.addLocations(file));
    }

    /**
     * Test uploaded multipart file with users with the number of line returned.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testNewUsersUpload() throws Exception {
        MockMultipartFile file = new MockMultipartFile("users", "users", "text/csv",
                ("memberID	location	country	sexe	age	nbAvis	nbContributions\n"
                        + "125484	Nancy	France	0	25-34	63	210\n"
                        + "125485	Paris	France	1	25-34	55	154").getBytes());
        DatabaseController instance = new DatabaseController();
        int expResult = 2;
        int result = instance.addUsers(file);
        assertEquals(expResult, result);
    }

    /**
     * Test uploaded multipart file with locations with the number of line
     * returned.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testNewLocationsUpload() throws Exception {
        MockMultipartFile file = new MockMultipartFile("locations", "locations", "text/csv",
                ("id	nom	rating	latitude	longitude	typeR	gadm36\n"
                        + "72177	The Hotel Captain Cook	4	61.217945	-149.900635	H	323625\n"
                        + "72179	Hotel Finial	5	33.665604	-85.827644	H	323563\n"
                        + "72180	Crenshaw Guest House	5	32.61232	-85.482117	H	323596\n"
                        + "174974	Côte Du Bois	5	34.80693	-87.67681	H	323594").getBytes());
        DatabaseController instance = new DatabaseController();
        int expResult = 4;
        int result = instance.addLocations(file);
        assertEquals(expResult, result);
    }

    /**
     * Test the getUsers route.
     */
    @Test
    public void testGetUsers() {
        DatabaseController instance = new DatabaseController();
        List<User> users = instance.getUsers(5);
        int randomNum = ThreadLocalRandom.current().nextInt(0, users.size() + 1);
        int expResult = 5;
        int result = users.size();
        assertEquals(expResult, result);
        assertTrue(users.get(0) instanceof User);
    }
}
