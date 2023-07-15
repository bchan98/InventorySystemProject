package bchan.inventorysystemproject;

import bchan.inventorysystemproject.Components.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProductsController implements Initializable {

    private static int productIDCounter = 2;
    public TextField prodIDField;
    public TextField prodNameField;
    public TextField prodInvField;
    public TextField prodPriceField;
    public TextField prodMaxField;
    public TextField prodMinField;
    public Label prodWindowLabel;

    public static boolean isTrigger;
    public TableView<Part> associatedPartsTable;
    public TableView<Part> availPartsTable;
    public Button addAssPartButton;
    public TextField searchAsProd;
    public Button remAsProBut;
    public Button saveButton;
    public Button cancelButton;
    public TableColumn partIDCol;
    public TableColumn partNameCol;
    public TableColumn partInvCol;
    public TableColumn partPriceCol;
    public TableColumn assIDCol;
    public TableColumn assNameCol;
    public TableColumn assInvCol;
    public TableColumn assPriceCol;
    public TextField availPartField;
    public Label checkInvInt;
    public Label checkPrDoub;
    public Label checkMaxInt;
    public Label checkMinInt;
    public Label checkMinMax;
    public Label checkInvValid;
    public Label checkNameVal;

    private boolean isFirstAssItem = false;

    private boolean allowExecution = true;
    private boolean secondCheck = true;

    private ObservableList<Part> intAssociatedPartList;
    private ObservableList<Part> intAvailablePartList;

    @Override
    /** This method initializes the Products window and populates necessary data. Information is passed from the main window to populate necessary text fields.
     *
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // checks if a product is being added or modified
        if(isTrigger) {
            prodWindowLabel.setText("Add Product");
            prodIDField.setText(String.valueOf(productIDCounter));
            isFirstAssItem = true;
        }
        else {
            prodWindowLabel.setText("Modify Product");

            // populates data into relevant text fields
            prodIDField.setText(String.valueOf(AppController.sendID));
            prodNameField.setText(AppController.sendName);
            prodInvField.setText(String.valueOf(AppController.sendStock));
            prodPriceField.setText(String.valueOf(AppController.sendPrice));
            prodMinField.setText(String.valueOf(AppController.sendMin));
            prodMaxField.setText(String.valueOf(AppController.sendMax));

            // obtains the associated parts to the relevant product
            Product nuProduct = Inventory.getAllProducts().get((Integer.parseInt(prodIDField.getText())) - 1);
            intAssociatedPartList = nuProduct.getAllAssociatedParts();

            // checks if any associated parts exist
            if(intAssociatedPartList.size() == 0) {
                isFirstAssItem = true;
            }
            else {
                isFirstAssItem = false;
                // populates TableView with associated part information
                associatedPartsTable.setItems(intAssociatedPartList);
                assIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
                assNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
                assInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
                assPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
            }
        }
        // populates TableView with available parts to be added
        intAvailablePartList = Inventory.getAllParts();
        FilteredList<Part> showAvailablePartList = new FilteredList<>(intAvailablePartList, Part -> Part != null);
        availPartsTable.setItems(showAvailablePartList);
        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /** This method returns the user to the main window.
     *
     * @param actionEvent Triggers upon pressing the cancel button.
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

    /** This method saves changes made to the product. This method carries out a basic logic check to determine all data inputted is valid before saving the product to the allProducts list from the Inventory class.
     * FUTURE ENHANCEMENT: Provide a window showing most associated parts as a suggestion for any possible parts to be added.
     * @param actionEvent This event triggers upon pressing the saveProductButton.
     * @throws IOException
     */
    public void saveChanges(ActionEvent actionEvent) throws IOException {
        // initializes variables
        allowExecution = true;
        int setID = Integer.parseInt(prodIDField.getText());
        String setName = prodNameField.getText();
        int setInv = 0;
        double setPrice = 0;
        int setMin = 0;
        int setMax = 0;

        // clears text fields that may have previously had errors
        checkNameVal.setText("");
        checkInvInt.setText("");
        checkPrDoub.setText("");
        checkMaxInt.setText("");
        checkMinInt.setText("");
        checkMinMax.setText("");
        checkInvValid.setText("");

        // checks logic to determine whether correct values have been supplied to each field
        if (setName.trim().isEmpty() == true) {
            allowExecution = false;
            checkNameVal.setText("Currently there is no name given to this product. Please enter in a name.");
        }
        try {
            setInv = Integer.parseInt(prodInvField.getText());
        } catch (NumberFormatException numberFormatException){
            allowExecution = false;
            checkInvInt.setText("Inventory is currently not an integer.");
        }
        try {
            setPrice = Double.parseDouble(prodPriceField.getText());
        } catch (NumberFormatException numberFormatException){
            allowExecution = false;
            checkPrDoub.setText("Price is currently not a number");
        }
        try {
            setMax = Integer.parseInt(prodMaxField.getText());
        } catch (NumberFormatException numberFormatException){
            allowExecution = false;
            secondCheck = false;
            checkMaxInt.setText("Maximum stock is currently not an integer.");
        }
        try {
            setMin = Integer.parseInt(prodMinField.getText());
        } catch (NumberFormatException numberFormatException){
            allowExecution = false;
            secondCheck = false;
            checkMinInt.setText("Minimum stock is currently not an integer.");
        }
        if(Integer.parseInt(prodMinField.getText()) > Integer.parseInt(prodMaxField.getText()))
        {
            allowExecution = false;
            checkMinMax.setText("The maximum stock is currently larger than the minimum stock");
        }
        if(secondCheck = true) {
            if(setInv > setMax || setInv < setMin) {
                allowExecution = false;
                checkInvValid.setText("The current inventory value is invalid.");
            }
        }
        else{
            System.out.println("The inventory value is acceptable.");
            allowExecution = true;
        }

        // if logic checks pass, product is saved
        if (allowExecution) {
            setInv = Integer.parseInt(prodInvField.getText());
            setPrice = Double.parseDouble(prodPriceField.getText());
            setMin = Integer.parseInt(prodMinField.getText());
            setMax = Integer.parseInt(prodMaxField.getText());
            Product nuProduct = new Product(setID, setName, setPrice, setInv, setMin, setMax);

            for (int nIndex = 0; nIndex < intAssociatedPartList.size(); nIndex ++) {
                nuProduct.addAssociatedPart(intAssociatedPartList.get(nIndex));
            }

            if (isTrigger) {
                Inventory.addProduct(nuProduct);
                productIDCounter++;
            } else if (!isTrigger) {
                Inventory.updateProduct(setID, nuProduct);
            }

            Parent root = FXMLLoader.load(getClass().getResource("main-window.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1000, 600);

            stage.setTitle("Inventory Management System");

            stage.setScene(scene);
            stage.show();
        }
    }

    /** This method sets the boolean to record whether a product is being added or modified.
     *
     * @param trigger This parameter indicates whether a product is being added or modified.
     */
    public static void setIsAddOrModify(boolean trigger){
        isTrigger = trigger;
    }

    /** This method removes any associated parts from the product. The selected associated part from the associatedParts TableView is removed after a confirmation warning.
     *
     * @param actionEvent This event triggers upon pressing the deleteAssPart button.
     */
    public void removeAssociatePart(ActionEvent actionEvent) {
        // spawns a warning
        Alert confDel = new Alert(Alert.AlertType.CONFIRMATION);
        confDel.setTitle("Confirm deletion");
        confDel.setHeaderText("Deletion Warning");
        confDel.setContentText("Are you sure you want to delete this part?");

        Optional<ButtonType> result = confDel.showAndWait();
        if (result.get() == ButtonType.OK) {
            intAssociatedPartList.remove(associatedPartsTable.getSelectionModel().getSelectedItem());
        }
    }

    /** This method adds an associated part to the product. The selected part from the availParts TableView is added to the list of associated parts.
     * RUNTIME ERROR: Previously, an exception occurred when adding associate parts to the product, stating an InvokationException occured. This is due to the fact that no product object existed within this method. To solve this, a product was constructed to serve as the placeholder for the associatedPartsList.
     * @param actionEvent This event triggers upon pressing the addAssPart button.
     */
    public void addAssociatePart(ActionEvent actionEvent) {
        if(availPartsTable.getSelectionModel().getSelectedItem() != null) {
            // checks whether any parts have been added to the product before
            if (isFirstAssItem) {
                // adds part to the list
                Product nuProduct = new Product(productIDCounter, "", 0, 0, 0, 0);
                nuProduct.addAssociatedPart((availPartsTable.getSelectionModel().getSelectedItem()));
                intAssociatedPartList = nuProduct.getAllAssociatedParts();
                // populates TableView with new information
                associatedPartsTable.setItems(intAssociatedPartList);
                assIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
                assNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
                assInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
                assPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

                // boolean to inform the program that a part has been associated to the product
                isFirstAssItem = false;
            }
            else {
                intAssociatedPartList.add((availPartsTable.getSelectionModel().getSelectedItem()));
            }
        }
    }

    /** This event passes search criteria for any available parts. The data from the availPartField is determined to be an integer or a string, and is then passed to the lookupPart method from the Inventory class and returns a list containing parts that fulfill search criteria.
     *
     * @param actionEvent This event triggers upon pressing the Enter key in the availPartField.
     */
    public void searchAvailPart(ActionEvent actionEvent) {
            ObservableList<Part> nuPartList = FXCollections.observableArrayList();
            nuPartList.clear();
            boolean checkType = true;
            try {
                int numResult = Integer.parseInt(availPartField.getText());
            } catch (NumberFormatException numberFormatException) {
                checkType = false;
            }
            if(checkType){
                nuPartList = Inventory.lookupPart(Integer.parseInt(availPartField.getText()));
            }
            else{
                nuPartList = Inventory.lookupPart(availPartField.getText());
            }

            if(nuPartList.size() == 0) {
                Alert noPartResults = new Alert(Alert.AlertType.WARNING);
                noPartResults.setTitle("Warning");
                noPartResults.setHeaderText("No parts found.");
                noPartResults.setContentText("No parts match the search criteria.");
                noPartResults.show();
            }
            availPartsTable.setItems(nuPartList);
    }
}
