package com.example.demoproduct.controller;

import com.example.demoproduct.model.Product;
import com.example.demoproduct.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public String listProducts(
            Model model,
            @RequestParam(name = "keyword", required = false) String keyword
    ) {
        model.addAttribute("listProducts", service.listAll(keyword));
        model.addAttribute("keyword", keyword);
        return "index";
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        return "add-product";
    }

    @PostMapping("/save")
    public String saveProduct(
            @Valid @ModelAttribute("product") Product product,
            BindingResult result
    ) {
        if (result.hasErrors()) return "add-product";
        service.save(product);
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Product product = service.get(id);
        if (product == null) return "redirect:/products";
        model.addAttribute("product", product);
        return "edit-product";
    }

    @PostMapping("/update/{id}")
    public String updateProduct(
            @PathVariable Long id,
            @Valid @ModelAttribute("product") Product product,
            BindingResult result
    ) {
        if (result.hasErrors()) return "edit-product";
        product.setId(id);
        service.save(product);
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/products";
    }
}

