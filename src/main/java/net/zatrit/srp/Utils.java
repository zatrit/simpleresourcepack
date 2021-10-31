package net.zatrit.srp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {
    public static MessageDigest MESSAGE_DIGEST;

    public static String checksum(MessageDigest digest, InputStream is)
            throws IOException {
        // Get file input stream for reading the file
        // content

        // Create byte array to read data in chunks
        byte[] byteArray = new byte[1024];
        int bytesCount;

        // read the data from file and update that data in
        // the message digest
        while ((bytesCount = is.read(byteArray)) != -1) {
            digest.update(byteArray, 0, bytesCount);
        }

        // close the input stream
        is.close();

        // store the bytes returned by the digest() method
        byte[] bytes = digest.digest();

        // this array of bytes has bytes in decimal format,
        // so we need to convert it into hexadecimal format

        // for this we create an object of StringBuilder
        // since it allows us to update the string i.e. its
        // mutable
        StringBuilder sb = new StringBuilder();

        // loop through the bytes array
        for (byte aByte : bytes) {

            // the following line converts the decimal into
            // hexadecimal format and appends that to the
            // StringBuilder object
            sb.append(Integer
                    .toString((aByte & 0xff) + 0x100, 16)
                    .substring(1));
        }

        // finally, we return the complete hash
        return sb.toString();
    }

    public static String checksum(MessageDigest digest, File f)
            throws IOException {
        return checksum(digest, new FileInputStream(f));
    }

    static {
        try {
            MESSAGE_DIGEST = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
