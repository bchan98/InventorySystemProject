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
    public TextField partIDField;
    public Label checkInvInt;
    public Label checkPrDoub;
    public Label checkMaxInt;
    public Label checkMinInt;
    public Label checkVarInt;
    public Label checkMinMax;
    public Label checkInvValid;
    public Label checkNameVal;
    @FXML
    private Label welcomeText;

    private static boolean pOriginFlag = true;

    public static boolean isTrigger;

    private static int partIDCounter = 3;

    /** This method sends the user to the main window upon pressing the cancel button.
     *
     * @param actionEvent This event triggers when the cancel button is pressed.
     * @throws IOException
     */
    public void toMainWindow(ActionEvent actionEvent) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("main-window.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 600);

        stage.setTitle ("Inventory Management System");

        stage.setScene(scene);
        stage.show();
    }

    /** This method passes information from the main window to determine whether a part is being added or modified.
     *
     * @param trigger The boolean to determine whether a part is modified or not
     */
    public static void setIsAddOrModify(boolean trigger){
        isTrigger = trigger;
    }

    /** This method signals that the current product is an In-house part.
     *
     * @param actionEvent This is triggered when the inHouse radio button is selected
     */
    public void inHousePartSelected(ActionEvent actionEvent) {
        pOriginFlag = true;
        varLabel.setText("Machine ID");
    }

    /** This method signals taht the current product is an Outsourced part.
     *
     * @param actionEvent This is triggered when the outsourced radio button is selected
     */
    public void outsourcedPartSelected(ActionEvent actionEvent) {
        pOriginFlag = false;
        varLabel.setText("Company Name");
    }

    @Override
    /** This method executes upon the window initialization and populates data to the various text fields.
     *
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // checks whether a part is being added or modified
        if (isTrigger){
            isAddOrModify.setText("Add Part");
            partIDField.setText(String.valueOf(partIDCounter));
        }
        else {
            isAddOrModify.setText("Modify Part");
            // passes in information from main window
            partIDField.setText(String.valueOf(AppController.sendID));
            partNameField.setText(AppController.sendName);
            partInvField.setText(String.valueOf(AppController.sendStock));
            partPriceField.setText(String.valueOf(AppController.sendPrice));
            partMinField.setText(String.valueOf(AppController.sendMin));
            partMaxField.setText(String.valueOf(AppController.sendMax));

            // passes information on the part to determine which radio button should be selected when the screen loads
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

    /** This method attempts to save data to the allParts list.
     * Sets part information to allParts from the Inventory class. Scrapes data from the text fields and uses the information to generate a new Part object which is then sent and stored in the allParts list.
     * RUNTIME ERROR: Initially, a numberFormatException occurred when saving a new part. This occurred because when a new part is added, the textfield stating the part ID simply displays "Autogen - do not modify". The program was attempting to parseInt this value, which yielded the exception. To solve this, the program only grabs partID if the part is being modified. Additionally, partID is now kept in a static variable to ensure all parts have unique partIDs.
     * FUTURE ENHANCEMENT: Add a unmodifiable set of text fields showing the current values to provide a reference versus any modifications being made.
     * @param actionEvent
     * @throws IOException
     */
    public void setNewPart(ActionEvent actionEvent) throws IOException {
        // resets test variables
        int setID;
        boolean allowExecution = true;
        boolean secondCheck = true;
        int setInv = 0;
        double setPri = 0;
        int setMaxFi = 0;
        int setMinFi = 0;
        String setName = partNameField.getText();

        // clears any error messages that may have previously appeared
        checkNameVal.setText("");
        checkInvInt.setText("");
        checkPrDoub.setText("");
        checkMaxInt.setText("");
        checkMinInt.setText("");
        checkMinMax.setText("");
        checkInvValid.setText("");
        checkVarInt.setText("");

        // checks logic to determine whether correct values have been supplied to each field
        if (setName.trim().isEmpty() == true) {
            allowExecution = false;
            checkNameVal.setText("Currently there is no name given to this part. Please enter in a name.");
        }
        try {
            setInv = Integer.parseInt(partInvField.getText());
        } catch (NumberFormatException numberFormatException){
            allowExecution = false;
            checkInvInt.setText("Inventory is currently not an integer.");
        }
        try {
            setPri = Double.parseDouble(partPriceField.getText());
        } catch (NumberFormatException numberFormatException){
            allowExecution = false;
            checkPrDoub.setText("Price is currently not a number");
        }
        try {
            setMaxFi = Integer.parseInt(partMaxField.getText());
        } catch (NumberFormatException numberFormatException){
            allowExecution = false;
            secondCheck = false;
            checkMaxInt.setText("Maximum stock is currently not an integer.");
        }
        try {
            setMinFi = Integer.parseInt(partMinField.getText());
        } catch (NumberFormatException numberFormatException){
            allowExecution = false;
            secondCheck = false;
            checkMinInt.setText("Minimum stock is currently not an integer.");
        }
        if(Integer.parseInt(partMinField.getText()) > Integer.parseInt(partMaxField.getText()))
        {
            allowExecution = false;
            checkMinMax.setText("The maximum stock is currently larger than the minimum stock");
        }
        if(secondCheck = true) {
            if(setInv > setMaxFi || setInv < setMinFi) {
                allowExecution = false;
                checkInvValid.setText("The current inventory value is invalid.");
            }
        }
        else{
            System.out.println("The inventory value is acceptable.");
            allowExecution = true;
        }
        if(pOriginFlag) {
            try {
                int checkMach = Integer.parseInt(varField.getText());
            } catch (NumberFormatException  numberFormatException) {
                allowExecution = false;
                checkVarInt.setText("The current machine ID is not an integer.");
            }
        }

        // if all logic checks pass, modifies or adds any new part.
        if(allowExecution) {
            if (isTrigger) {
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
            } else {
                setID = Integer.parseInt(partIDField.getText());
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
}