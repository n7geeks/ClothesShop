package com.bondif.clothesshop.controllers;

import com.bondif.clothesshop.models.Product;
import com.bondif.clothesshop.views.GUIGenerator;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;

public class AppController {
    private static BorderPane root;
    private static Stage stage;

    static {
        System.out.println("launched");
        root = new BorderPane();

        root.setLeft(GUIGenerator.getSideBar());
        root.setTop(GUIGenerator.getTopBar());
    }

    public static void launch(Stage stage) {
        AppController.stage = stage;
        configStage();
        AppController.stage.setTitle("Clothes Shop");

        Scene scene = new Scene(AppController.getRoot(), 1000, 600);

        AppController.stage.setScene(scene);
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

    public static void showCreateProductForm() {
        Pane pane = ProductsController.getCreateForm();
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
//        stage.initStyle(StageStyle.UNDECORATED);
    }
}
