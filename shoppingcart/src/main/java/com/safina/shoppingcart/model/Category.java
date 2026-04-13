package com.safina.shoppingcart.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    public Category(String name) {
        this.name = name;
    }


    @OneToMany(mappedBy = "category")
    private List<Product> Products;
}
