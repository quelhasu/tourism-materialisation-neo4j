/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esilv.quelhasu.materialisation_neo4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 *
 * @author esilv
 */
@SpringBootApplication
@EnableCaching
public class Application {
    
    public static void main(String[] args){
        SpringApplication.run(Application.class, args);
    }
}
