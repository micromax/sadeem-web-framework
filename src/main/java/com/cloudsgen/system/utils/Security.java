package com.cloudsgen.system.utils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

public class Security {

    private static final Pattern INSECURE_URI = Pattern.compile(".*[<>&\"].*");
    private static String sanitizeUri(String uri) {
        // Decode the path.
        try {
            uri = URLDecoder.decode(uri, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new Error(e);
        }

        if (uri.isEmpty() || uri.charAt(0) != '/') {
            return null;
        }

        // Convert file separators.
        uri = uri.replace('/', File.separatorChar);

        // Simplistic dumb security check.
        // You will have to do something serious in the production environment.
        if (uri.contains(File.separator + '.') ||
                uri.contains('.' + File.separator) ||
                uri.charAt(0) == '.' || uri.charAt(uri.length() - 1) == '.' ||
                INSECURE_URI.matcher(uri).matches()) {
            return null;
        }

        // Convert to absolute path.
        return  uri;
    }



    public static String Clean(String unseure , String ReplaceText )
    {

        String Tx = unseure;
        Tx = Tx.replaceAll("[^a-zA-Z0-9]", ReplaceText);
        return Tx;
    }



    public static String SHA225(String in){
        String out = null;
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");

            byte[] encodedhash = digest.digest(
                    in.getBytes(StandardCharsets.UTF_8));

            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < encodedhash.length; i++) {
                String hex = Integer.toHexString(0xff & encodedhash[i]);
                if(hex.length() == 1)
                {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            out = hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return out;

    }
}
