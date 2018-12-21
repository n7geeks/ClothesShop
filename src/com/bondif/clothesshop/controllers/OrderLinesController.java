package com.bondif.clothesshop.controllers;

import com.bondif.clothesshop.core.OrderLineDaoImpl;
import com.bondif.clothesshop.models.OrderLine;
import com.bondif.clothesshop.models.Product;
import com.bondif.clothesshop.views.ActionButtonTableCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class OrderLinesController {
    private static OrderLineDaoImpl orderLineDao;
    private static ObservableList<OrderLine> orderLinesOl;

    static {
        orderLineDao = new OrderLineDaoImpl();
        orderLinesOl = null;
    }

    public static TableView<OrderLine> getBasicTableView() {
        TableView<OrderLine> orderLinesTv = new TableView<>();

        TableColumn<OrderLine, Product> labelCol = new TableColumn<>("Produit");
        labelCol.setCellValueFactory(new PropertyValueFactory<>("product"));

        TableColumn<OrderLine, Double> priceCol = new TableColumn<>("Prix");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<OrderLine, Double> qtyCol = new TableColumn<>("Quantité");
        qtyCol.setCellValueFactory(new PropertyValueFactory<>("qty"));

        TableColumn<OrderLine, Double> totalCol = new TableColumn<>("Total");
        totalCol.setCellValueFactory(new PropertyValueFactory<>("total"));

        orderLinesTv.getColumns().addAll(labelCol, priceCol, qtyCol, totalCol);

        return orderLinesTv;
    }

    public static TableView<OrderLine> getSaleOrderLinesTv(boolean withPayment) {
        TableView<OrderLine> orderLinesTableView = getBasicTableView();
        orderLinesOl = getOrderLinesOl();

        // Edit column
        TableColumn removeOrderLineBtn = new TableColumn<>("Retirer");
        removeOrderLineBtn.setCellFactory(ActionButtonTableCell.forTableColumn("Retirer", (OrderLine orderLine) -> {
            orderLinesOl.remove(orderLine);
            if (withPayment) {
                PaymentsController.removeFromTotal(orderLine.getTotal());
            }
            return orderLine;
        }));

        orderLinesTableView.getColumns().add(removeOrderLineBtn);
        orderLinesTableView.setItems(orderLinesOl);

        return orderLinesTableView;
    }

    public static void add(OrderLine orderLine) {
        int index;
        PaymentsController.addToTotal(orderLine.getTotal());
        for (OrderLine oL: orderLinesOl) {
            if(oL.getProduct().getCode().equals(orderLine.getProduct().getCode())) {
                oL.setQty(oL.getQty() + orderLine.getQty());
                OrderLine orderLine1 = new OrderLine(oL);
                index = orderLinesOl.indexOf(oL);
                orderLinesOl.remove(oL);
                orderLinesOl.add(index, orderLine1);
                System.out.println(oL.getQty());
                return;
            }
        }
        orderLinesOl.add(orderLine);
    }

    public static ObservableList<OrderLine> getOrderLinesOl() {
        return orderLinesOl == null ? FXCollections.observableArrayList() : orderLinesOl;
    }

    public static boolean canAddQty(Product product, int qty) {
        for (OrderLine orderLine: orderLinesOl) {
            if (orderLine.getProduct() == product)
                if (orderLine.getQty() + qty > orderLine.getProduct().getQty()) return false;
        }
        return true;
    }
}
