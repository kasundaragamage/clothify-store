package edu.icet.crm.controller.order;

import edu.icet.crm.controller.customer.CustomerController;
import edu.icet.crm.controller.item.ItemController;
import edu.icet.crm.db.DBConnection;
import edu.icet.crm.model.Customer;
import edu.icet.crm.model.Item;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Duration;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlaceOrderFormController implements Initializable {
    public ComboBox cmbCustomerId;
    public ComboBox cmbItemId;
    public Label lblTime;
    public Label lblDate;
    public Label lblOrderId;
    public Label lblName;
    public Label lblAddress;
    public Label lblSalary;
    public Label lblCity;
    public Label lblDesc;
    public Label lblPackSize;
    public Label lblUnitPrice;
    public Label lblQty;
    public TableView tblCart;
    public TableColumn colItemCode;
    public TableColumn colDesc;
    public TableColumn colQty;
    public TableColumn colPrice;
    public TableColumn colTotal;
    public Label lblContactNo;
    public Label lblCategory;
    public Label lblType;
    public ComboBox cmbCategory;
    public ComboBox cmbSize;
    public Label lblSize;

    public void btnAddToCartOnAction(ActionEvent actionEvent) {
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {
    }

    public void btnAPICallOnAction(ActionEvent actionEvent) {
    }

    public void btnRollBackOnAction(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDateAndTime();
        loadCustomerIds();
        loadItemCodes();
        generateOrderId();     
        

        cmbCustomerId.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue) -> {
            System.out.println(newValue);
            setCustomerDataForLbl((String)newValue);
        });

        cmbItemId.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue) -> {
            System.out.println(newValue);
            setItemDataForLbl((String)newValue);
        });
    }
    private void setItemDataForLbl(String itemCode) {
        Item item = ItemController.getInstance().searchItem(itemCode);
        lblCategory.setText(item.getCategory());
        lblDesc.setText(item.getDescription());
        lblType.setText(item.getClothType());
        lblPackSize.setText(item.getPackSize());
        lblUnitPrice.setText(String.valueOf(item.getPrice()));
        lblQty.setText(String.valueOf(item.getQty()));
    }

    private void setCustomerDataForLbl(String customerId) {
        Customer customer = CustomerController.getInstance().searchCustomer(customerId);
        lblName.setText(customer.getName());
        lblAddress.setText(customer.getAddress());
        lblContactNo.setText(String.valueOf(customer.getContactNo()));
        lblCity.setText(customer.getCity());
    }

    private void loadItemCodes() {
        ObservableList<Item> items = ItemController.getInstance().loadItems();
        ObservableList<String> itemCode = FXCollections.observableArrayList();
        items.forEach(item -> {
            itemCode.add(item.getItemCode());
        });
        cmbItemId.setItems(itemCode);
    }

    private void loadCustomerIds() {
        ObservableList<Customer> allCustomers = CustomerController.getInstance().loadCustomers();
        ObservableList ids = FXCollections.observableArrayList();
        allCustomers.forEach(customer -> {
            ids.add(customer.getId());
        });

        cmbCustomerId.setItems(ids);
    }

    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));

        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime time = LocalTime.now();
            lblTime.setText(
                    time.getHour()+" : "+time.getMinute()+" : "+time.getSecond()
            );

        }),
        new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public  void generateOrderId() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM orders");
            Integer count = 0;
            while (resultSet.next()){
                count = resultSet.getInt(1);

            }
            if (count == 0) {
                lblOrderId.setText("D001");
            }
            String lastOrderId="";
            ResultSet resultSet1 = connection.createStatement().executeQuery("SELECT OrderID\n" +
                    "FROM orders\n" +
                    "ORDER BY OrderID DESC\n" +
                    "LIMIT 1;");
            if (resultSet1.next()){
                lastOrderId = resultSet1.getString(1);
                Pattern pattern = Pattern.compile("[A-Za-z](\\d+)");
                Matcher matcher = pattern.matcher(lastOrderId);
                if (matcher.find()) {
                    int number = Integer.parseInt(matcher.group(1));
                    number++;
                    lblOrderId.setText(String.format("D%03d", number));
                } else {
                    new Alert(Alert.AlertType.WARNING,"hello").show();
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
