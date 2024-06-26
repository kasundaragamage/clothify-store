package edu.icet.crm.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Table02TM {
    private String id;
    private String address;
    private String city;
    private String province;
    private String postalCode;
}
