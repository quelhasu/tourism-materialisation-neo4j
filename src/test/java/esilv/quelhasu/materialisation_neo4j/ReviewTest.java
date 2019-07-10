package esilv.quelhasu.materialisation_neo4j;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author esilv
 */
public class ReviewTest {

    /**
     * Test String line with multiple user with the mapToUser function.
     */
    @Test
    public void testMapToReview() {
        String line = "2533015	A21236ADD4C579E1191D5B4A29831538	3	0000-00-00	0000-00-00	fra\n"
                + "301820	18AF2575A015C7EE113B7B19F07D2E34	4	0000-00-00	0000-00-00	fra\n"
                + "622009	F10173FF067C05CB22C9CC9D8951995A	4	2013-07-01	0000-00-00	fra\n"
                + "739273	623D9911A9034AD0E4BD0D58ECB31BCF	4	0000-00-00	0000-00-00	fra\n"
                + "317768	61AA5B9476C9F07099062B99C9F1BBC3	3	2013-08-28	2013-08-01	fra\n"
                + "174974	A64D3DEBC0A05F81FAD552F219E16715	4	2019-05-13	0000-00-00	fra";
        Review expResult = new Review("739273","623D9911A9034AD0E4BD0D58ECB31BCF","4","0000-00-00","0000-00-00","fra");
        List<Review> reviews = line.lines().map(Review.mapToReview).collect(Collectors.toList());
        Review result = reviews.get(1);
        System.out.println("Expected: " + expResult);
        System.out.println("Result: " + result);
        assertEquals(expResult, result);
    }

}
