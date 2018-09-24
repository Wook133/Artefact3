package Application;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class menuController implements Controller {

    @Override
    public Scene linkToScene(Stage stage)
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
        Scene scout = new Scene(borderPane);

        return scout;
    }
}
