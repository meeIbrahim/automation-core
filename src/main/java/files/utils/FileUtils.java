package files.utils;

import lombok.SneakyThrows;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileUtils {

    private static final String Directory = "DIR/target/";

    public static String toAbsolutePath(String filePath) {
        File file = new File(filePath);
        return file.getAbsolutePath();
    }

    @SneakyThrows
public static Boolean Download(String url, String SaveAs){
        URL FileUrl = new URL(url);
        String FileName = Directory.replace("DIR",System.getProperty("user.dir")).replaceAll("/", Matcher.quoteReplacement(File.separator)) + SaveAs;  // Replaces File Separator based on Platform
    try {
        org.apache.commons.io.FileUtils.copyURLToFile(FileUrl, new File(FileName));
        return true;
    }
    catch (IOException e){System.out.println("Exception Caught when downloading Agent: " + e);return false;}

}

    public static Boolean Exists(String File){
        File f = new File(File);
        return (f.exists() && !f.isDirectory());
    }

    /// Copied from DigitalOcean
    public static Boolean Unzip(String ZipFile){
        String Source = Directory.replace("DIR",System.getProperty("user.dir")).replaceAll("/", Matcher.quoteReplacement(File.separator)) + ZipFile;
        String Dest = Directory.replace("DIR",System.getProperty("user.dir")).replaceAll("/", Matcher.quoteReplacement(File.separator));
        File destDir = new File(Dest);
        FileInputStream fStream;
        byte [] buffer = new byte[1024];
        try {
            fStream = new FileInputStream(Source);
            ZipInputStream zis = new ZipInputStream(fStream);
            ZipEntry ze = zis.getNextEntry();
            while(ze != null){
                String fileName = ze.getName();
                File newFile = new File(destDir + File.separator + fileName);
                System.out.println("Unzipping to "+newFile.getAbsolutePath());
                //create directories for sub directories in zip
                new File(newFile.getParent()).mkdirs();
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
                //close this ZipEntry
                zis.closeEntry();
                ze = zis.getNextEntry();
            }
            zis.closeEntry();
            zis.close();
            fStream.close();
            return true;
        } catch (IOException e){e.printStackTrace(); return false;}
    }
}
