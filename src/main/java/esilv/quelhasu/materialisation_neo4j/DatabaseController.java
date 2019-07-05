/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esilv.quelhasu.materialisation_neo4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author esilv
 */
@RestController
public class DatabaseController {

    @Autowired
    Database db = new Database();

    /**
     * Testing path.
     *
     * @param name
     * @return A User.
     */
    @RequestMapping("/test")
    public User test(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new User("1", "France", "25");
    }

    /**
     * Path returning all users of the database.
     *
     * @return A List of User.
     */
    @RequestMapping(value = "/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getUsers() {
        return db.getUsers();
    }

    /**
     * Create new users on the database according incoming CSV file.
     *
     * @param file CSV containing new users.
     * @return The number of users added.
     * @throws java.io.IOException
     */
    @PostMapping(value = "/users", consumes = "multipart/form-data")
    public @ResponseBody
    int newUsersUpload(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.getOriginalFilename().contains("users")) {
            List<User> inputList = new ArrayList<User>();
            JSONObject obj = new JSONObject();
            try ( BufferedReader br = FileUtils.fileToBuffer(file)) {
                inputList = br.lines().skip(1).map(User.mapToUser).collect(Collectors.toList());
            }
            System.out.println("Size fo inputList: " + inputList.size());
            return inputList.size();
        } else {
            return -1;
        }
    }

    /**
     * Path returning all reviews of the database.
     *
     * @param limit max number of comments to be returned.
     * @return A List of Reviews.
     */
    @RequestMapping(value = "/reviews", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Review> getReviews(@RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        return db.getLastReviews(limit);
    }

    /**
     * Path returning statistics of the database.
     *
     * @return A JSON object with statistics.
     */
    @GetMapping(value = "/statistics", produces = MediaType.APPLICATION_JSON_VALUE)
    @Cacheable(value = "statisticsCache")
    public JSONObject getStats() {
        JSONObject obj = new JSONObject();
        String last_update = "1560556800000";

        DatabaseStats dbs = db.getUserLocationRelStat(1);

        obj.put("nb_users", dbs.getNbUsers());
        obj.put("nb_reviews", dbs.getNbReviews());
        obj.put("nb_locations", dbs.getNbLocations());
        obj.put("latest_update", last_update);
        return obj;
    }

    /**
     * Updating the database according to the new pushed data.
     *
     * @return The number of users added.
     * @throws java.io.IOException
     */
    @GetMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @CacheEvict(value = "statisticsCache", allEntries = true)
    public int updateDatabase() throws IOException {
//        String filename = file.getOriginalFilename();
//        List<User> inputList = new ArrayList<User>();
//        JSONObject obj = new JSONObject();
//        BufferedReader br = FileUtils.fileToBuffer(file);
//        inputList = br.lines().skip(1).map(User.mapToUser).collect(Collectors.toList());
//        br.close();
//        System.out.println("Size fo inputList: " + inputList.size());
        return 10;
    }
}
