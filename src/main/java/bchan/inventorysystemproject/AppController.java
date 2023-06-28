package bchan.inventorysystemproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AppController {
    public TextField TextBox;
    public Button modifyPartsButton;
    public Button addPartsButton;
    @FXML
    private Label welcomeText;

    public boolean message;

    public void AddPartsWindow(ActionEvent actionEvent) throws IOException
    {
        message = true;
        PartsController.setIsAddOrModify(message);
        Parent root = FXMLLoader.load(getClass().getResource("partsModify.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 400);

        stage.setTitle ("Add Parts");
        stage.setScene(scene);
        stage.show();
    }

    public void modifyPartsWindow(ActionEvent actionEvent) throws IOException {
        message = false;
        PartsController.setIsAddOrModify(message);
        Parent root = FXMLLoader.load(getClass().getResource("partsModify.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 400);

        stage.setTitle ("Modify Parts");
        stage.setScene(scene);
        stage.show();
    }
}
