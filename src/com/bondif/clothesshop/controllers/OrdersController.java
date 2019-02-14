package com.bondif.clothesshop.controllers;

import com.bondif.clothesshop.core.*;
import com.bondif.clothesshop.models.*;
import com.bondif.clothesshop.views.ActionButtonTableCell;
import com.bondif.clothesshop.views.CardPaymentView;
import com.bondif.clothesshop.views.GUITools;
import com.bondif.clothesshop.views.utils.ComboBoxAutoComplete;
import com.bondif.clothesshop.views.utils.Toast;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.StringTokenizer;

public class OrdersController {
    private static ObservableList<Order> ordersOl;
    private static OrderDaoImpl orderDao;
    private static ComboBox<Customer> customersCb;
    private static TableView<Product> productsTv;
    private static ObservableList<Product> productOl;
    private static Label restValueLabel;
    private static double rest;

    static {
        orderDao = new OrderDaoImpl();
        productOl = null;
        rest = 0.0;
    }

    public static Pane getSalesPane() {
        ordersOl = FXCollections.observableArrayList(orderDao.findAll());

        VBox vBox = new VBox();

        // vbox config
        vBox.setPadding(new Insets(20));
        vBox.setSpacing(10);

        // Add sale button
        String addIconPath = "resources/icons/plus-math-30.png";
        Button addBtn = GUITools.getButton(GUITools.getImage(addIconPath), "Ajouter", 100);
        addBtn.setOnAction(event -> {
            AppController.showSaleCreateForm();
        });

        // Sales Table
        TableView<Order> salesTv = new TableView<>();

        // id column
        TableColumn<Order, Long> idCol = new TableColumn<>("Id");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idCol.prefWidthProperty().bind(salesTv.widthProperty().divide(100 / 5));

        // customer column
        TableColumn<Order, Customer> customerCol = new TableColumn<>("Client");
        customerCol.setCellValueFactory(new PropertyValueFactory<>("customer"));
        customerCol.prefWidthProperty().bind(salesTv.widthProperty().divide(100 / 30));

        // total column
        TableColumn<Order, Double> totalCol = new TableColumn<>("Total");
        totalCol.setCellValueFactory(new PropertyValueFactory<>("total"));
        totalCol.prefWidthProperty().bind(salesTv.widthProperty().divide(100 / 30));

        // createdAt column
        TableColumn<Order, Date> createdAtCol = new TableColumn<>("Date");
        createdAtCol.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        createdAtCol.prefWidthProperty().bind(salesTv.widthProperty().divide(100 / 30));

        salesTv.getColumns().addAll(idCol, customerCol, totalCol, createdAtCol);

        salesTv.setItems(ordersOl);

        salesTv.setRowFactory(tv -> {
            TableRow<Order> saleTr = new TableRow<>();

            saleTr.setOnMouseClicked(e -> {
                if (e.getClickCount() == 2 && !saleTr.isEmpty()) {
                    AppController.showOrder(saleTr.getItem().getId());
                }
            });

            return saleTr;
        });

        vBox.getChildren().addAll(addBtn, salesTv);
        return vBox;
    }

    public static Pane getCreateForm() {
        GridPane container = new GridPane();

        Pane searchSection = getSearchSection();
        Pane productsSection = getProductsSection();
        Pane orderLinesSection = getOrderLinesSection();
        Pane clientSection = getClientSection();
        Pane submitBtnSection = getSubmitBtnSection();
        Pane paymentsSection = PaymentsController.getPaymentsForm();
        Region region = new Region();
        HBox clientsHbox = new HBox();
        HBox submitHbox = new HBox();

        clientsHbox.getChildren().addAll(region, clientSection);
        submitHbox.getChildren().addAll(region, submitBtnSection);

        productsSection.setPadding(new Insets(15));
        orderLinesSection.setPadding(new Insets(15));
        clientSection.setPadding(new Insets(15));
        submitBtnSection.setPadding(new Insets(15));

        searchSection.setPadding(new Insets(15));
        searchSection.setMaxWidth(200);

//        container.getChildren().addAll(clientsHbox, searchSection, productsSection, orderLinesSection, submitHbox);
        //HBox orderLinesAndPayments = new HBox(orderLinesSection, paymentsSection);
        GridPane orderLinesAndPayments = new GridPane();
        orderLinesAndPayments.add(orderLinesSection, 0, 0);
        orderLinesAndPayments.add(paymentsSection, 0, 1);

        container.add(clientsHbox, 0, 0);
        container.add(searchSection, 0, 1);
        container.add(productsSection, 0, 2, 1, 3);
        container.add(submitBtnSection, 0, 5, 2, 1);

        container.add(orderLinesAndPayments, 1, 0, 1, 5);

        return container;
    }

