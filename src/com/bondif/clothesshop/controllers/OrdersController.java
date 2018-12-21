package com.bondif.clothesshop.controllers;

import com.bondif.clothesshop.core.CustomerDaoImpl;
import com.bondif.clothesshop.core.OrderDaoImpl;
import com.bondif.clothesshop.core.OrderLineDaoImpl;
import com.bondif.clothesshop.core.ProductDaoImpl;
import com.bondif.clothesshop.models.Customer;
import com.bondif.clothesshop.models.Order;
import com.bondif.clothesshop.models.OrderLine;
import com.bondif.clothesshop.models.Product;
import com.bondif.clothesshop.views.ActionButtonTableCell;
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

    static {
        orderDao = new OrderDaoImpl();
        productOl = null;
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
        VBox container = new VBox();

        Pane searchSection = getSearchSection();
        Pane productsSection = getProductsSection();
        Pane orderLinesSection = getOrderLinesSection();
        Pane clientSection = getClientSection();
        Pane submitBtnSection = getSubmitBtnSection();
        Region region = new Region();
        HBox clientsHbox = new HBox();
        HBox submitHbox = new HBox();

        HBox.setHgrow(region, Priority.ALWAYS);

        clientsHbox.getChildren().addAll(region, clientSection);
        submitHbox.getChildren().addAll(region, submitBtnSection);

        productsSection.setPadding(new Insets(15));
        orderLinesSection.setPadding(new Insets(15));
        clientSection.setPadding(new Insets(15));
        submitBtnSection.setPadding(new Insets(15));

        searchSection.setPadding(new Insets(15));
        searchSection.setMaxWidth(200);


        container.getChildren().addAll(clientsHbox, searchSection, productsSection, orderLinesSection, submitHbox);

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
        TableView<OrderLine> orderLinesTv = OrderLinesController.getSaleOrderLinesTv();
        orderLinesTv.setMaxHeight(500);
        orderLinesTv.getColumns().get(0).prefWidthProperty().bind(orderLinesTv.widthProperty().divide(100 / 35));
        orderLinesTv.getColumns().get(1).prefWidthProperty().bind(orderLinesTv.widthProperty().divide(100 / 15));
        orderLinesTv.getColumns().get(2).prefWidthProperty().bind(orderLinesTv.widthProperty().divide(100 / 10));
        orderLinesTv.getColumns().get(3).prefWidthProperty().bind(orderLinesTv.widthProperty().divide(100 / 12));
        orderLinesTv.getColumns().get(4).prefWidthProperty().bind(orderLinesTv.widthProperty().divide(100 / 10));
        return new VBox(orderLinesTv);
    }

    private static Pane getProductsSection() {
        productsTv = ProductsController.getBasicTableView();

        productsTv.getColumns().get(2).prefWidthProperty().bind(productsTv.widthProperty().divide(100 / 7));
        productsTv.getColumns().get(3).prefWidthProperty().bind(productsTv.widthProperty().divide(100 / 15));
        // "add product to order" column
        TableColumn addProductCol = new TableColumn<>("Ajouter");
        addProductCol.prefWidthProperty().bind(productsTv.widthProperty().divide(100 / 25));
        addProductCol.setCellFactory(ActionButtonTableCell.forTableColumn("Ajouter", (Product p) -> {
            int qty = GUITools.openQtyTextInputDialog();
            if (qty == -1) return p;
            //if (qty > 0 && p.getQty() >= qty && OrderLinesController.canAddQty(p, qty))
                OrderLinesController.add(new OrderLine(0, p, p.getSellingPrice(), qty));
            //else
                //GUITools.openDialogOk(null, null, "La quantité choisie est plus grande que celle en stock !!", Alert.AlertType.ERROR);
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
                orderDao.create(new Order(0, customersCb.getValue(), sum, LocalDateTime.now(), OrderLinesController.getOrderLinesOl()));
                OrderLinesController.getOrderLinesOl().clear();
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
        OrderLineDaoImpl orderLineDao = new OrderLineDaoImpl();
        Collection<OrderLine> orderLines = orderLineDao.findAll(order);

        GridPane gridPane = (GridPane) CustomersController.getCustomerInfoPane(order.getCustomer());
        TableView<OrderLine> orderLinesTv = OrderLinesController.getBasicTableView();
        orderLinesTv.setItems(FXCollections.observableArrayList(orderLines));
        orderLinesTv.getColumns().get(0).prefWidthProperty().bind(orderLinesTv.widthProperty().divide(100 / 30));
        orderLinesTv.getColumns().get(1).prefWidthProperty().bind(orderLinesTv.widthProperty().divide(100 / 20));
        orderLinesTv.getColumns().get(2).prefWidthProperty().bind(orderLinesTv.widthProperty().divide(100 / 15));
        orderLinesTv.getColumns().get(3).prefWidthProperty().bind(orderLinesTv.widthProperty().divide(100 / 27));

        HBox hBox = new HBox();
        Label totalLabel = new Label("Total : ");
        Label totalValueLabel = new Label(order.getTotal() + "");
        hBox.getChildren().addAll(totalLabel, totalValueLabel);

        VBox vBox = new VBox(gridPane, orderLinesTv, hBox);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10));

        return vBox;
    }

    public static ObservableList<Product> getProductsOl() {
        return productOl == null ? FXCollections.observableArrayList((new ProductDaoImpl()).findAll()) : productOl;
    }
}
