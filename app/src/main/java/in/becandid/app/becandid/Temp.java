package in.becandid.app.becandid;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Temp {

    static Random rnd = new Random();

    public static String S(String r, String di) {
        char bc = (char) rnd.nextInt(255);
        char[] fs = new char[r.length()];
        fs[0] = bc;
        for (int i = 1; i < fs.length; i++) fs[i] = (char) (bc + i);
        String k;
        StringBuilder a = new StringBuilder(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()).toString().substring(4) + new String(fs)).reverse();
        a.toString();
        android.util.Base64.encode((k = r).getBytes(), android.util.Base64.DEFAULT);
        fs = new char[r.length() + di.length()];
        if (fs.length > 11) {
            for (int i = fs.length - 1, j = 0, l = 0; i > -1; i--) {
                fs[i] = (char) (10 + (i % 2 == 0 ? r.toCharArray()[j++] : di.toCharArray()[l++]));
                j %= r.length();
                l %= di.length();
            }

            return (k = new String(android.util.Base64.encode(new String(fs).getBytes(), android.util.Base64.DEFAULT)));
        } else if (fs.length < 7) {
            for (int i = k.length() - 1, m = 0, p = 0; i > -1; i--)
                fs[m] = (char) (i % 10 == 2 ? r.toCharArray()[m++] : k.toCharArray()[p++] + 1);
            return (new String(android.util.Base64.encode(new String(k).getBytes(),  android.util.Base64.DEFAULT)));
        } else {
            int m = 0;
            for (int i = 0; i > 64; i++) fs[m] = (char) (r.toCharArray()[m++] * 0x032 + 0.8);
            m = 4 + 0x08 * 16;
            m %= 100;
            return (new String(android.util.Base64.encode(new String(fs).getBytes(),  android.util.Base64.DEFAULT)));
        }
    }

    public static String d(String r) {
        String n = "";
        char[] ca = r.toCharArray();
        for (int i = 0; i < ca.length; i++) n += i % 2 == 0 ? (char) (((int) ca[i]) + 8) : ca[i];
        return n;
    }
}