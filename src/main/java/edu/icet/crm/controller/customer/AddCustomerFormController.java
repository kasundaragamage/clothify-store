package edu.icet.crm.controller.customer;

import com.jfoenix.controls.JFXTextField;
import edu.icet.crm.controller.customer.CustomerController;
import edu.icet.crm.db.DBConnection;
import edu.icet.crm.model.Customer;
import edu.icet.crm.model.tm.Table01TM;
import edu.icet.crm.model.tm.Table02TM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class AddCustomerFormController implements Initializable {

    public JFXTextField txtCustomerId;
    public ChoiceBox cmbTitle;
    public JFXTextField txtName;
    public DatePicker dateDOB;
    public JFXTextField txtAddress;
    public JFXTextField txtCity;
    public JFXTextField txtProvince;
    public JFXTextField txtPostalCode;
    public TableView tblCustomer01;
    public TableColumn colCustomerId;
    public TableColumn colTitle;
    public TableColumn colName;
    public TableColumn colDob;
    public TableColumn colContactNo;
    public TableView tblCustomer02;
    public TableColumn colCustomerId02;
    public TableColumn colAddress;
    public TableColumn colCity;
    public TableColumn colProvince;
    public TableColumn colPostalCode;
    public JFXTextField txtContactNo;



    public void btnAddCustomerOnAction(ActionEvent actionEvent) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(dateDOB.getValue().toString());
        Customer customer = new Customer(
                txtCustomerId.getText(),
                cmbTitle.getValue().toString(),
                txtName.getText(),
                date,
                Integer.parseInt(txtContactNo.getText()),
                txtAddress.getText(),
                txtCity.getText(),
                txtProvince.getText(),
                txtPostalCode.getText()
        );

        boolean b = CustomerController.getInstance().addCustomer(customer);
        if (b){
            new Alert(Alert.AlertType.ERROR,"Customer Not Added !").show();
        }else{
            new Alert(Alert.AlertType.CONFIRMATION,"Customer Added !").show();
        }

        loadTable01();
        loadTable02();
        clearText();
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        try {
            boolean execute = DBConnection.getInstance().getConnection().createStatement().execute("DELETE FROM customer WHERE CustId='" + txtCustomerId.getText() + "'");

            loadTable01();
            loadTable02();
            clearText();
            if (execute){
                System.out.println("Customer Not Deleted");
            }else{
                System.out.println("Customer Deleted");
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        Customer customer = CustomerController.getInstance().searchCustomer(txtCustomerId.getText());
        System.out.println(customer);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colContactNo.setCellValueFactory(new PropertyValueFactory<>("contactNo"));
        colCustomerId02.setCellValueFactory(new PropertyValueFactory<>("id"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colProvince.setCellValueFactory(new PropertyValueFactory<>("province"));
        colPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        loadDropMenu();

        loadTable01();
        loadTable02();
    }

    private void loadTable02() {
        ObservableList<Table02TM> table02Data = FXCollections.observableArrayList();
        ObservableList<Customer> allCustomers = CustomerController.getInstance().loadCustomers();

        allCustomers.forEach(customer -> {
            Table02TM table02 = new Table02TM(
                    customer.getId(),
                    customer.getAddress(),
                    customer.getCity(),
                    customer.getProvince(),
                    customer.getPostalCode()
            );
            table02Data.add(table02);
        });
        tblCustomer02.setItems(table02Data);
    }

    private void loadTable01() {
        ObservableList<Table01TM> table01Data = FXCollections.observableArrayList();
        ObservableList<Customer> allCustomers = CustomerController.getInstance().loadCustomers();
        allCustomers.forEach(customer -> {
            Table01TM table01 = new Table01TM(
                    customer.getId(),
                    customer.getTitle(),
                    customer.getName(),
                    customer.getDob(),
                    customer.getContactNo()
            );
            table01Data.add(table01);
        });
        tblCustomer01.setItems(table01Data);
    }



    private void loadDropMenu() {
        ObservableList<Object> items = FXCollections.observableArrayList();
        items.add("Mr");
        items.add("Mrs");
        items.add("Miss");
        cmbTitle.setItems(items);
    }
    private void clearText(){
        txtCustomerId.setText(null);
        cmbTitle.setValue(null);
        txtName.setText(null);
        dateDOB.setValue(null);
        txtContactNo.setText(null);
        txtAddress.setText(null);
        txtCity.setText(null);
        txtProvince.setText(null);
        txtPostalCode.setText(null);

    }


}
