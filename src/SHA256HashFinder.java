import java.security.MessageDigest;
// import java.security.NoSuchAlgorithmException;
class  SHA256HashFinder {
    public static void main(String[] args) {
        String ourHashValue = "bfc7ff6540a90601239ffe055116ab88cc75c3cc0f8483c9ee7bad4fcf2aba7c";
        int desiredValue = -1;

        // these steps allow us to generate a hash function
        for (int number = 0; number < 500000000; number++) {
            String string = "LKD-" + number;
            String stringHash = sha256(string);

            if (ourHashValue.equalsIgnoreCase(stringHash)) {
                desiredValue = number;
                System.out.println(number + " - Hash: " + stringHash);
                break;
            }
        }

        // we use these to verify
        if (desiredValue != -1) {
            String string = "LKD-" + desiredValue;
            String stringHash = sha256(string);

            if (ourHashValue.equalsIgnoreCase(stringHash)) {
                System.out.println("You've located the hash you were searching for! The desired number is : " + desiredValue);
            }
        } else {
            System.out.println("There was no matching number discovered.");
        }
    }

    public static String sha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes("UTF-8"));

            // from hexadecimal to value of hash function
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}