package edu.icet.crm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Item {
    private String itemCode;
    private String category;
    private String description;
    private String clothType;
    private String packSize;
    private Double price;
    private Integer qty;
}
