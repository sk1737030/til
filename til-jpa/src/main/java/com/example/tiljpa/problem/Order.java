package com.example.tiljpa.problem;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "t_order")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    private String orderStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberEmail", referencedColumnName = "memberEmail", nullable = false, updatable = false)
    private Member member;

    public Order(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void assignMember(Member member) {
        this.member = member;
    }
}