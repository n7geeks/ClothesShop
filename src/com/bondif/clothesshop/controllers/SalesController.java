package com.bondif.clothesshop.controllers;

import com.bondif.clothesshop.core.SaleDaoImpl;
import com.bondif.clothesshop.models.Category;
import com.bondif.clothesshop.models.Product;
import com.bondif.clothesshop.models.Sale;
import com.bondif.clothesshop.views.ActionButtonTableCell;
import com.bondif.clothesshop.views.GUITools;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.Date;

public class SalesController {
    private static ObservableList<Sale> salesOl;
    private static SaleDaoImpl saleDao;

    static {
        saleDao = new SaleDaoImpl();
    }

    public static Pane getSalesPane() {
        salesOl = FXCollections.observableArrayList(saleDao.findAll());

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
        TableView<Sale> salesTv = new TableView<>();

        // id column
        TableColumn<Sale, Long> idCol = new TableColumn<>("Id");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        // customer column
        TableColumn<Sale, String> customerCol = new TableColumn<>("Client");
        customerCol.setCellValueFactory(new PropertyValueFactory<>("customer"));

        // total column
        TableColumn<Sale, Double> totalCol = new TableColumn<>("Total");
        totalCol.setCellValueFactory(new PropertyValueFactory<>("total"));

        // createdAt column
        TableColumn<Sale, Date> createdAtCol = new TableColumn<>("Date");
        createdAtCol.setCellValueFactory(new PropertyValueFactory<>("createdAt"));

        salesTv.getColumns().addAll(idCol, customerCol, totalCol, createdAtCol);

        salesTv.setItems(salesOl);

        salesTv.setRowFactory(tv -> {
            TableRow<Sale> saleTr = new TableRow<>();

            saleTr.setOnMouseClicked(e -> {
                if (e.getClickCount() == 2 && !saleTr.isEmpty()) {
                    SalesController.show(saleTr.getItem().getId());
                }
            });

            return saleTr;
        });

        vBox.getChildren().addAll(addBtn, salesTv);

        return vBox;
    }

    public static Pane getCreateForm() {
        Pane pane = new Pane();
        return pane;
    }

    public static void show(long id) {
        return;
    }
}
