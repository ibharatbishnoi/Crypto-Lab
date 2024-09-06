public class PlayfairCipher {
    private char[][] matrix;

    public PlayfairCipher(String key) {
        initializeMatrix(key);
    }

    private void initializeMatrix(String key) {
        matrix = new char[5][5];
        boolean[] usedChars = new boolean[26];
        int keyIndex = 0;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (keyIndex < key.length()) {
                    char currentChar = Character.toUpperCase(key.charAt(keyIndex));

                    if (Character.isAlphabetic(currentChar) && !usedChars[currentChar - 'A']) {
                        matrix[i][j] = currentChar;
                        usedChars[currentChar - 'A'] = true;
                        keyIndex++;
                    } else {
                        j--;
                    }
                } else {
                    for (char c = 'A'; c <= 'Z'; c++) {
                        if (c != 'J' && !usedChars[c - 'A']) {
                            matrix[i][j] = c;
                            usedChars[c - 'A'] = true;
                            break;
                        }
                    }
                }
            }
        }
    }

    private String formatMessage(String message) {
        message = message.toUpperCase().replaceAll("[^A-Z]", "");
        return message;
    }

    public String processPair(char first, char second, int direction) {
        int[] firstPos = findPosition(first);
        int[] secondPos = findPosition(second);
        int firstRow = (firstPos[0] + direction) % 5;
        int firstCol = (firstPos[1] + direction) % 5;
        int secondRow = (secondPos[0] + direction) % 5;
        int secondCol = (secondPos[1] + direction) % 5;
        firstRow = (firstRow + 5) % 5;
        firstCol = (firstCol + 5) % 5;
        secondRow = (secondRow + 5) % 5;
        secondCol = (secondCol + 5) % 5;

        if (firstPos[0] == secondPos[0]) {
            return getEncryptedPair(firstPos[0], firstCol, secondPos[0], secondCol);
        } else if (firstPos[1] == secondPos[1]) {
            return getEncryptedPair(firstRow, firstPos[1], secondRow, secondPos[1]);
        } else {
            return getEncryptedPair(firstPos[0], secondPos[1], secondPos[0], firstPos[1]);
        }
    }

    private String getEncryptedPair(int row1, int col1, int row2, int col2) {
        return String.valueOf(matrix[row1][col1]) + matrix[row2][col2];
    }

    private int[] findPosition(char target) {
        int[] position = new int[2];

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (matrix[i][j] == target) {
                    position[0] = i;
                    position[1] = j;
                    return position;
                }
            }
        }

        return position;
    }

    public String encrypt(String message) {
        message = formatMessage(message);
        StringBuilder ciphertext = new StringBuilder();

        for (int i = 0; i < message.length(); i += 2) {
            char first = message.charAt(i);
            char second = (i + 1 < message.length()) ? message.charAt(i + 1) : 'X';

            if (first == second) {
                second = 'X';
                i--;
            }

            ciphertext.append(processPair(first, second, 1));
        }

        return ciphertext.toString();
    }

    public String decrypt(String ciphertext) {
        StringBuilder plaintext = new StringBuilder();

        for (int i = 0; i < ciphertext.length(); i += 2) {
            char first = ciphertext.charAt(i);
            char second = ciphertext.charAt(i + 1);
            plaintext.append(processPair(first, second, -1));
        }

        return plaintext.toString();
    }

    public char[][] getMatrix() {
        return matrix;
    }
}
