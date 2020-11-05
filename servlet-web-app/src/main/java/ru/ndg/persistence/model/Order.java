package ru.ndg.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private Integer id;
    private String number;
    private Date date;
    private List<OrderItem> orderItems;
    private BigDecimal sum;
}