    private static Pane getClientSection() {
        CustomerDaoImpl customerDao = new CustomerDaoImpl();
        HBox hBox = new HBox();
        String promptText = "Choisissez un client";
        //Label clientLabel = new Label("Client : ");
        customersCb = new ComboBox<>(FXCollections.observableArrayList(customerDao.findAll()));
        customersCb.setPromptText(promptText);
        TextField selectedText = new TextField();
        //hBox.getChildren().addAll(selectedText, customersCb);
        hBox.getChildren().addAll(customersCb);

        //customersCb.setEditable(true);

        customersCb.setTooltip(new Tooltip());
        new ComboBoxAutoComplete<Customer>(customersCb);

        /*selectedText.textProperty().addListener((observable, oldValue, newValue) -> {
            if (customersCb.getEditor().getText().isEmpty()){
                customersCb.setItems(FXCollections.observableArrayList(customerDao.findAll()));
            }else{
                if (customersCb.getItems().size() != 0){
                    customersCb.show();
                    customersCb.setItems(FXCollections.observableArrayList(customerDao.findAll(customersCb.getEditor().getText())));
                }else{
                    customersCb.hide();
                }
            }
        });*/
        /*
        customersCb.getEditor().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println("(" + oldValue + ")-->(" + newValue + ")");
                if (newValue != "" && newValue.charAt(newValue.length() - 1) != ' ') {
                    if (customersCb.getEditor().getText().isEmpty()){
                        customersCb.setItems(FXCollections.observableArrayList(customerDao.findAll()));
                    }else{
                        if (customersCb.getItems().size() != 0){
                            customersCb.show();
                            customersCb.setItems(FXCollections.observableArrayList(customerDao.findAll(customersCb.getEditor().getText())));
                        }else{
                            customersCb.hide();
                        }
                    }
                }
            }
        });
        */

        return new VBox(hBox);
    }

    private static Pane getOrderLinesSection() {
        TableView<OrderLine> orderLinesTv = OrderLinesController.getSaleOrderLinesTv(true);
        orderLinesTv.setMaxHeight(400);
        orderLinesTv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        return new VBox(orderLinesTv);
    }

