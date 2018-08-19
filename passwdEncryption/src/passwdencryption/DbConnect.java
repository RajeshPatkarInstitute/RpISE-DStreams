package passwdencryption;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbConnect {

    static Connection conn;
    static PreparedStatement ps;
    String sql;
    String strPasswd;

    DbConnect() {
        setDBConn();
    }

    public Connection getDBConn() {
        return conn;
    }

    public void setDBConn() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("Driver Loaded");
            this.conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/testpdb", "hr", "hr123");
            System.out.println("Connection Established");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DbConnect.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DbConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Integer insertData(String uname, String passwd) {

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
            
            while (rs.next())
            {
                strPasswd = rs.getString("ENCRY_PASSWD");
           }
 
        } catch (SQLException ex) {
            System.out.println(" Sql error: " + ex.getMessage());
            return "";
        }

        return strPasswd;
    }

}
