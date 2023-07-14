package bchan.inventorysystemproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    // javadoc is found in the zip folder in the folder "javadocFiles"
    /** This is the main method that starts up the application.
     * RUNTIME ERROR: Initially, an issue occurred when drawing a new window - this was due to the fact that the relevant fxml file was misspelled. This was corrected.
     */
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-window.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}