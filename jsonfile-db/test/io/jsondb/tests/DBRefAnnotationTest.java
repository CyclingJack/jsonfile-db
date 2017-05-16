/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.jsondb.tests;

import io.jsondb.JsonDBTemplate;
import java.io.File;
import java.util.UUID;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import resources.Pojos.DOBehoerde;
import resources.Pojos.DOHaushaltsjahr;

/**
 *
 * @author Andreas
 */
public class DBRefAnnotationTest {
    
  private String dbFilesLocation = "test/resources/Pojos/DBRefsTests";
  private File dbFilesFolder = new File(dbFilesLocation);
  private File behoerdejson = new File(dbFilesFolder, "behoerde.json");
  private File haushaltsjahrjson = new File(dbFilesFolder, "haushaltsjahr.json");

  private JsonDBTemplate jsonDBTemplate = null;
  
  private String behoerdenkey;
  private String haushaltsjahrkey;
    
    
    public DBRefAnnotationTest() {
    }
    
    
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        dbFilesFolder.mkdir();
        jsonDBTemplate = new JsonDBTemplate(dbFilesLocation, "resources/Pojos", null, false, null);
    }
    
    @After
    public void tearDown() {
      //  Util.delete(dbFilesFolder);
//        behoerdejson.delete();
//        haushaltsjahrjson.delete();
        
    }

    @Test
    public void behoerdeanlegen() {
        if (!jsonDBTemplate.collectionExists(DOBehoerde.class)) {
            jsonDBTemplate.createCollection(DOBehoerde.class);
        }
        if (!jsonDBTemplate.collectionExists(DOHaushaltsjahr.class)) {
            jsonDBTemplate.createCollection(DOHaushaltsjahr.class);
        }
        
        
        
        DOBehoerde b = new DOBehoerde();
        b.setBehoerdenname("PD Hannover");
        
        this.behoerdenkey = UUID.randomUUID().toString();
        b.setKey(this.behoerdenkey);
        b.setOrgbezeichnung("Organisationsbezeichnung");
        jsonDBTemplate.insert(b);
        
        
        DOHaushaltsjahr h = new DOHaushaltsjahr();
        h.setJahr("2017");
        this.haushaltsjahrkey = UUID.randomUUID().toString();
        h.setKey(haushaltsjahrkey);
        jsonDBTemplate.insert(h);
        
       // JsonDBJoin jj = new JsonDbJoin(b, h);
        
        
//        System.out.println("source " + r[0].toString());
//        System.out.println("dest " + r[1].toString());
        
        
//        DOBehoerde c = new DOBehoerde();
//        c = jsonDBTemplate.findById(this.behoerdenkey,DOBehoerde.class);
//        System.out.println("Behoerde: " + c.getBehoerdenname());
//        
//        Map<String,CollectionMetaData> m = jsonDBTemplate.getCollectionMetaDataMap();
//        for (Map.Entry<String, CollectionMetaData> entry : m.entrySet()) {
//            String key = entry.getKey();
//            CollectionMetaData value = entry.getValue();
//            System.out.println("Key " + key);
//            
//        }
//        
    }
    
    
    

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
