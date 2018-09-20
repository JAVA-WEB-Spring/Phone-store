package com.codegym.phone.controller;

import com.codegym.phone.model.Brand;
import com.codegym.phone.model.Product;
import com.codegym.phone.service.BrandService;
import com.codegym.phone.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BrandController {
    @Autowired
    BrandService brandService;
    @Autowired
    ProductService productService;
    @GetMapping("/create-brand")
    public ModelAndView showCreate() {
        ModelAndView modelAndView = new ModelAndView("/brand/create");
        modelAndView.addObject("brand", new Brand());
        return modelAndView;
    }

    @PostMapping("/create-brand")
    public ModelAndView saveProduct(@ModelAttribute("brand") Brand brand) {
        brandService.save(brand);
        ModelAndView modelAndView = new ModelAndView("/brand/create");
        modelAndView.addObject("brand", new Brand());
        modelAndView.addObject("message", "Create brand successful");
        return modelAndView;
    }

    @GetMapping("/brands")
    public ModelAndView showList() {
        Iterable<Brand> brands = brandService.findAll();
        ModelAndView modelAndView = new ModelAndView("/brand/list");
        modelAndView.addObject("brands", brands);
        return modelAndView;
    }

    @GetMapping("/edit-brand/{id}")
    public ModelAndView showEditForm(@PathVariable("id") Long id) {
        Brand brand = brandService.findById(id);
        if (brand != null) {
            ModelAndView modelAndView = new ModelAndView("/brand/edit");
            modelAndView.addObject("brand", brand);
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("/error-404");
            return  modelAndView;
        }

    }

    @PostMapping("/edit-brand")
    public ModelAndView updateProduct(@ModelAttribute("brand") Brand brand) {
        brandService.save(brand);
        ModelAndView modelAndView = new ModelAndView("brand/edit");
        modelAndView.addObject("brand", brand);
        modelAndView.addObject("message", "Product has been edited");
        return modelAndView;
    }

    @GetMapping("/delete-brand/{id}")
    public ModelAndView showDeleteForm(@PathVariable("id") Long id) {
        Brand brand = brandService.findById(id);
        if (brand != null) {
            ModelAndView modelAndView = new ModelAndView("/brand/delete");
            modelAndView.addObject("brand", brand);
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("error-404");
            return modelAndView;
        }
    }

    @PostMapping("/delete-brand")
    public String deleteProduct(@ModelAttribute("brand") Brand brand) {
        brandService.remove(brand.getId());
        return "redirect:brands";
    }

    @GetMapping("/view-brand/{id}")
    public ModelAndView showView(@PathVariable("id") Long id) {
        Brand brand = brandService.findById(id);
        if (brand == null) {
            ModelAndView modelAndView = new ModelAndView("error-404");
            return modelAndView;
        } else {
            Iterable<Product> products = productService.findAllByBrand(brand);
            ModelAndView modelAndView = new ModelAndView("/brand/view");
            modelAndView.addObject("brand", brand);
            modelAndView.addObject("products", products);
            return modelAndView;
        }

    }
}
