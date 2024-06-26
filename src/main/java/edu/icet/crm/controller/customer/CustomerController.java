package edu.icet.crm.controller.customer;

import edu.icet.crm.db.DBConnection;
import edu.icet.crm.model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomerController implements CustomerService{
    private static CustomerController instance;
    private CustomerController(){}

    public static CustomerController getInstance(){
        if(instance==null){
            return instance= new CustomerController();
        }
        return instance;
    }

    public Customer searchCustomer(String customerId){

        try {
            PreparedStatement psTm=DBConnection.getInstance().getConnection().prepareStatement("SELECT*FROM customer WHERE CustID=?");
            psTm.setString(1,customerId);
            ResultSet resultSet=psTm.executeQuery();
            while(resultSet.next()){
                return new Customer(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDate(4),
                        resultSet.getInt(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getString(8),
                        resultSet.getString(9)
                );
            }

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public ObservableList<Customer> loadCustomers() {

        try {
            ResultSet resultSet = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT*FROM customer");
            ObservableList<Customer> customerList = FXCollections.observableArrayList();
            while(resultSet.next()){

                Customer customer = new Customer(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDate(4),
                        resultSet.getInt(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getString(8),
                        resultSet.getString(9)
                );
                customerList.add(customer);

            }
            return customerList;
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean addCustomer(Customer customer){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement("INSERT INTO Customer VALUES(?,?,?,?,?,?,?,?,?)");
            psTm.setString(1, customer.getId());
            psTm.setString(2,customer.getTitle());
            psTm.setString(3,customer.getName());
            psTm.setObject(4,customer.getDob());
            psTm.setInt(5,customer.getContactNo());
            psTm.setString(6,customer.getAddress());
            psTm.setString(7,customer.getCity());
            psTm.setString(8,customer.getProvince());
            psTm.setString(9,customer.getPostalCode());

            psTm.execute();

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
