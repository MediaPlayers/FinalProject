/*
Variable naming convention:

Containers -> positional elements 
Components -> Functionality elements
 */
package layout;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author sheilfer
 */
public class Layout extends Application {
    Scene scene;
    BorderPane root = new BorderPane();
    
    HBox mediaControlContainer = new HBox();
    Text bottomTest = new Text("Bottom Container");
    
    VBox imgViewContainer = new VBox();
    Text topLeftTest = new Text("Left Container");
 
    
    VBox listViewContainer = new VBox();
    Text topRightTest = new Text("Top Right Container");
    
    @Override
    public void start(Stage primaryStage) {
        
      
     
        scene = new Scene(root, 600, 600);
        setupContainers();
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public void setupContainers(){
        bottomTest.setFill(Color.WHITE);
        topRightTest.setFill(Color.WHITE);
        topLeftTest.setFill(Color.WHITE);
        
        mediaControlContainer.getChildren().add(bottomTest);
        mediaControlContainer.prefHeightProperty().bind(scene.heightProperty().multiply(0.15));
        
        listViewContainer.getChildren().add(topRightTest);
        listViewContainer.prefWidthProperty().bind(scene.widthProperty().multiply(0.23));
        listViewContainer.prefHeightProperty().bind(scene.heightProperty().multiply(0.85));
        
        imgViewContainer.getChildren().add(topLeftTest);
        imgViewContainer.prefWidthProperty().bind(scene.widthProperty().multiply(0.77));
        imgViewContainer.prefHeightProperty().bind(scene.heightProperty().multiply(0.85));
        
        root.setBottom(mediaControlContainer);
        root.setRight(listViewContainer);
        root.setLeft(imgViewContainer);
        
        root.setStyle("-fx-background-color: pink;");
        mediaControlContainer.setStyle("-fx-background-color: green;");
        listViewContainer.setStyle("-fx-background-color: blue;");
        imgViewContainer.setStyle("-fx-background-color: brown;");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
