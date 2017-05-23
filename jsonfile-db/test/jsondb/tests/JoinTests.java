package jsondb.tests;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import jsondb.JsonDBConfig;
import jsondb.JsonDBTemplate;
import jsondb.Util;
import java.io.File;
import java.util.UUID;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import jsondb.Pojos.DOBehoerde;
import jsondb.Pojos.DOHaushaltsjahr;

/**
 *
 * @author Andreas
 */
public class JoinTests {
    
  private String dbFilesLocation = "test/resources/Pojos/JoinTests";
  private File dbFilesFolder = new File(dbFilesLocation);
  private File behoerdejson = new File(dbFilesFolder, "behoerde.json");
  private File haushaltsjahrjson = new File(dbFilesFolder, "haushaltsjahr.json");
  private JsonDBTemplate jsontpl = null;
  private String bk = null;
  private String hk = null;
  private String neuesjahrkey = null;
    
    public JoinTests() {
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
        JsonDBConfig c = new JsonDBConfig(dbFilesLocation, "resources/Pojos", "pre", null, true, null);
        jsontpl = new JsonDBTemplate(c);
        
        if (!jsontpl.collectionExists("behoerden"))
        jsontpl.createCollection("behoerden");
        
        if (!jsontpl.collectionExists("haushaltsjahre"))
        jsontpl.createCollection("haushaltsjahre");
        
        bk = UUID.randomUUID().toString();
        hk = UUID.randomUUID().toString();
        neuesjahrkey = UUID.randomUUID().toString();
    }
    
    @After
    public void tearDown() {
        Util.delete(dbFilesFolder);
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void jointest() {
        DOBehoerde b = new DOBehoerde();
        b.setBehoerdenname("PD Hannover");
        
        b.setKey(bk);
        
        DOHaushaltsjahr h = new DOHaushaltsjahr();
        h.setJahr("2017");
        hk = UUID.randomUUID().toString();
        h.setKey(hk);
        
        jsontpl.insert(b, "behoerden");
        jsontpl.insert(h, "haushaltsjahre");
        
        jsontpl.join(bk,"behoerden",hk,"haushaltsjahre");
        
        DOHaushaltsjahr neuesjahr = new DOHaushaltsjahr();
        neuesjahr.setJahr("2018");
        neuesjahr.setKey(neuesjahrkey);
        
        jsontpl.insert(neuesjahr);
        jsontpl.join(bk, "behoerden", neuesjahrkey, "haushaltsjahre");
        
    }
    
    @Test
    public void joinEinszuNTest() {
       UUID muid = UUID.nameUUIDFromBytes(("Korzinowski").getBytes());
        System.out.println("mostsigni ist: " + muid.getMostSignificantBits());
        System.out.println("least ist " + muid.getLeastSignificantBits());
    }
}
