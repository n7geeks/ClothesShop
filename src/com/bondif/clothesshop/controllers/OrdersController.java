package com.bondif.clothesshop.controllers;

import com.bondif.clothesshop.core.OrderDaoImpl;
import com.bondif.clothesshop.models.Order;
import com.bondif.clothesshop.models.OrderLine;
import com.bondif.clothesshop.models.Product;
import com.bondif.clothesshop.views.ActionButtonTableCell;
import com.bondif.clothesshop.views.GUITools;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.Date;

public class OrdersController {
    private static ObservableList<Order> ordersOl;
    private static OrderDaoImpl orderDao;

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

        // customer column
        TableColumn<Order, String> customerCol = new TableColumn<>("Client");
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
                    OrdersController.show(saleTr.getItem().getId());
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

        gPane.add(productsSection, 0, 0);
        gPane.add(orderLinesSection, 0, 1);
        gPane.add(clientSection, 0, 2);

        return gPane;
    }

    private static Pane getClientSection() {
        HBox hBox = new HBox();
        return new VBox();
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
            if(qty != -1)
                OrderLinesController.add(new OrderLine(0, p, p.getSellingPrice(), qty));
            return p;
        }));

        productsTv.getColumns().add(addProductCol);

        productsTv.setItems(ProductsController.getProductsOl());

        return new VBox(productsTv);
    }

    public static void show(long id) {
        return;
    }
}
