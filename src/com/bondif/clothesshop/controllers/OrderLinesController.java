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
    static TableView<OrderLine> orderLinesTv;

    static {
        orderLineDao = new OrderLineDaoImpl();
        orderLinesOl = null;
    }

    public static TableView<OrderLine> getBasicTableView() {
        orderLinesTv = new TableView<>();
        orderLinesOl = getOrderLinesOl();

        //id column
        /*TableColumn<Product, String> codeCol = new TableColumn<>("Libellé");
        codeCol.setCellValueFactory(new PropertyValueFactory<>("label"));*/

        // price column
        TableColumn<OrderLine, Double> priceCol = new TableColumn<>("Prix");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        // quantity column
        TableColumn<OrderLine, Integer> qtyCol = new TableColumn<>("Quantité");
        qtyCol.setCellValueFactory(new PropertyValueFactory<>("qty"));

        // Edit column
        TableColumn removeOrderLineBtn = new TableColumn<>("Retirer");
        removeOrderLineBtn.setCellFactory(ActionButtonTableCell.forTableColumn("Retirer", (OrderLine orderLine) -> {
            orderLinesOl.remove(orderLine);
            return orderLine;
        }));

        orderLinesTv.getColumns().addAll(priceCol, qtyCol, removeOrderLineBtn);
        orderLinesTv.setItems(orderLinesOl);

        return orderLinesTv;
    }

    public static void add(OrderLine orderLine) {
        for (OrderLine oL: orderLinesOl) {
            if(oL.getProduct().getCode().equals(orderLine.getProduct().getCode())) {
                oL.setQty(oL.getQty() + orderLine.getQty());
                orderLinesOl.remove(oL);
                orderLinesOl.add(oL);
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
