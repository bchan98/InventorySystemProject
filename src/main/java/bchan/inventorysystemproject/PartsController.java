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
    public Label varLabel;
    @FXML
    private Label welcomeText;

    private static boolean pOriginFlag = true;

    public static boolean isTrigger;

    private static int partIDCounter = 3;
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
        isTrigger = trigger;
    }
    public void inHousePartSelected(ActionEvent actionEvent) {
        pOriginFlag = true;
        varLabel.setText("Machine ID");
    }

    public void outsourcedPartSelected(ActionEvent actionEvent) {
        pOriginFlag = false;
        varLabel.setText("Company Name");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (isTrigger){
            isAddOrModify.setText("Add Part");
        }
        else {
            isAddOrModify.setText("Modify Part");
            partNameField.setText(AppController.sendName);
            partInvField.setText(String.valueOf(AppController.sendStock));
            partPriceField.setText(String.valueOf(AppController.sendPrice));
            partMinField.setText(String.valueOf(AppController.sendMin));
            partMaxField.setText(String.valueOf(AppController.sendMax));

            if (AppController.sendInOut == true) {
                inHousePart.setSelected(true);
                varField.setText(String.valueOf(AppController.sendVarIn));
                pOriginFlag = true;
                varLabel.setText("Machine ID");
            } else if (!AppController.sendInOut) {
                outsourcedPart.setSelected(true);
                varField.setText(AppController.sendVarOut);
                pOriginFlag = false;
                varLabel.setText("Company Name");
            }
        }



    }

    /**
     * Sets part information to allParts from the Inventory class. Scrapes data from the text fields and uses the information to generate a new Part object which is then sent and stored in the allParts list.
     * @param actionEvent
     * @throws IOException
     */
    public void setNewPart(ActionEvent actionEvent) throws IOException {

        int setID = AppController.sendID;
        String setName = partNameField.getText();
        int setInv = Integer.parseInt(partInvField.getText());
        double setPri = Double.parseDouble(partPriceField.getText());
        int setMaxFi = Integer.parseInt(partMaxField.getText());
        int setMinFi = Integer.parseInt(partMinField.getText());

        if(isTrigger) {
            if (pOriginFlag) {
                int addMachID = Integer.parseInt(varField.getText());
                Part addedPart = new InHouse(partIDCounter, setName, setPri, setInv, setMinFi, setMaxFi, addMachID);
                Inventory.addPart(addedPart);
            } else {
                String addComp = varField.getText();
                Part addedPart = new Outsourced(partIDCounter, setName, setPri, setInv, setMinFi, setMaxFi, addComp);
                Inventory.addPart(addedPart);
            }
            partIDCounter++;
        }
        else {
            if (pOriginFlag) {
                int addMachID = Integer.parseInt(varField.getText());
                Part addedPart = new InHouse(partIDCounter, setName, setPri, setInv, setMinFi, setMaxFi, addMachID);
                Inventory.updatePart(setID, addedPart);
            } else {
                String addComp = varField.getText();
                Part addedPart = new Outsourced(partIDCounter, setName, setPri, setInv, setMinFi, setMaxFi, addComp);
                Inventory.updatePart(setID, addedPart);
            }
        }


        Parent root = FXMLLoader.load(getClass().getResource("main-window.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 600);

        stage.setTitle ("Inventory Management System");

        stage.setScene(scene);
        stage.show();
    }
}