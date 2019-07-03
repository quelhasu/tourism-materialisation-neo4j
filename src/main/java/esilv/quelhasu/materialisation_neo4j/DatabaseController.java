/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esilv.quelhasu.materialisation_neo4j;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author esilv
 */

@RestController
public class DatabaseController {
     private static final String template = "Hello, %s!";
     
     @Autowired
     Database db = new Database();
     
     @RequestMapping("/test")
     public User test(@RequestParam(value="name", defaultValue="World") String name){
             return new User("1", "France", "25");
    }
     
     @RequestMapping(value = "/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
     public List<User> getUsers(){
         return db.getUsers();
     }
     
     @RequestMapping(value="/reviews", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
     public List<Review> getReviews(@RequestParam(value="limit", defaultValue="10") Integer limit){
        return db.getLastReviews(limit);
     }
     
     @GetMapping(value="/statistics", produces=MediaType.APPLICATION_JSON_VALUE)
     @Cacheable(value="statisticsCache")
     public JSONObject getStats(){
         JSONObject obj = new JSONObject();
         String last_update = "1560556800000";
         
         DatabaseStats dbs = db.getUserLocationRelStat(1);
         
         obj.put("nb_users", dbs.getNbUsers());
         obj.put("nb_reviews", dbs.getNbReviews());
         obj.put("nb_locations", dbs.getNbLocations());
         obj.put("latest_update", last_update);
         return obj;
     }
     
     @GetMapping(value="/update", produces=MediaType.APPLICATION_JSON_VALUE)
     @CacheEvict(value="statisticsCache", allEntries=true)
     public int updateDatabase(){
         db.updateDatabase();
         return 10;
     }
}
