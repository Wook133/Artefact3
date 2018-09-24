package Application;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
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
        borderPane.setPrefWidth(300.0);
        FlowPane flowPane = new FlowPane();

        Label lbl = new Label("Select your Keystore: ");
        lbl.setId("lblInstruction1");

        Button btnLoad = new Button("Load");
        btnLoad.setId("load");

        Button btnLogin = new Button("Login");
        btnLogin.setId("login");

        PasswordField txtPassword = new PasswordField();
        txtPassword.setId("password");
        Label lbl2 = new Label("Enter your password: ");

        Region p = new Region();
        p.setPrefSize(200.0, 0.0);
        Region p2 = new Region();
        p2.setPrefSize(115.0, 0.0);
        flowPane.getChildren().addAll(lbl, btnLoad, p,
                lbl2, txtPassword, p2,
                btnLogin
        );
        borderPane.setTop(flowPane);
        btnLoad.setOnAction(event -> loadKeystore(editing));
        AtomicReference<Scene> scTemp = new AtomicReference<>(new Scene((borderPane)));
        btnLogin.setOnAction(event ->
        {
            login(txtPassword, editing);
            if (loggedIn)
            {
                // scTemp.set(createMenu(stage));
               // editing.setScene(scTemp.get());
            }
        });
        return scTemp.get();
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
