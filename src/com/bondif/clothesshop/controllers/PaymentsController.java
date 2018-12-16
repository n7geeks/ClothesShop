package com.bondif.clothesshop.controllers;

import com.bondif.clothesshop.core.PaymentDaoImpl;
import com.bondif.clothesshop.models.Payment;
import com.bondif.clothesshop.views.ActionButtonTableCell;
import com.bondif.clothesshop.views.GUITools;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.time.LocalDateTime;

public class PaymentsController {
    private static ObservableList<Payment> paymentsOl;
    private static double calculatedTotal;
    private static Label totalVal;
    private static TextField amountTf;
    private static Button addPaymentBtn;

    static {
        paymentsOl = null;
        calculatedTotal = 0.0;
        totalVal = new Label(calculatedTotal + " Dhs");
        amountTf = new TextField();
        addPaymentBtn = new Button("Ajouter");
    }

    public static TableView<Payment> getBasicTv() {
        TableView<Payment> paymentsTv = new TableView<>();

        // CreatedAt column
        TableColumn<Payment, LocalDateTime> createdAtCol = new TableColumn<>("Date");
        createdAtCol.setCellValueFactory(new PropertyValueFactory<>("createdAt"));

        // Amount column
        TableColumn<Payment, Double> amountCol = new TableColumn<>("Montant (Dh)");
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));

        paymentsTv.getColumns().addAll(createdAtCol, amountCol);

        return paymentsTv;
    }

    public static Pane getPaymentsForm() {
        VBox vBox = new VBox();

        Label total = new Label("Total : ");
        Label state = new Label("Etat : ");
        Label stateVal = new Label("En cours");
        Label amount = new Label("Montant");
        amountTf.setDisable(true);
        addPaymentBtn.setDisable(true);

        addPaymentBtn.setOnAction(e -> {
            double parsedAmount;
            try {
                parsedAmount = Double.parseDouble(amountTf.getText());
            } catch (NumberFormatException e1) {
                amountTf.clear();
                GUITools.openDialogOk("Erreur", null, "Veuillez enterer un numéro dans le champ du montant", Alert.AlertType.ERROR);
                return;
            }
            if(parsedAmount > calculatedTotal) {
                GUITools.openDialogOk("Erreur", null, "Impossible de payer un montant plus grand que le total", Alert.AlertType.WARNING);
                return;
            }
            amountTf.clear();
            Payment payment = new Payment(0, parsedAmount, LocalDateTime.now(), null);
            getPaymentsOl().add(payment);
        });

        TableView<Payment> paymentsTv = getBasicTv();

        // Remove column
        TableColumn removePaymentCol = new TableColumn<>("Retirer");
        removePaymentCol.setCellFactory(ActionButtonTableCell.forTableColumn("Retirer", (Payment payment) -> {
            paymentsOl.remove(payment);
            return payment;
        }));

        paymentsTv.getColumns().add(removePaymentCol);
        paymentsOl = getPaymentsOl();
        paymentsTv.setItems(paymentsOl);

        vBox.getChildren().addAll(new HBox(total, totalVal), new HBox(state, stateVal), new HBox(amount, amountTf, addPaymentBtn), paymentsTv);

        paymentsTv.getColumns().get(0).prefWidthProperty().bind(paymentsTv.widthProperty().divide(100 / 20));
        paymentsTv.getColumns().get(1).prefWidthProperty().bind(paymentsTv.widthProperty().divide(100 / 50));
        paymentsTv.getColumns().get(2).prefWidthProperty().bind(paymentsTv.widthProperty().divide(100 / 30));

        return vBox;
    }

    public static ObservableList<Payment> getPaymentsOl() {
        return paymentsOl == null ? FXCollections.observableArrayList(new PaymentDaoImpl().findAll()) : paymentsOl;
    }

    public static double getCalculatedTotal() {
        double t = 0;
        for (Payment payment : paymentsOl)
            t += payment.getAmount();
        System.out.println(t);
        return t;
    }

    public static void addToTotal(double amount) {
        System.out.println("+" + amount);
        PaymentsController.calculatedTotal += amount;
        setTotalVal();
        setDisableAmountTf();
        setDisableAddPaymentBtn();
    }

    public static void removeFromTotal(double amount) {
        System.out.println("-" + amount);
        PaymentsController.calculatedTotal -= amount;
        setTotalVal();
        setDisableAmountTf();
        setDisableAddPaymentBtn();
    }

    private static void setTotalVal() {
        PaymentsController.totalVal.setText(calculatedTotal + " Dh");
    }

    private static void setDisableAmountTf() {
        if(calculatedTotal == 0.0) amountTf.setDisable(true);
        else amountTf.setDisable(false);
    }

    private static void setDisableAddPaymentBtn() {
        if(calculatedTotal == 0.0) addPaymentBtn.setDisable(true);
        else addPaymentBtn.setDisable(false);
    }
}
