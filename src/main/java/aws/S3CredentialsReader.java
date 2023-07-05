package aws;
import java.io.*;
import java.net.URISyntaxException;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import org.bouncycastle.openpgp.PGPException;
import pgpUtils.PgpEncryptDecrypt;

public class S3CredentialsReader {

    private static String bucketName = "automation-test-resources";
    private static String keyName = "EncryptedFile.txt";
    private static void delete(File file) {
        if (file.delete()) {
            System.out.println("File deleted successfully.");
        } else {
            System.out.println("Failed to delete file.");
        }
    }

    private static S3Object getS3Object() {
       String accessKey = "AKIARJULHOYL45HWWYQV";
       String secretKey = "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";

        BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

        AmazonS3 s3client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();

        S3Object s3object = s3client.getObject(new GetObjectRequest(bucketName, keyName));

        return s3object;
    }
    private static File getDecryptedFile(S3Object s3Object) throws IOException, PGPException {
        // Create a temporary file
        File tempFile = File.createTempFile("Encrypted", "File");
        File decryptedFile = File.createTempFile("Decrypted", "File");

        // Write the S3Object data to the temporary file
        OutputStream outputStream = new FileOutputStream(tempFile);
        IOUtils.copy(s3Object.getObjectContent(), outputStream);
        outputStream.close();
        s3Object.close();

        //Encryption
        PgpEncryptDecrypt pgp= new PgpEncryptDecrypt();
        pgp.FileDecryption(tempFile.getPath(), decryptedFile.getPath());

        // Delete the temporary file when it is no longer needed
       delete(tempFile);

        return decryptedFile;
    }

    public static BasicAWSCredentials getCredentials(String profileName) throws IOException, PGPException {
        String profileAccessKey = null;
        String profileSecretKey = null;

        S3Object s3object =getS3Object();

        File decryptedFile = getDecryptedFile(s3object);
        InputStream inputStream = new FileInputStream(decryptedFile);

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;

        while ((line = reader.readLine()) != null) {
            String[] tokens = line.split("=");
            if (tokens[0].equals(profileName + ".aws_access_key_id ") | tokens[0].equals(profileName + ".aws_access_key_id")) {
                profileAccessKey = tokens[1].trim();
            } else if (tokens[0].equals(profileName + ".aws_secret_access_key ") | tokens[0].equals(profileName + ".aws_secret_access_key")){
                profileSecretKey = tokens[1].trim();
            }
        }
        reader.close();
        delete(decryptedFile);

        return new BasicAWSCredentials(profileAccessKey,profileSecretKey);
    }

    public static String getMfaSecret(String profileName) throws IOException, PGPException, URISyntaxException {
        String mfaSecet = null;

        S3Object s3object =getS3Object();

        File decryptedFile = getDecryptedFile(s3object);
        InputStream inputStream = new FileInputStream(decryptedFile);

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        while ((line = reader.readLine()) != null) {
            String[] tokens = line.split("=");
            if (tokens[0].equals(profileName + ".mfa_secret ") | tokens[0].equals(profileName + ".mfa_secret")) {
                mfaSecet = tokens[1].trim();
            }
        }
        reader.close();
        delete(decryptedFile);

        return mfaSecet;
    }
    public static String getAwsSerialNumber(String profileName) throws IOException, PGPException {
        String awsSerialNumber = null;

        S3Object s3object =getS3Object();

        File decryptedFile = getDecryptedFile(s3object);
        InputStream inputStream = new FileInputStream(decryptedFile);

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        while ((line = reader.readLine()) != null) {
            String[] tokens = line.split("=");
            if (tokens[0].equals(profileName + ".aws_serial_number ") | tokens[0].equals(profileName + ".aws_serial_number")) {
                awsSerialNumber = tokens[1].trim();
            }
        }
        reader.close();
        delete(decryptedFile);

        return awsSerialNumber;
    }

    public static void main(String[] args) throws PGPException, IOException, URISyntaxException {
        System.out.println(getAwsSerialNumber("aws-integration-1"));
    }
}

