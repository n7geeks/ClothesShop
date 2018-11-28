package com.bondif.clothesshop.controllers;

import com.bondif.clothesshop.core.CategoryDaoImpl;
import com.bondif.clothesshop.core.ProductDaoImpl;
import com.bondif.clothesshop.models.Category;
import com.bondif.clothesshop.models.Product;
import com.bondif.clothesshop.views.ActionButtonTableCell;
import com.bondif.clothesshop.views.GUITools;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.io.File;
import java.net.URI;
import java.util.Collection;

public class ProductsController {
    private static ObservableList<Product> productsOl;
    private static ProductDaoImpl productDao;

    static {
        productDao = new ProductDaoImpl();
    }

    public static VBox getProductsPane() {
        // bring data from the server
        productsOl = getProductsOl();

        VBox vBox = new VBox();

        // vbox config
        vBox.setPadding(new Insets(20));
        vBox.setSpacing(10);

        // Add product button
        String iconPath = "resources/avatar.jpg";
        Button addBtn = GUITools.getButton(GUITools.getImage(iconPath), "Ajouter", 100);
        addBtn.setOnAction(event -> {
            AppController.showCreateProductForm();
        });

        //manage Categories
        Button categoryBtn = GUITools.getButton(GUITools.getImage(iconPath), "catégories", 100);
        categoryBtn.setOnAction(event ->  {
            AppController.showCategoryForm();
        });

        // Products Table
        TableView<Product> productsTableView = getBasicTableView();

        // Edit column
        TableColumn editColumn = new TableColumn<>("Modifier");
        editColumn.setCellFactory(ActionButtonTableCell.forTableColumn("Modifier", (Product p) -> {
            ProductsController.editHandler(p);
            return p;
        }));

        // Delete column
        TableColumn deleteColumn = new TableColumn<>("Supprimer");
        deleteColumn.setCellFactory(ActionButtonTableCell.forTableColumn("Supprimer", (Product p) -> {
            ProductsController.deleteProductHandler(p);
            return p;
        }));

        productsTableView.getColumns().addAll(editColumn, deleteColumn);

        productsTableView.setItems(productsOl);

        productsTableView.setRowFactory(tv -> {
            TableRow<Product> productTableRow = new TableRow<>();

            productTableRow.setOnMouseClicked(e -> {
                if (e.getClickCount() == 2 && !productTableRow.isEmpty()) {
                    ProductsController.show(productTableRow.getItem().getCode());
                }
            });

            return productTableRow;
        });

        // Search
        HBox searchContainer = new HBox(20);
        TextField searchTf = new TextField();
        searchTf.setPromptText("search");
        searchTf.setMinWidth(300);

        searchTf.textProperty().addListener((observable, oldValue, newValue) -> {
            newValue = newValue.trim();
            oldValue = oldValue.trim();
            if(!newValue.equals(oldValue)) {
                System.out.println("searching...");
                Collection<Product> filterdProducts = new ProductDaoImpl().findAll(newValue);
                productsOl = FXCollections.observableArrayList(filterdProducts);
                productsTableView.setItems(productsOl);
            }
        });

        searchContainer.getChildren().add(searchTf);
        searchContainer.setAlignment(Pos.CENTER_RIGHT);

        vBox.getChildren().addAll(addBtn, categoryBtn, searchContainer, productsTableView);

        return vBox;
    }

