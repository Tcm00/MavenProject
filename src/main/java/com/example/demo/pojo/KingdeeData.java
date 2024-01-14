package com.example.demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author tangcanming
 * @date 2024/1/5 15:48
 * @describe
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KingdeeData implements Serializable {
    private String billno;
    private String billstatus;
    private String cqkd_name;
    private String cqkd_sex;
    private String cqkd_birthday;
    private String cqkd_registration_date;
}
