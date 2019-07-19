/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esilv.quelhasu.materialisation_neo4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.TransactionWork;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Class to communication with Neo4j database
 *
 * @author esilv
 */
@Service
public class Database {

    private final Driver driver;
    private final String defaultURI = "bolt://localhost:7687";
    private final String defaultUser = "neo4j";
    private final String defaultMdp = "admin";

    public Database() {
        driver = GraphDatabase.driver(defaultURI, AuthTokens.basic(defaultUser, defaultMdp));
    }

    public Database(String uri, String user, String password) {
        driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
    }

    public void close() throws Exception {
        driver.close();
    }

    /**
     * Returns all users and classifies them by number of contributions.
     *
     * @return List of users.
     *
     */
    public List<User> getUsers(int limit) {
        List<User> users = new ArrayList<User>();
        try ( Session session = driver.session()) {
            Map<String, Object> parameters = Collections.singletonMap("limit", limit);
            int nb = session.readTransaction(new TransactionWork<Integer>() {
                @Override
                public Integer execute(Transaction tx) {
                    int i = 0;
                    StatementResult result = tx.run(
                            "MATCH (u:User)"
                            + "RETURN u.id as id, u.country as country, u.age as age, u.nbContributions as nbContributions "
                            + "ORDER BY u.nbContributions ASC LIMIT $limit", parameters);
                    while (result.hasNext()) {
                        users.add(User.processRecord(result.next()));
                        i++;
                    }
                    return new Integer(i);
                }
            });
        }
        return users;
    }

    /**
     * Returns all users who contributed after the last update date and
     * classifies them by number of contributions.
     *
     * @return List of users.
     *
     */
    List<User> getUsers(int limit, String lastUpdate) {
        List<User> users = new ArrayList<User>();
        try ( Session session = driver.session()) {
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("limit", limit);
            parameters.put("lastUpdate", lastUpdate);
            int nb = session.readTransaction(new TransactionWork<Integer>() {
                @Override
                public Integer execute(Transaction tx) {
                    int i = 0;
                    StatementResult result = tx.run(
                            "MATCH (u:User) -[r:review]-> (l:Location) "
                            + "WHERE r.date_review >= toInteger($lastUpdate) "
                            + "RETURN DISTINCT u.id as id, u.country as country, "
                            + "u.age as age, u.nbContributions as nbContributions  "
                            + "ORDER BY u.nbContributions DESC LIMIT $limit", parameters);
                    while (result.hasNext()) {
                        users.add(User.processRecord(result.next()));
                        i++;
                    }
                    return new Integer(i);
                }
            });
        }
        return users;
    }

    /**
     * Returns all reviews made by user on a location.
     *
     * @return List of reviews.
     *
     */
    public List<Review> getReviews(final String user) {
        List<Review> reviews = new ArrayList<Review>();
        try ( Session session = driver.session()) {
            Map<String, Object> parameters = Collections.singletonMap("id", user);
            int nb = session.readTransaction(new TransactionWork<Integer>() {
                @Override
                public Integer execute(Transaction tx) {
                    int i = 0;
                    StatementResult result = tx.run(
                            "MATCH (u:User{id:$id}) -[r:review]-> (l:Location) "
                            + "RETURN l.name_0 as name_0, l.gid_0 as gid_0,"
                            + "l.name_1 as name_1, l.gid_1 as gid_1,"
                            + "l.name_2 as name_2, l.gid_2 as gid_2,"
                            + "l.name_3 as name_3, l.gid_3 as gid_3,"
                            + "l.name_4 as name_4, l.gid_4 as gid_4,"
                            + "l.gadm36 as shape_gid, r.year as year, r.month as month, r.date_review as date_review, r.date_visit as date_visit "
                            + "ORDER BY r.date_review ASC, r.date_visit ASC",
                            parameters);
                    while (result.hasNext()) {
                        reviews.add(Review.processRecord(result.next()));
                        i++;
                    }
                    return new Integer(i);
                }
            });
        }
        return reviews;
    }

