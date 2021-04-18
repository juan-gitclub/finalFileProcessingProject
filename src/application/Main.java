package application;
import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;



/**
 * 
 * @author jdalb
 * @version 1.0
 * 
 *
 */

public class Main extends Application {
	
	
	
	
public static void main(String[] args)  {
	  
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		
		FileChooser fileChooser = new FileChooser();
		primaryStage.setTitle("File reader");
        Button btn = new Button();
        btn.setText("Read File");
        
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
        
        btn.setOnAction(new EventHandler<ActionEvent>() {
		
        public void handle(ActionEvent event) {		 
			
			File selectedFile = fileChooser.showOpenDialog(primaryStage);
			
			FileProcessing fp = new FileProcessing();
			
			try {
				fp.getConnection();
				//fp.post();
			} catch (Exception e) {
				
			} 

			ArrayList <String> s = fp.readFileAsString(selectedFile);
		     
			Collections.sort(s); // This sorts all the words first
				
			fp.analizeContent(s); // After all the words are sorted this analyzes and orders all the information 
			
			
			
          }
      });
    
    
   }
}
	
	
