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
import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.TransactionWork;

/**
 *
 * @author esilv
 */
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

    List<User> getUsers() {
        List<User> users = new ArrayList<User>();
        try ( Session session = driver.session()) {
            int nb = session.readTransaction(new TransactionWork<Integer>() {
                @Override
                public Integer execute(Transaction tx) {
                    int i = 0;
                    StatementResult result = tx.run(
                            "MATCH (u:User)"
                            + "RETURN u.id as id, u.country as country, u.age as age, u.nbContributions as nbContributions "
                            + "ORDER BY u.nbContributions ASC");
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

    List<Review> getReviews(final String user) {
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

    void addArea0Link(Map<String, Object> parameters) {
        try ( Session session = driver.session()) {
            int nb = session.readTransaction(new TransactionWork<Integer>() {
                @Override
                public Integer execute(Transaction tx) {
                    int i = 0;
                    StatementResult result = tx.run(
                            "MERGE (a1:Area_0{gid:$name_01}) "
                            + "MERGE (a2:Area_0{gid:$name_02}) "
                            + "MERGE (a1) -[v:trip{age:$age,nat:$nat,year:$year,month:$month}]-> (a2) "
                            + "ON CREATE SET v.nb = 1\n"
                            + "ON MATCH SET v.nb = v.nb+1",
                            parameters);
                    return new Integer(i);
                }
            });
        }
    }

    void addArea2Link(Map<String, Object> parameters) {
        try ( Session session = driver.session()) {
            int nb = session.writeTransaction(new TransactionWork<Integer>() {
                @Override
                public Integer execute(Transaction tx) {
                    int i = 0;
                    StatementResult result = tx.run(
                            "MERGE (a1:Area_2{gid:$gid_21,gid_0:\"FRA\"}) "
                            + "MERGE (a2:Area_2{gid:$gid_22, gid_0:\"FRA\"}) "
                            + "MERGE (a1) -[v:trip{age:$age,nat:$nat,year:$year,month:$month}]-> (a2) "
                            + "ON CREATE SET v.nb = 1 "
                            + "ON MATCH SET v.nb = v.nb+1",
                            parameters);
                    return new Integer(i);
                }
            });
        }
    }

    void addArea3Link(Map<String, Object> parameters) {
        try ( Session session = driver.session()) {
            int nb = session.readTransaction(new TransactionWork<Integer>() {
                @Override
                public Integer execute(Transaction tx) {
                    int i = 0;
                    StatementResult result = tx.run(
                            "MERGE (a1:Area_3{gid:$gid_31}) "
                            + "MERGE (a2:Area_3{gid:$gid_32}) "
                            + "MERGE (a1) -[v:trip{age:$age,nat:$nat,year:$year,month:$month}]-> (a2) "
                            + "ON CREATE SET v.nb = 1\n"
                            + "ON MATCH SET v.nb = v.nb+1",
                            parameters);
                    return new Integer(i);
                }
            });
        }
    }

    void addArea4Link(Map<String, Object> parameters) {
        try ( Session session = driver.session()) {
            int nb = session.readTransaction(new TransactionWork<Integer>() {
                @Override
                public Integer execute(Transaction tx) {
                    int i = 0;
                    StatementResult result = tx.run(
                            "MERGE (a1:Area_4{gid:$gid_41}) "
                            + "MERGE (a2:Area_4{gid:$gid_42}) "
                            + "MERGE (a1) -[v:trip{age:$age,nat:$nat,year:$year,month:$month}]-> (a2) "
                            + "ON CREATE SET v.nb = 1\n"
                            + "ON MATCH SET v.nb = v.nb+1",
                            parameters);
                    return new Integer(i);
                }
            });
        }
    }
}
