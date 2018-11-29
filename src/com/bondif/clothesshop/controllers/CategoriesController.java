package com.bondif.clothesshop.controllers;

import com.bondif.clothesshop.core.CategoryDaoImpl;
import com.bondif.clothesshop.core.ProductDaoImpl;
import com.bondif.clothesshop.models.Category;
import com.bondif.clothesshop.views.GUITools;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import javax.xml.crypto.dsig.keyinfo.KeyValue;

public class CategoriesController {
    private static ObservableList<Category> categoriesOl;
    private static CategoryDaoImpl categoryDao;
    private static TableView<Category> categoriesTableView;
    private static TextField tfcategory;
    private static TextField titleTf;


    static {
        categoryDao = new CategoryDaoImpl();
    }


    public static void addCategoryHandler(String categorytitle) {
        if(categorytitle.isEmpty()){
            GUITools.openDialogOk("Erreur", null, "le champ categorie est vide", Alert.AlertType.WARNING);
            return ;
        }

        categoryDao.create(new Category(categorytitle));
        AppController.showCategoryForm();
    }

    public static VBox getCategoryForm() {
        // bring data from the server
        categoriesOl = FXCollections.observableArrayList(categoryDao.findAll());

        VBox catform = new VBox();
        String addIconPath = "resources/icons/plus-math-30.png";
        String deleteIconPath = "resources/icons/cancel-40.png";
        String editIconPath = "resources/icons/pencil-40.png";

        catform.setPadding(new Insets(20));
        catform.setSpacing(10);

        categoriesTableView = new TableView<>();
        Button bnajouter = GUITools.getButton(GUITools.getImage(addIconPath), "Ajouter", 100);
        bnajouter.setOnAction(e -> {
            AppController.showCreateCategoryForm();
        });

        TextField tfsearch = new TextField("");

        tfsearch.setPromptText("Rechercher");
        tfsearch.setMinWidth(200);
        tfsearch.setMinHeight(28);
        tfsearch.setAlignment(Pos.CENTER);
        tfsearch.getStyleClass().add("searchBar");
        tfsearch.getStyleClass().remove("text-field");
        tfsearch.textProperty().addListener((observable, oldValue, newValue) -> {
            categoriesOl =FXCollections.observableArrayList(categoryDao.findAll(newValue));
            categoriesTableView.setItems(categoriesOl);
        });

        GridPane littleWrapper = new GridPane();
        littleWrapper.setHgap(40);
        littleWrapper.setVgap(10);

        littleWrapper.add(bnajouter, 0, 0);
        littleWrapper.add(tfsearch, 2, 0);

        //id column
        TableColumn<Category, Long> idColumn = new TableColumn<>("id");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idColumn.setMinWidth(200);
        idColumn.prefWidthProperty().bind(categoriesTableView.widthProperty().divide(100 / 50));

        //id column
        TableColumn<Category, String> titleColumn = new TableColumn<>("Catégorie");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        titleColumn.setMinWidth(400);
        titleColumn.prefWidthProperty().bind(categoriesTableView.widthProperty().divide(100 / 50));

        categoriesTableView.getColumns().addAll(idColumn, titleColumn);
        categoriesTableView.setItems(categoriesOl);
        categoriesTableView.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                selectCategory();
            }
        });
        categoriesTableView.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER){
                selectCategory();
            }
        });

        //gridpane: delete, modify a selected category
        GridPane gp_alter = new GridPane();
        gp_alter.setAlignment(Pos.CENTER);

        Button bnsupprimer = GUITools.getButton(GUITools.getImage(deleteIconPath), "Supprimer", 100);
        Button bnmodifier = GUITools.getButton(GUITools.getImage(editIconPath), "Modifier", 100);

        bnmodifier.setOnAction(event -> {
            updateCategory();
        });

        bnsupprimer.setOnAction(event -> {
            deleteCategory();
        });

        Label titleLabel = new Label("Catégorie");
        tfcategory = new TextField();
        tfcategory.setPromptText("Aucune catégorie est selectionnée");
        // Younes : on text changed do: - disable button 'modifier' if no changes has been commited
        //                              - can't write if no category is selected
        tfcategory.textProperty().addListener((observable, oldValue, newValue) -> {
            Category c = categoriesTableView.getSelectionModel().getSelectedItem();
            if(c == null) {
                tfcategory.setText("");
                bnmodifier.setDisable(true);
            }
            else{
                if(tfcategory.getText().trim().equals(c.getTitle())){
                    bnmodifier.setDisable(true);
                } else{
                    bnmodifier.setDisable(false);
                }
            }
        });

        gp_alter.add(titleLabel, 0, 0);
        gp_alter.add(tfcategory, 1, 0);
        gp_alter.add(bnmodifier, 2, 0);
        gp_alter.add(bnsupprimer, 2, 1);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(20);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(40);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(20);

        gp_alter.getColumnConstraints().addAll(col1, col2, col3);


        GridPane.setHalignment(bnmodifier, HPos.CENTER);
        GridPane.setHalignment(bnsupprimer, HPos.CENTER);

        gp_alter.setHgap(10);
        gp_alter.setVgap(10);


