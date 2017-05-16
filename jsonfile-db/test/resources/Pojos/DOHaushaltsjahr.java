/*
 * Copyright 2017 KorzinowskiAndreasMI.
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

import io.jsondb.annotation.Document;
import io.jsondb.annotation.Id;

/**
 *
 * @author KorzinowskiAndreasMI
 */
@Document(collection = "haushaltsjahre", schemaVersion = "1.0")
public class DOHaushaltsjahr {
    
    @Id
    String key; 

    String behkey;
    String jahr;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getBehkey() {
        return behkey;
    }

    public void setBehkey(String behkey) {
        this.behkey = behkey;
    }

    public String getJahr() {
        return jahr;
    }

    public void setJahr(String jahr) {
        this.jahr = jahr;
    }

    
}