package aws;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.services.securitytoken.AWSSecurityTokenService;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClientBuilder;
import com.amazonaws.services.securitytoken.model.GetSessionTokenRequest;
import com.amazonaws.services.securitytoken.model.GetSessionTokenResult;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import org.bouncycastle.openpgp.PGPException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

import static ui.com.ztna.web.common.variables.CommonVariables.*;

public class TemporaryCredentialsWithMFAAuthentication {
    public static String generateTotp(String secret) {
        GoogleAuthenticator gAuth = new GoogleAuthenticator();
        int code = gAuth.getTotpPassword(secret);
        if(code<100000){return "0"+String.valueOf(code); }
        else return String.valueOf(code);
    }


    public static Performable generateCredentials(String AwsAccountId) {
        return Task.where("{0} generates temporary AWS credentials", actor -> {
            String profile = getProfile(AwsAccountId);

            AWSCredentialsProvider profileCredentialsProvider;
            try {
                profileCredentialsProvider = new AWSStaticCredentialsProvider(
                        S3CredentialsReader.getCredentials(profile));
            } catch (IOException | PGPException e) {
                throw new RuntimeException(e);
            }

            // Get MFA token from Google Auth
            String secret;
            try {
                secret = S3CredentialsReader.getMfaSecret(profile);
            } catch (IOException | PGPException | URISyntaxException e) {
                throw new RuntimeException(e);
            }
            String mfaToken = generateTotp(secret);

            // Create an instance of the STS client
            AWSSecurityTokenService stsClient = AWSSecurityTokenServiceClientBuilder.standard()
                    .withCredentials(profileCredentialsProvider)
                    .build();

            // Build the get session token request
            String serialNumber;
            try {
                serialNumber=S3CredentialsReader.getAwsSerialNumber(profile);
            } catch (IOException | PGPException e) {
                throw new RuntimeException(e);
            }
            GetSessionTokenRequest getSessionTokenRequest = new GetSessionTokenRequest()
                    .withSerialNumber(serialNumber)
                    .withTokenCode(mfaToken);

            // Call the get session token method
            GetSessionTokenResult getSessionTokenResult = stsClient.getSessionToken(getSessionTokenRequest);

            System.out.println("AWS Access Key ID : " + getSessionTokenResult.getCredentials().getAccessKeyId() + "\n" +
                    "AWS Secret : " + getSessionTokenResult.getCredentials().getSecretAccessKey() + "\n" +
                    "Session Token : " + getSessionTokenResult.getCredentials().getSessionToken());

            // Initialize global AWS credentials variables
            awsAccessKeyId = getSessionTokenResult.getCredentials().getAccessKeyId();
            awsSecret = getSessionTokenResult.getCredentials().getSecretAccessKey();
            sessionToken = getSessionTokenResult.getCredentials().getSessionToken();
        });
    }
    public static HashMap<String, String> getCredentials(String AwsAccountId) {
        HashMap<String, String> credentials=new HashMap<>();

            String profile = getProfile(AwsAccountId);

            AWSCredentialsProvider profileCredentialsProvider;
            try {
                profileCredentialsProvider = new AWSStaticCredentialsProvider(
                        S3CredentialsReader.getCredentials(profile));
            } catch (IOException | PGPException e) {
                throw new RuntimeException(e);
            }

            // Get MFA token from Google Auth
            String secret;
            try {
                secret = S3CredentialsReader.getMfaSecret(profile);
            } catch (IOException | PGPException | URISyntaxException e) {
                throw new RuntimeException(e);
            }
            String mfaToken = generateTotp(secret);

            // Create an instance of the STS client
            AWSSecurityTokenService stsClient = AWSSecurityTokenServiceClientBuilder.standard()
                    .withCredentials(profileCredentialsProvider)
                    .build();

            // Build the get session token request
            String serialNumber;
            try {
                serialNumber=S3CredentialsReader.getAwsSerialNumber(profile);
            } catch (IOException | PGPException e) {
                throw new RuntimeException(e);
            }
            GetSessionTokenRequest getSessionTokenRequest = new GetSessionTokenRequest()
                    .withSerialNumber(serialNumber)
                    .withTokenCode(mfaToken);

            // Call the get session token method
            GetSessionTokenResult getSessionTokenResult = stsClient.getSessionToken(getSessionTokenRequest);

            System.out.println("AWS Access Key ID : " + getSessionTokenResult.getCredentials().getAccessKeyId() + "\n" +
                    "AWS Secret : " + getSessionTokenResult.getCredentials().getSecretAccessKey() + "\n" +
                    "Session Token : " + getSessionTokenResult.getCredentials().getSessionToken());

            // Initialize global AWS credentials variables
        credentials.put("AccessKey",getSessionTokenResult.getCredentials().getAccessKeyId());
        credentials.put("Secret",getSessionTokenResult.getCredentials().getSecretAccessKey());
        credentials.put("SessionToken",getSessionTokenResult.getCredentials().getSessionToken());
       return credentials;
    }

    public static String getProfile(String awsAccountId) {
        String profile=null;
        if(awsAccountId.trim().equals("537054089900")){
            profile="aws-integration-1";
        }else if(awsAccountId.trim().equals("149049417381"))
            profile="aws-integration-2";

        return profile;
    }

}