    /**
     * Returns all reviews made by user on a location with date_review greater
     * than last update.
     *
     * @return List of reviews.
     *
     */
    List<Review> getReviews(String user, String lastUpdate) {
        List<Review> reviews = new ArrayList<Review>();
        try ( Session session = driver.session()) {
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("id", user);
            parameters.put("lastUpdate", lastUpdate);
            int nb = session.readTransaction(new TransactionWork<Integer>() {
                @Override
                public Integer execute(Transaction tx) {
                    int i = 0;
                    StatementResult result = tx.run(
                            "MATCH (u:User{id:$id}) -[r:review]-> (l:Location) "
                            + "WHERE r.date_review >= toInteger($lastUpdate) "
                            + "RETURN l.name_0 as name_0, l.gid_0 as gid_0,"
                            + "l.name_1 as name_1, l.gid_1 as gid_1,"
                            + "l.name_2 as name_2, l.gid_2 as gid_2,"
                            + "l.name_3 as name_3, l.gid_3 as gid_3,"
                            + "l.name_4 as name_4, l.gid_4 as gid_4,"
                            + "l.gadm36 as shape_gid, r.year as year, r.month as month, r.date_review as date_review, r.date_visit as date_visit "
                            + "ORDER BY r.date_review ASC, r.date_visit ASC",
                            parameters);
                    while (result.hasNext()) {
                        reviews.add(Review.processRecord(result.next()));
                        i++;
                    }
                    return new Integer(i);
                }
            });
        }
        return reviews;
    }

    /**
     * Creates trip relationship between two Area_0.
     *
     */
    public void addArea0Link(Map<String, Object> parameters) {
        try ( Session session = driver.session()) {
            int nb = session.readTransaction(new TransactionWork<Integer>() {
                @Override
                public Integer execute(Transaction tx) {
                    int i = 0;
                    StatementResult result = tx.run(
                            "MERGE (a1:Area_0{gid:$name_01}) "
                            + "MERGE (a2:Area_0{gid:$name_02}) "
                            + "MERGE (a1) -[v:trip{age:$age,nat:$nat,year:toInteger($year),month:toInteger($month)}]-> (a2) "
                            + "ON CREATE SET v.nb = 1\n"
                            + "ON MATCH SET v.nb = v.nb+1",
                            parameters);
                    return new Integer(i);
                }
            });
        }
    }

    /**
     * Creates trip relationship between two Area_2.
     *
     */
    public void addArea2Link(Map<String, Object> parameters) {
        try ( Session session = driver.session()) {
            int nb = session.writeTransaction(new TransactionWork<Integer>() {
                @Override
                public Integer execute(Transaction tx) {
                    int i = 0;
                    StatementResult result = tx.run(
                            "MERGE (a1:Area_2{gid:$gid_21,gid_0:\"FRA\"}) "
                            + "MERGE (a2:Area_2{gid:$gid_22, gid_0:\"FRA\"}) "
                            + "MERGE (a1) -[v:trip{age:$age,nat:$nat,year:toInteger($year),month:toInteger($month)}]-> (a2) "
                            + "ON CREATE SET v.nb = 1 "
                            + "ON MATCH SET v.nb = v.nb+1",
                            parameters);
                    return new Integer(i);
                }
            });
        }
    }

    /**
     * Creates trip relationship between two Area_1.
     *
     */
    public void addArea1Link(Map<String, Object> parameters) {
        try ( Session session = driver.session()) {
            int nb = session.writeTransaction(new TransactionWork<Integer>() {
                @Override
                public Integer execute(Transaction tx) {
                    int i = 0;
                    StatementResult result = tx.run(
                            "MERGE (a1:Area_1{gid:$gid_11,gid_0:\"FRA\"}) "
                            + "MERGE (a2:Area_1{gid:$gid_12, gid_0:\"FRA\"}) "
                            + "MERGE (a1) -[v:trip{age:$age,nat:$nat,year:toInteger($year),month:toInteger($month)}]-> (a2) "
                            + "ON CREATE SET v.nb = 1 "
                            + "ON MATCH SET v.nb = v.nb+1",
                            parameters);
                    return new Integer(i);
                }
            });
        }
    }

