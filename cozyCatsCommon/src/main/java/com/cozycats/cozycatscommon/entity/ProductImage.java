package com.cozycats.cozycatscommon.entity;

import javax.persistence.*;

@Entity
@Table(name = "product_images")
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public ProductImage() {
    }

    public ProductImage(String name, Product product) {
        this.name = name;
        this.product = product;
    }

    public ProductImage(Integer id, String name, Product product) {
        this.id = id;
        this.name = name;
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Transient
    public String getImagePath(){
        return "/product-images/" + product.getId() + "/extras/" + this.name;
    }
}
