
public class Test {

	
	public static void main(String[] args) {
		JdbcTemplate jt = new JdbcTemplate();
		/*
		Object[][] obs = (Object[][]) jt.executeSelect(FIND_WHERE);
		System.out.println(obs.length);
		for (int i = 0; i < obs.length; i++) {
		      for (int j = 0; j <3; j++) {
		        System.out.print(obs[i][j] + " ");
		 
		      }
		      System.out.println();
		    }
		  */
		System.out.println(jt.checkInDataBase("admin", "admin"));
	}
}

