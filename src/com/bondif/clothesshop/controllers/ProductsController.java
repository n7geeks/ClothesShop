package com.bondif.clothesshop.controllers;

import com.bondif.clothesshop.core.CategoryDaoImpl;
import com.bondif.clothesshop.core.ProductDaoImpl;
import com.bondif.clothesshop.models.Category;
import com.bondif.clothesshop.models.Product;
import com.bondif.clothesshop.views.ActionButtonTableCell;
import com.bondif.clothesshop.views.GUITools;
import com.bondif.clothesshop.views.utils.ComboBoxAutoComplete;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
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
        HBox hBox = new HBox();
        Region region = new Region();

        // vbox config
        vBox.setPadding(new Insets(20));
        vBox.setSpacing(10);

        //hBox config
        hBox.setPadding(new Insets(20));
        hBox.setSpacing(10);

        //region config
        HBox.setHgrow(region, Priority.ALWAYS);

        // Add product button
        String addIconPath = "resources/icons/plus-math-30.png";
        String categoriesIconPath = "resources/icons/category-40.png";

        Button addBtn = GUITools.getButton(GUITools.getImage(addIconPath), "Ajouter", 100);
        addBtn.setOnAction(event -> {
            AppController.showCreateProductForm();
        });

        //manage Categories
        Button categoryBtn = GUITools.getButton(GUITools.getImage(categoriesIconPath), "Catégories", 100);
        categoryBtn.setOnAction(event -> {
            AppController.showCategoryForm();
        });

        /*Button btn = new Button("Show products");
        btn.setOnAction(event -> {
            productsOl = getProductsOl();
            AppController.getRoot().setCenter(showProducts(productsOl));
        });*/

        // Products Table
        TableView<Product> productsTableView = getBasicTableView();

        // Edit column
        TableColumn editColumn = new TableColumn<>("Modifier");
        editColumn.setCellFactory(ActionButtonTableCell.forTableColumn("Modifier", (Product p) -> {
            ProductsController.editHandler(p);
            return p;
        }));
        editColumn.prefWidthProperty().bind(productsTableView.widthProperty().divide(100 / 15));

        // Delete column
        TableColumn deleteColumn = new TableColumn<>("Supprimer");
        deleteColumn.setCellFactory(ActionButtonTableCell.forTableColumn("Supprimer", (Product p) -> {
            ProductsController.deleteProductHandler(p);
            return p;
        }));
        deleteColumn.prefWidthProperty().bind(productsTableView.widthProperty().divide(100 / 15));

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
        //HBox searchContainer = new HBox(20);
        TextField searchTf = new TextField();
        searchTf.setPromptText("Rechercher");
        //searchTf.setMinWidth(300);
        searchTf.getStyleClass().add("searchBar");
        searchTf.setMinWidth(200);
        searchTf.setMinHeight(28);
        searchTf.setAlignment(Pos.CENTER);
        searchTf.getStyleClass().remove("text-field");

        searchTf.textProperty().addListener((observable, oldValue, newValue) -> {
            newValue = newValue.trim();
            oldValue = oldValue.trim();
            if (!newValue.equals(oldValue)) {
                System.out.println("searching...");
                Collection<Product> filterdProducts = new ProductDaoImpl().findAll(newValue);
                productsOl = FXCollections.observableArrayList(filterdProducts);
                productsTableView.setItems(productsOl);
            }
        });

        /*searchContainer.getChildren().add(searchTf);
        searchContainer.setAlignment(Pos.CENTER_RIGHT);*/

        //hBox config
        hBox.getChildren().addAll(addBtn, categoryBtn, region, searchTf);

        vBox.getChildren().addAll(hBox, productsTableView);

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
        /*Label qtyLabel = new Label("Quantité");*/
        Label buyPriceLabel = new Label("Prix d'achat");
        Label sellPriceLabel = new Label("Prix de vente");
        Label categoryLabel = new Label("Catégorie");

        TextField codeTf = new TextField();
        codeTf.setDisable(true);
        codeTf.setPromptText("Code");
        TextField labelTf = new TextField();
        labelTf.setPromptText("Désignation");
        /*TextField qtyTf = new TextField();
        qtyTf.setPromptText("Quantité");*/
        TextField buyPriceTf = new TextField();
        buyPriceTf.setPromptText("Prix d'achat");
        TextField sellPriceTf = new TextField();
        sellPriceTf.setPromptText("Prix de vente");
        ComboBox<Category> categoriesCb = new ComboBox<>(FXCollections.observableArrayList(new CategoryDaoImpl().findAll()));
        categoriesCb.setPromptText("Choisissez une catégorie");
        new ComboBoxAutoComplete<Category>(categoriesCb);

        String submitIconBtn = "resources/icons/checkmark-40.png";
        Button submitBtn = GUITools.getButton(GUITools.getImage(submitIconBtn), "Créer", 100);

        submitBtn.setOnAction(event -> {
            boolean isValidInput = true;
            String errorMsg = "";
            int qty = 0;
            double sellingPrice = 0.0, buyingPrice = 0.0;

            String label = labelTf.getText();
            if (label.trim().isEmpty()) {
                errorMsg += "- La designation est vide!\r\n";
                isValidInput = false;
            }

            /*try {
                qty = Integer.parseInt(qtyTf.getText());
                if (qty <= 0) {
                    errorMsg += "- La qunatité doit être supérieure à 0\r\n";
                    isValidInput = false;
                }
            } catch (NumberFormatException ex) {
                errorMsg += "- la quantité doit être un entier\r\n";
                isValidInput = false;
            }*/

            try {
                buyingPrice = Double.parseDouble(buyPriceTf.getText());
                if (buyingPrice <= 0) {
                    errorMsg += "- Le prix d'achat doit être superieur à 0\r\n";
                    isValidInput = false;
                }
            } catch (NumberFormatException ex) {
                   errorMsg += "- Le prix d'achat doit être un reel\r\n";
                isValidInput = false;
            }
            try {
                sellingPrice = Double.parseDouble(sellPriceTf.getText());
                if (sellingPrice <= 0) {
                    errorMsg += "- Le prix de vente doit être superieur à 0\r\n";
                    isValidInput = false;
                }
            } catch (NumberFormatException ex) {
                errorMsg += "- Le prix de vente doit être un reel\r\n";
                isValidInput = false;
            }

            String image = new File(URI.create(imageView.getImage().getUrl())).getAbsolutePath();
            Category category = categoriesCb.getValue();
            if (category == null) {
                errorMsg += "- Aucune categorie est selectionnée\r\n";
                isValidInput = false;
            }
            if (isValidInput) {
                addProductHandler(new Product(null, label, qty, buyingPrice, sellingPrice, image, category));
                AppController.showProducts();
            } else {
                GUITools.openDialogOk(null, null, errorMsg, Alert.AlertType.WARNING);
            }
        });

        gridPane.add(imageView, 0, 0, 2, 1);

        gridPane.add(codeLabel, 0, 1);
        gridPane.add(labelLabel, 0, 2);
        /*gridPane.add(qtyLabel, 0, 3);*/
        gridPane.add(buyPriceLabel, 0, 3);
        gridPane.add(sellPriceLabel, 0, 4);
        gridPane.add(categoryLabel, 0, 5);

        gridPane.add(codeTf, 1, 1);
        gridPane.add(labelTf, 1, 2);
        //gridPane.add(qtyTf, 1, 3);
        gridPane.add(buyPriceTf, 1, 3);
        gridPane.add(sellPriceTf, 1, 4);
        gridPane.add(categoriesCb, 1, 5);

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

        gridPane.getChildren().get(6).getStyleClass().remove("text-field");
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
        if(GUITools.openDialogYesNo("Supperession d'un produit", null, "Voulez-vous vraiment supprimer ce produit?", Alert.AlertType.WARNING)) {
            productDao.delete(product);
        }
        AppController.showProducts();
    }

    public static TableView<Product> getBasicTableView() {
        // Products Table
        TableView<Product> productsTableView = new TableView<>();

        // code column
        TableColumn<Product, Long> codeColumn = new TableColumn<>("Code");
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        codeColumn.prefWidthProperty().bind(productsTableView.widthProperty().divide(100 / 5)); // 5 --> 8

        // label column
        TableColumn<Product, String> labelColumn = new TableColumn<>("Désignation");
        labelColumn.setCellValueFactory(new PropertyValueFactory<>("label"));
        labelColumn.prefWidthProperty().bind(productsTableView.widthProperty().divide(100 / 15)); // 15 --> 18

        // qty column
        TableColumn<Product, Integer> qtyColumn = new TableColumn<>("Quantité");
        qtyColumn.setCellValueFactory(new PropertyValueFactory<>("qty"));
        qtyColumn.prefWidthProperty().bind(productsTableView.widthProperty().divide(100 / 6));

        // buyPrice column
        TableColumn<Product, Double> buyPriceColumn = new TableColumn<>("Prix d'achat");
        buyPriceColumn.setCellValueFactory(new PropertyValueFactory<>("buyingPrice"));
        buyPriceColumn.prefWidthProperty().bind(productsTableView.widthProperty().divide(100 / 10)); // 10 --> 20

        // sellPrice column
        TableColumn<Product, Double> sellPriceColumn = new TableColumn<>("Prix de vente");
        sellPriceColumn.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));
        sellPriceColumn.prefWidthProperty().bind(productsTableView.widthProperty().divide(100 / 10));

        // category column
        TableColumn<Product, Category> categoryColumn = new TableColumn<>("Catégorie");
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        categoryColumn.prefWidthProperty().bind(productsTableView.widthProperty().divide(100 / 15));

        productsTableView.getColumns().addAll(codeColumn, labelColumn, qtyColumn, buyPriceColumn, sellPriceColumn, categoryColumn);

        return productsTableView;
    }

    public static ObservableList<Product> getProductsOl() {
        return FXCollections.observableArrayList(productDao.findAll());
    }

    private static Pane getEditForm(Product product) {
        GridPane gridPane = (GridPane) getCreateForm();

        ImageView imageView = (ImageView) gridPane.getChildren().get(0);
        imageView.setImage(GUITools.getImage(product.getImage()));

        TextField codeTf = (TextField) gridPane.getChildren().get(6);
        codeTf.setText(product.getCode().toString());
        codeTf.setDisable(true);

        TextField labelTf = (TextField) gridPane.getChildren().get(7);
        labelTf.setText(product.getLabel());

        /*TextField qtyTf = (TextField) gridPane.getChildren().get(9);
        qtyTf.setText(product.getQty() + "");*/

        TextField buyingPriceTf = (TextField) gridPane.getChildren().get(8);
        buyingPriceTf.setText(product.getBuyingPrice() + "");

        TextField sellingPriceTf = (TextField) gridPane.getChildren().get(9);
        sellingPriceTf.setText(product.getSellingPrice() + "");

        ComboBox<Category> categoriesCb = (ComboBox<Category>) gridPane.getChildren().get(10);
        categoriesCb.setValue(product.getCategory());

        Button updateButton = (Button) gridPane.getChildren().get(11);
        updateButton.setText("Modifier");

        updateButton.setOnAction(e -> {
            boolean isValidInput = true;
            String errorMsg = "";
            long code = 0;
            int qty = 0;
            double buyingPrice = 0.0, sellingPrice = 0.0;

            try {
                code = Long.parseLong(codeTf.getText());
            } catch (NumberFormatException ex) {
                errorMsg += "- Le code doit être un entier\r\n";
                isValidInput = false;
            }
            String label = labelTf.getText();
            if (label.trim().isEmpty()) {
                errorMsg += "- Désignation est vide!\r\n";
                isValidInput = false;
            }

            /*try {
                qty = Integer.parseInt(qtyTf.getText());
                if (qty <= 0) {
                    errorMsg += "- La qunatité doit être superieure à 0\r\n";
                    isValidInput = false;
                }
            } catch (NumberFormatException ex) {
                errorMsg += "- La qunatité doit être un entier\r\n";
                isValidInput = false;
            }*/

            try {
                buyingPrice = Double.parseDouble(buyingPriceTf.getText());
                if (buyingPrice <= 0) {
                    errorMsg += "- Le prix d'achat doit être superieur à 0\r\n";
                    isValidInput = false;
                }
            } catch (NumberFormatException ex) {
                errorMsg += "- Le prix d'achat doit être un réel\r\n";
                isValidInput = false;
            }
            try {
                sellingPrice = Double.parseDouble(sellingPriceTf.getText());
                if (sellingPrice <= 0) {
                    errorMsg += "- Le prix de vente doit être superieur à 0\r\n";
                    isValidInput = false;
                }
            } catch (NumberFormatException ex) {
                errorMsg += "- Le prix de vente doit être un reel\r\n";
                isValidInput = false;
            }

            String image = new File(URI.create(imageView.getImage().getUrl())).getAbsolutePath();
            Category category = categoriesCb.getValue();
            if (category == null) {
                errorMsg += "- Aucune categorie est selectionnée\r\n";
                isValidInput = false;
            }

            if (isValidInput) {
                productDao.update(new Product(code, label, qty, buyingPrice, sellingPrice, image, category));
                AppController.getRoot().setCenter(getProductPane(productDao.findOne(product.getCode())));
            } else {
                GUITools.openDialogOk(null, null, errorMsg, Alert.AlertType.WARNING);
            }
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

    public static GridPane showProducts(ObservableList<Product> products){
        GridPane productsGrid = new GridPane();

        Circle roundedImages[] = new Circle[products.size()];
        Rectangle rectangles[] = new Rectangle[products.size()];
        Image productImages[] = new Image[products.size()];
        Label designation[] = new Label[products.size()];
        Label quantity[] = new Label[products.size()];
        Label buyingPrice[] = new Label[products.size()];
        Label sellingPrice[] = new Label[products.size()];
        Label category[] = new Label[products.size()];
        HBox hBoxes[] = new HBox[products.size()];
        int columnIndex = 0, rowIndex = 0;

        for (int i = 0; i < products.size();i++){
            productImages[i] = GUITools.getImage(products.get(i).getImage());
            roundedImages[i] = new Circle(50);
            roundedImages[i].getStyleClass().add("image");
            roundedImages[i].setFill(new ImagePattern(productImages[i]));
            designation[i] = new Label();
            designation[i].setText(products.get(i).getLabel());
            quantity[i] = new Label();
            quantity[i].setText(products.get(i).getQty() + "");
            buyingPrice[i] = new Label();
            buyingPrice[i].setText(products.get(i).getBuyingPrice() + "");
            sellingPrice[i] = new Label();
            sellingPrice[i].setText(products.get(i).getSellingPrice() + "");
            category[i] = new Label();
            category[i].setText(products.get(i).getCategory().getTitle());
            hBoxes[i] = new HBox();
            hBoxes[i].setSpacing(15);
            hBoxes[i].getChildren().addAll(roundedImages[i], designation[i], quantity[i], buyingPrice[i], sellingPrice[i], category[i]);
            productsGrid.add(hBoxes[i], columnIndex, rowIndex);
            columnIndex++;
            hBoxes[i].setAlignment(Pos.CENTER_LEFT);
            if (columnIndex == 2){
                rowIndex++;
                columnIndex = 0;
                hBoxes[i].setAlignment(Pos.CENTER_RIGHT);
            }
        }

        productsGrid.setAlignment(Pos.CENTER_LEFT);
        productsGrid.setPadding(new Insets(15));

        return productsGrid;
    }
}