package edu.icet.crm.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Table01TM {
    private String id;
    private String title;
    private String name;
    private Date dob;
    private Integer contactNo;
}
