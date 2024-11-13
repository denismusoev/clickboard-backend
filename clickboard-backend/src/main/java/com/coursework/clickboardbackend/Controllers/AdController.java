package com.coursework.clickboardbackend.Controllers;

import com.coursework.clickboardbackend.Models.DTO.Ad.CategoryCompositeDTO;
import com.coursework.clickboardbackend.Models.DTO.Product.AdViewDTO;
import com.coursework.clickboardbackend.Services.AdService;
import com.coursework.clickboardbackend.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ads")
public class AdController {
    @Autowired
    private AdService adService;
    @Autowired
    private UserService userService;

    @GetMapping(path = "/sortByPrice/{categoryId}")
    public Page<AdViewDTO> filterByCategory(
            @PathVariable int categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "price") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return adService.filterByCategory(categoryId, pageable).map(AdViewDTO::new);
    }

    @GetMapping(path = "/byCategory/{categoryId}")
    public Page<AdViewDTO> filterByCategory(@PathVariable int categoryId,
                                                 @RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return adService.filterByCategory(categoryId, pageable).map(AdViewDTO::new);
    }

    @GetMapping(path = "/categories")
    public List<CategoryCompositeDTO> getCategories() {
        return adService.getCategories().stream().map(CategoryCompositeDTO::new).toList();
    }

    @GetMapping(path = "/search/{categoryId}")
    public Page<AdViewDTO> search(
            @PathVariable int categoryId,
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "price") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sortBy));
        return adService.searchByCategoryAndName(categoryId, name, pageable).map(AdViewDTO::new);
    }

    @GetMapping(path = "/get/{adId}")
    public AdViewDTO getById(@PathVariable int adId) {
        return new AdViewDTO(adService.getById(adId));
    }


//    @GetMapping(path = "/test")
//    public void test() {
//        adService.test();
//    }


}
