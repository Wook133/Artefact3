package Application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.io.File;
import java.util.concurrent.atomic.AtomicReference;

public class Runner extends Application {
    String smartcontractaddress1 = "0xc83bebe3fe6f197715d18d9723458802ce068c8f";
    protected boolean loggedIn = false;
    protected File selectedFile;
    protected String sPath;
    protected String sPassword;

    Scene scLogin;
    Scene scMenu;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("deVill Chain");

        scLogin = createLoginScene(primaryStage);
        primaryStage.setScene(scLogin);
        primaryStage.show();
    }




    Scene scCur;

    public Scene createLoginScene(Stage editing)
    {
        BorderPane borderPane = new BorderPane();
        borderPane.setPrefWidth(340.0);
        borderPane.setPadding(new Insets(10,50,50,50));

        HBox hb = new HBox();
        hb.setPadding(new Insets(20,20,20,30));
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20,20,20,20));
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        Label lbl = new Label("Select your Keystore: ");
        lbl.setId("lblInstruction1");
        lbl.setStyle("    -fx-font-size: 14px;\n" +
                "    -fx-font-weight: bold;\n" +
                "    -fx-font-family: \"Arial Narrow\";\n" +
                "    -fx-text-fill: #333333;\n" +
                "    -fx-effect: dropshadow( gaussian , rgba(255,255,255,0.5) , 0,0,0,1 );");
        Button btnLoad = new Button("Load");
        btnLoad.setStyle("    -fx-text-fill: white;\n" +
                "    -fx-font-family: \"Arial Narrow\";\n" +
                "    -fx-font-weight: bold;\n" +
                "    -fx-background-color: linear-gradient(#61a2b1, #2A5058);\n" +
                "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );");
        btnLoad.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        btnLoad.setId("load");
        Button btnLogin = new Button("Login");
        btnLogin.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        btnLogin.setId("login");
        btnLogin.setStyle("    -fx-text-fill: white;\n" +
                "    -fx-font-family: \"Arial Narrow\";\n" +
                "    -fx-font-weight: bold;\n" +
                "    -fx-background-color: linear-gradient(#61a2b1, #2A5058);\n" +
                "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );");
        PasswordField txtPassword = new PasswordField();
        txtPassword.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        txtPassword.setId("password");
        Label lbl2 = new Label("Enter your password: ");
        lbl2.setStyle("    -fx-font-size: 14px;\n" +
                "    -fx-font-weight: bold;\n" +
                "    -fx-font-family: \"Arial Narrow\";\n" +
                "    -fx-text-fill: #333333;\n" +
                "    -fx-effect: dropshadow( gaussian , rgba(255,255,255,0.5) , 0,0,0,1 );");


        gridPane.add(lbl, 0, 0);
        gridPane.add(btnLoad, 1, 0, 2, 1);
        gridPane.add(lbl2, 0, 1);
        gridPane.add(txtPassword, 1, 1, 2, 1);
        gridPane.add(btnLogin,1,2, 2, 2);
        Text text = new Text("deVill Chain Login");
        text.setFont(Font.font("Arial Narrow", FontWeight.BOLD, 28));
        hb.getChildren().add(text);
        borderPane.setId("bp");
        gridPane.setId("root");
        text.setId("text");
        borderPane.setTop(hb);
        borderPane.setCenter(gridPane);
        btnLoad.setOnAction(event -> loadKeystore(editing));
        borderPane.setStyle(" -fx-background-color:  linear-gradient(lightblue, white);\n" +
                " -fx-border-color: black;\n" +
                " -fx-border-radius: 20;\n" +
                " -fx-padding: 10 10 10 10;\n" +
                " -fx-background-radius: 20;");


        AtomicReference<Scene> scTemp = new AtomicReference<>(new Scene((borderPane)));
        btnLogin.setOnAction(event ->
        {
            scTemp.set(createMenu(editing));
            editing.setScene(scTemp.get());
          /*  login(txtPassword, editing);
            if (loggedIn)
            {
                Controller cont = new menuController();
                Scene s = cont.linkToScene(editing);
                editing.setScene(s);
            }*/
        });
        scCur = scTemp.get();
        return scTemp.get();
    }

    public Scene createMenu(Stage stage)
    {
        BorderPane borderPane = new BorderPane();
        borderPane.setPrefWidth(150.0);
        borderPane.setPrefHeight(100.0);
        borderPane.setPadding(new Insets(10,50,50,50));
        borderPane.setStyle(" -fx-background-color:  linear-gradient(lightblue, white);\n" +
                " -fx-border-color: black;\n" +
                " -fx-border-radius: 20;\n" +
                " -fx-padding: 10 10 10 10;\n" +
                " -fx-background-radius: 20;");
        GridPane grid = new GridPane();
        borderPane.setCenter(grid);
        grid.setPadding(new Insets(20,20,20,20));
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPrefWidth(150.0);
        grid.setPrefHeight(100.0);
        grid.setVgap(4);
        grid.setPadding(new Insets(5, 5, 5, 5));

        Button btnAddSource = new Button("Add Source");
        btnAddSource.setStyle("-fx-text-fill: white;\n" +
                "    -fx-font-family: \"Arial Narrow\";\n" +
                "    -fx-font-weight: bold;\n" +
                "    -fx-background-color: linear-gradient(#61a2b1, #2A5058);\n" +
                "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );");
        //btnAddInformaion.setOnAction(event -> stage.setScene(setAddInformationScene(stage)));

        Button btnViewSource = new Button("View Sources");
        btnViewSource.setStyle("-fx-text-fill: white;\n" +
                "    -fx-font-family: \"Arial Narrow\";\n" +
                "    -fx-font-weight: bold;\n" +
                "    -fx-background-color: linear-gradient(#61a2b1, #2A5058);\n" +
                "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );");
        // btnGetInformation.setOnAction(event -> stage.setScene(createViewInformation(stage)));



        Button btnClose = new Button("Close");
        btnClose.setStyle("-fx-text-fill: white;\n" +
                "    -fx-font-family: \"Arial Narrow\";\n" +
                "    -fx-font-weight: bold;\n" +
                "    -fx-background-color: linear-gradient(#61a2b1, #2A5058);\n" +
                "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );");
        btnClose.setOnAction(event -> stage.close());
        btnAddSource.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        btnViewSource.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        btnClose.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        grid.add(btnAddSource, 0, 0, 2, 1);
        grid.add(btnViewSource, 0, 1, 2, 1);
        grid.add(btnClose, 0, 2, 2, 1);
        scMenu = new Scene(borderPane);

        return scMenu;
    }


    private void loadKeystore(Stage stage)
    {
        try {
            // create a file chooser that opens *.shapes files
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Keystore File");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Keystore", "*.*"));
            // display the chooser and return the file selected
            selectedFile = fileChooser.showOpenDialog(stage);
            System.out.println(selectedFile.getAbsolutePath());
            sPath = selectedFile.getAbsolutePath().replaceAll("[/\\\\]", "//");
            System.out.println(sPath);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    private void login(PasswordField txtPassword, Stage stage)
    {
        try {
            sPassword = txtPassword.getText();
            System.out.println("Password: " + sPassword);
            Web3j web3j = Web3j.build(new HttpService());
            System.out.println("Connected to Ethereum client version: " + web3j.web3ClientVersion().send().getWeb3ClientVersion().toString());
            Credentials credentials = WalletUtils.loadCredentials(sPassword, sPath);
            System.out.println("Credentials loaded: " + credentials.getAddress());
            loggedIn = true;
            System.out.println("Correct Password and keystore combo");
        }
        catch (CipherException ce)
        {
            loggedIn = false;
            System.out.println("Wrong Password and keystore combo");
        }
        catch (Exception e)
        {
            loggedIn = false;
            e.printStackTrace();
        }
    }


}
