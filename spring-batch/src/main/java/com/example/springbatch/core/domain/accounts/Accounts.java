package com.example.springbatch.core.domain.accounts;

import com.example.springbatch.core.domain.orders.Orders;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@NoArgsConstructor
@Getter
@ToString
public class Accounts {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String orderItem;
  private Integer price;
  private Date orderDate;
  private Date accountDate;


  public Accounts(Orders item) {
    this.id = item.getId();
    this.orderItem = item.getOrderItem();
    this.price = item.getPrice();
    this.orderDate = item.getOrderDate();
    this.accountDate = new Date();
  }

}
