package com.example.demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.formula.functions.T;

import java.io.Serializable;
import java.util.List;

/**
 * @author tangcanming
 * @date 2024/1/2 13:37
 * @describe
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyParam<T> implements Serializable {
    private int size;
    private List<T> entityList;

    private int limit;
}
