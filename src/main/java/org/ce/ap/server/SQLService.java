package main.java.org.ce.ap.server;
import java.sql.*;

public class SQLService {

    private Connection conn;
    private String sql;
    private Statement   statement;
    private ResultSet res;
    private final String dbURL = "jdbc:sqlserver://localhost;integratedSecurity=true;databaseName=twitter";
    public SQLService(){
        try {
            conn = DriverManager.getConnection(dbURL);
            if (conn != null) {
                DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
                System.out.println("Connected to SQL. SQL version: " + dm.getDatabaseProductVersion());
                //conn.close();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
}
