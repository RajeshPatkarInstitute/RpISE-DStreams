package userauthentication;

import authenticationinterface.AuthenticationInterface;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;
import encryption.Encryption;

public class AuthenticationByJSONFile implements AuthenticationInterface {

    private Encryption encryption;

    public AuthenticationByJSONFile() {
        this.encryption = new Encryption();
    }

    public AuthenticationByJSONFile(Encryption encryption) {
        this.encryption = encryption;
    }

    public int authenticateUser(String usernm, String passwd) {
        try {
            JSONParser parser = new JSONParser();
            Object object = parser.parse(new FileReader("JSONusers.json"));
            JSONObject jsonObject = (JSONObject) object;

            JSONArray ulist = (JSONArray) jsonObject.get("Users");
            for (Object ulst : ulist) {
                JSONObject userInfo = (JSONObject) ulst;
                String uname = (String) userInfo.get("Name");
                if (uname.equals(usernm)) {
                    Encryption er = new Encryption();
                    String encryptedPasswd = er.encrypt((String) userInfo.get("Password"));
                    if (encryptedPasswd.equals(er.encrypt(passwd))) {
                        return 0;
                    } else {
                        return 1;
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println(" File not found " + ex.getMessage());
            return 1;
        } catch (IOException ex) {
            System.out.println(" IO Exception " + ex.getMessage());
            return 1;
        } catch (ParseException ex) {
            System.out.println(" Parser Exception " + ex.getMessage());
            return 1;
        }
            return 0;
    }

}
