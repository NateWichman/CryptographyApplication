/******************************************************
 * Graphical User Interface for a cryptography toolbox
 * application using other classes in this package.
 *
 * @author Nathan Wichman
 * @version Winter 2019
 ******************************************************/
package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.math.BigInteger;

public class Main extends Application{
    Stage window;
    BorderPane borderPane;

    public static void main(String args[]){
        //method from application parent class, triggers 'start' method
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        window.setTitle("Cryptography Toolbox");

        //creating left hand menu
        VBox leftMenu = new VBox();
        leftMenu.setSpacing(20);
        leftMenu.setAlignment(Pos.CENTER);
        Button btnVig = new Button("  Vigenere Cipher  ");
        Button btnFreq = new Button("Frequency Anylizer");
        Button btnRSA = new Button("           RSA            ");
        leftMenu.setStyle("-fx-background-color: #68E1E6;");
        leftMenu.getChildren().addAll(btnVig,btnRSA,btnFreq);

        //Vigenere button method
        btnVig.setOnAction(e -> {
            runVigenere();
        });

        //Frequency analyzer button
        btnFreq.setOnAction(e -> {
            runFequencyAnalyzer();
        });

        btnRSA.setOnAction(e -> {
            runRSA();
        });


        //Initializing instance variable. This layout is the overall frame of the application's view
        borderPane = new BorderPane();

        //Adding left hand menu to the border pane.
        borderPane.setLeft(leftMenu);

        //Adding border pane layout to a scene
        Scene scene = new Scene(borderPane, 1000, 500);

        //adding that scene to the window
        window.setScene(scene);

        //displaying the window
        window.show();
    }

