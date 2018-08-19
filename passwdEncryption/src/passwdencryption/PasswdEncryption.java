package passwdencryption;

import static java.awt.SystemColor.text;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.reflect.Array.set;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class PasswdEncryption {

    static Connection conn;
    static PreparedStatement ps;
    static String usernm = "ETEST4";
    static String passwd = "test444";
    String sql;
    int i = 0;

    public static void main(String[] args) {
        DbConnect c = new DbConnect();
        Encryption er = new Encryption();

        String strOption = JOptionPane.showInputDialog(" Select User Validation Mode (DB/File): ");

        switch (strOption) {
            case "DB":
            case "db":
                dbAuthentcation d = new dbAuthentcation(c, er);
                String dbOption = JOptionPane.showInputDialog(" 1. Insert : " + "\n" + "2. Validate");

                int iOption = Integer.parseInt(dbOption);

                switch (iOption) {
                    case 1:
                        d.insertUser("ETEST5", "e12345");
                        break;
                    case 2:
                        usernm = JOptionPane.showInputDialog(" Enter username :");
                        passwd = JOptionPane.showInputDialog(" Enter password :");
                        clientInterface(d, usernm, passwd);
                    default:
                        clientInterface(d, usernm, passwd);
                        break;
                }
                    break;
            case "FILE":
            case "file":
                fileAuthenticaion fa = new fileAuthenticaion(er);
                clientInterface(fa, usernm, passwd);
                break;

        }
        //      dbAuthentcation d = new dbAuthentcation(c);
        //     d.insertUser(usernm, passwd);
        //   d.autheticateUser("ETEST3", "test333");
    }

    static void clientInterface(Authentication obj, String uname, String passwd) {
        obj.autheticateUser(uname, passwd);
    }

}

interface Authentication {

    void autheticateUser(String usernm, String passwd);
}

class dbAuthentcation implements Authentication {

    private DbConnect conn;
    private Encryption er;

    dbAuthentcation(DbConnect conn, Encryption er) {
        this.conn = conn;
        this.er = er;
    }

    void insertUser(String usernm, String passwd) {
        //   conn.insertData(usernm,new String(textEncrypted));
        //     usernm = "ETEST2";
        //   passwd = "test123";
        String str = this.er.encrypt(passwd);
        conn.insertData(usernm, str);
    }

    @Override
    public void autheticateUser(String usernm, String passwd) {

        Encryption er = new Encryption();
        String strPasswd = er.encrypt(passwd);
        String str = this.conn.ValidatePassword(usernm);

        System.out.println(" password  " + str);
        if (str.equals(strPasswd)) {
            System.out.println(" matching");
        } else {
            System.out.println(" incorrect ...");
        }
    }
}

class fileAuthenticaion implements Authentication {

    static Properties p;
    private Encryption er;

    fileAuthenticaion(Encryption er) {
        this.er = er;
    }

    @Override
    public void autheticateUser(String usernm, String passwd) {

        try {
            p = new Properties();

            FileReader fr = new FileReader("user.properties");
            p.load(fr);
            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader kin = new BufferedReader(isr);

            System.out.println(" Enter UserName :");
            String strUsernm = kin.readLine();
            System.out.println(" Enter Password :");
            String strPasswd = kin.readLine();

            validatePassword(strUsernm, strPasswd);

        } catch (FileNotFoundException ex) {
            System.out.println(" File not found msg " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex);
        }

    }

    public void validatePassword(String name, String pwd) {
        // Encryption er = new Encryption();
        Set<Object> keys = p.keySet();
        // Displaying keys
        System.out.println(" Keys = " + keys.toString() + "\n");

        if (keys.contains(name)) {
            String password = this.er.encrypt(p.getProperty(name));

            System.out.println(" encrypted  value from file " + password);
            if (password.equals(this.er.encrypt(pwd))) {
                System.out.println("matching ....");
            } else {
                System.out.println(" incorrect ...");
            }
        }

    }

}
