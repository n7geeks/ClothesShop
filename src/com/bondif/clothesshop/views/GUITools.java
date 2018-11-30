package com.bondif.clothesshop.views;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.awt.dnd.DragSourceContext;
import java.text.ParseException;
import java.util.Optional;

import java.io.File;

public class GUITools {
    public static Image getImage(String path) {
        File file = new File(path);
        Image image = new Image(file.toURI().toString());

        return image;
    }

    public static Button getButton(Image icon, String text, double width) {
        ImageView imageView = new ImageView(icon);
        imageView.setFitWidth(20.0);
        imageView.setPreserveRatio(true);

        Button button = new Button(text, imageView);
        button.setMinWidth(width);
        button.setAlignment(Pos.CENTER_LEFT);

        return button;
    }

    public static Alert messageBox (Alert.AlertType type, String title, String header, String content){
        Alert msgBox = new Alert(type);
        msgBox.setTitle(title);
        msgBox.setHeaderText(header);
        msgBox.setContentText(content);
        return msgBox;
    }

    public static boolean openDialogYesNo(String title, String header, String message, Alert.AlertType at) {
        Alert alert = new Alert(at);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);

        ButtonType buttonTypeOne = new ButtonType("Oui");
        ButtonType buttonTypeTwo = new ButtonType("Non");

        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == buttonTypeOne;
    }

    public static boolean openDialogOk(String title, String header, String message, Alert.AlertType at) {
        Alert alert = new Alert(at);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);

        ButtonType buttonTypeTwo = new ButtonType("Ok");

        alert.getButtonTypes().setAll(buttonTypeTwo);

        Optional<ButtonType> result = alert.showAndWait();
        return true;
    }

    public static int openQtyTextInputDialog() {
        TextInputDialog dialog = new TextInputDialog("1");
        dialog.setTitle("Quantité");
        dialog.setHeaderText(null);
        dialog.setContentText("Entrer la quantité : ");
        dialog.setOnCloseRequest(e-> dialog.hide());
        while(true) {
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                try {
                    int qty = Integer.parseInt(result.get());
                    if(qty <= 0) continue;

                    return qty;
                } catch (NumberFormatException e) {
                    // nothing
                }
            } else break;
        }

        return -1;
    }
}
