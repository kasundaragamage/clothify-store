package edu.icet.crm.controller.customer;

import edu.icet.crm.model.Customer;
import javafx.collections.ObservableList;

public interface CustomerService {

    Customer searchCustomer(String customerId);
    ObservableList<Customer> loadCustomers();
    boolean addCustomer(Customer customer);
}
