package sample;

import java.util.ArrayList;

public class Vigenere {
    /** Cipher key **/
    String key;

    //Constructor
    public Vigenere(String key){
        this.key = key;
    }

    /*****************************************
     * Takes in a plaintext string and encrypts
     * it to a ciphertext string
     *
     * @param plainText
     * @return encrypted string
     */
    public String encrypt(String plainText){
        //Holds encrypted numbers
        ArrayList<Integer> encryptedNumbers = new ArrayList<Integer>();

        //holds the message as their integer value (ascii value - 97)
        ArrayList<Integer> numbers;
        numbers = stringToInteger(plainText);

        //getting the key as a list of numbers as well
        ArrayList<Integer> key = stringToInteger(this.key);

        //represents our position in the key as we loop through it
        int pointer = 0;

        //temporary variable to hold each encrypted number during the loop
        int encryptedNumber;

        //Encrypts each letter one at a time and adds them to encryptedNumbers
        for(int number: numbers){
            //Encrypting the letter to itself plus the letter of the key
            encryptedNumber = (number + key.get(pointer)) % 26;

            //adding that new letter to the list of encrypted letters
            encryptedNumbers.add(encryptedNumber);

            //increasing where we are in the key
            pointer++;

            //Making sure the pointer loops back to the beginning of the key if it gets bigger than it
            pointer = pointer % key.size();
        }

        String encryptedText = integersToString(encryptedNumbers);
        return encryptedText;

    }

    /***************************************************
     * Decrypts a cipher text by looping through the key
     * and minusing it from the letter.
     * @param cipherText
     * @return the decrypted plaintext message
     */
    public String decrypt(String cipherText){
        ArrayList<Integer> decryptedNumbers = new ArrayList<Integer>();
        ArrayList<Integer> numbers;
        numbers = stringToInteger(cipherText);
        ArrayList<Integer> key = stringToInteger(this.key);

        //represents our position in the key as we loop through it
        int pointer = 0;

        //temporary variable to hold each encrypted number during the loop
        int decryptedNumber;

        //Encrypts each letter one at a time and adds them to encryptedNumbers
        for(int number: numbers){
            //Encrypting the letter to itself plus the letter of the key
            //if it goes negative, we must increase by 26 to make it its positive counterpart
            int numberMinusKeyNumber = (number - key.get(pointer)) % 26;
            if(numberMinusKeyNumber < 0){
                numberMinusKeyNumber += 26;
            }
            decryptedNumber = numberMinusKeyNumber;

            //adding that new letter to the list of encrypted letters
            decryptedNumbers.add(decryptedNumber);

            //increasing where we are in the key
            pointer++;

            //Making sure the pointer loops back to the beginning of the key if it gets bigger than it
            pointer = pointer % key.size();
        }

        String decryptedText = integersToString(decryptedNumbers);
        return decryptedText;

    }

    /*********************************************
     * converts a string to an integer array list
     * with a = 0, b = 1, and so on.
     * @param text
     * @return the integers representing the string
     */
    private ArrayList<Integer> stringToInteger(String text){
        ArrayList<Integer> lettersAsNumbers = new ArrayList<Integer>();
        for(char letter: text.toCharArray()){
            int letterNumber = letter - 97;
            lettersAsNumbers.add(letterNumber);
        }
        return lettersAsNumbers;
    }


    /***********************************************
     * Converts an arraylist of integers to a string.
     * 0 = a, 1 = b, and so on.
     * @param lettersAsNumbers
     * @return the string that the numbers represented
     */
    private String integersToString(ArrayList<Integer> lettersAsNumbers){
        String text = "";
        for(int number: lettersAsNumbers){
            char letter = (char)((number + 97));
            text += letter;
        }
        return text;
    }

    public static void main(String args[]){
        Vigenere tester = new Vigenere("secret");

        String plaintext = "attackatdawn";
        System.out.println("Plain text: " + plaintext);

        String encrypted = tester.encrypt(plaintext);
        System.out.println("Message encrypted: " + encrypted);

        String decrypted = tester.decrypt(encrypted);
        System.out.println("Message decrypted: " + decrypted);
    }
}
