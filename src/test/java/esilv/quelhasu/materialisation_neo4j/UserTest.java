/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esilv.quelhasu.materialisation_neo4j;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.neo4j.driver.v1.Record;

/**
 *
 * @author esilv
 */
public class UserTest {

    public UserTest() {
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

    @Test
    public void testMapToUser() {
        String line = "125484	Nancy	France	0	25-34	63	210\n"
                + "15424	Nancy	France	0	25-34	63	210\n"
                + "12554	Nancy	France	0	25-34	63	210\n";
        User expResult = new User("125484", "France", "25-34");
        List<User> users = line.lines().map(User.mapToUser).collect(Collectors.toList());
        User result = users.get(0);
        assertEquals(expResult, result);
    }
}
