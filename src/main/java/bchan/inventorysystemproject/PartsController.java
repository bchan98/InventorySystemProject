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
import javafx.fxml.Initializable;

import java.io.IOException;

public class PartsController {
    public TextField TextBox;
    public Label isAddOrModify;
    @FXML
    private Label welcomeText;

    public static boolean istrigger;

    @FXML
    public void initialize() {
        if (istrigger == true){
            isAddOrModify.setText("Add Part");
        }
        else {
            isAddOrModify.setText("Modify Part");
        }
    }

    public void toMainWindow(ActionEvent actionEvent) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("main-window.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 600);

        stage.setTitle ("Inventory Management System");

        stage.setScene(scene);
        stage.show();
    }


    public static void setIsAddOrModify(boolean trigger){
        istrigger = trigger;
    }
    public void inHousePartSelected(ActionEvent actionEvent) {
    }

    public void outsourcedPartSelected(ActionEvent actionEvent) {

    }
}