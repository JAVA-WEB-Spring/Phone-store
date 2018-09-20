package com.codegym.phone.model;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "product")
public class Product implements Serializable {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;
        private String code;
        private String name;
        private double price;
        private String image;

        public String getImage() {
                return image;
        }

        public void setImage(String image) {
                this.image = image;
        }

        @ManyToOne()
        @JoinColumn(name = "brand_id")
        private Brand brand;

        @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
        @JoinTable(name = "product_category",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id"))
        private List<Category> categories;

        public Product() {
        }

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public String getCode() {
                return code;
        }

        public void setCode(String code) {
                this.code = code;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public double getPrice() {
                return price;
        }

        public void setPrice(double price) {
                this.price = price;
        }

        public Brand getBrand() {
                return brand;
        }

        public void setBrand(Brand brand) {
                this.brand = brand;
        }

        public List<Category> getCategories() {
                return categories;
        }

        public void setCategories(List<Category> categories) {
                this.categories = categories;
        }
}