    /**
     * Creates trip relationship between two Area_3.
     *
     */
    public void addArea3Link(Map<String, Object> parameters) {
        try ( Session session = driver.session()) {
            int nb = session.readTransaction(new TransactionWork<Integer>() {
                @Override
                public Integer execute(Transaction tx) {
                    int i = 0;
                    StatementResult result = tx.run(
                            "MERGE (a1:Area_3{gid:$gid_31}) "
                            + "MERGE (a2:Area_3{gid:$gid_32}) "
                            + "MERGE (a1) -[v:trip{age:$age,nat:$nat,year:toInteger($year),month:toInteger($month)}]-> (a2) "
                            + "ON CREATE SET v.nb = 1\n"
                            + "ON MATCH SET v.nb = v.nb+1",
                            parameters);
                    return new Integer(i);
                }
            });
        }
    }

    /**
     * Creates trip relationship between two Area_4.
     *
     */
    public void addArea4Link(Map<String, Object> parameters) {
        try ( Session session = driver.session()) {
            int nb = session.readTransaction(new TransactionWork<Integer>() {
                @Override
                public Integer execute(Transaction tx) {
                    int i = 0;
                    StatementResult result = tx.run(
                            "MERGE (a1:Area_4{gid:$gid_41}) "
                            + "MERGE (a2:Area_4{gid:$gid_42}) "
                            + "MERGE (a1) -[v:trip{age:$age,nat:$nat,year:toInteger($year),month:toInteger($month)}]-> (a2) "
                            + "ON CREATE SET v.nb = 1\n"
                            + "ON MATCH SET v.nb = v.nb+1",
                            parameters);
                    return new Integer(i);
                }
            });
        }
    }

    /**
     * Returns last reviews made by user on a location.
     *
     * @param Integer limit : max number of reviews
     *
     * @return List of reviews.
     *
     */
    List<Review> getLastReviews(Integer limit) {
        List<Review> reviews = new ArrayList<Review>();
        try ( Session session = driver.session()) {
            Map<String, Object> parameters = Collections.singletonMap("limit", limit);
            int nb = session.readTransaction(new TransactionWork<Integer>() {
                @Override
                public Integer execute(Transaction tx) {
                    int i = 0;
                    StatementResult result = tx.run(
                            "MATCH (u:User) -[r:review]-> (l:Location) \n"
                            + "WHERE r.date_review >= 1558742400000  and l.name_4 is not null\n"
                            + "RETURN u.country as user,\n"
                            + "l.name_4 as name_4, l.gid_4 as gid_4,\n"
                            + "l.gadm36 as shape_gid, r.year as year, r.month as month, r.date_review as date_review, r.date_visit as date_visit\n"
                            + "LIMIT $limit",
                            parameters);
                    while (result.hasNext()) {
                        reviews.add(Review.processRecord(result.next()));
                        i++;
                    }
                    return new Integer(i);
                }
            });
        }
        return reviews;
    }

//    @Cacheable(value = "statisticsCache", key = "#last_update", sync = true)
    DatabaseStats getUserLocationRelStat(int last_update) {
        DatabaseStats dbs = new DatabaseStats();
        try ( Session session = driver.session()) {
            int nb = session.readTransaction(new TransactionWork<Integer>() {
                @Override
                public Integer execute(Transaction tx) {
                    int i = 0;
                    StatementResult result = tx.run(
                            "MATCH (u:User) -[r:review]-> (l:Location) RETURN count(distinct u) as nbUsers, \n"
                            + "count(distinct l) as nbLocations, \n"
                            + "count(r) as nbReviews"
                    );
                    dbs.setParams(result.single());
                    return new Integer(i);
                }
            });
        }
        return dbs;
    }

    void updateDatabase() {
        System.out.println("Hello, i'm updating the database");
    }