    public static Pane getCreateForm() {
        GridPane gridPane = new GridPane();

        gridPane.setAlignment(Pos.CENTER);

        String imagePath = "resources/icons8-add-image-64.png";
        ImageView imageView = new ImageView(GUITools.getImage(imagePath));
        imageView.setFitWidth(300);
        imageView.setPreserveRatio(true);

        imageView.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                String newPath = AppController.chooseProductImageHandler();
                if (newPath != null)
                    imageView.setImage(GUITools.getImage(newPath));
            }
        });

        Label codeLabel = new Label("Code");
        Label labelLabel = new Label("Désignation");
        Label qtyLabel = new Label("Quantité");
        Label buyPriceLabel = new Label("Prix d'achat");
        Label sellPriceLabel = new Label("Prix de vente");
        Label categoryLabel = new Label("Catégorie");

        TextField codeTf = new TextField();
        codeTf.setDisable(true);
        codeTf.setPromptText("Code");
        TextField labelTf = new TextField();
        labelTf.setPromptText("Désignation");
        TextField qtyTf = new TextField();
        qtyTf.setPromptText("Quantité");
        TextField buyPriceTf = new TextField();
        buyPriceTf.setPromptText("Prix d'achat");
        TextField sellPriceTf = new TextField();
        sellPriceTf.setPromptText("Prix de vente");
        ComboBox<Category> categoriesCb = new ComboBox<>(FXCollections.observableArrayList(new CategoryDaoImpl().findAll()));

        Button submitBtn = new Button("Créer");

        submitBtn.setOnAction(event -> {
            String label = labelTf.getText();
            int qty = Integer.parseInt(qtyTf.getText());
            Double buyingPrice = Double.parseDouble(buyPriceTf.getText());
            Double sellingPrice = Double.parseDouble(sellPriceTf.getText());
            String image = new File(URI.create(imageView.getImage().getUrl())).getAbsolutePath();
            Category category = categoriesCb.getValue();

            addProductHandler(new Product(null, label, qty, buyingPrice, sellingPrice, image, category));

            AppController.showProducts();
        });

        gridPane.add(imageView, 0, 0, 2, 1);

        gridPane.add(codeLabel, 0, 1);
        gridPane.add(labelLabel, 0, 2);
        gridPane.add(qtyLabel, 0, 3);
        gridPane.add(buyPriceLabel, 0, 4);
        gridPane.add(sellPriceLabel, 0, 5);
        gridPane.add(categoryLabel, 0, 6);

        gridPane.add(codeTf, 1, 1);
        gridPane.add(labelTf, 1, 2);
        gridPane.add(qtyTf, 1, 3);
        gridPane.add(buyPriceTf, 1, 4);
        gridPane.add(sellPriceTf, 1, 5);
        gridPane.add(categoriesCb, 1, 6);

        gridPane.add(submitBtn, 0, 6, 2, 1);

        GridPane.setHalignment(imageView, HPos.CENTER);
        GridPane.setHalignment(submitBtn, HPos.RIGHT);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(20);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(60);

        gridPane.getColumnConstraints().addAll(col1, col2);

        gridPane.setHgap(10);
        gridPane.setVgap(10);

        return gridPane;
    }

    public static void addProductHandler(Product product) {
        productDao.create(product);
    }

    public static void show(Long code) {
        Product product = productDao.findOne(code);

        Pane pane = getProductPane(product);

        AppController.getRoot().setCenter(pane);
    }

    public static void editHandler(Product product) {
        Pane pane = getEditForm(product);
        AppController.getRoot().setCenter(pane);
    }

    public static void deleteProductHandler(Product product) {
        productDao.delete(product);
        AppController.showProducts();
    }

    public static TableView<Product> getBasicTableView() {
        // Products Table
        TableView<Product> productsTableView = new TableView<>();

        // code column
        TableColumn<Product, Long> codeColumn = new TableColumn<>("Code");
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));

        // label column
        TableColumn<Product, String> labelColumn = new TableColumn<>("Désignation");
        labelColumn.setCellValueFactory(new PropertyValueFactory<>("label"));

        // qty column
        TableColumn<Product, Integer> qtyColumn = new TableColumn<>("Quantité");
        qtyColumn.setCellValueFactory(new PropertyValueFactory<>("qty"));

        // buyPrice column
        TableColumn<Product, Double> buyPriceColumn = new TableColumn<>("Prix d'achat");
        buyPriceColumn.setCellValueFactory(new PropertyValueFactory<>("buyingPrice"));

        // sellPrice column
        TableColumn<Product, Double> sellPriceColumn = new TableColumn<>("Prix de vente");
        sellPriceColumn.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));

        // category column
        TableColumn<Product, Category> categoryColumn = new TableColumn<>("Categorie");
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

        productsTableView.getColumns().addAll(codeColumn, labelColumn, qtyColumn, buyPriceColumn, sellPriceColumn, categoryColumn);

        return productsTableView;
    }

    public static ObservableList<Product> getProductsOl() {
        return FXCollections.observableArrayList(productDao.findAll());
    }

    private static Pane getEditForm(Product product) {
        GridPane gridPane = (GridPane)getCreateForm();

        ImageView imageView = (ImageView) gridPane.getChildren().get(0);
        imageView.setImage(GUITools.getImage(product.getImage()));

        TextField codeTf = (TextField) gridPane.getChildren().get(7);
        codeTf.setText(product.getCode().toString());
        codeTf.setDisable(true);

        TextField labelTf = (TextField) gridPane.getChildren().get(8);
        labelTf.setText(product.getLabel());

        TextField qtyTf = (TextField) gridPane.getChildren().get(9);
        qtyTf.setText(product.getQty() + "");

        TextField buyingPriceTf = (TextField) gridPane.getChildren().get(10);
        buyingPriceTf.setText(product.getBuyingPrice() + "");

        TextField sellingPriceTf = (TextField) gridPane.getChildren().get(11);
        sellingPriceTf.setText(product.getSellingPrice() + "");

        ComboBox<Category> categoriesCb = (ComboBox<Category>) gridPane.getChildren().get(12);
        categoriesCb.setValue(product.getCategory());

        Button updateButton = (Button) gridPane.getChildren().get(13);
        updateButton.setText("Modifier");

        updateButton.setOnAction(e -> {
            long code = Long.parseLong(codeTf.getText());
            String label = labelTf.getText();
            int qty = Integer.parseInt(qtyTf.getText());
            double buyingPrice = Double.parseDouble(buyingPriceTf.getText());
            double sellingPrice = Double.parseDouble(sellingPriceTf.getText());
            String image = new File(URI.create(imageView.getImage().getUrl())).getAbsolutePath();
            Category category = categoriesCb.getValue();

            productDao.update(new Product(code, label, qty, buyingPrice, sellingPrice, image, category));
            AppController.getRoot().setCenter(getProductPane(productDao.findOne(product.getCode())));
        });

        return gridPane;
    }

    private static Pane getProductPane(Product product) {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);

        Text codeLabelTxt = new Text("Code");
        Text labelLabelTxt = new Text("Label");
        Text qtyLabelTxt = new Text("Quantité");
        Text buyingPriceLabelTxt = new Text("Prix d'achat");
        Text sellingPriceLabelTxt = new Text("Prix de vente");
        Text categoryLabelTxt = new Text("Catégorie");

        ImageView imageView = new ImageView(GUITools.getImage(product.getImage()));
        imageView.setFitWidth(250);
        imageView.setPreserveRatio(true);
        Text codeTxt = new Text(product.getCode().toString());
        Text labelTxt = new Text(product.getLabel());
        Text qtyTxt = new Text(product.getQty() + "");
        Text buyingPriceTxt = new Text(product.getBuyingPrice() + "");
        Text sellingPriceTxt = new Text(product.getSellingPrice() + "");
        Text categoryTxt = new Text(product.getCategory().getTitle());

        gridPane.add(codeLabelTxt, 0, 1);
        gridPane.add(labelLabelTxt, 0, 2);
        gridPane.add(qtyLabelTxt, 0, 3);
        gridPane.add(buyingPriceLabelTxt, 0, 4);
        gridPane.add(sellingPriceLabelTxt, 0, 5);
        gridPane.add(categoryLabelTxt, 0, 6);

        gridPane.add(imageView, 0, 0, 2, 1);
        gridPane.add(codeTxt, 1, 1);
        gridPane.add(labelTxt, 1, 2);
        gridPane.add(qtyTxt, 1, 3);
        gridPane.add(buyingPriceTxt, 1, 4);
        gridPane.add(sellingPriceTxt, 1, 5);
        gridPane.add(categoryTxt, 1, 6);

        GridPane.setHalignment(imageView, HPos.CENTER);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(20);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(60);

        gridPane.getColumnConstraints().addAll(col1, col2);

        gridPane.setHgap(10);
        gridPane.setVgap(10);

        return gridPane;
    }
}
