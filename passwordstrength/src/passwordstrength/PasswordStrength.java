package passwordstrength;

public class PasswordStrength {

    boolean hasLetter = false;
    boolean hasDigit = false;
    boolean hasUpper = false;
    boolean hasLower = false;
    boolean hasSpecialChar = false;
    boolean haslength = false;
    String specialChar = "!@#$%^&*()-+";

    public boolean checkPasswordStrenth(String passwd) {
        
        if (passwd.length() >= 8) {
            haslength = true;
            for (int i = 0; i < passwd.length(); i++) {
                char singleChar = passwd.charAt(i);
                if (Character.isLetter(singleChar)) {
                    hasLetter = true;
                }
                if (Character.isDigit(singleChar)) {
                    hasDigit = true;
                }
                if (Character.isUpperCase(singleChar)) {
                     hasUpper = true;
                }
                if (Character.isLowerCase(singleChar)) {
                    hasLower = true;
                }
                if (passwd.matches("(?=.*[~!@#$%^&*()_-]).*")) {
                    hasSpecialChar = true;
                }
            }
        }
        if (haslength && hasLetter && hasDigit && hasUpper && hasLower && hasSpecialChar) {
            return true;
        } else {
            return false;
        }
    }
}
