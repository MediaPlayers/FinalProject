


/*
TODO:
1. Change body font to a nice modern font instead of our current plain text.
1. Understand slider functionality & control better
2. Rewrite metadata parts in English or remove altogether
3. study files a little more (too dependent on resource)
*/

/*
Ideas above & beyond:

Mess around with box-shadows and other nice visual effects
User can choose multiple color schemes
Search functions
Placing objects in different places for nice UI/UX (current project has us making a VCR like player)





*/

package muse;
import java.io.File;
import javafx.util.Duration;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class Muse extends Application
{
    private Scene scene;
    private BorderPane borderPane = new BorderPane();
    
    //bottom section components
    private HBox bottomBorder;
    private HBox buttonContainer;
    private HBox sliderContainer;
    
    private Button previousButton;
    private Button playButton;
    private Button nextButton;
    private Button rewindButton;
    private Button fastForwardButton;
           
    private Slider volumeSlider;
    private Slider timeSlider;
    
    //playlist side components
    private VBox playlistContainer;
    private ListView<String> playlist_song;
    
    private ObservableList<File> files;
    private File sourceFile;
   
    private Button addSongButton;
    
    //album side components
    private File placeholderIMG;

    private HBox songContainer;
    private HBox artistContainer;
    private HBox titleContainer;
    
    private Label artistLabel = new Label("");
    private Label titleLabel = new Label("");
   
    //Media components
    private MediaPlayer mediaPlayer;
    private Media mediaObject;
    private ImageView album_IMGView;
    private Image album_IMG;
    
    
    //song indices
    private int startSong = 0;
    private int nextSong = startSong;
    private int previousSong = nextSong;
    

    
    /****
    *****
    *****
    *****
    *****   
    ****/
    public static void main(String[] args)
    {
        Application.launch(args);
    }
        
    /****
    *****
    *****
    *****
    *****   
    ****/
    
    @Override
    public void start(Stage primaryStage){
    
        scene = new Scene(borderPane, 1280,800);
        createLayout();  //issue: could be a little more responsive. Easy fix for "polishing" stage
        handleMediaData();
        handleEvents();
        
        primaryStage.setTitle("Muse - for your aMUSEment *bada tss*");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public void createLayout()
    {
        
        
        //Note: You can comment out the color I have added here. It was mostly for testing purposes.
        /*
         Polishing stage:
         
            Let's mess around and make beautiful:
             size binding,
             padding & shape,
             color schemes (paletton.com is a great color tool),        
             minimum and maximum scene width & height,
             reposition elements for sexy UI/UX
        */
        
        //basic scene size binding
        borderPane.prefWidthProperty().bind(scene.widthProperty());
        borderPane.prefHeightProperty().bind(scene.heightProperty());
        borderPane.setStyle("-fx-background-color: seagreen;");
        //Main parent container in bottom -> can bind
        bottomBorder = new HBox(5);
        
        /*Sub containers*/
        
        //Bottom buttons
        buttonContainer = new HBox(8);
        buttonContainer.prefWidthProperty().bind(bottomBorder.widthProperty().multiply(0.4));
        buttonContainer.setPadding(new Insets(40));    
        buttonContainer.setStyle("-fx-background-color: pink;");
        
        
        sliderContainer = new HBox();
        sliderContainer.prefWidthProperty().bind(bottomBorder.widthProperty().multiply(0.7));
        sliderContainer.setAlignment(Pos.CENTER);
        sliderContainer.setPadding(new Insets(40));
        bottomBorder.getChildren().addAll(buttonContainer, sliderContainer);
        sliderContainer.setStyle("-fx-background-color: hotpink;");
        
        borderPane.setBottom(bottomBorder);
        
        
        //Potential UI fix: we can add nice looking images instead of plain text.
        //Buttons can be less plain looking.
        //The use of arrows in general is kind of cryptic so Im using plain words for now.
        previousButton = new Button("Previous song"); //<<<
        previousButton.setStyle("-fx-background-color: #19DCC7;");
        
        rewindButton = new Button("Rewind");       //<<
        rewindButton.setStyle("-fx-background-color: #19DCC7;");
              
        playButton = new Button("Play"); //>
        playButton.setStyle("-fx-background-color: #19DCC7;");
        
        fastForwardButton = new Button("Fast Forward"); //>>
        fastForwardButton.setStyle("-fx-background-color: #19DCC7;"); 
        
        nextButton = new Button("Next Song"); //>>>
        nextButton.setStyle("-fx-background-color: #19DCC7;");
        buttonContainer.getChildren().addAll(previousButton, rewindButton, playButton, fastForwardButton, nextButton);
        
        
        timeSlider = new Slider();
        timeSlider.setValue(0);
        timeSlider.setShowTickMarks(true);
        timeSlider.setMin(0.00);
        timeSlider.prefWidthProperty().bind(sliderContainer.widthProperty().multiply(.7));
        timeSlider.setStyle("-fx-background-color: #19DCC7;");
        
        volumeSlider = new Slider();
        volumeSlider.setValue(50);
        volumeSlider.setShowTickMarks(false); 
        volumeSlider.setStyle("-fx-background-color: #19DCC7;");
        //setting to true will make it even with the other slider
        //But tick marks are kind of weird when the slider is so small
        //we can add some labels here to even it out with appropriate padding.
        
        sliderContainer.getChildren().addAll(timeSlider, volumeSlider);
        
        playlistContainer = new VBox(10);
        
        files = FXCollections.observableArrayList();
        playlist_song = new ListView<>();
        playlistContainer.setPadding(new Insets(40, 50, 0, 20));
        addSongButton = new Button("+");
        
        
        
        playlistContainer.getChildren().addAll(playlist_song, addSongButton);
        borderPane.setRight(playlistContainer);
        playlistContainer.setStyle("-fx-background-color: red;");
        
        HBox imgContainer = new HBox();
        imgContainer.prefWidthProperty().bind(borderPane.widthProperty().multiply(.45));
        imgContainer.prefHeightProperty().bind(borderPane.heightProperty().multiply(.45));
        imgContainer.setAlignment(Pos.CENTER);
        imgContainer.setStyle("-fx-background-color: orange;");
        
        
        //TEST OF VBOX - GET RID OF IF NOT WORKING <<<<<<<<<<<<<<<<< ADDED 
        //ALIGNMENT OR SPACING GOTTA BE FIXED        
        VBox centerBox = new VBox();
        centerBox.prefWidthProperty().bind(borderPane.widthProperty().multiply(.45));
        centerBox.prefHeightProperty().bind(borderPane.heightProperty().multiply(.45));
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setStyle("-fx-background-color: brown;");
        //END OF VBOX
        
                
        /*temporary holder to show where album art will go. This
        will be replaced down the line with a method to pull metadata
        from mp3 file.
        */
        
        //final Reflection reflection = new Reflection(); //Adds reflection effect to artwork
        //reflection.setFraction(0.2);
        album_IMG = new Image("album_art/Gilbert_Album_Art.jpg"); //will be a default artwork, cant get it to work yet
        album_IMGView = new ImageView(album_IMG);
        album_IMGView.setPreserveRatio(true);
        
        album_IMGView.setFitWidth(240);
        album_IMGView.setPreserveRatio(true);
        album_IMGView.setSmooth(true);
        //album_IMGView.setEffect(reflection);
        

        songContainer = new HBox();
        songContainer.setStyle("-fx-background-color: yellow;");
        
        artistContainer = new HBox();
           artistContainer.setStyle("-fx-background-color: purple;");
           
        titleContainer = new HBox();
        titleContainer.setStyle("-fx-background-color: gray;");
        
        artistContainer.getChildren().add(artistLabel);
        titleContainer.getChildren().add(titleLabel);        
        songContainer.setAlignment(Pos.CENTER);
        songContainer.getChildren().addAll(artistContainer, titleContainer);
  
       
        imgContainer.getChildren().add(album_IMGView);
        centerBox.getChildren().addAll(songContainer, imgContainer);
        
        
        borderPane.setCenter(centerBox);
        
       
        
    }
    
    public void handleMediaData(){
        
  
      //Polishing stage:
      //get metadata of file
      //May not be a necessary function
      //Could possibly remove      
      try {      
        mediaObject.getMetadata().addListener(new MapChangeListener<String, Object>() {
          @Override
          public void onChanged(MapChangeListener.Change<? extends String, ? extends Object> ch) {
            if (ch.wasAdded()) {
              metadataHandler(ch.getKey(), ch.getValueAdded());
            }
          }
        });

        mediaPlayer = new MediaPlayer(mediaObject);
        mediaPlayer.volumeProperty().bind(volumeSlider.valueProperty().divide(100)); 
        mediaPlayer.setOnError(new Runnable() {
          @Override
          public void run() {    // Handle playback errors
            final String errorMessage = mediaObject.getError().getMessage();
         
            System.out.println("MediaPlayer Error: " + errorMessage);
          }
      });
    } catch (RuntimeException re) {
          // handle more errors
          System.out.println("Caught Exception: " + re.getMessage());
        }    
    }
    public void metadataHandler(String key, Object value){
         //set meta data of the file playing
        if (key.equals("image")) {
            album_IMGView.setImage((Image)value);
        }else if (key.equals("artist")) {
          artistLabel.setText(value.toString() + " - ");
        } if (key.equals("title")) {
          titleLabel.setText(value.toString());
        }            

        
    }
    
    
    
    public void chooseFile()
    {
    
       // need to study a little more
        FileChooser f = new FileChooser();
        f.getExtensionFilters().add(new ExtensionFilter("Mp3 Files", "M4A Files", "*.mp3", "*.m4a")); //can remove m4a                    
                
                   
                 sourceFile=f.showOpenDialog(null);
                 if(sourceFile != null){
                    playlist_song.getItems().add(sourceFile.getName()); 
                    if(files.isEmpty())
                    {
                        files.add(sourceFile);
                        createMedia(startSong);                        
                    }
                    else
                        files.add(sourceFile);
                    
                 }else{
                     System.out.println("error in chooseFile()");
                 }
                 
                
        
    }
    
    public void handleEvents()
    {
        //add .mp3
        addSongButton.setOnMouseClicked(e -> {
           chooseFile(); 
        });
       
       
       //handle buttons
        playButton.setOnAction(e->{
                if (playButton.getText().equals(">"))
                {
                   mediaPlayer.play();
                   playButton.setText("Pause");
                }
                else 
                {
                   mediaPlayer.pause();                 
                   playButton.setText(">");
                }
                      
                
        });
        
        rewindButton.setOnAction(e->{
           if (mediaPlayer.getCurrentTime()==Duration.ZERO) //todo: implement
            {

            }
          
              mediaPlayer.seek((Duration.ZERO));
        
        });
        
        
         
         
         //stops playlist when previous is pressed on first song..
         //can m
        previousButton.setOnAction(e->{
            
            previousSong = previousSong - 1;             
            nextSong = previousSong;
            mediaPlayer.stop();

            if(previousSong == -1){
                
                mediaPlayer.stop();
                playButton.setText(">");
                startSong = 0;
                nextSong = startSong;
                createMedia(startSong);
                
            }else{            
                createMedia(previousSong);
                mediaPlayer.play();
            }
          
            
        });
        
        //NEXT SONG BUTTON
        //Currently i set this up as a loop
        //so by clicking nextButton you can play all of the songs
        //over and over again, but we gotta change it
        
        nextButton.setOnAction(e->{
            
            nextSong = nextSong + 1;
            previousSong = nextSong;
            mediaPlayer.stop();
            
            if(files.size() == nextSong){
                
                mediaPlayer.stop();
                startSong = 0;
                nextSong = startSong;
                createMedia(startSong);
                
            }else{            
                createMedia(nextSong);
            }
            mediaPlayer.play();

            
            
        });
        
    }
    
    public void createMedia(int index) // <- i added a parameter 'index'
    {
        mediaObject = new Media(files.get(index).toURI().toString());
        mediaPlayer = new MediaPlayer(mediaObject);
        playlist_song.getSelectionModel().select(index);

        handleMediaData();
        //<- starts the song/playlist on start once a song is loaded
        //mp.play(); 
    }
}
