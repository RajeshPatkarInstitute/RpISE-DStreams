package userauthentication;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import encryption.Encryption;


public class UserHandler extends org.xml.sax.helpers.DefaultHandler {

    private String userName;
    private String uPassword;
    private Encryption er;
    public String uName = "";
    public String uPasswd = "";
    private boolean isValid;

    boolean bName = false;
    boolean bPassword = false;

    public UserHandler() {
        super();
        this.er = new Encryption();
    }

    public boolean getisValid()
    {
        return isValid;
    }
    
    public void setisValid (boolean isValid)
    {
        this.isValid = isValid;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getuPassword() {
        return uPassword;
    }

    public void setuPassword(String uPassword) {
        this.uPassword = uPassword;
    }

    public int validatePassword(String name, String xmlPwd) {
        if ( this.er.encrypt(xmlPwd).equalsIgnoreCase(this.er.encrypt(this.uPassword))) {
            System.out.println(" correct");
            this.isValid =true;
            return 0;
        } else {
            System.out.println(" not correct");
               this.isValid =false;
            return 1;
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
            throws SAXException {
        if (qName.equalsIgnoreCase("Name")) {
            String strName = attributes.getValue("Name");
            System.out.println("User Name : " + strName);
            bName = true;
        } else if (qName.equalsIgnoreCase("Password")) {
            String strPassword = attributes.getValue("Password");
            bPassword = true;
        }

    }

    @Override
    public void endElement(String uri, String localName, String qName) {

        if (qName.equalsIgnoreCase("User")) {
            System.out.println("End Element :" + qName);
        }
    }

    @Override
    public void characters(char ch[], int start, int length) {

        if (bName) {
          //  System.out.println("Name: " + new String(ch, start, length));
            uName = new String(ch, start, length);
            bName = false;
        } else if (bPassword) {
            //System.out.println(" uname " + uName + " this user " + this.userName);
            if (this.userName.equals(uName)) {
              //  System.out.println("Password: " + new String(ch, start, length));
                uPasswd = new String(ch, start, length);
                validatePassword(uName, uPasswd);
                bPassword = false;
            }
        }
    }
}
