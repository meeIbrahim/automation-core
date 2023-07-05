package aws;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;

public class S3PemReader {

    public static Performable downloadPemFile(String filename) throws IOException {
        return Task.where("{0} gets pem file from aws", actor -> {

            String accessKey = "AKIARJULHOYL45HWWYQV";
            String secretKey = "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";
            String bucketName = "automation-test-resources";
            String keyName = filename.trim();// the name of the credentials file in the bucket
            String FilePath = "src/test/resources/pem/" + filename.trim();


            BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
            AmazonS3 s3client = AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(credentials))
                    .build();

            S3Object s3object = s3client.getObject(new GetObjectRequest(bucketName, keyName));
            File file = new File(FilePath);
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file);
                fos.write(s3object.getObjectContent().readAllBytes());
                fos.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            System.out.println("File downloaded to: " + FilePath);

        });
    }
    public static Performable downloadPemFileAt(String FilePath) throws IOException {
        return Task.where("{0} gets pem file at "+FilePath+" from aws", actor -> {

            String accessKey = "AKIARJULHOYL45HWWYQV";
            String secretKey = "ya2TwOjeyXM99c7T3b1kCdLQa+4+VgniDdMl1mUI";
            String bucketName = "automation-test-resources";
            String keyName = FilePath.replace("src/test/resources/pem/","").trim();// the name of the credentials file in the bucket
            System.out.println("Trying getting key : "+keyName+" from bucket : "+bucketName);


            BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
            AmazonS3 s3client = AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(credentials))
                    .build();

            S3Object s3object = s3client.getObject(new GetObjectRequest(bucketName, keyName));
            File file = new File(FilePath.trim());
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file);
                fos.write(s3object.getObjectContent().readAllBytes());
                fos.close();
                System.out.println("File downloaded to: " + FilePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
    public static Performable deletePemFile(String filename) {
        return Task.where("{0} gets pem file from aws", actor -> {
            String filePath = "src/test/resources/pem/" + filename.trim();
            Path fileToDelete = Paths.get(filePath);
            try {
                Files.delete(fileToDelete);
                System.out.println("File deleted successfully.");
            } catch (Exception e) {
                System.out.println("Failed to delete the file.");
            }
        });
    }

    public static Performable deletePemFileAt(String filePath) {
        return Task.where("{0} gets pem file from aws", actor -> {
            Path fileToDelete = Paths.get(filePath);
            try {
                Files.delete(fileToDelete);
                System.out.println("File deleted successfully.");
            } catch (Exception e) {
                System.out.println("Failed to delete the file.");
            }
        });
    }


}

