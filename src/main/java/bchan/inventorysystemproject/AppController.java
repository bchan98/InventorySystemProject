package bchan.inventorysystemproject;

import bchan.inventorysystemproject.Components.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AppController implements Initializable {
    // variables for specific widgets from JavaFXML
    public TextField TextBox;
    public Button modifyPartsButton;
    public Button addPartsButton;
    public TableColumn partIDCol;
    public TableColumn partNameCol;
    public TableColumn partInvCol;
    public TableColumn partPriceCol;
    public TableView<Part> partsTable;
    public TableView<Product> productTable;
    public TableColumn productIDCol;
    public TableColumn productNameCol;
    public TableColumn productInvCol;
    public TableColumn productPriceCol;
    public Button exitButton;
    @FXML
    private Label welcomeText;

    // Variable to check whether the program has been run before to insert dummy data
    private static boolean isFirst = true;

    // Variable to check whether Add or Modify was selected
    public boolean message;

    // variables to be sent to modify Parts window

    public static int sendID;
    public static String sendName;
    public static double sendPrice;
    public static int sendStock;
    public static int sendMin;
    public static int sendMax;
    public static int sendVarIn;
    public static String sendVarOut;
    public static boolean sendInOut = true;

    // Lists to have data sent into from Inventory
    private ObservableList<Part> intPartList = FXCollections.observableArrayList();
    private ObservableList<Product> productList = FXCollections.observableArrayList();


    /** Method to add a part to allParts. Passes information to inform partsModify window that new data is being added.
     *
     * @param actionEvent
     * @throws IOException
     */
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

    /**
    Method to open the Modify Parts Window. Passes selected Part object to have data manipulated and modified, as well as information to inform partsModify window that old data is being modified.
     **/
    public void modifyPartsWindow(ActionEvent actionEvent) throws IOException
    {
        message = false;
        PartsController.setIsAddOrModify(message);

        Part sendP = (Part) partsTable.getSelectionModel().getSelectedItem();
        sendID = sendP.getId();
        sendName = sendP.getName();
        sendPrice = sendP.getPrice();
        sendStock = sendP.getStock();
        sendMin = sendP.getMin();
        sendMax = sendP.getMax();

        if (sendP instanceof InHouse){
            sendVarIn = ((InHouse) sendP).getMachineID();
            sendInOut = true;
        } else if (sendP instanceof Outsourced) {
            sendVarOut = ((Outsourced) sendP).getCompanyName();
            sendInOut = false;
        }

        Parent root = FXMLLoader.load(getClass().getResource("partsModify.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle ("Modify Parts");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    /**
     Method to initialize the program. Sends data from Tableviews, with data obtained from the allParts list and allProducts list from Inventory class. Additionally, template data is generated and inserted into the two tables when required.
     **/

    public void initialize(URL url, ResourceBundle resourceBundle) {


        intPartList = Inventory.getAllParts();
        FilteredList<Part> showPartsList = new FilteredList<>(intPartList, Part -> Part != null);

        productList = Inventory.getAllProducts();
        FilteredList<Product> showProductsList = new FilteredList<>(productList, Product -> Product != null);

        if(isFirst)
        {
            isFirst = false;
            Part inTest = new InHouse(1, "test", 2.00, 5, 1, 5, 22);
            Part outTest = new Outsourced (2, "outTest", 4.00, 3, 3, 15, "Tesla");
            Inventory.addPart(inTest);
            Inventory.addPart(outTest);

            Product prodTest = new Product(1, "Donkey Kong", 4.95, 1, 1, 3);
            Inventory.addProduct(prodTest);
        }

        partsTable.setItems(showPartsList);
        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productTable.setItems(showProductsList);
        productIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /** Method to initiate part deletion from the delete button command. Selects highlighted part from cursor and runs a confirmation window before calling the deletePart from the Inventory class.
     *
     * @param actionEvent
     */
    public void deletePartCommand(ActionEvent actionEvent) {
        Alert confDel = new Alert(Alert.AlertType.CONFIRMATION);
        confDel.setTitle("Confirm deletion");
        confDel.setHeaderText("Deletion Warning");
        confDel.setContentText("Are you sure you want to delete this part?");

        Optional<ButtonType> result = confDel.showAndWait();
        if (result.get() == ButtonType.OK) {
            Inventory.deletePart(partsTable.getSelectionModel().getSelectedItem());
        }
    }

    /** Method to handle window closing. Closes the window upon button press of the exitButton button.
     *
     * @param actionEvent
     */
    public void closeProgram(ActionEvent actionEvent) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    public void addProductsWindow(ActionEvent actionEvent) throws IOException {
        message = true;
        ProductsController.setIsAddOrModify(message);

        Parent root = FXMLLoader.load(getClass().getResource("products-window.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 550, 800);
        stage.setTitle ("Add Products");
        stage.setScene(scene);
        stage.show();
    }

    public void deleteProductCommand(ActionEvent actionEvent) {
        Alert confDel = new Alert(Alert.AlertType.CONFIRMATION);
        confDel.setTitle("Confirm deletion");
        confDel.setHeaderText("Deletion Warning");
        confDel.setContentText("Are you sure you want to delete this product?");

        Optional<ButtonType> result = confDel.showAndWait();
        if (result.get() == ButtonType.OK) {
            Inventory.deleteProduct(productTable.getSelectionModel().getSelectedItem());
        }
    }

    public void modifyProductsWindow(ActionEvent actionEvent) throws IOException {
        message = false;
        ProductsController.setIsAddOrModify(message);

        Product sendPro = (Product) productTable.getSelectionModel().getSelectedItem();
        sendID = sendPro.getId();
        sendName = sendPro.getName();
        sendPrice = sendPro.getPrice();
        sendStock = sendPro.getStock();
        sendMin = sendPro.getMin();
        sendMax = sendPro.getMax();

        Parent root = FXMLLoader.load(getClass().getResource("products-window.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 800);
        stage.setTitle ("Modify Parts");
        stage.setScene(scene);
        stage.show();
    }
}