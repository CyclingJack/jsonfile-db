/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsondb.tests;

import jsondb.JsonDBTemplate;
import jsondb.annotation.DBRef;
import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.UUID;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import jsondb.Pojos.DOBehoerde;

/**
 *
 * @author Andreas
 */
public class DOInpektor {
    
     private String dbFilesLocation = "test/resources/Pojos/DBRefsTests";
  private File dbFilesFolder = new File(dbFilesLocation);
  private File behoerdejson = new File(dbFilesFolder, "behoerde.json");
  private File haushaltsjahrjson = new File(dbFilesFolder, "haushaltsjahr.json");

  private JsonDBTemplate jsonDBTemplate = null;
  
  private String behoerdenkey;
  private String haushaltsjahrkey;
    
    public DOInpektor() {
       //  this.inspectClass();
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
    public void inspectClass() {
        DOBehoerde b = new DOBehoerde();
        b.setBehoerdenname("PD Hannover");
        b.setKey(UUID.randomUUID().toString());
        Object obj = b;
        
        Class o = obj.getClass();
        Field[] fields = o.getDeclaredFields();
        for (Field field : fields) {
            Annotation[] annos = field.getDeclaredAnnotationsByType(DBRef.class);
            for (Annotation anno : annos) {
                System.out.println("Feld: " + field.getName() + " Annotation: " + anno.toString());
                
                String gettermethod = "get" + field.getName();
                String settermethod = "set" + field.getName();
                
                Method[] methods = o.getDeclaredMethods();
                for (Method method : methods) {
                    if (method.getName().equalsIgnoreCase(settermethod)) {
                        System.out.println("Setter: " + method.getName());
                    }
                    if (method.getName().equalsIgnoreCase(gettermethod)) {
                        System.out.println("Getter: " + method.getName());
                    }
                   
                }
            }
        }
    }  

}
