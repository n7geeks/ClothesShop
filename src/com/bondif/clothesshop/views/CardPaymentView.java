package com.bondif.clothesshop.views;

import com.bondif.clothesshop.models.CardType;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.Collection;
import java.util.LinkedList;

public class CardPaymentView {
    private Label cardTypeLabel = new Label("Type de carte : ");
    private Label cardNumberLabel = new Label("Numéro de carte : ");
    private Label expirationDateLabel = new Label("Date d'expiration : ");
    private Label verificationCodeLabel = new Label("Code de vérification : ");
    private ComboBox<CardType> cardTypesCb = new ComboBox<>();
    private ComboBox<Integer> expMonthCb = new ComboBox<>();
    private ComboBox<Integer> expYearCb = new ComboBox<>();
    private TextField cardNumberField = new TextField();
    private TextField verificationCardField = new TextField();

    public CardPaymentView() {
        cardTypesCb.setItems(FXCollections.observableArrayList(CardType.values()));
        expMonthCb.setItems(FXCollections.observableArrayList(this.getMonths()));
        expYearCb.setItems(FXCollections.observableArrayList(this.getYears()));
    }

    public Pane render() {
        VBox container = new VBox();

        HBox cardTypeBox = new HBox(cardTypeLabel, cardTypesCb);
        HBox cardNumBox = new HBox(cardNumberLabel, cardNumberField);
        HBox expDateBox = new HBox(expirationDateLabel, new HBox(expMonthCb, expYearCb));
        HBox verificationCodeBox = new HBox(verificationCodeLabel, verificationCardField);

        container.getChildren().addAll(cardTypeBox, cardNumBox, expDateBox, verificationCodeBox);

        return container;
    }

    public ComboBox<CardType> getCardTypesCb() {
        return cardTypesCb;
    }

    public ComboBox<Integer> getExpMonthCb() {
        return expMonthCb;
    }

    public ComboBox<Integer> getExpYearCb() {
        return expYearCb;
    }

    public TextField getCardNumberField() {
        return cardNumberField;
    }

    public TextField getVerificationCardField() {
        return verificationCardField;
    }

    private Collection<Integer> getMonths() {
        Collection<Integer> months = new LinkedList<>();

        for (int i = 1; i <= 12; i++) {
            months.add(i);
        }

        return months;
    }

    private Collection<Integer> getYears() {
        Collection<Integer> years = new LinkedList<>();

        for (int i = 19; i <= 25; i++) {
            years.add(2000 + i);
        }

        return years;
    }
}
