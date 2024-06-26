package edu.icet.crm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Customer {
    private String id;
    private String title;
    private String name;
    private Date dob;
    private Integer contactNo;
    private String address;
    private String city;
    private String province;
    private String postalCode;
}
