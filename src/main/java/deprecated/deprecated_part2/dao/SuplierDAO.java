package deprecated.deprecated_part2.dao;

import java.util.ArrayList;
import java.util.List;

import dao.entity.Product;
import dao.entity.Suplier;
import deprecated.deprecated_part2.dao.superb.AbstractDAO;

public class SuplierDAO  extends AbstractDAO<Suplier, Integer>{

	@Override
	public String getSelectQuery() {
		return "select * from SUPLIERS";
	}

	@Override
	public String getUpdateQuery(Suplier entity) {
		return "update SUPLIERS set name = '"  + entity.getName() + "' , description = '" + entity.getDescription() + "' where suplierid = " + entity.getSuplierid();
	}

	@Override
	public String getDeleteQuery(Suplier entity) {
		return "delete from SUPLIERS where suplierid = " + entity.getSuplierid();
	}

	@Override
	public String getInsertQuery(Suplier entity) {
		return "insert into SUPLIERS(name,description) values('" + entity.getName() +"', '" + entity.getDescription() + "')";
	}

	@Override
	public List<Suplier> parseObjectsToList(Object[][] obs) {
		List<Suplier> list = new ArrayList<>();
		for(int i =0 ; i< obs[0].length;i++) {
			list.add(new Suplier((String)obs[0][0],(String) obs[0][1]));
		}
		return list;
	}

	@Override
	public Suplier parseObjectsToEntity(Object[][] obs) {
		return new Suplier((String)obs[0][0],(String) obs[0][1]);
	}
    
	
}
