package Application;

import javafx.util.Pair;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import static java.nio.file.Files.size;

public class DownloadHasher {


    public static String getFileName(URL urlInput)
    {
        String sout = "";

        // System.out.println(FilenameUtils.getBaseName(url.getPath())); // -> file
        //System.out.println(FilenameUtils.getExtension(url.getPath())); // -> xml
        //System.out.println(FilenameUtils.getName(url.getPath())); // -> file.xml
        sout = FilenameUtils.getName(urlInput.getPath());
        return sout;
    }

    /** Create directory to save? FileUtils.moveFileToDirectory
     * Download the file, save it, get the hash, if not permenantly storing file, delete it
     * @param sInputLocation URL of item
     * @param bdelete if true delete else keep
     */
    public static String bufferDownFullHash(String sInputLocation, boolean bdelete, int bsize)
    {
        ArrayList<Pair<String,String>> meta = new ArrayList<>();
        String sout = "";
        String sFile = "";
        try {
            URL website = new URL(sInputLocation);
            sFile = getFileName(website);
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            FileOutputStream fos = new FileOutputStream(sFile);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        }
        catch (Exception except)
        {
            System.out.println(except);
        }
        try
        {
            Hasher Hash = new Hasher();
            sout = Hash.naiveBufferedFileReaderHash(sFile, "SHA3-256", bsize);

            if (bdelete == true) {
                File donewith = new File(sFile);
                FileUtils.deleteQuietly(donewith);
            }

        }
        catch (Exception exc)
        {
            System.out.println(exc);
        }
        return sout;
    }

    /** Create directory to save? FileUtils.moveFileToDirectory
     * Download the file, save it, get the hash, if not permenantly storing file, delete it
     * @param sInputLocation URL of item
     */
    public static String bufferDownFullHash(String sInputLocation)
    {
        ArrayList<Pair<String,String>> meta = new ArrayList<>();
        String sout = "";
        String sFile = "";
        try {
            URL website = new URL(sInputLocation);
            sFile = getFileName(website);
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            FileOutputStream fos = new FileOutputStream(sFile);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        }
        catch (Exception except)
        {
            System.out.println(except);
        }
        try
        {
            Hasher Hash = new Hasher();
            sout = Hash.naiveBufferedFileReaderHash(sFile, "SHA3-256", 8192);
            File donewith = new File(sFile);
            FileUtils.deleteQuietly(donewith);

        }
        catch (Exception exc)
        {
            System.out.println(exc);
        }
        return sout;
    }

    /** Create directory to save? FileUtils.moveFileToDirectory
     * Download the file, save it, get the hash, if not permenantly storing file, delete it
     * @param sInputLocation URL of item
     * @return Hash : Name : Size
     */
    public static Pair<String, Pair<String, String>> bufferDownFullHashTuple(String sInputLocation)
    {
        String sTrimmed = sInputLocation.replace(" ", "");
        Pair<String, Pair<String, String>> tuple;
        ArrayList<Pair<String,String>> meta = new ArrayList<>();
        String sout = "";
        String sFile = "";
        try {
            URL website = new URL(sTrimmed);
            sFile = getFileName(website);
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            FileOutputStream fos = new FileOutputStream(sFile);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        }
        catch (Exception except)
        {
            System.out.println(except);
        }
        try
        {
            Hasher Hash = new Hasher();
            sout = Hash.naiveBufferedFileReaderHash(sFile, "SHA3-256", 8192);
            Long size = getFileSize(sFile);
            File donewith = new File(sFile);
            FileUtils.deleteQuietly(donewith);
            tuple = new Pair<String, Pair<String, String>>(sout, new Pair<String, String>(sFile, Long.toString(size)));
            return tuple;
        }
        catch (Exception exc)
        {
            System.out.println(exc);
        }
        return new Pair<String, Pair<String, String>>(sout, new Pair<String, String>(sFile, Long.toString(0l)));
    }


    public static long getFileSize(String sFile){
        try {
            Path pfilepath = Paths.get(sFile);
            return size(pfilepath);
        }
        catch (IOException ioe)
        {
            System.out.println(ioe);
        }
        return 0;
    }

}
