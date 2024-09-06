public class VigenereCipher {
    private String key;
    private String extendedKey;

    public VigenereCipher(String key) {
        validateKey(key);
        this.key = key.toUpperCase();
    }

    private void validateKey(String key) {
        if (!key.matches("[a-zA-Z]+")) {
            throw new IllegalArgumentException("Key should only contain alphabetic characters.");
        }
    }

    private String generateExtendedKey(String message) {
        StringBuilder extendedKeyBuilder = new StringBuilder();
        int keyIndex = 0;
        for (int i = 0; i < message.length(); i++) {
            char currentChar = message.charAt(i);
            if (Character.isAlphabetic(currentChar)) {
                extendedKeyBuilder.append(key.charAt(keyIndex));
                keyIndex = (keyIndex + 1) % key.length();
            } else {
                extendedKeyBuilder.append(currentChar);
            }
        }
        return extendedKeyBuilder.toString();
    }

    public String encrypt(String message) {
        message = message.toUpperCase();
        extendedKey = generateExtendedKey(message);
        StringBuilder ciphertext = new StringBuilder();
        for (int i = 0; i < message.length(); i++) {
            char currentChar = message.charAt(i);
            if (Character.isAlphabetic(currentChar)) {
                char encryptedChar = (char) (((currentChar - 'A' +
                        extendedKey.charAt(i) - 'A') % 26) + 'A');
                ciphertext.append(encryptedChar);
            } else {
                ciphertext.append(currentChar);
            }
        }
        return ciphertext.toString();
    }

    public String decrypt(String ciphertext) {
        ciphertext = ciphertext.toUpperCase();
        extendedKey = generateExtendedKey(ciphertext);
        StringBuilder plaintext = new StringBuilder();
        for (int i = 0; i < ciphertext.length(); i++) {
            char currentChar = ciphertext.charAt(i);
            if (Character.isAlphabetic(currentChar)) {
                char decryptedChar = (char) (((currentChar - 'A' -
                        (extendedKey.charAt(i) - 'A') + 26) % 26) + 'A');
                plaintext.append(decryptedChar);
            } else {
                plaintext.append(currentChar);
            }
        }
        return plaintext.toString();
    }
}
