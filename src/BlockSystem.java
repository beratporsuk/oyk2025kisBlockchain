import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class BlockSystem {
    private int index;
    private String previousHash;
    private String data;
    private long timestamp;
    private int nonce;
    private String hash;
    private int difficulty;

    //  Blok oluşturucu (constructor)
    public BlockSystem(int index, String previousHash, String data, int difficulty) {
        this.index = index;
        this.previousHash = previousHash;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
        this.difficulty = difficulty;
        this.nonce = 0;
        this.hash = mineBlock(); // Hash işlemi burada yapılıyor
    }

    //  SHA-256 ile hash hesaplama fonksiyonu
    private String calculateHash() {
        String input = index + previousHash + data + timestamp + nonce;
        return applySHA256(input);
    }

    //  Madencilik (Proof of Work) fonksiyonu
    private String mineBlock() {
        String targetPrefix = "0".repeat(difficulty);
        while (true) {
            hash = calculateHash();
            if (hash.startsWith(targetPrefix)) {
                return hash; // Uygun hash bulunduğunda döndür
            }
            nonce++;
        }
    }

    //  SHA-256 hash algoritması
    public static String applySHA256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


    public String getHash() {
        return hash;
    }


    @Override
    public String toString() {
        return "Block " + index +
                " | Hash: " + hash +
                " | Nonce: " + nonce +
                " | Previous Hash: " + previousHash;
    }

    public static void main(String[] args) {
        int difficulty = 5;

        BlockSystem genesisBlock = new BlockSystem(0, "0", "Genesis Block", difficulty);
        System.out.println(genesisBlock);


        BlockSystem secondBlock = new BlockSystem(1, genesisBlock.getHash(), "Second Block", difficulty);
        System.out.println(secondBlock);




    }
}