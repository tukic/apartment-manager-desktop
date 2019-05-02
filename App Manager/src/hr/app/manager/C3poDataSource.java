package hr.app.manager;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3poDataSource {

	private static ComboPooledDataSource cpds = new ComboPooledDataSource();
	 
    static {
        try {
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
