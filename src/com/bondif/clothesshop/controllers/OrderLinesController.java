package com.bondif.clothesshop.controllers;

import com.bondif.clothesshop.core.OrderLineDaoImpl;
import com.bondif.clothesshop.models.OrderLine;
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
    }

    public static TableView<OrderLine> getBasicTableView() {
        TableView<OrderLine> orderLinesTv = new TableView<>();
        orderLinesOl = FXCollections.observableArrayList();

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
        orderLinesOl.add(orderLine);
    }
}
