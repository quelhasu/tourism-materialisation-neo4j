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
import org.junit.Ignore;
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
        assertEquals(expResult, instance.addReviews(file));
    }

    /**
     * Test uploaded multipart file with users with the number of line returned.
     *
     * @throws java.lang.Exception
     */
    @Ignore("only local")
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
    @Ignore("only local")
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
     * Test uploaded multipart file with reviews with the number of line
     * returned.
     *
     * @throws java.lang.Exception
     */
    @Ignore("only local")
    @Test
    public void testNewReviewsUpload() throws Exception {
        MockMultipartFile file = new MockMultipartFile("reviews", "reviews", "text/csv",
                ("idplace	idauteur	note	date_review	date_visit	langue\n"
                        + "2533015	A21236ADD4C579E1191D5B4A29831538	3	0000-00-00	0000-00-00	fra\n"
                        + "301820	18AF2575A015C7EE113B7B19F07D2E34	4	0000-00-00	0000-00-00	fra\n"
                        + "622009	F10173FF067C05CB22C9CC9D8951995A	4	2013-07-01	0000-00-00	fra\n"
                        + "739273	623D9911A9034AD0E4BD0D58ECB31BCF	4	0000-00-00	0000-00-00	fra\n"
                        + "317768	61AA5B9476C9F07099062B99C9F1BBC3	3	2013-08-28	2013-08-01	fra\n"
                        + "174974	A64D3DEBC0A05F81FAD552F219E16715	4	2019-05-13	0000-00-00	fra").getBytes());
        DatabaseController instance = new DatabaseController();
        int expResult = 6;
        int result = instance.addReviews(file);
        assertEquals(expResult, result);
    }

    /**
     * Test the getUsers route.
     */
    @Ignore("only local")
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
