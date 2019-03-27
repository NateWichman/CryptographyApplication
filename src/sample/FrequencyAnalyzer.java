package sample;


public class FrequencyAnalyzer {
    private int[] alphabet = new int[128];
    private double[] frequencies = new double[128];

    public FrequencyAnalyzer(){
        //Empty constructor, just to remind that this is OO
    }

    public void analyzeString(String text){
        alphabet = new int[128];
        frequencies = new double[128];
        for(char letter: text.toCharArray()){
            alphabet[letter]++;
        }

        //Finding total number of letters.
        int total = 0;
        for(int i = 0; i < alphabet.length; i++){
            total += alphabet[i];
        }

        //Finding the frequencies for each letter
        for(int i = 0; i < alphabet.length; i++){
            frequencies[i] = ((double) alphabet[i]) / total;
        }
    }

    public double[] getFrequencies(){
        return frequencies;
    }

    public int[] getAlphabet() {
        return alphabet;
    }

    public static void main(String args[]){
    }
}


