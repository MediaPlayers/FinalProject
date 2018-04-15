package mediaplayerproject

/*
Variable naming convention:
Containers -> positional elements 
Components -> Functionality elements
*/


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


public class MediaPlayerProject extends Application {
    Scene scene;
    BorderPane root = new BorderPane();
    
    HBox mediaControlContainer = new HBox();
    Text bottomTest = new Text("Bottom Container");
 
    
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
        
        mediaControlContainer.getChildren().add(bottomTest);
        mediaControlContainer.prefHeightProperty().bind(scene.heightProperty().multiply(0.15));
        
        listViewContainer.getChildren().add(topRightTest);
        listViewContainer.prefWidthProperty().bind(scene.widthProperty().multiply(0.23));

        
        root.setBottom(mediaControlContainer);
        root.setRight(listViewContainer);
        
        root.setStyle("-fx-background-color: pink;");
        mediaControlContainer.setStyle("-fx-background-color: green;");
        listViewContainer.setStyle("-fx-background-color: blue;");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
