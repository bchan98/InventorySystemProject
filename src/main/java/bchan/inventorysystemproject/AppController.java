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
    public TextField searchPartField;
    public Button deletePartsButton;
    public TextField searchProductField;
    public Label checkProdEmpty;
    @FXML
    private Label welcomeText;

    // variable to check whether the program has been run before to insert dummy data
    private static boolean isFirst = true;

    // variable to check whether Add or Modify was selected
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


    /** This method sends the user to the Add Parts Window. Passes information to inform partsModify window that new data is being added.
     *
     * @param actionEvent This triggers when the addPartsButton is pressed
     * @throws IOException
     */
    public void AddPartsWindow(ActionEvent actionEvent) throws IOException
    {
        // this message informs the modify-part window that this part is to be added.
        message = true;
        PartsController.setIsAddOrModify(message);

        // draws the new scene
        Parent root = FXMLLoader.load(getClass().getResource("partsModify.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle ("Add Parts");
        stage.setScene(scene);
        stage.show();
    }

    /**
    This method sends the user to the Modify Parts Window. Passes selected Part object to have data manipulated and modified, as well as information to inform partsModify window that old data is being modified.
     * @param actionEvent This triggers when the modifyPartsButton is pressed
     * @throws IOException
     **/
    public void modifyPartsWindow(ActionEvent actionEvent) throws IOException
    {
        // this message informs the modify-part window that this part is to be modified.
        message = false;
        PartsController.setIsAddOrModify(message);

        // requisite information from the part is then sent to the modify-part window
        Part sendP = (Part) partsTable.getSelectionModel().getSelectedItem();
        sendID = sendP.getId();
        sendName = sendP.getName();
        sendPrice = sendP.getPrice();
        sendStock = sendP.getStock();
        sendMin = sendP.getMin();
        sendMax = sendP.getMax();

        // logic check to determine the state of the radio button for InHouse/Outsourced at the modify-part window, as well as what data to send
        if (sendP instanceof InHouse){
            sendVarIn = ((InHouse) sendP).getMachineID();
            sendInOut = true;
        } else if (sendP instanceof Outsourced) {
            sendVarOut = ((Outsourced) sendP).getCompanyName();
            sendInOut = false;
        }

        // draws the new scene
        Parent root = FXMLLoader.load(getClass().getResource("partsModify.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle ("Modify Parts");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    /**
     This method is used to initialize the program. Sends data from Tableviews, with data obtained from the allParts list and allProducts list from Inventory class. Additionally, template data is generated and inserted into the two tables when required.
     **/

    public void initialize(URL url, ResourceBundle resourceBundle) {
        // intermediary part list pre-filtering
        intPartList = Inventory.getAllParts();

        // displays a filtered part list with no null items
        FilteredList<Part> showPartsList = new FilteredList<>(intPartList, Part -> Part != null);

        // intermediary product list pre-filtering
        productList = Inventory.getAllProducts();

        // displays a filtered product list with no null items
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

        // inputs data to the partsTable TableView
        partsTable.setItems(showPartsList);
        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        // inputs data to the productTable TableView
        productTable.setItems(showProductsList);
        productIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /** This method deletes a part. Selects highlighted part from cursor and runs a confirmation window before calling the deletePart from the Inventory class.
     *
     * @param actionEvent This triggers when the deletePartButton is pressed
     */
    public void deletePartCommand(ActionEvent actionEvent) {
        // confirmation text prior to deletion
        Alert confDel = new Alert(Alert.AlertType.CONFIRMATION);
        confDel.setTitle("Confirm deletion");
        confDel.setHeaderText("Deletion Warning");
        confDel.setContentText("Are you sure you want to delete this part?");

        Optional<ButtonType> result = confDel.showAndWait();
        if (result.get() == ButtonType.OK) {
            Inventory.deletePart(partsTable.getSelectionModel().getSelectedItem());
        }
    }

    /** This method to closes the window. Closes the window upon button press of the exitButton button.
     *
     * @param actionEvent This triggers when the exit button is pressed.
     */
    public void closeProgram(ActionEvent actionEvent) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    /** This method sends the user to the add product screen. Passes information to the products-window to inform it that a product is being added.
     *
     * @param actionEvent This triggers when the addProducts button is pressed.
     * @throws IOException
     */
    public void addProductsWindow(ActionEvent actionEvent) throws IOException {
        // this message informs the products-window that the product is to be added
        message = true;
        ProductsController.setIsAddOrModify(message);

        Parent root = FXMLLoader.load(getClass().getResource("products-window.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 900, 700);
        stage.setTitle ("Add Products");
        stage.setScene(scene);
        stage.show();
    }

    /** This method deletes a product. Obtains the product selected in the Products Table and removes product. Carries out a check to determine whether the selected product has any associated parts before deletion.
     *
     * @param actionEvent This triggers when the deleteProduct button is pressed.
     */
    public void deleteProductCommand(ActionEvent actionEvent) {
       // boolean to determine whether logic check is passed
        boolean allowExecution = true;

        Alert confDel = new Alert(Alert.AlertType.CONFIRMATION);
        confDel.setTitle("Confirm deletion");
        confDel.setHeaderText("Deletion Warning");
        confDel.setContentText("Are you sure you want to delete this product?");

        Optional<ButtonType> result = confDel.showAndWait();
        // logic check to see if product has no associated parts
        if(productTable.getSelectionModel().getSelectedItem().getAllAssociatedParts().size() != 0)
        {
            allowExecution = false;
        }
        if (result.get() == ButtonType.OK) {
            if(allowExecution) {
                Inventory.deleteProduct(productTable.getSelectionModel().getSelectedItem());
            }
            else {
                checkProdEmpty.setText("This product has parts still associated to it.");
            }
        }
    }

    /** This method sends the user to the modify products screen.
     *
     * @param actionEvent This event triggers when the modifyProduct button is pressed.
     * @throws IOException
     */
    public void modifyProductsWindow(ActionEvent actionEvent) throws IOException {
        // this message informs the products-modify window that the product is to be modified
        message = false;
        ProductsController.setIsAddOrModify(message);

        // product data is sent to the products-modify window
        Product sendPro = (Product) productTable.getSelectionModel().getSelectedItem();
        sendID = sendPro.getId();
        sendName = sendPro.getName();
        sendPrice = sendPro.getPrice();
        sendStock = sendPro.getStock();
        sendMin = sendPro.getMin();
        sendMax = sendPro.getMax();

        Parent root = FXMLLoader.load(getClass().getResource("products-window.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 900, 700);
        stage.setTitle ("Modify Parts");
        stage.setScene(scene);
        stage.show();
    }

    /** This method displays parts that fall under the search criteria. Text from the searchPartField is scraped and determined to either be an integer or a string. This data is then passed to the lookupPart function from the Inventory class which then returns the necessary parts to be displayed.
     *
     * @param actionEvent This event is triggered when the user presses the Enter key in the searchPartField.
     */
    public void searchPart(ActionEvent actionEvent) {
        // generates an empty list that will have relevant data passed from the Inventory lookupPart function
        ObservableList<Part> nuPartList = FXCollections.observableArrayList();
        nuPartList.clear();

        // determines whether search criteria is an integer or a string
        boolean checkType = true;
        try {
            int numResult = Integer.parseInt(searchPartField.getText());
        } catch (NumberFormatException numberFormatException) {
            checkType = false;
        }
        if(checkType){
            nuPartList = Inventory.lookupPart(Integer.parseInt(searchPartField.getText()));
        }
        else{
            nuPartList = Inventory.lookupPart(searchPartField.getText());
        }

        //sets partsTable to display relevant information.
        partsTable.setItems(nuPartList);
        System.out.println(nuPartList.size());
    }

    /** This method displays products that fall under the search criteria. Text from the searchProductField is scraped and determined to be either an integer or a string. This data is then passed to the lookupProduct function from the Inventory class, which then returns the necessary products to be displayed.
     *
     * @param actionEvent This event triggers when the user presses the Enter key in the searchProductField.
     */
    public void searchProduct(ActionEvent actionEvent) {
        // generates an empty list that will have relevant data passed from the Inventory lookupProduct function
        ObservableList<Product> nuProductList = FXCollections.observableArrayList();
        nuProductList.clear();
        // logic check to determine whether search criteria is an integer or a string
        boolean checkType = true;
        try {
            int numResult = Integer.parseInt(searchProductField.getText());
        } catch (NumberFormatException numberFormatException) {
            checkType = false;
        }
        if(checkType) {
            nuProductList = Inventory.lookupProduct(Integer.parseInt(searchProductField.getText()));
        }
        else {
            nuProductList = Inventory.lookupProduct(searchProductField.getText());
        }
        // sets productTable to display relevant information.
        productTable.setItems(nuProductList);
        System.out.println(nuProductList.size());
    }
}