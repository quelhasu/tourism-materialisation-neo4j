/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esilv.quelhasu.materialisation_neo4j;

import java.nio.charset.StandardCharsets;
import java.util.List;
import net.minidev.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author esilv
 */
public class DatabaseControllerTest {

    public DatabaseControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of newUsersUpload method, of class DatabaseController.
     * @throws java.lang.Exception
     */
    @Test
    public void testNewUsersUpload() throws Exception {
        System.out.println("newUsersUpload");
        MockMultipartFile file = new MockMultipartFile("users", "users", "text/csv", 
                ("memberID	location	country	sexe	age	nbAvis	nbContributions\n"
                        + "125484	Nancy	France	0	25-34	63	210"
                        + "125485	Paris	France	1	25-34	55	154").getBytes());
        DatabaseController instance = new DatabaseController();
        int expResult = 1;
        int result = instance.newUsersUpload(file);
        assertEquals(expResult, result);
    }

    /**
     * Test of file uploaded to server.
     */
    @Test
    public void testNewUsersUploadWrongFilename() throws Exception {
        System.out.println("newUsersUpload File Test");
        MockMultipartFile file = new MockMultipartFile("foo", "foo.properties", "text/plain", "bar".getBytes());
        System.out.println(file.getOriginalFilename());
        DatabaseController instance = new DatabaseController();
        int expResult = -1;
        int result = instance.newUsersUpload(file);
        assertEquals(expResult, result);
    }
}
