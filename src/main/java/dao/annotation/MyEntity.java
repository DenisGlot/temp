package dao.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
/**
 * It's really important that fields, parameters in constructor of entities would be in the same order as columns in table 
 * @author Denis
 *
 */
public @interface MyEntity {
	/**
	 * Name of table of entity
	 * @return
	 */
    public String tableName();
    
    /**
     * Name of id column in named table
     * @return
     */
    public String id();
}