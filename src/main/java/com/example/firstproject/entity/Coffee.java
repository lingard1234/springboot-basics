package com.example.firstproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Entity
@Getter
@AllArgsConstructor
@ToString
public class Coffee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB가 id 자동생성
    private Long id;
    @Column
    private String name;
    @Column
    private String price;

    public Coffee() {
    }

    public void patch(Coffee coffee) {
        if(coffee.name != null) {
            this.name = coffee.name;
        }
        if(coffee.price != null) {
            this.price = coffee.price;
        }
    }
}