package com.bondif.clothesshop;

import com.bondif.clothesshop.controllers.AppController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        AppController.launch(primaryStage);
    }

    public static void main(String[] args) {
        launch();
    }
}
