package bchan.inventorysystemproject;

import bchan.inventorysystemproject.Components.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PartsController implements Initializable{
    public TextField TextBox;
    public Label isAddOrModify;
    public RadioButton inHousePart;
    public RadioButton outsourcedPart;
    public ToggleGroup partOrigin;
    public TextField partNameField;
    public TextField partInvField;
    public TextField partPriceField;
    public TextField partMaxField;
    public TextField partMinField;
    public TextField varField;
    @FXML
    private Label welcomeText;

    private static boolean pOriginFlag = true;

    public static boolean istrigger;

    private static int partIDCounter = 2;
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
        pOriginFlag = true;
    }

    public void outsourcedPartSelected(ActionEvent actionEvent) {
        pOriginFlag = false;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (istrigger == true){
            isAddOrModify.setText("Add Part");
        }
        else {
            isAddOrModify.setText("Modify Part");
        }
    }

    public void addNewPart(ActionEvent actionEvent) throws IOException {

        String addName = partNameField.getText();
        int addInv = Integer.parseInt(partInvField.getText());
        double addPri = Double.parseDouble(partPriceField.getText());
        int addMaxFi = Integer.parseInt(partMaxField.getText());
        int addMinFi = Integer.parseInt(partMinField.getText());

        if(pOriginFlag){
            int addMachID = Integer.parseInt(varField.getText());
            Part addedPart = new InHouse(partIDCounter, addName, addPri, addInv, addMinFi, addMaxFi, addMachID);
            Inventory.addPart(addedPart);
        }
        else{
            String addComp = varField.getText();
            Part addedPart = new Outsourced(partIDCounter, addName, addPri, addInv, addMinFi, addMaxFi, addComp);
            Inventory.addPart(addedPart);
        }

        partIDCounter++;

        Parent root = FXMLLoader.load(getClass().getResource("main-window.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 600);

        stage.setTitle ("Inventory Management System");

        stage.setScene(scene);
        stage.show();
    }
}