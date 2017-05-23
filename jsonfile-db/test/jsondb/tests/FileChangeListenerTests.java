/*
 * Copyright (c) 2016 Farooq Khan
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package jsondb.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.common.io.Files;
import jsondb.JsonDBConfig;
import jsondb.JsonDBTemplate;
import jsondb.Util;
import jsondb.crypto.DefaultAESCBCCipher;
import jsondb.crypto.ICipher;
import jsondb.events.CollectionFileChangeListener;
import jsondb.tests.model.Instance;
import jsondb.tests.model.PojoWithEnumFields;
import jsondb.tests.util.TestUtils;

/**
 * @version 1.0 24-Oct-2016
 */
public class FileChangeListenerTests {

  private static final long DB_RELOAD_TIMEOUT = 5 * 1000;
  private String dbFilesLocation = "test/resources/dbfiles/eventsTests";
  private File dbFilesFolder = new File(dbFilesLocation);
  private File instancesJson = new File(dbFilesFolder, "instances.json");
  private File pojoWithEnumFieldsJson = new File(dbFilesFolder, "pojowithenumfields.json");

  private JsonDBTemplate jsonDBTemplate = null;

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  @Before
  public void setUp() throws Exception {
    //Filewatcher does not work on Mac and hence JsonDB events will never fire
    //and so the EventTests will never succeed. So we run the tests only if
    //it is not a Mac system
    assumeTrue(!TestUtils.isMac());

    dbFilesFolder.mkdir();
    Files.copy(new File("test/resources/dbfiles/pojowithenumfields.json"), pojoWithEnumFieldsJson);
    ICipher cipher = new DefaultAESCBCCipher("1r8+24pibarAWgS85/Heeg==");
    JsonDBConfig c = new JsonDBConfig(dbFilesLocation, "io.jsondb.tests.model", "pre", cipher, true, null);
    jsonDBTemplate = new JsonDBTemplate(c);
  }

  @After
  public void tearDown() throws Exception {
    Util.delete(dbFilesFolder);
  }

  @Test
  public void testAutoReloadOnCollectionFileAdded() {
    jsonDBTemplate.addCollectionFileChangeListener(new CollectionFileChangeListener() {

      @Override
      public void collectionFileModified(String collectionName) {
      }

      @Override
      public void collectionFileDeleted(String collectionName) {
      }

      @Override
      public void collectionFileAdded(String collectionName) {
        jsonDBTemplate.reloadCollection(collectionName);
      }
    });
    
    //Add a additional do nothing listener to test addition of one more listener
    jsonDBTemplate.addCollectionFileChangeListener(new CollectionFileChangeListener() {
      @Override
      public void collectionFileModified(String collectionName) {
      }

      @Override
      public void collectionFileDeleted(String collectionName) {
      }

      @Override
      public void collectionFileAdded(String collectionName) {
      }
    });
    
    assertFalse(jsonDBTemplate.collectionExists(Instance.class));
    try {
      Files.copy(new File("test/resources/dbfiles/instances.json"), instancesJson);
    } catch (IOException e1) {
      fail("Failed to copy data store files");
    }
    try {
      // Give it some time to reload DB
      Thread.sleep(DB_RELOAD_TIMEOUT);
    } catch (InterruptedException e) {
      fail("Failed to wait for db reload");
    }
    List<Instance> instances = jsonDBTemplate.findAll(Instance.class);
    assertNotNull(instances);
    assertNotEquals(instances.size(), 0);
  }

  @Test
  public void testAutoReloadOnCollectionFileModified() throws FileNotFoundException {
    try {
      Files.copy(new File("test/resources/dbfiles/instances.json"), instancesJson);
    } catch (IOException e1) {
      fail("Failed to copy data store files");
    }
    jsonDBTemplate.reLoadDB();
    int oldCount = jsonDBTemplate.findAll(Instance.class).size();
    jsonDBTemplate.addCollectionFileChangeListener(new CollectionFileChangeListener() {

      @Override
      public void collectionFileModified(String collectionName) {
        jsonDBTemplate.reloadCollection(collectionName);
      }

      @Override
      public void collectionFileDeleted(String collectionName) {
      }

      @Override
      public void collectionFileAdded(String collectionName) {
      }
    });

    @SuppressWarnings("resource")
    Scanner sc = new Scanner(new File("test/resources/dbfiles/instances.json")).useDelimiter("\\Z");
    String content = sc.next();
    sc.close();

    content = content + "\n" + "{\"id\":\"07\",\"hostname\":\"ec2-54-191-07\","
        + "\"privateKey\":\"Zf9vl5K6WV6BA3eL7JbnrfPMjfJxc9Rkoo0zlROQlgTslmcp9iFzos+MP93GZqop\","
        + "\"publicKey\":\"d3aa045f71bf4d1dffd2c5f485a4bc1d\"}";

    PrintWriter out = new PrintWriter(instancesJson);
    out.println(content);
    out.close();

    try {
      // Give it some time to reload DB
      Thread.sleep(DB_RELOAD_TIMEOUT);
    } catch (InterruptedException e) {
      fail("Failed to wait for db reload");
    }
    int newCount = jsonDBTemplate.findAll(Instance.class).size();
    assertEquals(oldCount + 1, newCount);
  }

  @Test
  public void testAutoReloadOnCollectionFileDeleted() throws FileNotFoundException {
    assertTrue(jsonDBTemplate.collectionExists(PojoWithEnumFields.class));

    jsonDBTemplate.addCollectionFileChangeListener(new CollectionFileChangeListener() {

      @Override
      public void collectionFileModified(String collectionName) {
      }

      @Override
      public void collectionFileDeleted(String collectionName) {
        jsonDBTemplate.reLoadDB();
      }

      @Override
      public void collectionFileAdded(String collectionName) {
      }
    });

    pojoWithEnumFieldsJson.delete();

    try {
      // Give it some time to reload DB
      Thread.sleep(DB_RELOAD_TIMEOUT);
    } catch (InterruptedException e) {
      fail("Failed to wait for db reload");
    }

    assertFalse(jsonDBTemplate.collectionExists(PojoWithEnumFields.class));
  }

  @Test
  public void testRemoveListener() {
    assertFalse(jsonDBTemplate.hasCollectionFileChangeListener());
    CollectionFileChangeListener listener = new CollectionFileChangeListener() {
      @Override
      public void collectionFileModified(String collectionName) {
      }

      @Override
      public void collectionFileDeleted(String collectionName) {
      }

      @Override
      public void collectionFileAdded(String collectionName) {
      }
    };

    jsonDBTemplate.addCollectionFileChangeListener(listener);
    assertTrue(jsonDBTemplate.hasCollectionFileChangeListener());

    jsonDBTemplate.removeCollectionFileChangeListener(listener);
    assertFalse(jsonDBTemplate.hasCollectionFileChangeListener());
  }
}
