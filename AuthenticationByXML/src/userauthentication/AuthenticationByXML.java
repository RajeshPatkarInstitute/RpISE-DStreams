package userauthentication;

import authenticationinterface.AuthenticationInterface;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;


public class AuthenticationByXML implements AuthenticationInterface {

    public AuthenticationByXML()
    {
    //  super();  
    }
    
    @Override
    public int authenticateUser(String usernm, String passwd) {
      System.out.println("Reading Xml File using SAX API");
        String xmlFilePath = "userlist.xml";
        SAXParserFactory spf = SAXParserFactory.newInstance();
        try {
            SAXParser sparser = spf.newSAXParser();
            FileInputStream fis = new FileInputStream(xmlFilePath);
            UserHandler h = new UserHandler();
            h.setUserName(usernm);
            h.setuPassword(passwd);
            sparser.parse(fis, h);
            if (h.getisValid()) {
            //    System.out.println(" password correct");
                return 0;
            } 
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(AuthenticationByXML.class.getName()).log(Level.SEVERE, null, ex);
        }
                   return 1;
    }
}