package bchan.inventorysystemproject;

import bchan.inventorysystemproject.Components.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.ResourceBundle;

public class AppController implements Initializable {
    public TextField TextBox;
    public Button modifyPartsButton;
    public Button addPartsButton;
    public TableColumn partIDCol;
    public TableColumn partNameCol;
    public TableColumn partInvCol;
    public TableColumn partPriceCol;
    public TableView partsTable;
    public TableView productTable;
    public TableColumn productIDCol;
    public TableColumn productNameCol;
    public TableColumn productInvCol;
    public TableColumn productPriceCol;
    @FXML
    private Label welcomeText;

    private boolean isFirst = true;

    public boolean message;

    private ObservableList<Part> partList = FXCollections.observableArrayList();
    private ObservableList<Product> productList = FXCollections.observableArrayList();



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

    public void modifyPartsWindow(ActionEvent actionEvent) throws IOException
    {
        message = false;
        PartsController.setIsAddOrModify(message);

        Parent root = FXMLLoader.load(getClass().getResource("partsModify.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle ("Modify Parts");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(isFirst)
        {
            isFirst = false;
            Part inTest = new InHouse(1, "test", 2.00, 5, 1, 5, 22);
            Part outTest = new Outsourced (2, "outTest", 4.00, 3, 3, 15, "Tesla");
            partList.add(inTest);
            partList.add(outTest);
        }

        partsTable.setItems(partList);
        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }
}