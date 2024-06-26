package edu.icet.crm.controller.item;

import edu.icet.crm.db.DBConnection;
import edu.icet.crm.model.Customer;
import edu.icet.crm.model.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemController {
    private static ItemController instance;

    private ItemController(){}

    public static ItemController getInstance(){
        if(instance==null){
            return instance = new ItemController();
        }
        return instance;
    }

    public Item searchItem(String itemCode){

        try {
            PreparedStatement psTm=DBConnection.getInstance().getConnection().prepareStatement("SELECT*FROM item WHERE ItemCode=?");
            psTm.setString(1,itemCode);
            ResultSet resultSet=psTm.executeQuery();
            while(resultSet.next()){
                return new Item(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getDouble(6),
                        resultSet.getInt(7)
                );
            }

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public ObservableList<Item> loadItems() {
        try {
            ResultSet resultSet = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT*FROM item");
            ObservableList<Item> itemList = FXCollections.observableArrayList();
            while(resultSet.next()){

                Item item = new Item(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getDouble(6),
                        resultSet.getInt(7)

                );
                itemList.add(item);

            }
            return itemList;
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
