module bchan.inventorysystemproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens bchan.inventorysystemproject to javafx.fxml;
    exports bchan.inventorysystemproject;
}