    private static TableView<Product> getMinimalProductsTv() {
        TableView<Product> productsTableView = new TableView<>();

        // label column
        TableColumn<Product, String> labelColumn = new TableColumn<>("Désignation");
        labelColumn.setCellValueFactory(new PropertyValueFactory<>("label"));

        // sellPrice column
        TableColumn<Product, Double> sellPriceColumn = new TableColumn<>("Prix de vente");
        sellPriceColumn.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));

        productsTableView.getColumns().addAll(labelColumn, sellPriceColumn);

        return productsTableView;
    }

    private static Pane getProductsSection() {
        productsTv = OrdersController.getMinimalProductsTv();
        productsTv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // "add product to order" column
        TableColumn addProductCol = new TableColumn<>("Ajouter");
        addProductCol.setCellFactory(ActionButtonTableCell.forTableColumn("Ajouter", (Product p) -> {
            int qty = GUITools.openQtyTextInputDialog();
            if (qty == -1) return p;

            OrderLinesController.add(new OrderLine(0, p, p.getSellingPrice(), qty));
            return p;
        }));

        productsTv.getColumns().add(addProductCol);

        productsTv.setItems(getProductsOl());

        return new VBox(productsTv);
    }

    private static Pane getSubmitBtnSection() {
        String submitIconPath = "resources/icons/checkmark-40.png";
        Button submitBtn = GUITools.getButton(GUITools.getImage(submitIconPath), "Passer la commande", 70);

        submitBtn.setOnAction(event -> {
            boolean isValidInput = true;
            String errorMsg = "";
            double total = 0;
            for (OrderLine orderLine : OrderLinesController.getOrderLinesOl())
                total += orderLine.getTotal();

            System.out.println(total);
            if(total < PaymentsController.getCalculatedTotal()) {
                errorMsg += "- Impossible de payer un montant plus grand que le total!\r\n";
                isValidInput = false;
            }

            if (OrderLinesController.getOrderLinesOl().size() == 0) {
                errorMsg += "- La commande est vide!\r\n";
                isValidInput = false;
            }

            if (customersCb.getValue() == null) {
                errorMsg += "- Aucun client n'a pas été selectionné\r\n";
                isValidInput = false;
            }

            if (isValidInput) {
                double sum = 0;
                for (OrderLine orderLine : OrderLinesController.getOrderLinesOl()) {
                    sum += orderLine.getTotal();
                    orderLine.getProduct().setQty(orderLine.getProduct().getQty() - orderLine.getQty());
                    (new ProductDaoImpl()).updateQty(orderLine.getProduct());

                }
                Collection<Payment> payments = PaymentsController.getPaymentsOl();
                if(PaymentsController.getPaymentMethodsCb().getValue().equals(PaymentMethod.CASH)) {
                    payments.add(new Payment(0, total, PaymentMethod.CASH, LocalDateTime.now(), null));
                } else if(PaymentsController.getPaymentMethodsCb().getValue().equals(PaymentMethod.ONLINE)) {
                    // create Account instance
                    CardPaymentView cpV = PaymentsController.getCardPaymentView();
                    Card card = new Card(cpV.getCardTypesCb().getValue(),
                            Integer.parseInt(cpV.getCardNumberField().getText()),
                            Integer.parseInt(cpV.getExpMonthCb().getValue().toString()),
                            Integer.parseInt(cpV.getExpYearCb().getValue().toString()),
                            Integer.parseInt(cpV.getVerificationCardField().getText()));
                    Account account = new Account(0, card, customersCb.getValue(), total);
                    // send instance to remote server
                    BankSocket bankSocket = new BankSocket(account);
                    // get response
                    int success = bankSocket.sendPayment();
                    // persist payment
                    switch (success) {
                        case 200:
                            payments.add(new Payment(0, total, PaymentMethod.ONLINE, LocalDateTime.now(), null));
                            break;
                        case 400:
                            GUITools.openDialogOk("Erreur", null, "Votre carte ne contient le montant demandé", Alert.AlertType.ERROR);
                            return;
                        case 404:
                            GUITools.openDialogOk("Erreur", null, "Les données de la carte ne sont incorrecte", Alert.AlertType.ERROR);
                            return;
                        case 500:
                            GUITools.openDialogOk("Erreur", null, "Une erreur s'est produite, merci de réessayer ultrièrement", Alert.AlertType.ERROR);
                            return;
                    }
                } else if(PaymentsController.getPaymentMethodsCb().getValue().equals(PaymentMethod.CHECK)) {
                    Cheque cheque = new Cheque(0, PaymentsController.getChequePaymentView().getOwnerField().getText(),
                            PaymentsController.getChequePaymentView().getEffectiveDateField().getText());
                    payments.add(new Payment(0, total, PaymentMethod.CHECK, cheque, LocalDateTime.now(), null));
                }
                orderDao.create(new Order(0, customersCb.getValue(), sum, LocalDateTime.now(), OrderLinesController.getOrderLinesOl(), payments));
                OrderLinesController.getOrderLinesOl().clear();
                PaymentsController.getPaymentsOl().clear();
                PaymentsController.getPaymentMethodsCb().setValue(PaymentMethod.CASH);
                PaymentsController.getAmountTf().setText("");
                PaymentsController.getTotalVal().setText("0.0 Dhs");
                AppController.showSales();
            } else {
                GUITools.openDialogOk(null, null, errorMsg, Alert.AlertType.WARNING);
            }
        });

        return new HBox(submitBtn);
    }

    private static Pane getSearchSection() {
        Pane p = new VBox();
        // Search
        //HBox searchContainer = new HBox(20);
        TextField searchTf = new TextField();
        searchTf.setPromptText("Rechercher");
        //searchTf.setMinWidth(300);
        searchTf.getStyleClass().add("searchBar");
        searchTf.setMinWidth(200);
        searchTf.setMinHeight(35);
        searchTf.setAlignment(Pos.CENTER);
        searchTf.getStyleClass().remove("text-field");

        searchTf.textProperty().addListener((observable, oldValue, newValue) -> {
            newValue = newValue.trim();
            oldValue = oldValue.trim();
            if (!newValue.equals(oldValue)) {
                System.out.println("searching...");
                Collection<Product> filterdProducts = new ProductDaoImpl().findAll(newValue);
                productOl = FXCollections.observableArrayList(filterdProducts);
                productsTv.setItems(productOl);
            }
        });

        p.getChildren().add(searchTf);

        return p;
    }

    public static Pane show(long id) {
        Order order = orderDao.findOne(id);
        Collection<Payment> payments = new PaymentDaoImpl().findAll(order);
        OrderLineDaoImpl orderLineDao = new OrderLineDaoImpl();
        Collection<OrderLine> orderLines = orderLineDao.findAll(order);

        GridPane gridPane = (GridPane) CustomersController.getCustomerInfoPane(order.getCustomer());
        TableView<OrderLine> orderLinesTv = OrderLinesController.getBasicTableView();
        orderLinesTv.setItems(FXCollections.observableArrayList(orderLines));
        orderLinesTv.getColumns().get(0).prefWidthProperty().bind(orderLinesTv.widthProperty().divide(100 / 30));
        orderLinesTv.getColumns().get(1).prefWidthProperty().bind(orderLinesTv.widthProperty().divide(100 / 20));
        orderLinesTv.getColumns().get(2).prefWidthProperty().bind(orderLinesTv.widthProperty().divide(100 / 15));
        orderLinesTv.getColumns().get(3).prefWidthProperty().bind(orderLinesTv.widthProperty().divide(100 / 27));

        HBox moneyBox = new HBox();
        Label totalLabel = new Label("Total : ");
        Label totalValueLabel = new Label(order.getTotal() + " Dh");
        moneyBox.getChildren().addAll(totalLabel, totalValueLabel);

        double paid = 0.0;
        for (Payment payment : payments)
            paid += payment.getAmount();

        rest = order.getTotal() - paid;
        Label restLabel = new Label("Reste : ");
        restValueLabel = new Label(rest + " Dh");
        moneyBox.getChildren().addAll(restLabel, restValueLabel);
        moneyBox.setSpacing(10);

        TableView<Payment> paymentsTv = PaymentsController.getBasicTv();

        // Method column
        TableColumn<Payment, PaymentMethod> methodCol = new TableColumn<>("Méthode");
        methodCol.setCellValueFactory(new PropertyValueFactory<>("method"));
        paymentsTv.getColumns().add(methodCol);

        paymentsTv.setItems(FXCollections.observableArrayList(payments));
        paymentsTv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // add payments
        TextField amountTf = new TextField();
        Button addPaymentBtn = new Button("Ajouter traite");
        Button saveNewPayments = new Button("Enregistrer");
        saveNewPayments.setAlignment(Pos.CENTER_RIGHT);
        saveNewPayments.setDisable(true);

        TableView<Payment> newPaymentsTv;
        if (rest != 0) {
            System.out.println("rest is not 0");
            newPaymentsTv = PaymentsController.getBasicTv();

            // Remove new payment column
            TableColumn removeNewPaymentCol = new TableColumn<>("Retirer");
            removeNewPaymentCol.setCellFactory(ActionButtonTableCell.forTableColumn("Retirer", (Payment payment) -> {
                newPaymentsTv.getItems().remove(payment);
                if(newPaymentsTv.getItems().size() == 0) saveNewPayments.setDisable(true);
                return payment;
            }));

            newPaymentsTv.getColumns().add(removeNewPaymentCol);
            newPaymentsTv.setItems(FXCollections.observableArrayList());
            newPaymentsTv.getColumns().get(0).prefWidthProperty().bind(newPaymentsTv.widthProperty().divide(100 / 30));
            newPaymentsTv.getColumns().get(1).prefWidthProperty().bind(newPaymentsTv.widthProperty().divide(100 / 30));
            newPaymentsTv.getColumns().get(2).prefWidthProperty().bind(newPaymentsTv.widthProperty().divide(100 / 30));

            addPaymentBtn.setOnAction(e -> {
                try {
                    double amount = Double.parseDouble(amountTf.getText());

                    if(amount <= 0) {
                        GUITools.openDialogOk("Erreur de saisie", null, "Le montant de la traite doit etre strictement positif!", Alert.AlertType.ERROR);
                        return;
                    }

                    if(amount > rest) {
                        GUITools.openDialogOk("Erreur de saisie", null, "Le montant de la traite doit etre inférieur ou égal au reste!", Alert.AlertType.ERROR);
                        return;
                    }

                    Payment payment = new Payment(0, amount, PaymentMethod.DRAFTS, LocalDateTime.now(), order);
                    newPaymentsTv.getItems().add(payment);
                    saveNewPayments.setDisable(false);
                    rest -= amount;
                    restValueLabel.setText(rest + " Dh");
                } catch (NumberFormatException e1) {
                    GUITools.openDialogOk("Erreur de saisie", null, "Veuillez entrer le montant en chiffre", Alert.AlertType.ERROR);
                }
                amountTf.clear();
            });
            saveNewPayments.setOnAction(e -> {
                PaymentsController.addPayments(newPaymentsTv.getItems());
                AppController.showOrder(order.getId());
            });
        } else {
            newPaymentsTv = null;
        }

        HBox addPaymentHBox = new HBox(amountTf, addPaymentBtn, saveNewPayments);

        VBox vBox = new VBox(gridPane, orderLinesTv, moneyBox, newPaymentsTv == null ? paymentsTv : new HBox(newPaymentsTv, paymentsTv));
        if(newPaymentsTv != null) {
            vBox.getChildren().add(addPaymentHBox);
        }
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(5));

        return vBox;
    }

    public static ObservableList<Product> getProductsOl() {
        return productOl == null ? FXCollections.observableArrayList((new ProductDaoImpl()).findAll()) : productOl;
    }
}
