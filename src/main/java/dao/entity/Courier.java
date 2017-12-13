package dao.entity;

import java.sql.Timestamp;

import dao.annotation.MyColumn;
import dao.annotation.MyEntity;

@MyEntity(id="courierid", tableName = "COURIERS" )
public class Courier {

	@MyColumn(clazz = Integer.class, columnName = "courierid")
	private Integer courierid;
	
	@MyColumn(clazz = String.class, columnName = "firstname")
	private String firstname;
	
	@MyColumn(clazz = String.class, columnName = "lastname")
	private String lastname;
	
	@MyColumn(clazz = Timestamp.class, columnName = "hiredate")
	private Timestamp hiredate;
	
	@MyColumn(clazz = Timestamp.class, columnName = "birth")
	private Timestamp birth;
}
