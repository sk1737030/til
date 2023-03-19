package com.example.springbatch.core.domain.orders;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@AllArgsConstructor
@Entity
@NoArgsConstructor
@Getter
@ToString
public class Orders {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String orderItem;
  private Integer price;
  private Date orderDate;

}
