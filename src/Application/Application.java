package Application;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Application extends javafx.application.Application {
    public static void main(String[] args) {
        launch(args);
    }

    public Scene createMainScene() {
        VBox root = new VBox();
        root.setFillWidth(true);
        return new Scene(root);
    }

    public Stage createEditingStage(Stage owner) {
        Stage stage = new Stage();

        return stage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("deVill Chain");
        Scene scene = createMainScene();
        Stage editingStage = createEditingStage(primaryStage);
        Controller controller = new loginController();
        primaryStage.setScene(controller.linkToScene(scene, editingStage));
        primaryStage.show();
    }
}
