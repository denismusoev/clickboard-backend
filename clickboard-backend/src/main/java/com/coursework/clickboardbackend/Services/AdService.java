package com.coursework.clickboardbackend.Services;

import com.coursework.clickboardbackend.Models.Database.Ad.Category;
import com.coursework.clickboardbackend.Models.Database.Product.Ad;
import com.coursework.clickboardbackend.Repositories.CategoryRepository;
import com.coursework.clickboardbackend.Repositories.AdRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Transactional
public class AdService {
    @Autowired
    private AdRepository adRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public AdService(AdRepository adRepository) {
        this.adRepository = adRepository;
    }

    public List<Category> getCategories(){
        return categoryRepository.findAll();
    }

    public Page<Ad> filterByCategory(int categoryId, Pageable pageable){
        return adRepository.findByCategory_Id(categoryId, pageable);
    }

    public Page<Ad> getAll(Pageable pageable) {
        return adRepository.findAll(pageable);
    }

    public Ad getById(int adId){
        return adRepository.findById(adId).orElseThrow();
    }

//    public void test(){
//
//        try {
//
//            List<Ad> ads = adRepository.findAll();
//            for (int i = 0; i < ads.size(); i++){
//                Ad ad = ads.get(i);
//                String imagePath = "";
//                switch (ad.getId()){
//                    case 1:
//                        imagePath = "images/Gainward GeForce RTX 4090 Phantom GS.png";
//                        break;
//                    case 2:
//                        imagePath = "images/DEEPCOOL LS720 WH.png";
//                        break;
//                    case 3:
//                        imagePath = "images/Intel Core i9-14900K.png";
//                        break;
//                    case 4:
//                        imagePath = "images/MSI Katana B12VFK-463XRU.png";
//                        break;
//                    case 5:
//                        imagePath = "images/Razer Viper Ultimate.png";
//                        break;
//                }
//                ClassPathResource imageResource = new ClassPathResource(imagePath);
//                Path path = imageResource.getFile().toPath();
//                byte[] imageBytes = Files.readAllBytes(path);
//                ad.setImage(imageBytes);
//                adRepository.save(ad);
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public Page<Ad> searchByCategoryAndName(int categoryId, String name, Pageable pageable) {
        return adRepository.findByCategoryAndNameContainingIgnoreCase(categoryId, name, pageable);
    }
}
