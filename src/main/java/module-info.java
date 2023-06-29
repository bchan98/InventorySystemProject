module bchan.inventorysystemproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens bchan.inventorysystemproject to javafx.fxml;
    opens bchan.inventorysystemproject.Components to javafx.base;

    exports bchan.inventorysystemproject;
}