    private void runRSA(){
        BorderPane rsaPane = new BorderPane();
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10,10,10,10));
        gridPane.setVgap(8);
        gridPane.setHgap(10);

        Label labelP = new Label("Large Prime Integer 'P': ");
        GridPane.setConstraints(labelP,0,0);
        TextField pInput = new TextField();
        GridPane.setConstraints(pInput, 1,0);

        Label labelQ = new Label("Large Prime Integer 'Q': ");
        GridPane.setConstraints(labelQ,0,1);
        TextField qInput = new TextField();
        GridPane.setConstraints(qInput, 1,1);

        Label labelE = new Label("Encryption Exponent e guess (Self Correcting): ");
        GridPane.setConstraints(labelE,0,2);
        TextField eInput = new TextField();
        GridPane.setConstraints(eInput, 1,2);

        Label labelMessage = new Label("Message: ");
        GridPane.setConstraints(labelMessage,0,3);
        TextField messageInput = new TextField();
        GridPane.setConstraints(messageInput, 1,3);

        //Button to initiate enciphering
        Button btnEncrypt = new Button("Encrypt");
        GridPane.setConstraints(btnEncrypt, 0, 4);

        //Button to initiate decrypting
        Button btnAnalyze = new Button("Decrypt");
        GridPane.setConstraints(btnAnalyze, 0, 5);

        gridPane.getChildren().addAll(labelP, pInput, labelE, eInput, labelQ, qInput, messageInput, labelMessage,
                btnAnalyze,btnEncrypt);

        //Text Area to display results of cipher
        TextArea resultsTextArea = new TextArea();
        resultsTextArea.setDisable(true);

        /**********************************Logic Area************************************/
        RSA rsaMachine = new RSA();

        btnEncrypt.setOnAction(e -> {
                BigInteger p = new BigInteger(pInput.getText().replace(" ", ""));
                BigInteger q = new BigInteger(qInput.getText().replace(" ", ""));
                BigInteger ex = new BigInteger(eInput.getText().replace(" ", ""));

                try {
                    rsaMachine.setup(p, q, ex);
                    BigInteger message = new BigInteger(messageInput.getText().replace(" ",""));
                    BigInteger result = rsaMachine.encrypt(message);
                    resultsTextArea.setText(result.toString());
                } catch (Exception exe) {
                    resultsTextArea.setText(exe.getMessage());
                }
        });

        btnAnalyze.setOnAction(e -> {
            try{
                BigInteger message = new BigInteger(messageInput.getText().replace(" ",""));
                BigInteger plaintext = rsaMachine.decyrpt(message);
                resultsTextArea.setText(plaintext.toString());
            }catch (Exception ex){
                resultsTextArea.setText(ex.getMessage());
            }
        });

        /**********************************Initialize Area************************************/
        rsaPane.setCenter(resultsTextArea);
        rsaPane.setLeft(gridPane);
        borderPane.setCenter(rsaPane);
    }

    public void handle(){

    }

    private void runFequencyAnalyzer(){
        FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();

        BorderPane frequncyPane = new BorderPane();

        //Creating grid pane to display
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10,10,10,10));
        gridPane.setVgap(8);
        gridPane.setHgap(10);

        //Label for key
        Label labelMessage = new Label("Text: ");
        GridPane.setConstraints(labelMessage,0,3);

        //Text field for key input
        TextField messageInput = new TextField();
        GridPane.setConstraints(messageInput, 1,3);

        //Text Area to display results of cipher
        TextArea resultsTextArea = new TextArea();
        resultsTextArea.setDisable(true);

        //Button to initiate enciphering
        Button btnAnalyze = new Button("Analyze");
        GridPane.setConstraints(btnAnalyze, 0, 5);

        btnAnalyze.setOnAction(e -> {
            frequencyAnalyzer.analyzeString(messageInput.getText());

            double[] frequencies = frequencyAnalyzer.getFrequencies();
            String outputText = "";
            DecimalFormat fm = new DecimalFormat("#.00");
            for(int i = 0; i < frequencies.length; i++){
                if(frequencies[i] > 0.0){
                    double percent = (frequencies[i] * 100.0);
                    outputText += (char)i + ": " + fm.format(percent) + " %\n";
                }
            }

            resultsTextArea.setText(outputText);
        });

        //Title label
        Label title = new Label(" Frequency Analyzer ");
        title.setMinSize(100,50);
        GridPane.setConstraints(title, 0,0);

        gridPane.getChildren().addAll(labelMessage, messageInput, btnAnalyze, title);

        frequncyPane.setCenter(resultsTextArea);
        frequncyPane.setLeft(gridPane);

        borderPane.setCenter(frequncyPane);

    }

    /*****************************************************
     *Switches the program's view to the Vigenere cipher's
     * page, and runs the logic for that cipher
     ****************************************************/
    private void runVigenere(){
        Vigenere vigenereMachine = new Vigenere("key");

        BorderPane vigenerePane = new BorderPane();

        //Creating grid pane to display
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10,10,10,10));
        gridPane.setVgap(8);
        gridPane.setHgap(10);

        //Label for key
        Label labelKey = new Label("Enter a key:");
        GridPane.setConstraints(labelKey,0,3);

        //Text field for key input
        TextField keyInput = new TextField();
        GridPane.setConstraints(keyInput, 1,3);

        //Label for the message
        Label labelMessage = new Label("Message: " );
        GridPane.setConstraints(labelMessage,0,4);

        //Text field for the message input
        TextField messageInput = new TextField();
        GridPane.setConstraints(messageInput, 1, 4);

        //Text Area to display results of cipher
        TextArea resultsTextArea = new TextArea();
        resultsTextArea.setDisable(true);
        GridPane.setConstraints(resultsTextArea, 3,3);

        //Button to initiate enciphering
        Button btnEncipher = new Button("Encipher");
        GridPane.setConstraints(btnEncipher, 0, 5);

        //Button to initiate deciphering
        Button btnDecipher = new Button("Decipher");
        GridPane.setConstraints(btnDecipher,1,5);

        //Enciphers the inputted message by the inputted key, displays to text area
        btnEncipher.setOnAction(e -> {
            try {
                if(validateText(keyInput.getText().toLowerCase())
                        && validateText(messageInput.getText().toLowerCase())){
                    vigenereMachine.key = keyInput.getText().toLowerCase();
                    String cipherText = vigenereMachine.encrypt(messageInput.getText());
                    resultsTextArea.setText("Key: " + vigenereMachine.key);
                    resultsTextArea.appendText("\nPlain-Text given: " + messageInput.getText());
                    resultsTextArea.appendText("\nCipher-Text encrypted: " + cipherText);
                }else{
                    resultsTextArea.setText("Message and key should be letters a-z only\n" +
                            "no spaces or special characters please");
                }
            }catch(Exception ex){
                System.err.println("Error encountered encrypting: " + ex.getMessage());
                resultsTextArea.setText("Error encountered encrypting: " + ex.getMessage());
            }
        });

        //Deciphers the inputted message by the inputted key, displays to text area
        btnDecipher.setOnAction(e -> {
            try {
                if(validateText(keyInput.getText().toLowerCase())
                        && validateText(messageInput.getText().toLowerCase())) {
                    vigenereMachine.key = keyInput.getText();
                    String plainText = vigenereMachine.decrypt(messageInput.getText());
                    resultsTextArea.setText("Key: " + vigenereMachine.key);
                    resultsTextArea.appendText("\nCipher-Text given: " + messageInput.getText());
                    resultsTextArea.appendText("\nPlain-Text decrypted: " + plainText);
                }else{
                    resultsTextArea.setText("Message and key should be letters a-z only\n" +
                            "no spaces or special characters please");
                }
            }catch(Exception ex){
                System.err.println("Error encountered decrypting: " + ex.getMessage());
                resultsTextArea.setText("Error encountered decrypting: " + ex.getMessage());
            }
        });

        //Title label
        Label title = new Label(" Vigenere Cipher ");
        title.setMinSize(100,50);
        GridPane.setConstraints(title, 0,0);

        //Adding everything to the grid pane
        gridPane.getChildren().addAll(labelKey,keyInput,messageInput,labelMessage,
                btnEncipher, btnDecipher, title);

        //finishing up the pane and adding it within the border pane instance variable
        vigenerePane.setLeft(gridPane);
      //  StackPane rightSide = new StackPane();
      //  rightSide.getChildren().add(resultsTextArea);
        vigenerePane.setCenter(resultsTextArea);
        borderPane.setCenter(vigenerePane);
    }

    /***************************************************************
     * Takes in a string and returns true if it only contains
     * letters a through z, with no spaces of special characters.
     * Only checks for lower case letters, so be sure to convert
     * to lower case before hand if you are interested in capitals
     * as well
     *
     * @param text to be validated
     * @return whether or not the string is valid
     **************************************************************/
    private Boolean validateText(String text){
        //Making sure text is all lowercase letters
        for(char character: text.toCharArray()){
            if(character < 97 || character > 122)
                return false;
        }

        //returns true if it makes it out of the for loop
        return true;
    }
}

  /*  Stage window;
    Scene scene1, scene2;

    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        window.setOnCloseRequest(e -> {
        });

        Label label1 = new Label("Welcome to the first scene");
        Button btn1 = new Button("Go to scene 2");
        btn1.setOnAction(e -> {
            //Changing the scene
            window.setScene(scene2);
        });

        //Layout 1- children are laid out in a vertical column
        VBox layout1 = new VBox(20);
        layout1.getChildren().addAll(label1, btn1);
        scene1 = new Scene(layout1, 1000, 500);

        Button btn2 = new Button("Return to scene 1");
        btn2.setOnAction(e -> {
            window.setScene(scene1);
        });

        //Layout 2
        StackPane layout2 = new StackPane();
        layout2.getChildren().add(btn2);
        scene2 = new Scene(layout2, 1000,500);

        //Setting default scene
        window.setScene(scene1);

        window.setTitle("Test Application");
        window.show();
    }

    public static void main(String[] args){
        launch(args);
    } */





        //This is going to be my only window, so primaryStage = only stage
        //primaryStage.setTitle("Cryptography ToolBox");

        /*
        btn = new Button();
        btn.setText("Heres a fucking button");


        //Setting the 'handle' method to be run if the button is clicked
        btn.setOnAction(e -> {
            System.out.println("Hey now, brown cow");
        });

        //Creating home layout
        StackPane  layoutHome = new StackPane();
        layoutHome.getChildren().add(btn);

        //Creating home scene, with the layoutHome as its template
        Scene scene = new Scene(layoutHome, 300, 250);

        //Displaying the home scene
        primaryStage.setScene(scene);
        primaryStage.show();


    } */

   // @Override
   /* public void handle(ActionEvent event){
        //Called whenever an event occurs
        if(event.getSource() == btn){
            //Runs if the button is clicked.
        }

    } */

  /*  public static void main(String[] args) {
        //Inherited method from the Application class, calls overridden start method
        launch(args);
    } */

