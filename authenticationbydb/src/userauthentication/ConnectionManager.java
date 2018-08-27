package userauthentication;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionManager {

    private static String url = "jdbc:oracle:thin:@localhost:1521/testpdb";
    private static String driverName = "oracle.jdbc.driver.OracleDriver";
    private static String username = "hr";
    private static String password = "hr123";
    static Connection conn;
    static PreparedStatement ps;
    String sql;
    String strPasswd;
    int upperUsername;

    ConnectionManager() {
        setDBConn();
    }

    public Connection getDBConn() {
        return conn;
    }

    public void setDBConn() {
        try {
            // Class.forName("oracle.jdbc.driver.OracleDriver");
            Class.forName(driverName);
            System.out.println("Driver Loaded");
            // this.conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/testpdb", "hr", "hr123");
            this.conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connection Manager Established");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean checkUserExists(String uname) {
        sql = " select 1 uname from user_tab where upper(username) = ?";
        String convertToUpper = uname.toUpperCase();
        try {
            ps = this.conn.prepareStatement(sql);
            ps.setString(1, convertToUpper);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                upperUsername = rs.getInt("uname");
            }

        } catch (SQLException ex) {
            System.out.println(" user Exists " + ex.getMessage());
        }

        if (upperUsername == 1) {
            return true;
        } else {
            return false;
        }
    }

    public Integer addUser(String uname, String passwd) {

        int i = 0;

        sql = "Insert into user_tab ( username , ENCRY_PASSWD) values (?,?)";
        System.out.println("zDBsql " + uname);
        try {
            ps = this.conn.prepareStatement(sql);
            ps.setString(1, uname);
            ps.setString(2, passwd);

            System.out.println(" sql " + sql);
            ps.execute();
            // conn.commit();
            // conn.close();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return i;
    }

    public String ValidatePassword(String usernm) {
        sql = " select ENCRY_PASSWD from user_tab where username='" + usernm + "'";
        System.out.println(" inside db " + sql);
        try {
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery(sql);

            while (rs.next()) {
                strPasswd = rs.getString("ENCRY_PASSWD");
            }

        } catch (SQLException ex) {
            System.out.println(" Sql error: " + ex.getMessage());
            return "";
        }

        return strPasswd;
    }

}