//      catform.getChildren().addAll(addBtn, productsTableView);
        catform.getChildren().addAll(littleWrapper, categoriesTableView, gp_alter);

        return catform;

    }

    //[form: Pane] to insert new category
    public static Pane getCreateCategoryForm() {
        GridPane gridPane = new GridPane();

        gridPane.setAlignment(Pos.CENTER);
//        gridPane.setStyle("-fx-border-width: 2px; -fx-border-style: solid; -fx-grid-lines-visible: true");

        Label idLabel = new Label("id");
        Label titleLabel = new Label("Categorie");

        TextField idTf = new TextField();
        idTf.setPromptText("id catégorie <to be generated>");
        idTf.setDisable(true);
        titleTf = new TextField();
        titleTf.setPromptText("Nouvelle catégorie");

        String submitIcon = "resources/icons/checkmark-40.png";
        Button submitBtn = GUITools.getButton(GUITools.getImage(submitIcon), "Créer", 100);

        submitBtn.setOnAction(event -> {
            addCategoryHandler(titleTf.getText().trim());
        });

        gridPane.add(idLabel, 0, 0);
        gridPane.add(titleLabel, 0, 1);

        gridPane.add(idTf, 1, 0);
        gridPane.add(titleTf, 1, 1);

        gridPane.add(submitBtn, 0, 5, 2, 1);

        GridPane.setHalignment(submitBtn, HPos.RIGHT);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(20);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(60);

        gridPane.getColumnConstraints().addAll(col1, col2);

        gridPane.setHgap(10);
        gridPane.setVgap(10);

        return gridPane;
    }

    private static void selectCategory() {
        Category c = categoriesTableView.getSelectionModel().getSelectedItem();

        if (c != null) {
            System.out.println("Title : " + c.getTitle());
            tfcategory.setText(c.getTitle());
        } else {
            categoriesTableView.requestFocus();

        }
    }

    private static void deleteCategory() {
        Category c = categoriesTableView.getSelectionModel().getSelectedItem();
        //todo if no Category is selected
        if (c != null) {
            if (GUITools.openDialogYesNo("sûr?", null, "Etes-vous sûr de supprimer la catégorie : " + c.getTitle(), Alert.AlertType.WARNING)) {
                categoryDao.delete(c);
                AppController.showCategoryForm();
            }
        } else {
            GUITools.openDialogOk("Oops", "vide", "Aucune catégorie est selectionnée", Alert.AlertType.WARNING);
            categoriesTableView.requestFocus();

        }
    }

    private static void updateCategory() {
        Category c = categoriesTableView.getSelectionModel().getSelectedItem();
        //TODO if textfield is empty, or no Category is selected
        if (c != null) {
            if ((tfcategory.getText().trim()).isEmpty()) {
                GUITools.openDialogOk("Erreur", "Modification échouée", "Le champ catégorie est vide", Alert.AlertType.WARNING);

                return;
            }

            if (GUITools.openDialogYesNo("sûr?", null, "Etes-vous sûr de modifier la catégorie: " + c.getTitle() + " à " + (tfcategory.getText()).trim(), Alert.AlertType.CONFIRMATION)) {
                c.setTitle(tfcategory.getText().trim());
                categoryDao.update(c);
                AppController.showCategoryForm();
            }
        } else {
            GUITools.openDialogOk("Oops", "vide", "Aucune catégorie est selectionnée", Alert.AlertType.WARNING);
            categoriesTableView.requestFocus();
        }
    }

}

