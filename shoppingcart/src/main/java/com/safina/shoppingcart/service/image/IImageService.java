package com.safina.shoppingcart.service.image;

import com.safina.shoppingcart.dto.ImageDto;
import com.safina.shoppingcart.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    Image getImageById(long id);
    void deleteImageById(long id);
    List<ImageDto> saveImage(List<MultipartFile> files, Long productId);
    void updateImage(MultipartFile file,Long imageId);


}
