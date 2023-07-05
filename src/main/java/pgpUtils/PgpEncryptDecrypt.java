package pgpUtils;

import org.bouncycastle.openpgp.PGPException;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;

import org.bouncycastle.bcpg.CompressionAlgorithmTags;
import org.bouncycastle.bcpg.SymmetricKeyAlgorithmTags;


public class PgpEncryptDecrypt {
    public PgpEncryptDecrypt() {
    }

    public static URL getUrl(String filePath) throws MalformedURLException {
        File file = new File(filePath);
        URI uri = file.toURI();
        URL url = uri.toURL();
        return url;
    }
    private static PgpEncryptionUtil pgpEncryptionUtil = null;
    private static PgpDecryptionUtil pgpDecryptionUtil = null;

    private static final String passkey = "dummy";

    private static final URL privateKey;
    private static final URL publicKey;

    static {
        try {
            privateKey = getUrl("src/test/resources/pgp/private.pgp");
            publicKey = getUrl("src/test/resources/pgp/public.pgp");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    static {
        pgpEncryptionUtil = PgpEncryptionUtil.builder()
                .armor(true)
                .compressionAlgorithm(CompressionAlgorithmTags.ZIP)
                .symmetricKeyAlgorithm(SymmetricKeyAlgorithmTags.AES_128)
                .withIntegrityCheck(true)
                .build();

        try {
            pgpDecryptionUtil = new PgpDecryptionUtil( privateKey.openStream(),passkey);
        } catch (IOException | PGPException e) {
            throw new RuntimeException(e);
        }
    }

    public void FileEncryption(String OriginalFilePath,String EncryptedFilePath) throws IOException, URISyntaxException, PGPException {

        // Generating a pgp encrypted temp file from the test file
        File encryptedFile = new File(EncryptedFilePath);
        try (OutputStream fos = Files.newOutputStream(encryptedFile.toPath())) {
            pgpEncryptionUtil.encrypt(fos, Files.newInputStream(new File(OriginalFilePath).toPath()), new File(OriginalFilePath).length(), publicKey.openStream());
        }

    }

    public void FileDecryption(String EncryptedFilePath, String DecryptedFilePath) throws IOException, PGPException {
        // Decrypting the generated pgp encrypted temp file and writing to another temp file
        pgpDecryptionUtil.decrypt(Files.newInputStream(new File(EncryptedFilePath).toPath()), Files.newOutputStream(new File(DecryptedFilePath).toPath()));
    }

    public static void main(String[] args) throws PGPException, IOException, URISyntaxException {
//        PgpEncryptDecrypt test= new PgpEncryptDecrypt();
////        test.FileEncryption("src/test/resources/pgp/Sample_CSV.csv","src/test/resources/pgp/UnencryptedFile.txt");
//        test.FileDecryption("src/test/resources/pgp/UnencryptedFile.txt","src/test/resources/pgp/DecryptedFile.txt");
//        System.out.println(loadResource("").getPath());
    }
}
