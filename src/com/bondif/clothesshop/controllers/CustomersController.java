package com.bondif.clothesshop.controllers;

import com.bondif.clothesshop.core.CustomerDaoImpl;
import com.bondif.clothesshop.models.Customer;
import com.bondif.clothesshop.models.Order;
import com.bondif.clothesshop.views.GUITools;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Optional;

public class CustomersController {
    public static ObservableList<Customer> CustomersOl;
    private static CustomerDaoImpl customerDao;
    private static Customer customer;

    static {
        customerDao = new CustomerDaoImpl();
    }

    public static VBox createCustomerPane(){
        CustomersOl = FXCollections.observableArrayList(customerDao.findAll());
        VBox panel = new VBox();
        HBox buttonsPanel = new HBox();
        String addIconPath = "resources/icons/plus-math-30.png";
        String deleteIconPath = "resources/icons/cancel-40.png";
        String editIconPath = "resources/icons/pencil-40.png";
        Button addButton = GUITools.getButton(GUITools.getImage(addIconPath), "Ajouter", 100);
        Button deleteButton = GUITools.getButton(GUITools.getImage(deleteIconPath), "Supprimer", 100);
        Button editButton = GUITools.getButton(GUITools.getImage(editIconPath), "Modifier", 100);
        Label customerLbl = new Label("Liste des clients");
        Text text = new Text("Aucun client n'est trouvé");
        TextField searchBar = new TextField();
        Region region = new Region();

        searchBar.setPromptText("Rechercher");
        searchBar.setMinWidth(200);
        searchBar.setMinHeight(28);
        searchBar.setAlignment(Pos.CENTER);

        customerLbl.setFont(new Font("Century Gothic", 20));

        buttonsPanel.setPadding(new Insets(20));
        buttonsPanel.setSpacing(10);


        HBox.setHgrow(region, Priority.ALWAYS);

        buttonsPanel.getChildren().addAll(addButton, deleteButton, editButton, region, searchBar);

        panel.setPadding(new Insets(20));
        panel.setSpacing(10);
        panel.setId("panel");

        TableView<Customer> customersList = new TableView<>();

        customersList.setPlaceholder(text);
        customersList.setId("list");

        TableColumn<Customer, Long> idColumn = new TableColumn<>("id");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idColumn.prefWidthProperty().bind(customersList.widthProperty().divide(100 / 5));

        TableColumn<Customer, String> codeColumn = new TableColumn<>("code");
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        codeColumn.prefWidthProperty().bind(customersList.widthProperty().divide(100 / 10));

        TableColumn<Customer, String> firstNameColumn = new TableColumn<>("Prénom");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        firstNameColumn.prefWidthProperty().bind(customersList.widthProperty().divide(100 / 10));

        TableColumn<Customer, String> lastNameColumn = new TableColumn<>("Nom");
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        lastNameColumn.prefWidthProperty().bind(customersList.widthProperty().divide(100 / 10));

        TableColumn<Customer, String> phoneColumn = new TableColumn<>("Téléphone");
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        phoneColumn.prefWidthProperty().bind(customersList.widthProperty().divide(100 / 15));

        TableColumn<Customer, String> addressColumn = new TableColumn<>("Adresse");
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        addressColumn.prefWidthProperty().bind(customersList.widthProperty().divide(100 / 25));

        TableColumn<Customer, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailColumn.prefWidthProperty().bind(customersList.widthProperty().divide(100 / 25));

        customersList.getColumns().addAll(idColumn, codeColumn, firstNameColumn, lastNameColumn, phoneColumn, addressColumn, emailColumn);

        customersList.setItems(CustomersOl);

        panel.getChildren().addAll(buttonsPanel, customerLbl, customersList);

        //"customer" get the value selected in the tableView when the mouse click event happened
        customersList.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY){
                customer = customersList.getSelectionModel().getSelectedItem();
            }
        });

        //add button event
        addButton.setOnAction(event -> {
            AppController.showCreateCustomerForm();
        });

        //delete button event
        deleteButton.setOnAction(event -> {
            Alert confirmation;
            //Adding an alert
            if (customersList.getItems().size() != 0){
                if (customersList.getSelectionModel().getSelectedIndex() != -1){
                    Customer selectedItem = customersList.getSelectionModel().getSelectedItem();
                    confirmation = GUITools.messageBox(Alert.AlertType.CONFIRMATION, "Suppression d'un client", null, "Vous voulez vaiment supprimer le client : " +
                            selectedItem.getFirstName() + " " + selectedItem.getLastName());
                    Optional<ButtonType> result = confirmation.showAndWait();
                    ButtonType res = result.orElse(ButtonType.CANCEL);

                    if (res == ButtonType.OK){
                        customerDao.delete(selectedItem);
                        AppController.showCustomers();
                    }
                }else{
                    Alert msg;
                    msg = GUITools.messageBox(Alert.AlertType.ERROR, "Suppression d'un client", null, "Aucun client n'est séléctionné");
                    msg.showAndWait();
                }
            }
        });

        //edit button event
        editButton.setOnAction(event -> {
            if (customersList.getSelectionModel().getSelectedIndex() != -1){
                AppController.showEditCustomerForm();
            }else{
                Alert msg;
                msg = GUITools.messageBox(Alert.AlertType.ERROR, "Modification d'un client", null, "Aucun client n'est séléctionné");
                msg.showAndWait();
            }
            //Adding an alert
        });

        //search bar event
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            if (searchBar.getText().isEmpty()){
                customersList.setItems(CustomersOl);
            }else{
                ObservableList<Customer>  customerList = FXCollections.observableArrayList(customerDao.findAll(searchBar.getText()));
                customersList.setItems(customerList);
            }
        });
        return panel;
    }

    public static Pane getEditPane(){
        Pane editPane = getCustomerPane();
        Long customerId;

        TextField codeTf = (TextField) editPane.lookup("#c");
        TextField firstNameTf = (TextField) editPane.lookup("#fn");
        TextField lastNameTf = (TextField) editPane.lookup("#ln");
        TextField phoneTf = (TextField) editPane.lookup("#p");
        TextField addressTf = (TextField) editPane.lookup("#a");
        TextField emailTf = (TextField) editPane.lookup("#e");
        Button submit = (Button) editPane.lookup("#submitBtn");

        customerId = customer.getId();
        codeTf.setText(customer.getCode());
        firstNameTf.setText(customer.getFirstName());
        lastNameTf.setText(customer.getLastName());
        phoneTf.setText(customer.getPhone());
        addressTf.setText(customer.getAddress());
        emailTf.setText(customer.getEmail());

        submit.setText("Valider");

        submit.setOnAction(event -> {
            customerDao.update(new Customer(customerId, codeTf.getText(), firstNameTf.getText(), lastNameTf.getText(), phoneTf.getText(),
                    addressTf.getText(), emailTf.getText()));
            AppController.showCustomers();
            //Adding an alert
        });
        return editPane;
    }

    public static Pane getCustomerPane(){
        GridPane gridPane = new GridPane();

        Label codeLbl = new Label("Code : ");
        Label firstNameLbl = new Label("Prénom : ");
        Label lastNameLbl = new Label("Nom : ");
        Label phoneLbl = new Label("Téléphone : ");
        Label addressLbl = new Label("Adresse : ");
        Label emailLbl = new Label("Email : ");

        TextField codeTf = new TextField();
        TextField firstNameTf = new TextField();
        TextField lastNameTf = new TextField();
        TextField phoneTf = new TextField();
        TextField addressTf = new TextField();
        TextField emailTf = new TextField();

        codeTf.setId("c");
        firstNameTf.setId("fn");
        lastNameTf.setId("ln");
        phoneTf.setId("p");
        addressTf.setId("a");
        emailTf.setId("e");

        Button submitButton = new Button("Ajouter Client");

        codeTf.setPromptText("Saisir un code");
        firstNameTf.setPromptText("Saisir un prénom");
        lastNameTf.setPromptText("Saisir un nom");
        phoneTf.setPromptText("Saisir un numéro de téléphone");
        addressTf.setPromptText("Saisir une adresse");
        emailTf.setPromptText("Saisir un email");
        submitButton.setId("submitBtn");

        gridPane.add(codeLbl, 0, 0);
        gridPane.add(codeTf, 1, 0);

        gridPane.add(firstNameLbl, 0, 1);
        gridPane.add(firstNameTf, 1,1);

        gridPane.add(lastNameLbl,0, 2);
        gridPane.add(lastNameTf, 1, 2);

        gridPane.add(phoneLbl, 0, 3);
        gridPane.add(phoneTf, 1, 3);

        gridPane.add(addressLbl, 0, 4);
        gridPane.add(addressTf, 1, 4);

        gridPane.add(emailLbl, 0, 5);
        gridPane.add(emailTf, 1, 5);

        gridPane.add(submitButton, 1,6);

        gridPane.setAlignment(Pos.CENTER);
        GridPane.setHalignment(submitButton, HPos.RIGHT);
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(20);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50);

        gridPane.getColumnConstraints().addAll(col1, col2);

        submitButton.setOnAction(event -> {
            String code = codeTf.getText();
            String firstName = firstNameTf.getText();
            String lastName = lastNameTf.getText();
            String phone = phoneTf.getText();
            String address = addressTf.getText();
            String email = emailTf.getText();
            addCustomerHandler(new Customer(null, code, firstName, lastName, phone, address, email));

            AppController.showCustomers();
        });
        return gridPane;
    }

    public static Pane getCustomerInfoPane(Customer customer) {
        GridPane gridPane = new GridPane();

        Label codeLabel = new Label("Code : ");
        Label firstNameLabel = new Label("Prénom : ");
        Label lastNameLabel = new Label("Nom : ");
        Label telLabel = new Label("Téléphone : ");
        Label addressLabel = new Label("Adresse : ");
        Label emailLabel = new Label("Email : ");

        Label codeValueLabel = new Label(customer.getCode());
        Label firstNameValueLabel = new Label(customer.getFirstName());
        Label lastNameValueLabel = new Label(customer.getLastName());
        Label telValueLabel = new Label(customer.getPhone());
        Label addressValueLabel = new Label(customer.getAddress());
        Label emailValueLabel = new Label(customer.getEmail());

        gridPane.add(codeLabel, 0, 0);
        gridPane.add(firstNameLabel, 0, 1);
        gridPane.add(lastNameLabel, 0, 2);
        gridPane.add(telLabel, 2, 0);
        gridPane.add(addressLabel, 2, 1);
        gridPane.add(emailLabel, 2, 2);

        gridPane.add(codeValueLabel, 1, 0);
        gridPane.add(firstNameValueLabel, 1, 1);
        gridPane.add(lastNameValueLabel, 1, 2);
        gridPane.add(telValueLabel, 3, 0);
        gridPane.add(addressValueLabel, 3, 1);
        gridPane.add(emailValueLabel, 3, 2);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(20);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(20);
        ColumnConstraints col3 = new ColumnConstraints();
        col2.setPercentWidth(20);
        ColumnConstraints col4 = new ColumnConstraints();
        col2.setPercentWidth(20);

        gridPane.getColumnConstraints().addAll(col1, col2, col3, col4);

        gridPane.setHgap(5);
        gridPane.setVgap(10);

        return gridPane;
    }

    public static void addCustomerHandler(Customer customer) {
        customerDao.create(customer);
    }
}