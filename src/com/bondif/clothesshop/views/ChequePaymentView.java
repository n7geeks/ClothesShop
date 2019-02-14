package com.bondif.clothesshop.views;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class ChequePaymentView {
    private Label ownerLabel = new Label("Propriétaire : ");
    private Label effectiveDateLabel = new Label("Date d'effet : ");
    private TextField ownerField = new TextField();
    private TextField effectiveDateField = new TextField();

    public Pane render() {
        Pane container = new VBox();

        container.getChildren().add(new HBox(ownerLabel, ownerField));
        container.getChildren().add(new HBox(effectiveDateLabel, effectiveDateField));

        return container;
    }

    public TextField getOwnerField() {
        return ownerField;
    }

    public TextField getEffectiveDateField() {
        return effectiveDateField;
    }
}
