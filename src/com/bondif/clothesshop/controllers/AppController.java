package com.bondif.clothesshop.controllers;

import com.bondif.clothesshop.views.GUIGenerator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class AppController {
    public static BorderPane root;

    static {
        System.out.println("lance");
        root = new BorderPane();

        root.setLeft(GUIGenerator.getSideBar());
        root.setTop(GUIGenerator.getTopBar());
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

    public static BorderPane getRoot() {
        return root;
    }
}
