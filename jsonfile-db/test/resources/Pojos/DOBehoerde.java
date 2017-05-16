/*
 * Copyright 2017 Andreas.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package resources.Pojos;

import io.jsondb.annotation.DBRef;
import io.jsondb.annotation.Document;
import io.jsondb.annotation.Id;

/**
 *
 * @author Andreas
 */


@Document(collection = "behoerden", schemaVersion = "1.0")
public class DOBehoerde {

    @Id
    String key;
    
    
    @DBRef(destcollection = "haushaltsjahre", destfieldname = "behkey")
    String hhjkey;
    
    String behoerdenname;
    String orgbezeichnung;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getHhjkey() {
        return hhjkey;
    }

    public void setHhjkey(String hhjkey) {
        this.hhjkey = hhjkey;
    }

    public String getBehoerdenname() {
        return behoerdenname;
    }

    public void setBehoerdenname(String behoerdenname) {
        this.behoerdenname = behoerdenname;
    }

    public String getOrgbezeichnung() {
        return orgbezeichnung;
    }

    public void setOrgbezeichnung(String orgbezeichnung) {
        this.orgbezeichnung = orgbezeichnung;
    }

   
    
}