    /**
     * Add all new users uploaded.
     *
     * @param users
     * @return number of transactions
     */
    public int addUsers(List<User> users) {
        try ( Session session = driver.session()) {
            int nbUsers = 0;
            for (final User user : users) {
                Map<String, Object> parameters = new HashMap<String, Object>();
                parameters.put("memberID", user.getId());
                parameters.put("location", user.getLocation());
                parameters.put("country", user.getNationality());
                parameters.put("sexe", user.getSexe());
                parameters.put("age", user.getAge());
                parameters.put("nbAvis", user.getNbAvis());
                parameters.put("nbContributions", user.getNbContributions());
                nbUsers += session.writeTransaction(new TransactionWork<Integer>() {
                    @Override
                    public Integer execute(Transaction tx) {
                        tx.run("MERGE(u:User{id:$memberID}) "
                                + "SET u.location=$location, u.country=$country, "
                                + "u.sexe=$sexe, u.age=$age, "
                                + "u.nbAvis=toInteger($nbAvis), "
                                + "u.nbContributions=toInteger($nbContributions)",
                                parameters);
                        return 1;
                    }
                });
                parameters.clear();
            }
            return nbUsers;
        }
    }

    int addLocations(List<Location> locations) {
        try ( Session session = driver.session()) {
            int nbLocations = 0;
            for (final Location loc : locations) {
                Map<String, Object> parameters = new HashMap<String, Object>();
                parameters.put("id", loc.getId());
                parameters.put("nom", loc.getNom());
                parameters.put("rating", loc.getRating());
                parameters.put("latitude", loc.getLatitude());
                parameters.put("longitude", loc.getLongitude());
                parameters.put("typeR", loc.getTypeR());
                parameters.put("gadm36", loc.getGadm36());
                nbLocations += session.writeTransaction(new TransactionWork<Integer>() {
                    @Override
                    public Integer execute(Transaction tx) {
                        tx.run("MERGE(loc:Location{id:toInteger($id), "
                                + "nom:$nom, rating:toFloat($rating), "
                                + "latitude:toFloat($latitude), "
                                + "longitude:toFloat($longitude), "
                                + "typeR:$typeR, "
                                + "gadm36:toInteger($gadm36)})",
                                parameters);
                        return 1;
                    }
                });
                parameters.clear();
            }
            return nbLocations;
        }
    }

    int addReviews(List<Review> reviews) {
        try ( Session session = driver.session()) {
            int nbReviews = 0;
            for (final Review r : reviews) {
                Map<String, Object> parameters = new HashMap<String, Object>();
                parameters.put("idauteur", r.getIdAuteur());
                parameters.put("idplace", r.getIdPlace());
                parameters.put("note", r.getNote());
                parameters.put("date_review", r.getjDate_review());
                parameters.put("date_visit", r.getjDate_visit());
                parameters.put("langue", r.getLangue());
                nbReviews += session.writeTransaction(new TransactionWork<Integer>() {
                    @Override
                    public Integer execute(Transaction tx) {
                        tx.run("MATCH (u:User{id:$idauteur} )"
                                + "MATCH (l:Location{id:toInteger($idplace)} )"
                                + "MERGE(u) -[r:review"
                                + "{"
                                + "  note:toInteger($note), "
                                + "  date_review: apoc.date.parse($date_review, 'ms','yyyy-MM-dd'), "
                                + "  date_visit: apoc.date.parse($date_visit, 'ms','yyyy-MM-dd'), "
                                + "  lang:$langue,"
                                + "  year: toInteger(apoc.date.format(apoc.date.parse(toString($date_review),'ms','yyyy-MM-dd'),'ms','yyyy')), \n"
                                + "  month: toInteger(apoc.date.format(apoc.date.parse(toString($date_visit),'ms','yyyy-MM-dd'),'ms','MM'))"
                                + "}]-> (l)",
                                parameters);
                        return 1;
                    }
                });
                parameters.clear();
            }
            return nbReviews;
        }
    }
}
