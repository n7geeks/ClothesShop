package com.bondif.clothesshop.views;

import com.bondif.clothesshop.controllers.AppController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GUIGenerator {
    public static VBox getSideBar() {
        VBox sideBar = new VBox();

        sideBar.setMinWidth(185);
        sideBar.setBackground(new Background(new BackgroundFill(Paint.valueOf("#eee"), null, null)));
        sideBar.setSpacing(15);
        VBox adminPart = new VBox();
        // insert admin image
        ImageView avatar = new ImageView();
        avatar.setImage(GUITools.getImage("resources/admin.png"));

        avatar.setFitWidth(100.0);
        avatar.setPreserveRatio(true);

        adminPart.getChildren().add(avatar);
        adminPart.setPadding(new Insets(10));
        adminPart.setStyle("-fx-alignment: center");

        Text adminName = new Text("Admin");
        adminPart.getChildren().add(adminName);

        Button dashboardBtn = getSideBarButton(GUITools.getImage("resources/icons/home-page-40.png"), "Tableau de bord", sideBar.getMinWidth());
        Button productsBtn = getSideBarButton(GUITools.getImage("resources/icons/product-40.png"), "Produits", sideBar.getMinWidth());
        Button clientsBtn = getSideBarButton(GUITools.getImage("resources/icons/customer-40.png"), "Clients", sideBar.getMinWidth());
        Button sellsBtn = getSideBarButton(GUITools.getImage("resources/icons/sell-40.png"), "Ventes", sideBar.getMinWidth());

        dashboardBtn.setOnAction(event ->  {
            AppController.showDashboard();
        });

        productsBtn.setOnAction(event ->  {
            AppController.showProducts();
        });

        clientsBtn.setOnAction(event -> {
            AppController.showCustomers();
        });

        sellsBtn.setOnAction(event ->  {
            AppController.showSales();
        });

        sideBar.getChildren().add(adminPart);
        sideBar.getChildren().addAll(dashboardBtn, productsBtn, clientsBtn, sellsBtn);

        return sideBar;
    }

    public static HBox getTopBar() {
        HBox topBar = new HBox();
        HBox windowEvents = new HBox();
        ImageView closeIcon = new ImageView();
        ImageView maximizeIcon = new ImageView();
        ImageView minimizeIcon = new ImageView();
        ImageView maximizeArrows = new ImageView();
        ImageView minimizeDash = new ImageView();
        ImageView closeCross = new ImageView();

        topBar.setBackground(new Background(new BackgroundFill(Paint.valueOf("#488b8f"), null, null)));
        topBar.setMinHeight(40);
        topBar.setAlignment(Pos.CENTER);

        closeIcon.setImage(GUITools.getImage("resources/icons/close-24.png"));
        maximizeIcon.setImage(GUITools.getImage("resources/icons/maximize-24.png"));
        minimizeIcon.setImage(GUITools.getImage("resources/icons/minimize-24.png"));

        /*maximizeArrows.setImage(GUITools.getImage("resources/icons/close-cross-15.png"));
        minimizeDash.setImage(GUITools.getImage(""));
        closeCross.setImage(GUITools.getImage(""));*/

        windowEvents.setSpacing(7);
        windowEvents.setPadding(new Insets(10,10,10,10));

        windowEvents.getChildren().addAll(maximizeIcon, minimizeIcon, closeIcon);

        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);

        closeIcon.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY){
                AppController.getStage().close();
            }
        });

        maximizeIcon.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY){
                if (AppController.getStage().isMaximized()){
                    AppController.getStage().setMaximized(false);
                }else{
                    AppController.getStage().setMaximized(true);
                }
            }
        });

        minimizeIcon.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY){
                AppController.getStage().setIconified(true);
            }
        });

        minimizeIcon.setOnMouseEntered(event -> {

        });

        minimizeIcon.setOnMouseExited(event -> {

        });

        Text appName = new Text("   Magasin de vêtements");
        appName.setFill(Color.WHITE);
        appName.setFont(new Font("Century Gothic", 20));

        topBar.getChildren().addAll(appName, region, windowEvents);

        return topBar;
    }

    private static Button getSideBarButton(Image icon, String text, double width) {
        return GUITools.getButton(icon, text, width);
    }
}
