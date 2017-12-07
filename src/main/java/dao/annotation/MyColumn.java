package dao.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MyColumn {
	/**
	 * Name of the column in table
	 * @return
	 */
    public String columnName();
    
    /**
     * Class in java entity that contains type of column
     * @return
     */
    public Class clazz();
}