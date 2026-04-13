package com.safina.shoppingcart.service.image;

import com.safina.shoppingcart.dto.ImageDto;
import com.safina.shoppingcart.exceptions.ResurceNotFoundException;
import com.safina.shoppingcart.model.Image;
import com.safina.shoppingcart.model.Product;
import com.safina.shoppingcart.repository.ImageRepository;
import com.safina.shoppingcart.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService{
    private ImageRepository imageRepository;
    private IProductService productService;


    @Override
    public Image getImageById(long id) {
        return imageRepository.findById(id)
                .orElseThrow(()->new ResurceNotFoundException("No image found with id:"+id));
    }

    @Override
    public void deleteImageById(long id) {
        imageRepository.findById(id).ifPresentOrElse(imageRepository::delete,()->{
            throw new ResurceNotFoundException("No image found with id:"+id);
        });
    }

    @Override
    public List<ImageDto> saveImage(List<MultipartFile> files, Long productId) {
        return List.of();
    }

    @Override
    public List<ImageDto> saveImages(List<MultipartFile> files, Long productId) {
        Product product = productService.getProductById(productId);
        List<ImageDto> savedImageDto=new ArrayList<>();

        for(MultipartFile file:files){
            try{
                Image image=new Image();
                image.setFileName(file.getOriginalFilename());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                String buildDownloadUrl="/api/v1/images/image/download/";

                String downladURL =buildDownloadUrl + image.getId();
                image.setDownloadUrl(downladURL);
                Image savedImage = imageRepository.save(image);

                savedImage.setDownloadUrl(buildDownloadUrl +savedImage.getId());
                imageRepository.save(savedImage);

                ImageDto imageDto1=new ImageDto();
                imageDto1.setImageId(savedImage.getId());
                imageDto1.setImageName(savedImage.getFileName());
                imageDto1.setDownloadURL(savedImage.getDownloadUrl());
                savedImageDto.add(imageDto1);
            }
            catch(IOException  | SQLException e){
                throw new RuntimeException(e.getMessage());
            }
        }
        return savedImageDto;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Image image = getImageById(imageId);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileName(file.getOriginalFilename());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

    }
}
