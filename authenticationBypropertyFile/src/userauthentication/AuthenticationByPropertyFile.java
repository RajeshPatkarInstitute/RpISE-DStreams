package userauthentication;

import authenticationinterface.AuthenticationInterface;
import encryption.Encryption;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.Set;

public class AuthenticationByPropertyFile implements AuthenticationInterface {

    static Properties p;
    private Encryption er;

    public AuthenticationByPropertyFile()
    {
             this.er = new Encryption();
    }
    public AuthenticationByPropertyFile(Encryption er) {
        this.er = er;
    }

    @Override
    public int authenticateUser(String usernm, String passwd) {
        try {
            boolean flag = true;
            p = new Properties();
            FileReader fr = new FileReader("user.properties");
            p.load(fr);
            while (flag) {
                if (validatePassword(usernm, passwd)) {
                    flag = false;
                    return 0;
                }
            }

        } catch (FileNotFoundException ex) {
            System.out.println(" File not found msg " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return 1;
    }

    public boolean validatePassword(String name, String pwd) {
        Set<Object> keys = p.keySet();
        // Displaying keys
      //  System.out.println(" name : " + name);
        System.out.println(" Keys = " + keys.toString() + "\n");
        if (keys.contains(name)) {
            String password = this.er.encrypt(p.getProperty(name));
            System.out.println(" encrypted  value from file " + password);
            if (password.equals(this.er.encrypt(pwd))) {
              //  System.out.println("matching ....");
                return true;
            } else
            {
            return false; }
        }
       return false;
    }
}
