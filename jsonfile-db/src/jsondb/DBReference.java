/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsondb;

/**
 *
 * @author Andreas
 */
public class DBReference {

    
   
    private String destfield;
    private String destcollection;
    private String destgetterMethod;
    private String destsetterMethod;
   

    public String getDestfield() {
        return destfield;
    }

    public void setDestfield(String destfield) {
        this.destfield = destfield;
    }

    public String getDestgetterMethod() {
        return destgetterMethod;
    }

    public void setDestgetterMethod(String destgetterMethod) {
        this.destgetterMethod = destgetterMethod;
    }

    public String getDestsetterMethod() {
        return destsetterMethod;
    }

    public void setDestsetterMethod(String destsetterMethod) {
        this.destsetterMethod = destsetterMethod;
    }

    public String getDestcollection() {
        return destcollection;
    }

    public void setDestcollection(String destcollection) {
        this.destcollection = destcollection;
    }
    
    
    
}
