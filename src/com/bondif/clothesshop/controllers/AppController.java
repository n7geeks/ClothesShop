package com.bondif.clothesshop.controllers;

import com.bondif.clothesshop.views.GUIGenerator;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;

public class AppController {
    private static BorderPane root;
    private static Stage stage;
    private static Scene scene;

    static {
        System.out.println("launched");
        root = new BorderPane();
        //root.setStyle("-fx-background-radius: 20");
        //root.setLeft(GUIGenerator.getSideBar());
        root.setTop(GUIGenerator.getTopBar());
        //root.getLeft().setStyle("-fx-background-radius: 0 0 0 20; -fx-background-color: #eee");
        //root.getTop().setStyle("-fx-background-radius: 20 20 0 0; -fx-background-color: #488b8f;");
        //root.getLeft().getStyleClass().add("left");
        root.getTop().getStyleClass().add("top");
        root.getStyleClass().add("root");
    }

    public static Stage getStage(){
        return stage;
    }

    public static Scene getScene() { return scene; }

    public static void setScene(Region pane, float width, float height) {
        scene = new Scene(pane, width, height);
    }

    public static void launch(Stage stage) {
        AppController.stage = stage;
        AppController.stage.setTitle("Clothes Shop");
        //Scene scene = new Scene(AppController.getRoot(), 1000, 600);
        //scene = new Scene(AdminController.getLoginInterface(), 450, 620);
        setScene(AdminController.getLoginInterface(), 450, 620);
        scene.getStylesheets().add("decorateForm.css");
        AppController.stage.setScene(scene);
        scene.setFill(Color.TRANSPARENT);
        AppController.stage.initStyle(StageStyle.TRANSPARENT);
        AppController.stage.show();
    }

    public static void showDashboard(){
        Pane pane = DashboardController.getDashboardPane();
        root.setCenter(pane);
    }

    public static void showProducts(){
        Pane pane = ProductsController.getProductsPane();
        root.setCenter(pane);
    }

    public static void showSales() {
        Pane pane = OrdersController.getSalesPane();
        root.setCenter(pane);
    }

    public static void showCreateProductForm() {
        Pane pane = ProductsController.getCreateForm();
        root.setCenter(pane);
    }

    public static void showCustomers(){
        Pane pane = CustomersController.createCustomerPane();
        root.setCenter(pane);
    }

    public static void showCreateCustomerForm(){
        Pane pane = CustomersController.getCustomerPane();
        root.setCenter(pane);
    }

    public static void showEditCustomerForm(){
        Pane pane = CustomersController.getEditPane();
        root.setCenter(pane);
    }

    public static void showCategoryForm(){
        Pane pane = CategoriesController.getCategoryForm();
        root.setCenter(pane);
    }

    public static void showCreateCategoryForm(){
        Pane pane = CategoriesController.getCreateCategoryForm();
        root.setCenter(pane);
    }

    public static void showSaleCreateForm() {
        Pane pane = OrdersController.getCreateForm();
        root.setCenter(pane);
    }

    public static void showOrder(long id) {
        Pane pane = OrdersController.show(id);
        root.setCenter(pane);
    }

    public static BorderPane getRoot() {
        return root;
    }

    public static String chooseProductImageHandler() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir l'image du produit");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("images", "*.jpeg; *.png; *.jpg")
        );
        File f = fileChooser.showOpenDialog(stage);

        if(f != null) {
            return f.toPath().toString();
        }

        return null;
    }

    private static void configStage() {
        stage.initStyle(StageStyle.UNDECORATED);
    }
}
