package hr.app.manager;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3poDataSource {

	private static ComboPooledDataSource cpds = new ComboPooledDataSource();
	 
    static {
        try {
        	
        	/*
        	 * 			!!! TEST !!!
        	 * 
        	 *         	Database: test_app_ukic
        	 *			Username: app_test_johto
        	 *			Email: b1190020@urhen.com
        	 *			Password: antejoni
        	 *
        	 *
        	
            cpds.setDriverClass("com.mysql.jdbc.Driver");
            cpds.setJdbcUrl("jdbc:mysql://85.10.205.173:3306/test_app_ukic");
            cpds.setUser("app_test_johto");
            cpds.setPassword("antejoni");
            */
            
            /*
        	 * 			!!! LOCAL TEST !!! mysql
        	 * 
        	 *         	Database: test_app_ukic
        	 *			Username: app_test_johto
        	 *			Email: b1190020@urhen.com
        	 *			Password: antejoni
        	 **

        	
            cpds.setDriverClass("com.mysql.jdbc.Driver");
            cpds.setJdbcUrl("jdbc:mysql://localhost:3306/local_test?useUnicode=true&useJDBCCompiaantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
            cpds.setUser("root");
            cpds.setPassword("antejoni2007");
        	
        	
        	 * 			!!! REAL !!!
        	 * 
        	 * 			Database: app_ukic
        	 * 			Username: johto_db
        	 * 			Email: tomislav.ukic7@gmail.com 
        	 * 			db4free.net
        	 * 			ip: 85.10.205.173
        	 *			port: 3306
        	 */
        	
        
            cpds.setDriverClass("com.mysql.jdbc.Driver");
            cpds.setJdbcUrl("jdbc:mysql://85.10.205.173:3306/app_ukic");
            cpds.setUser("johto_db");
            cpds.setPassword("antejoni2007");
          
            
        } catch (PropertyVetoException e) {
            // handle the exception
        }
    }
     
    public static Connection getConnection() throws SQLException {
        return cpds.getConnection();
    }
     
    private C3poDataSource(){}
}
