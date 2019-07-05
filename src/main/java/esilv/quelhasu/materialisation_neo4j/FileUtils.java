/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esilv.quelhasu.materialisation_neo4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author esilv
 */
public class FileUtils {
    
     /**
     * Create file buffered reader according Multipart file.
     *
     * @param file mulitpartFile
     * @return A BufferedReader
     * @throws java.io.FileNotFoundException
     */
    public static BufferedReader fileToBuffer(MultipartFile file) throws FileNotFoundException, IOException {
        File inputF = convertMultiPartToFile(file);
        InputStream inputFS = new FileInputStream(inputF);
        inputF.delete();
        return new BufferedReader(new InputStreamReader(inputFS));
    }

    /**
     * Convert a Multipart file to Java File.
     * 
     * @param file mulitpartFile
     * @return A File
     * @throws IOException 
     */
    public static File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convFile)) {
            fos.write(file.getBytes());
        }
        return convFile;
    }
}
