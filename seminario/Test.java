package seminario;

import java.io.File;

public class Test {
    public static void main(String[] args) {
        String key = "0000000000001234";
        File inputFile = new File("document2022.encrypted");
        File encryptedFile = new File ("document2022.encrypted");
        File decryptedFile = new File("document2022.decrypted");

        try{
            Crypto.encrypt(key, inputFile, encryptedFile);
            Crypto.decrypt(key, encryptedFile, decryptedFile);
        }catch( CryptoException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
}
