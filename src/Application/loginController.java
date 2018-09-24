package Application;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
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

public class loginController implements Controller {

    protected boolean loggedIn = false;
    protected File selectedFile;
    protected String sPath;
    protected String sPassword;

    @Override
    public Scene linkToScene(Scene scene, Stage editing)
    {
        BorderPane borderPane = new BorderPane();
        borderPane.setPrefWidth(400.0);
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


        //DropShadow effect
        /*DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(1);
        dropShadow.setOffsetY(1);*/


        Text text = new Text("deVill Chain Login");
        text.setFont(Font.font("Arial Narrow", FontWeight.BOLD, 28));
       /* text.setEffect(dropShadow);*/


        hb.getChildren().add(text);

        borderPane.setId("bp");

        gridPane.setId("root");
        text.setId("text");

        borderPane.setTop(hb);
        borderPane.setCenter(gridPane);




       /* Region p = new Region();
        p.setPrefSize(200.0, 0.0);
        Region p2 = new Region();
        p2.setPrefSize(115.0, 0.0);
        flowPane.getChildren().addAll(lbl, btnLoad, p,
                lbl2, txtPassword, p2,
                btnLogin
        );
        borderPane.setTop(flowPane);*/
        btnLoad.setOnAction(event -> loadKeystore(editing));
        //AtomicReference<Scene> scTemp = new AtomicReference<>(new Scene((borderPane)));
        borderPane.setStyle(" -fx-background-color:  linear-gradient(lightblue, white);\n" +
                " -fx-border-color: black;\n" +
                " -fx-border-radius: 20;\n" +
                " -fx-padding: 10 10 10 10;\n" +
                " -fx-background-radius: 20;");


        Scene scout = new Scene(borderPane);
        btnLogin.setOnAction(event ->
        {
            login(txtPassword, editing);
            if (loggedIn)
            {
                // scTemp.set(createMenu(stage));
               // editing.setScene(scTemp.get());
            }
        });
        return scout;
        //return scTemp.get();
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
