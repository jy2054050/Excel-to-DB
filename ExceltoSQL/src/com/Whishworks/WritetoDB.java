package com.Whishworks;

import java.sql.*;

public class WritetoDB {
   // JDBC driver name and database URL
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://localhost/test";


   
   public static String putValues(String[] columns ,String db_Name,String table_Name,String username,String  Passsword){
	    String USER = username;
	    String PASS = Passsword;
	    String DB_URL = "jdbc:mysql://localhost/" + db_Name;
	    //System.out.println();
	   String sqlToExecute = "INSERT INTO "+ table_Name +" VALUES (";
	   for (int K = 0; K < columns.length; K++) {
			/*String string = columns[K];
			System.out.println(string);*/
		   if(columns[K] != null){
			sqlToExecute = sqlToExecute + "'"+ columns[K]+"',";
			} else {
				sqlToExecute = sqlToExecute +  columns[K]+",";
			}
		   
		}
	   int length = sqlToExecute.length();
	   sqlToExecute= sqlToExecute.substring(0, length-1);
	   sqlToExecute+= ");";
	//  System.out.println("toexe : " + sqlToExecute);
	   
   Connection conn = null;
   Statement stmt = null;
   
   try{    
      Class.forName("com.mysql.jdbc.Driver");
      conn = DriverManager.getConnection(DB_URL, USER, PASS);
      stmt = conn.createStatement();
      stmt.executeUpdate(sqlToExecute);
      return "Successfull";
      
     }catch(SQLException se){
    	 se.printStackTrace();
    	 return se.getMessage();
    	      
     
   }catch(Exception e){
	   e.printStackTrace();
	   return e.getMessage();
    
     
   }finally{
      //finally block used to close resources
      try{
         if(stmt!=null)
            conn.close();
      }catch(SQLException se){
      }// do nothing
      try{
         if(conn!=null)
            conn.close();
      }catch(SQLException se){
    	  System.out.println("Jitu Bhai 3"+se.getCause());
         se.printStackTrace();
      }
   }

}
}