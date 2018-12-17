package org.afrikcode.pes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile("^[\\w-+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
