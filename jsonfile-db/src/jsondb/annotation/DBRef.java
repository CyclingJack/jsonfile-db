/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsondb.annotation;

import static java.lang.annotation.ElementType.FIELD;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author Andreas
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ FIELD })
public @interface DBRef {
    String destcollection();
    String destfieldname();
}
