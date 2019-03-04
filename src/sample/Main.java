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
        Button btnAffine = new Button("      Affine Shift      ");
        leftMenu.setStyle("-fx-background-color: #68E1E6;");
        leftMenu.getChildren().addAll(btnVig,btnAffine,btnFreq);

        btnVig.setOnAction(e -> {
            System.out.println("RAN");
            runVigenere();
        });

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10,10,10,10));
        gridPane.setVgap(8);
        gridPane.setHgap(10);


        Label nameLabel = new Label("Username");
        GridPane.setConstraints(nameLabel,0,0);

        TextField nameInput = new TextField("Username");
        GridPane.setConstraints(nameInput, 1,0);

        Label passLabel = new Label("Password");
        GridPane.setConstraints(passLabel,0,1);

        TextField passInput = new TextField("Password");
        GridPane.setConstraints(passInput, 1,1);

        Button btnLogin = new Button("Log In");
        GridPane.setConstraints(btnLogin, 1, 2);

        gridPane.getChildren().addAll(nameLabel, nameInput, passLabel, passInput, btnLogin);



        borderPane = new BorderPane();
        borderPane.setLeft(leftMenu);
        borderPane.setCenter(gridPane);

        Scene scene = new Scene(borderPane, 1000, 500);
        window.setScene(scene);
        window.show();
    }

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

        Label title = new Label(" Vigenere Cipher ");
        title.setMinSize(100,50);
        GridPane.setConstraints(title, 0,0);

        gridPane.getChildren().addAll(labelKey,keyInput,messageInput,labelMessage,
                btnEncipher, btnDecipher, title);

        vigenerePane.setLeft(gridPane);
        StackPane rightSide = new StackPane();
        rightSide.getChildren().add(resultsTextArea);
        vigenerePane.setCenter(rightSide);
        borderPane.setCenter(vigenerePane);
    }

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

