package com.safina.shoppingcart.controller;

import com.safina.shoppingcart.dto.ProductDto;
import com.safina.shoppingcart.exceptions.AlreadyExistsException;
import com.safina.shoppingcart.exceptions.ResourceNotFoundException;
import com.safina.shoppingcart.model.Product;
import com.safina.shoppingcart.request.AddProductRequest;
import com.safina.shoppingcart.request.ProductUpdateRequest;
import com.safina.shoppingcart.response.apiResponse;
import com.safina.shoppingcart.service.image.ImageService;
import com.safina.shoppingcart.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {
    private final IProductService productService;

    @GetMapping("/all")
    public ResponseEntity<apiResponse> getAllProducts(){
        List<Product> products = productService.getAllProducts();
        List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
        return ResponseEntity.ok(new apiResponse("success",convertedProducts));
    }

    @GetMapping("product/{productId}/product")
    public ResponseEntity<apiResponse> getProductById(@PathVariable Long productId){
        try {
            Product product = productService.getProductById(productId);
            ProductDto productDto = productService.convertToDto(product);
            return ResponseEntity.ok(new apiResponse("success",productDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new apiResponse(e.getMessage(),null));
        }
    }



    @PostMapping("/add")
    public ResponseEntity<apiResponse> addProduct(@RequestBody AddProductRequest request){
        try {
            Product product=productService.addProduct(request);
            ProductDto productDto = productService.convertToDto(product);
            return ResponseEntity.ok(new apiResponse("Added Product Successfully",productDto));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new apiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/products/{productId}/update")
    public ResponseEntity<apiResponse> updateProduct(@RequestBody ProductUpdateRequest request, @PathVariable Long productId){
        try {
            Product product=productService.updateProduct(request,productId);
            ProductDto productDto = productService.convertToDto(product);
            return ResponseEntity.ok(new apiResponse("Updated Product Successfully",productDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new apiResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping("/product/{productId}/delete")
    public ResponseEntity<apiResponse> deleteProduct(@PathVariable Long productId){
        try {
            productService.deleteProductById(productId);
            return ResponseEntity.ok(new apiResponse("Deleted Product Successfully",productId));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new apiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("products/by/brand-and-name")
    public ResponseEntity<apiResponse> getProductByBrandAndName(@RequestParam String brandName,@RequestParam String productName){
        try {
            List<Product> products=productService.getProductsByBrandAndName(brandName,productName);
            if(products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new apiResponse("Products Not Found",null));
            }
            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
            return ResponseEntity.ok(new apiResponse("sucess",convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new apiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("products/by/category-and-brand")
    public ResponseEntity<apiResponse> getProductByCategoryAndBrand(@PathVariable String category,@PathVariable String brand){
        try {
            List<Product> products=productService.getProductsByCategoryAndBrand(category,brand);
            if(products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new apiResponse("Products Not Found",null));
            }
            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
            return ResponseEntity.ok(new apiResponse("sucess",convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new apiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("products/{name}/products")
    public ResponseEntity<apiResponse> getProductByName(@PathVariable String name){
        try {
            List<Product> products=productService.getProductsByName(name);
            if(products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new apiResponse("Products Not Found",null));
            }
            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
            return ResponseEntity.ok(new apiResponse("sucess",convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new apiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/product/by-brand")
    public ResponseEntity<apiResponse> findProductByBrand(@RequestParam String brand){
        try {
            List<Product> products=productService.getProductsByBrand(brand);
            if(products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new apiResponse("Products Not Found",null));
            }
            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
            return ResponseEntity.ok(new apiResponse("sucess",convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new apiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("product/{category}/all/products")
    public ResponseEntity<apiResponse> findProductByCategory(@PathVariable String category){
        try {
            List<Product> products=productService.getAProductsByCategory(category);
            if(products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new apiResponse("Products Not Found",null));
            }
            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
            return ResponseEntity.ok(new apiResponse("sucess",convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new apiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("product/count/by-brand/and-name")
    public ResponseEntity<apiResponse> countProductsByBrandAndName(@RequestParam String brand,@RequestParam String name){
        try {
            var productCount=productService.countProductsByBrandAndName(brand,name);
            return ResponseEntity.ok(new apiResponse("Product Count",productCount));
        } catch (Exception e) {
            return ResponseEntity.ok(new apiResponse(e.getMessage(),null));
        }
    }
}
