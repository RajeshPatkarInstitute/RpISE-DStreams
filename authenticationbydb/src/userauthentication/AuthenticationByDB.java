package userauthentication;

import authenticationinterface.AuthenticationInterface;
import encryption.Encryption;
import passwordstrength.PasswordStrength;

public class AuthenticationByDB implements AuthenticationInterface {

    public static ConnectionManager conn;
    public static Encryption er;
    public static PasswordStrength passwdStrength;
 
    public AuthenticationByDB()
    {
         this.conn = new ConnectionManager();
        this.er = new Encryption();
    }
 //   public AuthenticationByDB(ConnectionManager conn, Encryption er) {
  //      this.conn = conn;
  //      this.er = er;
    //}
    @Override
    public int authenticateUser(String usernm, String passwd) {
       passwdStrength = new PasswordStrength();
        System.out.println(" inside verification" + usernm);
        if (conn.checkUserExists(usernm)) {
                 System.out.println(" passwd verification" + passwd);
                String strPasswd = this.er.encrypt(passwd);
                String str = this.conn.ValidatePassword(usernm);

                System.out.println(" password  " + str);
                if (str.equals(strPasswd)) {
                    System.out.println(" matching");
                    return 0;
                } else {
                    System.out.println(" incorrect ...");
                    return 1;
                }
          
            } else {
                    System.out.println(" passwd  verification" );
                return 1;
            }
        } 
    }
