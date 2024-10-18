package com.example.customer.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="product",schema = "myschema")
public class Product {
    @Id
    // @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer productId;
    private String productName;
    private String productDetails;

}
