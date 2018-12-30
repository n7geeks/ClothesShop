package com.bondif.clothesshop.controllers;

import com.bondif.clothesshop.core.AdminDaoImpl;
import com.bondif.clothesshop.core.BCrypt;
import com.bondif.clothesshop.models.Admin;
import com.bondif.clothesshop.views.GUIGenerator;
import com.bondif.clothesshop.views.GUITools;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;


import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.KeySpec;

public class AdminController {
    private static AdminDaoImpl adminDao;
    public static ObservableList<Admin> AdminsOl;
    public static long id;

    static {
        adminDao = new AdminDaoImpl();
    }

    public static BorderPane getLoginInterface(){
        AdminsOl = FXCollections.observableArrayList(adminDao.findAll());
        BorderPane pane = new BorderPane();
        HBox header = GUIGenerator.getTopBar();
        VBox loginInterface = new VBox();
        Circle loginIcon = new Circle(50);
        Image img = GUITools.getImage("resources/icons/login-icon-100.png");
        Label loginMessage = new Label();
        TextField emailTxt = new TextField();
        PasswordField passwordTxt = new PasswordField();
        CheckBox showPassword = new CheckBox("Show/Hide password");
        HBox buttons = new HBox();
        Button loginBtn = new Button("LOGIN");
        Button signUpBtn = new Button("SIGN UP");

        //Styling the interface
        loginInterface.setSpacing(40);
        /*loginInterface.setMinHeight(620);
        loginInterface.setMinWidth(450);*/

        pane.setMinHeight(620);
        pane.setMinWidth(450);

        loginIcon.setFill(new ImagePattern(img));
        loginIcon.setTranslateX(180 - loginIcon.getRadius());
        loginIcon.setTranslateY(40);

        buttons.setSpacing(15);
        buttons.setAlignment(Pos.CENTER_RIGHT);
        buttons.getChildren().addAll(loginBtn, signUpBtn);

        //Adding styles
        loginInterface.getStyleClass().add("loginInterface");
        loginMessage.getStyleClass().add("msgLbl");
        emailTxt.getStyleClass().remove("text-field");
        emailTxt.getStyleClass().add("logTxt");
        emailTxt.setPromptText("Your email/username");
        passwordTxt.getStyleClass().remove("text-field");
        passwordTxt.getStyleClass().add("logTxt");
        passwordTxt.setPromptText("Your password");
        showPassword.getStyleClass().add("checkbox");
        loginBtn.getStyleClass().remove("Button");
        loginBtn.getStyleClass().add("loginBtn");
        signUpBtn.getStyleClass().remove("Button");
        signUpBtn.getStyleClass().add("loginBtn");

        //Adding controllers to the interface
        loginInterface.getChildren().addAll(loginIcon, loginMessage, emailTxt, passwordTxt, showPassword, buttons);
        //login button event
        loginBtn.setOnAction(event -> {
            //Something to do
            if (adminDao.findAdmin(emailTxt.getText())) {
                if (BCrypt.checkpw(passwordTxt.getText(), adminDao.getPassword(emailTxt.getText()))) {
                    id = adminDao.findAdminId(emailTxt.getText());
                    AppController.getRoot().setLeft(GUIGenerator.getSideBar());
                    AppController.getRoot().getLeft().getStyleClass().add("left");
                    AppController.getStage().setWidth(1000);
                    AppController.getStage().setHeight(600);
                    AppController.getScene().setRoot(AppController.getRoot());
                    AppController.getStage().centerOnScreen();
                }else {
                    System.out.println("Wrong password");
                }
            }else {
                System.out.println("Username not found");
            }
        });

        signUpBtn.setOnAction(event -> {
            AppController.getStage().setWidth(450);
            AppController.getStage().setHeight(620);
            AppController.getScene().setRoot(getSignUpInterface());
            AppController.getStage().centerOnScreen();
        });

        showPassword.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (showPassword.isSelected()){
                    System.out.println("Selected");
                }
            }
        });

        pane.setCenter(loginInterface);
        pane.setTop(header);

        return pane;
    }

    public static BorderPane getSignUpInterface(){
        AdminsOl = FXCollections.observableArrayList(adminDao.findAll());
        BorderPane pane = new BorderPane();
        VBox signUpForm = new VBox();
        HBox header = GUIGenerator.getTopBar();

        Image image = GUITools.getImage("resources/icons/add-admin-100.png");
        Circle roundedImage = new Circle(70);
        TextField firstName = new TextField();
        TextField lastName = new TextField();
        TextField emailTxt = new TextField();
        PasswordField passwordField1 = new PasswordField();
        PasswordField passwordField2 = new PasswordField();
        Button signUp = new Button("SIGN UP");
        Button cancel = new Button("CANCEL");
        HBox hBox = new HBox();

        //Adding styles
        signUpForm.getStyleClass().add("loginInterface");
        signUpForm.setSpacing(25);
        firstName.getStyleClass().remove("text-field");
        firstName.getStyleClass().add("logTxt");
        firstName.setPromptText("First name");
        lastName.getStyleClass().remove("text-field");
        lastName.getStyleClass().add("logTxt");
        lastName.setPromptText("Last name");
        emailTxt.getStyleClass().remove("text-field");
        emailTxt.getStyleClass().add("logTxt");
        emailTxt.setPromptText("Email/Username");
        passwordField1.getStyleClass().remove("text-field");
        passwordField1.getStyleClass().add("logTxt");
        passwordField1.setPromptText("Password");
        passwordField2.getStyleClass().remove("text-field");
        passwordField2.getStyleClass().add("logTxt");
        passwordField2.setPromptText("Confirm password");
        signUp.getStyleClass().remove("Button");
        signUp.getStyleClass().add("loginBtn");
        cancel.getStyleClass().remove("Button");
        cancel.getStyleClass().add("loginBtn");

        roundedImage.setFill(new ImagePattern(image));
        hBox.getChildren().addAll(cancel, signUp);
        hBox.setAlignment(Pos.CENTER_RIGHT);
        hBox.setSpacing(15);

        pane.setMinHeight(620);
        pane.setMinWidth(450);

        roundedImage.setTranslateX(180 - roundedImage.getRadius());
        Text imagePath = new Text("resources/icons/add-admin-100.png");

        roundedImage.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY){
                String newPath = AppController.chooseProductImageHandler();
                if (newPath != null) {
                    roundedImage.setFill(new ImagePattern(GUITools.getImage(newPath)));
                    imagePath.setText(newPath);
                }
            }
        });

        signUp.setOnAction(event -> {
            if (firstName.getText().isEmpty()) GUITools.openDialogOk("Erreur", null, "Il faut saisir un nom", Alert.AlertType.ERROR);
            else if (lastName.getText().isEmpty()) GUITools.openDialogOk("Erreur", null, "Il faut saisir un prénom", Alert.AlertType.ERROR);
            else if (emailTxt.getText().isEmpty()) GUITools.openDialogOk("Erreur", null, "Il faut saisir un email", Alert.AlertType.ERROR);
            else if (passwordField1.getText().isEmpty()) GUITools.openDialogOk("Erreur", null, "Il faut saisir un mot de passe", Alert.AlertType.ERROR);
            else if (passwordField2.getText().isEmpty()) GUITools.openDialogOk("Erreur", null, "Il faut confirmer le mot de passe", Alert.AlertType.ERROR);
            else if (!passwordField1.getText().equals(passwordField2.getText()))
                GUITools.openDialogOk("Erreur", null, "Les mots de passe ne sont pas compatibles", Alert.AlertType.ERROR);
            else{
                File imgFile;
                FileInputStream fin = null;
                try {
                    imgFile = new File(imagePath.getText());
                    fin = new FileInputStream(imgFile);
                }catch(IOException exp){
                    exp.printStackTrace();
                }

                String hashedPassword = BCrypt.hashpw(passwordField1.getText(), BCrypt.gensalt());

                Admin admin = new Admin(null, firstName.getText(), lastName.getText(), emailTxt.getText(), hashedPassword, fin);
                adminDao.createAdmin(admin);

                AppController.getStage().setWidth(450);
                AppController.getStage().setHeight(620);
                AppController.getScene().setRoot(getLoginInterface());
                AppController.getStage().centerOnScreen();
            }
        });

        cancel.setOnAction(event -> {
            AppController.getStage().setWidth(450);
            AppController.getStage().setHeight(620);
            AppController.getScene().setRoot(getLoginInterface());
            AppController.getStage().centerOnScreen();
        });

        signUpForm.getChildren().addAll(roundedImage, firstName, lastName, emailTxt, passwordField1, passwordField2, hBox);

        pane.setTop(header);
        pane.setCenter(signUpForm);

        return pane;
    }

    public static VBox getAdminPanel(){
        VBox vBox = new VBox();

        Admin admin = adminDao.findAdmin(id);
        Text username = new Text(admin.getFirstName().toUpperCase() + " " + admin.getLastName().toUpperCase());
        Circle roundedImage = new Circle(50);
        vBox.setPadding(new Insets(10, 10, 40, 10));
        vBox.setSpacing(10);
        vBox.setStyle("-fx-alignment: center");

        roundedImage.setFill(new ImagePattern(adminDao.getAdminImage(admin.getEmail())));
        roundedImage.getStyleClass().add("image");
        username.getStyleClass().add("username");

        vBox.getChildren().addAll(roundedImage, username);

        return vBox;
    }
}