package com.bondif.clothesshop.views;

import com.bondif.clothesshop.controllers.AdminController;
import com.bondif.clothesshop.controllers.AppController;
import com.bondif.clothesshop.models.Admin;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GUIGenerator {
    private static double dragAnchorX;
    private static double dragAnchorY;

    //getSideBar() used to get the left bar inside the app containing the admin and buttons panels
    public static VBox getSideBar() {
        VBox sideBar = new VBox();

        sideBar.setMinWidth(185);
        sideBar.setBackground(new Background(new BackgroundFill(Paint.valueOf("#eee"), null, null)));
        //sideBar.setSpacing(15);

        VBox adminPart = AdminController.getAdminPanel();
        // insert admin image
        ImageView avatar = new ImageView();

        avatar.setImage(GUITools.getImage("resources/admin.png"));
        avatar.setFitWidth(100.0);
        avatar.setPreserveRatio(true);

        /*adminPart.getChildren().add(avatar);
        adminPart.setPadding(new Insets(10, 10, 40, 10));
        adminPart.setStyle("-fx-alignment: center");*/

        Text adminName = new Text("Admin");

        //adminPart.getChildren().add(adminName);

        Button dashboardBtn = getSideBarButton(GUITools.getImage("resources/icons/home-page-56.png"), "Tableau de bord", sideBar.getMinWidth());
        Button productsBtn = getSideBarButton(GUITools.getImage("resources/icons/product-56.png"), "Produits", sideBar.getMinWidth());
        Button clientsBtn = getSideBarButton(GUITools.getImage("resources/icons/client-management-56.png"), "Clients", sideBar.getMinWidth());
        Button sellsBtn = getSideBarButton(GUITools.getImage("resources/icons/sell-56.png"), "Ventes", sideBar.getMinWidth());
        Button logOutBtn = getSideBarButton(GUITools.getImage("resources/icons/log-out-56.png"), "Se déconnecter", sideBar.getMinWidth());

        dashboardBtn.getStyleClass().addAll("leftSideButton", "setFont");
        productsBtn.getStyleClass().addAll("leftSideButton", "setFont");
        clientsBtn.getStyleClass().addAll("leftSideButton", "setFont");
        sellsBtn.getStyleClass().addAll("leftSideButton", "setFont");
        logOutBtn.getStyleClass().addAll("leftSideButton", "setFont");
        dashboardBtn.getStyleClass().remove("button");
        productsBtn.getStyleClass().remove("button");
        clientsBtn.getStyleClass().remove("button");
        sellsBtn.getStyleClass().remove("button");
        logOutBtn.getStyleClass().remove("button");

        dashboardBtn.setOnAction(event ->  {
            AppController.showDashboard();

        });

        //dashboardBtn.setStyle("-fx-background-color: white; -fx-border-color: #F5DA17; -fx-border-width: 2px; -fx-border-style: solid; -fx-text-fill: black;");
        productsBtn.setOnAction(event -> {
            AppController.showProducts();
        });

        clientsBtn.setOnAction(event -> {
            AppController.showCustomers();
        });

        sellsBtn.setOnAction(event ->  {
            AppController.showSales();
        });

        logOutBtn.setOnAction(event -> {
            AppController.getStage().setWidth(450);
            AppController.getStage().setHeight(620);
            AppController.getScene().setRoot(AdminController.getLoginInterface());
            AppController.getStage().centerOnScreen();
        });

        sideBar.getChildren().add(adminPart);
        sideBar.getChildren().addAll(dashboardBtn, productsBtn, clientsBtn, sellsBtn, logOutBtn);

        return sideBar;
    }

    //getTopBar() used to generate the top bar of the application
    public static HBox getTopBar() {
        HBox topBar = new HBox();
        HBox windowEvents = new HBox();
        ImageView closeIcon = new ImageView();
        ImageView maximizeIcon = new ImageView();
        ImageView minimizeIcon = new ImageView();

        topBar.setBackground(new Background(new BackgroundFill(Paint.valueOf("#488b8f"), null, null)));
        topBar.setMinHeight(40);
        topBar.setAlignment(Pos.CENTER);

        //Mouse pressed event used for dragging control
        topBar.setOnMousePressed((MouseEvent me) -> {
            dragAnchorX = me.getScreenX() - AppController.getStage().getX();
            dragAnchorY = me.getScreenY() - AppController.getStage().getY();
        });

        //Mouse dragged event used for dragging control
        topBar.setOnMouseDragged((MouseEvent me) -> {
            AppController.getStage().setX(me.getScreenX() - dragAnchorX);
            AppController.getStage().setY(me.getScreenY() - dragAnchorY);
        });

        //Set the stage maximized when the double click event happened
        topBar.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2){
                if (AppController.getStage().isMaximized()){
                    AppController.getStage().setMaximized(false);
                }else{
                    AppController.getStage().setMaximized(true);
                }
            }
        });

        //Set images for the window events
        closeIcon.setImage(GUITools.getImage("resources/icons/close-empty.png"));
        maximizeIcon.setImage(GUITools.getImage("resources/icons/maximize-empty.png"));
        minimizeIcon.setImage(GUITools.getImage("resources/icons/minimize-empty.png"));

        windowEvents.setSpacing(7);
        windowEvents.setPadding(new Insets(10,10,10,10));

        windowEvents.getChildren().addAll(maximizeIcon, minimizeIcon, closeIcon);

        // region used to set set spacing between element inside a HBox (May be Node)
        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);

        //Closing window event
        closeIcon.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY){
                AppController.getStage().close();
            }
        });

        closeIcon.setOnMouseEntered(event -> {
            closeIcon.setImage(GUITools.getImage("resources/icons/close.png"));
        });

        closeIcon.setOnMouseExited(event -> {
            closeIcon.setImage(GUITools.getImage("resources/icons/close-empty.png"));
        });

        //Maximizing window event
        maximizeIcon.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY){
                if (AppController.getStage().isMaximized()){
                    AppController.getStage().setMaximized(false);
                }else{
                    AppController.getStage().setMaximized(true);
                }
            }
        });

        maximizeIcon.setOnMouseEntered(event -> {
            if (AppController.getStage().isMaximized()){
                maximizeIcon.setImage(GUITools.getImage("resources/icons/restore.png"));
            }else{
                maximizeIcon.setImage(GUITools.getImage("resources/icons/maximize.png"));
            }
        });

        maximizeIcon.setOnMouseExited(event -> {
            maximizeIcon.setImage(GUITools.getImage("resources/icons/maximize-empty.png"));
        });

        //Minimizing window event
        minimizeIcon.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY){
                AppController.getStage().setIconified(true);
            }
        });

        minimizeIcon.setOnMouseEntered(event -> {
            minimizeIcon.setImage(GUITools.getImage("resources/icons/minimize.png"));
        });

        minimizeIcon.setOnMouseExited(event -> {
            minimizeIcon.setImage(GUITools.getImage("resources/icons/minimize-empty.png"));
        });

        //App title
        Text appName = new Text("   Magasin de vêtements");
        appName.setFill(Color.WHITE);
        appName.setFont(new Font("Century Gothic", 20));

        //Adding controls into the top bar
        topBar.getChildren().addAll(appName, region, windowEvents);

        return topBar;
    }

    private static Button getSideBarButton(Image icon, String text, double width) {
        return GUITools.getButton(icon, text, width);
    }
}
