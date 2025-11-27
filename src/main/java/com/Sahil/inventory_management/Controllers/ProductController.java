package com.Sahil.inventory_management.Controllers;

import com.Sahil.inventory_management.DTO.BaseResponseDTO;
import com.Sahil.inventory_management.DTO.ProductDTO;
import com.Sahil.inventory_management.DTO.ProductPageResponse;
import com.Sahil.inventory_management.DTO.StockDTO;
import com.Sahil.inventory_management.Service.IProductService;
import com.Sahil.inventory_management.model.Product;
import com.Sahil.inventory_management.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {

    private final IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }


    //  Add new product — Only Admin
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<BaseResponseDTO<ProductDTO>> addProduct(@RequestBody ProductDTO productDTO) {
        ProductDTO saved = productService.addProduct(productDTO);
        return ResponseEntity.ok(BaseResponseDTO.success("Product added successfully", saved));
    }


    //  Get product by ID — All roles
    @PreAuthorize("hasAnyRole('ADMIN', 'DEALER', 'CUSTOMER')")
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponseDTO<ProductDTO>> getProductById(@PathVariable Long id) {
        ProductDTO product = productService.getProductById(id);
        return ResponseEntity.ok(BaseResponseDTO.success("Product fetched successfully", product));
    }


    //  Get All Product + Filter + Pagination
    @PreAuthorize("hasAnyRole('ADMIN', 'DEALER', 'CUSTOMER')")
    @GetMapping
    public ResponseEntity<BaseResponseDTO<ProductPageResponse>> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice
    ) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();

        Long userId = currentUser.getId();
        String name = currentUser.getName();
        String role = currentUser.getRole().toString();
        String email = currentUser.getEmail();

        List<ProductDTO> products = productService.getProducts(page, size, category, brand, minPrice, maxPrice);
        Long totalCount = productService.gettotalcount();
        String message;
        if (category == null && brand == null && minPrice == null && maxPrice == null) {
            message = "All products fetched successfully";
        } else {
            message = "Filtered products fetched successfully";
        }

        ProductPageResponse data = new ProductPageResponse(products, totalCount);

        return ResponseEntity.ok(BaseResponseDTO.success(message, data));

    }



    //  Update product — Only Admin
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<BaseResponseDTO<ProductDTO>> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductDTO productDTO) {
        ProductDTO updatedProduct = productService.updateProduct(id, productDTO);
        return ResponseEntity.ok(BaseResponseDTO.success("Product updated successfully", updatedProduct));
    }


    //  Delete product — Only Admin
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponseDTO<String>> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(BaseResponseDTO.success("Product deleted successfully", null));
    }


    //  Update stock — Dealer
    @PreAuthorize("hasRole('DEALER')")
    @PatchMapping("/{id}/stock")
    public ResponseEntity<BaseResponseDTO<Product>> updateStockQuantity(
            @PathVariable Long id,
            @RequestBody StockDTO stockDTO
    ) {
        System.out.println("HELLO WORLD");
        Product updatedProduct = productService.updateStockQuantity(id, stockDTO.getQuantity());
        return ResponseEntity.ok(BaseResponseDTO.success("Stock quantity updated successfully", updatedProduct));
    }



    //  View low-stock products — Admin
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/low-stock")
    public ResponseEntity<BaseResponseDTO<List<ProductDTO>>> getLowStockProducts() {
        List<ProductDTO> lowStockProducts = productService.getLowStockProducts();
        return ResponseEntity.ok(BaseResponseDTO.success("Low stock products fetched successfully", lowStockProducts));
    }




}
