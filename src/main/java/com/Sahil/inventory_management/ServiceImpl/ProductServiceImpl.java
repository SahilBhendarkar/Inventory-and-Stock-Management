package com.Sahil.inventory_management.ServiceImpl;

import com.Sahil.inventory_management.DTO.ProductDTO;
import com.Sahil.inventory_management.Exception.AccessDeniedException;
import com.Sahil.inventory_management.Exception.InvalidInputException;
import com.Sahil.inventory_management.Exception.ResourceNotFoundException;
import com.Sahil.inventory_management.Mapper.Mapper;
import com.Sahil.inventory_management.Repository.ProductRespository;
import com.Sahil.inventory_management.Repository.TransactionLogRepository;
import com.Sahil.inventory_management.Repository.UserRepository;
import com.Sahil.inventory_management.Service.IProductService;
import com.Sahil.inventory_management.model.Product;
import com.Sahil.inventory_management.model.TransactionLog;
import com.Sahil.inventory_management.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductRespository productRespository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionLogRepository transactionLogRepository;


    // ---------------- Add Product ----------------
    @Override
    public ProductDTO addProduct(ProductDTO productDTO) {

        if (productDTO.getName() == null || productDTO.getPrice() == null) {
            throw new InvalidInputException("Product name and price are required.");
        }

        String userRole = getLoggedInUserRole();
        if (!"ADMIN".equalsIgnoreCase(userRole)) {
            throw new AccessDeniedException("Only Admins can add new products.");
        }


        Product product = Mapper.toProduct(productDTO);
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());

        Product saved = productRespository.save(product);
        return Mapper.toProductDTO(saved);
    }


    // ---------------- Get Product by ID ----------------
    @Override
    public ProductDTO getProductById(Long id) {
        Product product = productRespository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));
        return Mapper.toProductDTO(product);
    }

    // ---------------- Get All Products ----------------

    @Override
    public List<ProductDTO> getProducts(int page, int size, String category, String brand, Double minPrice, Double maxPrice) {
        Pageable pageable = PageRequest.of(page, size);

        List<Product> products;

        // If no filters applied → fetch all paginated
        if (category == null && brand == null && minPrice == null && maxPrice == null) {
            products = productRespository.findAll(pageable).getContent();
        } else {
            // Apply filters manually
            products = productRespository.findAll(pageable)
                    .getContent()
                    .stream()
                    .filter(p -> (category == null || p.getCategory().equalsIgnoreCase(category)) &&
                            (brand == null || p.getBrand().equalsIgnoreCase(brand)) &&
                            (minPrice == null || p.getPrice() >= minPrice) &&
                            (maxPrice == null || p.getPrice() <= maxPrice))
                    .collect(Collectors.toList());
        }

        if (products.isEmpty()) {
            throw new ResourceNotFoundException("No products found with the given criteria");
        }
        Long totalcount = productRespository.count();

        return products.stream()
                .map(Mapper::toProductDTO)
                .collect(Collectors.toList());
    }


    // ---------------- Update Product ----------------
    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Product product = productRespository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));

        if (productDTO.getPrice() != null && productDTO.getPrice() < 0) {
            throw new InvalidInputException("Product Price cannot be negative.");
        }

        if (productDTO.getName() != null) product.setName(productDTO.getName());
        if (productDTO.getDescription() != null) product.setDescription(productDTO.getDescription());
        if(productDTO.getBrand() !=null) product.setBrand((productDTO.getBrand()));
        if (productDTO.getPrice() != null) product.setPrice(productDTO.getPrice());
        if (productDTO.getCategory() != null) product.setCategory(productDTO.getCategory());

        product.setUpdatedAt(LocalDateTime.now());
        Product saved = productRespository.save(product);
        return Mapper.toProductDTO(saved);
    }


    // ---------------- Delete Product ----------------
    @Override
    public void deleteProduct(Long id) {
        String userRole = getLoggedInUserRole();
        if (!"ADMIN".equalsIgnoreCase(userRole)) {
            throw new AccessDeniedException("Only Admin can delete products.");
        }
        Product product = productRespository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));
        productRespository.delete(product);
    }


    // ---------------- Update Stock & Save Transaction Log ----------------
    @Override
    public Product updateStockQuantity(Long productId, Integer quantity) {
        Product product = productRespository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + productId));

        // Validate non-negative stock
        if (quantity < 0) {
            throw new InvalidInputException("Stock cannot be negative.");
        }

        // Directly set the new stock (NO addition)
        product.setQuantity(quantity);
        product.setUpdatedAt(LocalDateTime.now());
        productRespository.save(product);

        // Save transaction log
        Long userId = getLoggedInUserId();
        TransactionLog log = new TransactionLog(
                productId,
                userId,
                quantity,
                LocalDateTime.now()
        );
        transactionLogRepository.save(log);

        return product;
    }


    private Long getLoggedInUserId() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + username));

        return user.getId();
    }


    // ---------------- Low Stock Products ----------------
    @Override
    public List<ProductDTO> getLowStockProducts() {
        List<ProductDTO> lowStockProducts = productRespository.findAll().stream()
                .filter(p -> p.getQuantity() < p.getMinStockLevel())
                .map(Mapper::toProductDTO)
                .collect(Collectors.toList());


        String userRole = getLoggedInUserRole();
        if (!"ADMIN".equalsIgnoreCase(userRole)) {
            throw new AccessDeniedException("Only Admin can view low stock reports.");
        }
        if (lowStockProducts.isEmpty()) {
            throw new ResourceNotFoundException("No products found below minimum stock level");
        }

        return lowStockProducts;
    }

    @Override
    public Long gettotalcount() {
        Long totalcount = productRespository.count();
        return totalcount;
    }

    private String getLoggedInUserRole() {
        return "Admin";
    }
}