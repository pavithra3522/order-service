package com.example.order_service.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.order_service.dto.ProductDto;
import com.example.order_service.model.Order;
import com.example.order_service.repository.OrderRepository;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderRepository repo;
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Value("${app.productServiceUrl}")
    private String productServiceUrl;
 
    @GetMapping
    public List<Order> getAllOrders() {
        return repo.findAll();
    }
 
    @PostMapping
    public Order placeOrder(@RequestBody Map<String, Object> body) {
        String productId = (String) body.get("productId");
        Integer quantity = body.get("quantity") == null ? 1 : (Integer) body.get("quantity");
 
        if (productId == null || productId.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "productId is required");
        }
 

        String url = productServiceUrl + "/products/" + productId;
        ProductDto product = restTemplate.getForObject(url, ProductDto.class);
        if (product == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found: " + productId);
        }
 
        Order order = new Order();
        order.setName(product.getName());
        order.setPrice(product.getDiscountedPrice());
        order.setQuantity(quantity);
 
        return repo.save(order);
    }
    
    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable String id) {
    	repo.deleteById(id);
    }
}
