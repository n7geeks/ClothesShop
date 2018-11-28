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
import com.bondif.clothesshop.views.utils.Toast;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;

public class OrdersController {
    private static ObservableList<Order> ordersOl;
    private static OrderDaoImpl orderDao;
    private static ComboBox<Customer> customersCb;

    static {
        orderDao = new OrderDaoImpl();
    }

    public static Pane getSalesPane() {
        ordersOl = FXCollections.observableArrayList(orderDao.findAll());

        VBox vBox = new VBox();

        // vbox config
        vBox.setPadding(new Insets(20));
        vBox.setSpacing(10);

        // Add sale button
        String iconPath = "resources/avatar.jpg";
        Button addBtn = GUITools.getButton(GUITools.getImage(iconPath), "Ajouter", 100);
        addBtn.setOnAction(event -> {
            AppController.showSaleCreateForm();
        });

        // Sales Table
        TableView<Order> salesTv = new TableView<>();

        // id column
        TableColumn<Order, Long> idCol = new TableColumn<>("Id");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        // customer column
        TableColumn<Order, Customer> customerCol = new TableColumn<>("Client");
        customerCol.setCellValueFactory(new PropertyValueFactory<>("customer"));

        // total column
        TableColumn<Order, Double> totalCol = new TableColumn<>("Total");
        totalCol.setCellValueFactory(new PropertyValueFactory<>("total"));

        // createdAt column
        TableColumn<Order, Date> createdAtCol = new TableColumn<>("Date");
        createdAtCol.setCellValueFactory(new PropertyValueFactory<>("createdAt"));

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
        GridPane gPane = new GridPane();

        Pane productsSection = getProductsSection();
        Pane orderLinesSection = getOrderLinesSection();
        Pane clientSection = getClientSection();
        Pane submitBtnSection = getSubmitBtnSection();

        gPane.add(productsSection, 0, 0);
        gPane.add(orderLinesSection, 0, 1);
        gPane.add(clientSection, 0, 2);
        gPane.add(submitBtnSection, 0, 3);

        return gPane;
    }

    private static Pane getClientSection() {
        CustomerDaoImpl customerDao = new CustomerDaoImpl();
        HBox hBox = new HBox();
        Label clientLabel = new Label("Client : ");
        customersCb = new ComboBox<>(FXCollections.observableArrayList(customerDao.findAll()));
        hBox.getChildren().addAll(clientLabel, customersCb);

        return new VBox(hBox);
    }

    private static Pane getOrderLinesSection() {
        TableView<OrderLine> orderLinesTv = OrderLinesController.getBasicTableView();
        orderLinesTv.setMaxHeight(500);
        return new VBox(orderLinesTv);
    }

    private static Pane getProductsSection() {
        TableView<Product> productsTv = ProductsController.getBasicTableView();

        // "add product to order" column
        TableColumn addProductCol = new TableColumn<>("Ajouter");
        addProductCol.setCellFactory(ActionButtonTableCell.forTableColumn("Ajouter", (Product p) -> {
            int qty = GUITools.openQtyTextInputDialog();
            if (qty == -1) return p;
            if(qty > 0 && p.getQty() >= qty && OrderLinesController.canAddQty(p, qty))
                OrderLinesController.add(new OrderLine(0, p, p.getSellingPrice(), qty));
            else
                GUITools.openDialogOk(null, null, "La quantité choisi est plus grande que celle en stock !!", Alert.AlertType.ERROR);
            return p;
        }));

        productsTv.getColumns().add(addProductCol);

        productsTv.setItems(ProductsController.getProductsOl());

        return new VBox(productsTv);
    }

    private static Pane getSubmitBtnSection() {
        Button submitBtn = GUITools.getButton(null, "Passer la commande", 70);

        submitBtn.setOnAction(event -> {

            boolean isValidInput = true;

            if(OrderLinesController.getOrderLinesOl().size() == 0){
                GUITools.openDialogOk(null, null, "la commande est vide!", Alert.AlertType.WARNING);
                isValidInput = false;
            }

            double sum = 0;
            for (OrderLine orderLine: OrderLinesController.getOrderLinesOl()) {
                sum += orderLine.getTotal();
                orderLine.getProduct().setQty(orderLine.getProduct().getQty() - orderLine.getQty());
                (new ProductDaoImpl()).updateQty(orderLine.getProduct());

            }

            if(customersCb.getValue() == null){
                GUITools.openDialogOk(null, null, "aucun Client est selectionné", Alert.AlertType.WARNING);
                isValidInput = false;
            }

            if(isValidInput){
                orderDao.create(new Order(0, customersCb.getValue(), sum, LocalDateTime.now(), OrderLinesController.getOrderLinesOl()));
                AppController.showSales();
            }
        });

        return new HBox(submitBtn);
    }

    public static Pane show(long id) {
        Order order = orderDao.findOne(id);
        OrderLineDaoImpl orderLineDao = new OrderLineDaoImpl();
        Collection<OrderLine> orderLines = orderLineDao.findAll(order);

        GridPane gridPane = (GridPane)CustomersController.getCustomerInfoPane(order.getCustomer());
        TableView<OrderLine> orderLineTv = new TableView<>();

        TableColumn<OrderLine, Product> labelCol = new TableColumn<>("Produit");
        labelCol.setCellValueFactory(new PropertyValueFactory<>("product"));

        TableColumn<OrderLine, Double> priceCol = new TableColumn<>("Prix");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<OrderLine, Double> qtyCol = new TableColumn<>("Quantité");
        qtyCol.setCellValueFactory(new PropertyValueFactory<>("qty"));

        TableColumn<OrderLine, Double> totalCol = new TableColumn<>("Total");
        totalCol.setCellValueFactory(new PropertyValueFactory<>("total"));

        orderLineTv.getColumns().addAll(labelCol, priceCol, qtyCol, totalCol);
        orderLineTv.setItems(FXCollections.observableArrayList(orderLines));

        HBox hBox = new HBox();
        Label totalLabel = new Label("Total : ");
        Label totalValueLabel = new Label(order.getTotal() + "");
        hBox.getChildren().addAll(totalLabel, totalValueLabel);

        VBox vBox = new VBox(gridPane, orderLineTv, hBox);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10));

        return vBox;
    }
}
