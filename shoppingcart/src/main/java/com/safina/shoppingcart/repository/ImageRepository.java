package com.safina.shoppingcart.repository;

import com.safina.shoppingcart.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository  extends JpaRepository<Image,Long>{
}
