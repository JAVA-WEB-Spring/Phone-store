package com.codegym.phone.controller;

import com.codegym.phone.model.Brand;
import com.codegym.phone.model.Category;
import com.codegym.phone.model.Product;
import com.codegym.phone.model.ProductForm;
import com.codegym.phone.service.BrandService;
import com.codegym.phone.service.CategoryService;
import com.codegym.phone.service.ProductService;
import com.codegym.phone.ultils.StorageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Controller
public class ProductController {
    @Autowired
    ProductService productService;

    @Autowired
    BrandService brandService;

    @Autowired
    CategoryService categoryService;

    @ModelAttribute("brands")
    public Iterable<Brand> brands() {
        return brandService.findAll();
    }

    @ModelAttribute("categories")
    public Iterable<Category> categories() {
        return categoryService.findAll();
    }

    @GetMapping("/products")
    public ModelAndView listProduct(@RequestParam("s")Optional<String> s, Pageable pageable) {
        Page<Product> products ;
        if (s.isPresent()) {
            products = productService.findAllByNameContaining(s.get(), pageable);
        } else {
            products = productService.findAll(pageable);
        }

        ModelAndView modelAndView = new ModelAndView("/product/list");
        modelAndView.addObject("products", products);
        return modelAndView;
    }

    @GetMapping("/create-product")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("/product/create");
        modelAndView.addObject("productForm", new ProductForm());
        return modelAndView;
    }

    @PostMapping("/create-product")
    public ModelAndView createProduct(@ModelAttribute("productForm") ProductForm productForm) throws IOException {
        ModelAndView modelAndView = new ModelAndView("/product/create");
        String randomCode = UUID.randomUUID().toString();
        String originFileName = productForm.getImage().getOriginalFilename();
        String randomName = randomCode + StorageUtils.getFileExtension(originFileName);
        productForm.getImage().transferTo(new File(StorageUtils.FEATURE_LOCATION + "/" + randomName));


        Product product = new Product();
        product.setCode(productForm.getCode());
        product.setName(productForm.getName());
        product.setPrice(productForm.getPrice());
        product.setImage(randomName);
        product.setBrand(productForm.getBrand());
        product.setCategories(productForm.getCategories());

        productService.save(product);
        product.setCode("PH" + product.getId());
        productService.save(product);
        modelAndView.addObject("productForm", new ProductForm());
        modelAndView.addObject("message", "New product has been created");
        return modelAndView;
    }

    @GetMapping("/edit-product/{id}")
    public ModelAndView showEditForm(@PathVariable("id") Long id) {
        Product product = productService.findById(id);
        ProductForm productForm = new ProductForm();

        productForm.setId(product.getId());
        productForm.setName(product.getName());
        productForm.setBrand(product.getBrand());
        productForm.setPrice(product.getPrice());
        productForm.setCategories(product.getCategories());
        productForm.setImageUrl(product.getImage());

        ModelAndView modelAndView;
        if (product != null) {
            modelAndView = new ModelAndView("product/edit");
            modelAndView.addObject("productForm", productForm);
            return modelAndView;
        } else {
            modelAndView = new ModelAndView("error-404");
            return modelAndView;
        }
    }

    @PostMapping("/edit-product/{id}")
    public ModelAndView editProduct(@ModelAttribute("productForm") ProductForm productForm,
                                    @PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/product/edit");
        Product product = productService.findById(id);
        if (!productForm.getImage().isEmpty()) {
            StorageUtils.removeFeature(product.getImage());
            String randomCode = UUID.randomUUID().toString();
            String originFileName = productForm.getImage().getOriginalFilename();
            String randomName = randomCode + StorageUtils.getFileExtension(originFileName);
            try {
                productForm.getImage().transferTo(new File(StorageUtils.FEATURE_LOCATION + "/" + randomName));
            } catch (IOException e) {
                e.printStackTrace();
            }
            product.setImage(randomName);
            productForm.setImageUrl(randomName);
        }

        product.setName(productForm.getName());
        product.setPrice(productForm.getPrice());
        product.setBrand(productForm.getBrand());
        product.setCategories(productForm.getCategories());
        productService.save(product);
        product.setCode("PH" + product.getId());
        productService.save(product);
        modelAndView.addObject("productForm", productForm);
        modelAndView.addObject("message", "This product has been edited successfully");
        return modelAndView;
        }

    @GetMapping("/delete-product/{id}")
    public ModelAndView showDeleteForm(@PathVariable("id") Long id) {
        Product product = productService.findById(id);
        if (product != null) {
            ModelAndView modelAndView = new ModelAndView("/product/delete");
            modelAndView.addObject("product", product);
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("error-404");
            return modelAndView;
        }
    }

    @PostMapping("/delete-product")
    public String deleteProduct(@ModelAttribute("product") Product product ) {
        productService.remove(product.getId());
        return "redirect:products";
    }
}
