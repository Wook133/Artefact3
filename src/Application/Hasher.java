package Application;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.io.DigestInputStream;

public class Hasher {

    public String Hash(String sinput) throws NoSuchAlgorithmException
    {
        String sOut = "";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA3-256");
            StringBuffer sout = new StringBuffer();
            byte[] hashedString = messageDigest.digest(sinput.getBytes());

            for (int i = 0; i < hashedString.length; i++) {
                sout.append(Integer.toString((hashedString[i] & 0xff) + 0x100, 16).substring(1));
            }
            sOut = hashToString(hashedString);
        }
        catch (Exception e) {
            return sOut;
        }
        return sOut;
    }

    public String Hash(String sinput, String hashToUse) throws NoSuchAlgorithmException
    {
        String sOut = "";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(hashToUse);
            StringBuffer sout = new StringBuffer();
            byte[] hashedString = messageDigest.digest(sinput.getBytes());

            for (int i = 0; i < hashedString.length; i++) {
                sout.append(Integer.toString((hashedString[i] & 0xff) + 0x100, 16).substring(1));
            }
            sOut = hashToString(hashedString);
        }
        catch (Exception e) {
            return sOut;
        }
        return sOut;
    }

    public String hashoffile(String sfileName)
    {
        try {
            byte[] buffer = new byte[8192];
            int count;
            MessageDigest digest = MessageDigest.getInstance("SHA3-256");
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(sfileName));
            while ((count = bis.read(buffer)) > 0) {
                digest.update(buffer, 0, count);
            }
            bis.close();
            String ed = "";

            byte[] hash = digest.digest();
            System.out.println(hash.toString());
            return ed;
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
            return "No";
        }
    }



    public static String hashToString(byte[] hash) {
        StringBuffer buff = new StringBuffer();
        byte[] var2 = hash;
        int var3 = hash.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            byte b = var2[var4];
            buff.append(String.format("%02x", b & 255));
        }
        return buff.toString();
    }

    public static String hashToString(MessageDigest hash) {
        return hashToString(hash.digest());
    }


    public String naiveBufferedFileReaderHash(String sFileInput, String hashToUse, Integer bsize) throws NoSuchAlgorithmException
    {
        String scur = "";
        String scurHash = "";
        String sHash = "";
        try
        {
            FileInputStream fin = new FileInputStream(sFileInput);
            try (BufferedInputStream in = new BufferedInputStream(fin))
            {
                byte[] bbuf = new byte[bsize];
                int len;
                while ((len = in.read(bbuf)) != -1) {
                    // process data here: bbuf[0] thru bbuf[len - 1]
                    scur = hashToString(bbuf);//scur == hexadecimal ascii
                    scurHash = Hash(scur, hashToUse);
                    sHash = sHash + scurHash;
                    sHash = Hash(sHash, hashToUse); }
            }
            fin.close();
            return sHash;
        }
        catch (Exception e)
        {
            return sHash;
        }
    }





}
