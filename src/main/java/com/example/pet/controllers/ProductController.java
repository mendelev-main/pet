package com.example.pet.controllers;

import com.example.pet.models.Product;
import com.example.pet.repositories.ProductRepository;
import com.example.pet.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Base64;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/")
    public String products(@RequestParam(name = "title", required = false) String title, Model model) {
        model.addAttribute("products", productService.listProducts(title));
        return "products";

    }

    @GetMapping("product/{id}")
    public String productInfo(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "product-info";
    }

    @GetMapping("product/create")
    public String createProductGet() {
        return "product-create";

    }

    @PostMapping("product/create")
    public String createProductPost(
            @ModelAttribute Product product, RedirectAttributes redirectAttributes) throws IOException {
        productService.saveProduct(product);
        redirectAttributes.addAttribute("id", product.getId());
        return "redirect:/product/{id}";

    }

    @PostMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/";
    }


}
