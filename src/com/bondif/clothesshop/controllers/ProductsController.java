package com.bondif.clothesshop.controllers;

import com.bondif.clothesshop.models.Product;
import com.bondif.clothesshop.views.GUITools;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class ProductsController {
    public static VBox getProductsPane() {
        VBox vBox = new VBox();

        // vbox config
        vBox.setPadding(new Insets(20));
        vBox.setSpacing(10);

        // Add product button
        String iconPath = "resources/avatar.jpg";
        Button addBtn = GUITools.getButton(GUITools.getImage(iconPath), "Ajouter", 100);
        addBtn.setOnAction(event ->  {
            AppController.showCreateProductForm();
        });

        // Products Table
        TableView<Product> productsTableView = new TableView<>();

        // code column
        TableColumn<Product, Long> codeColumn = new TableColumn<>("Code");
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));

        // label column
        TableColumn<Product, String> labelColumn = new TableColumn<>("Désignation");
        labelColumn.setCellValueFactory(new PropertyValueFactory<>("label"));

        // buyPrice column
        TableColumn<Product, Double> buyPriceColumn = new TableColumn<>("Prix d'achat");
        buyPriceColumn.setCellValueFactory(new PropertyValueFactory<>("buyPrice"));

        // sellPrice column
        TableColumn<Product, Double> sellPriceColumn = new TableColumn<>("Prix de vente");
        sellPriceColumn.setCellValueFactory(new PropertyValueFactory<>("sellPrice"));

        productsTableView.getColumns().addAll(codeColumn, labelColumn, buyPriceColumn, sellPriceColumn);

        vBox.getChildren().addAll(addBtn, productsTableView);

        return vBox;
    }

    public static Pane getCreateForm() {
        GridPane gridPane = new GridPane();

        String imagePath = "resources/admin.jpg";
        ImageView imageView = new ImageView(GUITools.getImage(imagePath));
        imageView.setFitWidth(50);
        imageView.setPreserveRatio(true);

        Label codeLabel = new Label("Code");
        Label labelLabel = new Label("Désignation");
        Label buyPriceLabel = new Label("Prix d'achat");
        Label sellPriceLabel = new Label("Prix de vente");

        TextField codeTf = new TextField();
        codeTf.setPromptText("Code");
        TextField labelTf = new TextField();
        labelTf.setPromptText("Désignation");
        TextField buyPriceTf = new TextField();
        buyPriceTf.setPromptText("Prix d'achat");
        TextField sellPriceTf = new TextField();
        sellPriceTf.setPromptText("Prix de vente");

        Button submitBtn = new Button("Créer");

        gridPane.add(imageView, 1,1, 2, 1);

        gridPane.add(codeLabel, 1, 2);
        gridPane.add(labelLabel, 1, 3);
        gridPane.add(buyPriceLabel, 1, 4);
        gridPane.add(sellPriceLabel, 1, 5);

        gridPane.add(codeTf, 2, 2);
        gridPane.add(labelTf, 2, 3);
        gridPane.add(buyPriceTf, 2, 4);
        gridPane.add(sellPriceTf, 2, 5);

        gridPane.add(submitBtn, 0, 6);

        gridPane.setHgap(10);
        gridPane.setVgap(10);


        return gridPane;
    }
}
