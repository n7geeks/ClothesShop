package com.bondif.clothesshop.views;

import com.bondif.clothesshop.controllers.AppController;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

public class GUIGenerator {
    public static VBox getSideBar() {
        VBox sideBar = new VBox();
        sideBar.setMinWidth(150);
        sideBar.setBackground(new Background(new BackgroundFill(Paint.valueOf("#eee"), null, null)));
        VBox adminPart = new VBox();
        // insert admin image
        ImageView avatar = new ImageView();
        avatar.setImage(GUITools.getImage("resources/admin.jpg"));

        avatar.setFitWidth(100.0);
        avatar.setPreserveRatio(true);

        adminPart.getChildren().add(avatar);
        adminPart.setPadding(new Insets(10));
        adminPart.setStyle("-fx-alignment: center");

        Text adminName = new Text("Admin");
        adminPart.getChildren().add(adminName);

        Button dashboardBtn = getSideBarButton(GUITools.getImage("resources/admin.jpg"), "Tableau de bord", sideBar.getMinWidth());
        Button productsBtn = getSideBarButton(GUITools.getImage("resources/admin.jpg"), "Produits", sideBar.getMinWidth());
        Button clientsBtn = getSideBarButton(GUITools.getImage("resources/admin.jpg"), "Clients", sideBar.getMinWidth());
        Button sellsBtn = getSideBarButton(GUITools.getImage("resources/admin.jpg"), "Ventes", sideBar.getMinWidth());

        dashboardBtn.setOnAction(event ->  {
            AppController.showDashboard();
        });

        productsBtn.setOnAction(event ->  {
            AppController.showProducts();
        });

        sideBar.getChildren().add(adminPart);
        sideBar.getChildren().addAll(dashboardBtn, productsBtn, clientsBtn, sellsBtn);

        return sideBar;
    }

    public static HBox getTopBar() {
        HBox topBar = new HBox();

        topBar.setBackground(new Background(new BackgroundFill(Paint.valueOf("#488b8f"), null, null)));

        Text appName = new Text("Clothes Shop");

        topBar.getChildren().add(appName);

        return topBar;
    }

    private static Button getSideBarButton(Image icon, String text, double width) {
        return GUITools.getButton(icon, text, width);
    }
}
