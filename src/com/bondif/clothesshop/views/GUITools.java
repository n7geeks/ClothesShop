package com.bondif.clothesshop.views;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
}
