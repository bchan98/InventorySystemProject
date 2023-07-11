package bchan.inventorysystemproject;

import bchan.inventorysystemproject.Components.*;
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

    private boolean isFirstAssItem = false;

    private ObservableList<Part> intAssociatedPartList;
    private ObservableList<Part> intAvailablePartList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if(isTrigger) {
            prodWindowLabel.setText("Add Product");
            prodIDField.setText(String.valueOf(productIDCounter));

            Product nuProduct = new Product(productIDCounter, "", 0, 0, 0, 1);
            ObservableList<Part> intAssociatedPartList = nuProduct.getAllAssociatedParts();

            isFirstAssItem = true;
        }
        else {
            prodWindowLabel.setText("Modify Product");
            prodIDField.setText(String.valueOf(AppController.sendID));
            prodNameField.setText(AppController.sendName);
            prodInvField.setText(String.valueOf(AppController.sendStock));
            prodPriceField.setText(String.valueOf(AppController.sendPrice));
            prodMinField.setText(String.valueOf(AppController.sendMin));
            prodMaxField.setText(String.valueOf(AppController.sendMax));

            Product nuProduct = Inventory.getAllProducts().get((Integer.parseInt(prodIDField.getText())) - 1);
            intAssociatedPartList = nuProduct.getAllAssociatedParts();

            associatedPartsTable.setItems(intAssociatedPartList);
            assIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            assNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            assInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
            assPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        }
        intAvailablePartList = Inventory.getAllParts();
        FilteredList<Part> showAvailablePartList = new FilteredList<>(intAvailablePartList, Part -> Part != null);


        availPartsTable.setItems(showAvailablePartList);
        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
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

    public void saveChanges(ActionEvent actionEvent) throws IOException {

        int setID = Integer.parseInt(prodIDField.getText());
        String setName = prodNameField.getText();
        int setInv = Integer.parseInt(prodInvField.getText());
        double setPrice = Double.parseDouble(prodPriceField.getText());
        int setMin = Integer.parseInt(prodMinField.getText());
        int setMax = Integer.parseInt(prodMaxField.getText());
        Product nuProduct = new Product(setID, setName, setPrice, setInv, setMin, setMax);

        if(isTrigger) {
            Inventory.addProduct(nuProduct);
            productIDCounter++;
        }
        else if(!isTrigger){
            Inventory.updateProduct(setID, nuProduct);
        }

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

    public void removeAssociatePart(ActionEvent actionEvent) {
        Alert confDel = new Alert(Alert.AlertType.CONFIRMATION);
        confDel.setTitle("Confirm deletion");
        confDel.setHeaderText("Deletion Warning");
        confDel.setContentText("Are you sure you want to delete this part?");

        Optional<ButtonType> result = confDel.showAndWait();
        if (result.get() == ButtonType.OK) {
            intAssociatedPartList.remove(associatedPartsTable.getSelectionModel().getSelectedItem());
        }
    }

    public void addAssociatePart(ActionEvent actionEvent) {
        intAssociatedPartList.add((Part) availPartsTable.getSelectionModel().getSelectedItem());

        if(isFirstAssItem) {
            FilteredList<Part> showAssociatedPartList = new FilteredList<>(intAssociatedPartList, Part -> Part != null);

            associatedPartsTable.setItems(intAssociatedPartList);
            assIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            assNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            assInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
            assPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

            isFirstAssItem = false;
        }
    }
}
