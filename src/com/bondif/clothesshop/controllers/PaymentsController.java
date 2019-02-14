package com.bondif.clothesshop.controllers;

import com.bondif.clothesshop.core.PaymentDaoImpl;
import com.bondif.clothesshop.core.PaymentMethod;
import com.bondif.clothesshop.models.Payment;
import com.bondif.clothesshop.views.ActionButtonTableCell;
import com.bondif.clothesshop.views.CardPaymentView;
import com.bondif.clothesshop.views.ChequePaymentView;
import com.bondif.clothesshop.views.GUITools;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.time.LocalDateTime;
import java.util.Collection;

public class PaymentsController {
    private static ObservableList<Payment> paymentsOl;
    private static double calculatedTotal;
    private static Label totalVal;
    private static TextField amountTf;
    private static Button addPaymentBtn;
    private static PaymentDaoImpl paymentDao;
    private static ComboBox<PaymentMethod> paymentMethodsCb;
    private static HBox paymentAdvanceHb;
    private static CardPaymentView cardPaymentView;
    private static ChequePaymentView chequePaymentView;

    static {
        paymentDao = new PaymentDaoImpl();
        paymentsOl = null;
        calculatedTotal = 0.0;
        totalVal = new Label(calculatedTotal + " Dhs");
        amountTf = new TextField();
        addPaymentBtn = new Button("Ajouter");
        paymentMethodsCb = new ComboBox<>();
        paymentAdvanceHb = new HBox();
        cardPaymentView = new CardPaymentView();
        chequePaymentView = new ChequePaymentView();
    }

    public static void addPayments(Collection<Payment> payments) {
        for (Payment payment : payments) paymentDao.create(payment);
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
        Label advance = new Label("Avance ");
        VBox cardPaymentBox = (VBox)cardPaymentView.render();
        VBox chequePaymentBox = (VBox)chequePaymentView.render();
        amountTf.setDisable(false);
        addPaymentBtn.setDisable(true);

        Label choosePaymentMethod = new Label("Methode de paiement : ");
        paymentMethodsCb.getItems().setAll(PaymentMethod.values());
        paymentMethodsCb.setValue(PaymentMethod.CASH);

        addPaymentBtn.setOnAction(e -> {
            double parsedAmount = 0;
            try {
                parsedAmount = Double.parseDouble(amountTf.getText());
            } catch (NumberFormatException e1) {
                amountTf.clear();
                GUITools.openDialogOk("Erreur", null, "Veuillez enterer un numéro dans le champ du montant", Alert.AlertType.ERROR);
                return;
            }
            if (parsedAmount > calculatedTotal) {
                GUITools.openDialogOk("Erreur", null, "Impossible de payer un montant plus grand que le total", Alert.AlertType.WARNING);
                return;
            }
            amountTf.clear();
            Payment payment = new Payment(0, parsedAmount, PaymentMethod.DRAFTS, LocalDateTime.now(), null);
            getPaymentsOl().add(payment);
        });
        paymentAdvanceHb = new HBox(advance, amountTf);

        paymentMethodsCb.setOnAction(e -> {
            if (paymentMethodsCb.getValue() != null && paymentMethodsCb.getValue().equals(PaymentMethod.DRAFTS)) {
                vBox.getChildren().add(paymentAdvanceHb);
            } else {
                vBox.getChildren().remove(paymentAdvanceHb);
            }
            if (paymentMethodsCb.getValue() != null && paymentMethodsCb.getValue().equals(PaymentMethod.ONLINE)) {
                vBox.getChildren().add(cardPaymentBox);
            } else {
                vBox.getChildren().remove(cardPaymentBox);
            }
            if (paymentMethodsCb.getValue() != null && paymentMethodsCb.getValue().equals(PaymentMethod.CHECK)) {
                vBox.getChildren().add(chequePaymentBox);
            } else {
                vBox.getChildren().remove(chequePaymentBox);
            }
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
        paymentsTv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        vBox.getChildren().addAll(new HBox(total, totalVal), new HBox(state, stateVal), new HBox(choosePaymentMethod, paymentMethodsCb));

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
        if (calculatedTotal == 0.0) amountTf.setDisable(true);
        else amountTf.setDisable(false);
    }

    private static void setDisableAddPaymentBtn() {
        if (calculatedTotal == 0.0) addPaymentBtn.setDisable(true);
        else addPaymentBtn.setDisable(false);
    }

    public static ComboBox<PaymentMethod> getPaymentMethodsCb() {
        return paymentMethodsCb;
    }

    public static TextField getAmountTf() {
        return amountTf;
    }

    public static Label getTotalVal() {
        return totalVal;
    }

    public static CardPaymentView getCardPaymentView() {
        return cardPaymentView;
    }

    public static ChequePaymentView getChequePaymentView() {
        return chequePaymentView;
    }
}
