/* Basic functionality using ListView */ 

package simplelistview;


import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author sheilfer
 */
public class SimpleListView extends Application {
    Button submitButton;
    ListView<String> listViewComponent;
    //A ListView object that manages strings
    //Our assignment will most likely parse a file string.
    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(10);
        root.setPadding(new Insets(25));
        
        
        submitButton = new Button();
        submitButton.setText("Print song");
        
        listViewComponent = new ListView<>();
        listViewComponent.getItems().addAll("Song 1", "Song 2", "Song 3", "Song N");
        //listViewComponent.getSelectionModel().getSelectionMode(SelectionMode.MULTIPLE);
        
        
        root.getChildren().addAll(listViewComponent, submitButton);
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Simple ListView");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        submitButton.setOnAction(e -> songClicked());
    }
    
    //print out songs
    private void songClicked(){
                //Important
        //All lists in javafx are of type ObservableList
        //This is how you "get" item types and add functionality to them.
        String songSelected = "";
        ObservableList<String> songs;
        //return selected item
        songs = listViewComponent.getSelectionModel().getSelectedItems();
        
        //store to songSelected
        //
        for(String song: songs){
            songSelected += song;
        }

        System.out.println(songSelected);
        
        //Songs gets the list of strings in listViewComponent
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
