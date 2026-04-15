package com.safina.shoppingcart.controller;

import com.safina.shoppingcart.exceptions.AlreadyExistsException;
import com.safina.shoppingcart.exceptions.ResourceNotFoundException;
import com.safina.shoppingcart.model.Category;
import com.safina.shoppingcart.response.apiResponse;
import com.safina.shoppingcart.service.category.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {
    private final ICategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<apiResponse> getAllCategories(){
        try{
            List<Category> categories=categoryService.getAllCategories();
            return ResponseEntity.ok(new apiResponse("Found!",categories));
        }
        catch(Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new apiResponse("Error!", INTERNAL_SERVER_ERROR));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<apiResponse> addCategory(@RequestBody Category name){
        try {
            Category theCategory=categoryService.addCategory(name);
            return ResponseEntity.ok(new apiResponse("Sucess!",theCategory));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new apiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/category/{id}/category")
    public ResponseEntity<apiResponse> getCategoryById(@PathVariable Long id){
        try {
            Category theCategory=categoryService.getCategoryById(id);
            return ResponseEntity.ok(new apiResponse("Found!",theCategory));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new apiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/category/{name}/category")
    public ResponseEntity<apiResponse> getCategoryByName(@PathVariable String name){
        try {
            Category theCategory=categoryService.getCategoryByName(name);
            return ResponseEntity.ok(new apiResponse("Found!",theCategory));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new apiResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping("/category/{id}/delete")
    public ResponseEntity<apiResponse> deleteCategory(@PathVariable Long id){
        try {
            Category theCategory=categoryService.getCategoryById(id);
            return ResponseEntity.ok(new apiResponse("Found!",null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new apiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/category/{name}/update")
    public ResponseEntity<apiResponse> getCategoryByName(@PathVariable Long id,@RequestBody Category category){
        try {
            Category updatedCategory=categoryService.updateCategory(category,id);
            return ResponseEntity.ok(new apiResponse("Update Sucess!",updatedCategory));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new apiResponse(e.getMessage(),null));
        }
    }

}
