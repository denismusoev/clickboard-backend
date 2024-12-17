package com.coursework.clickboardbackend.ad;

import com.coursework.clickboardbackend.ad.model.Ad;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AdSpecification {

    public Specification<Ad> titleStartsWith(String title) {
        return (root, query, criteriaBuilder) ->
                title == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), title.toLowerCase() + "%");
    }

    public Specification<Ad> categoryEquals(Integer categoryId) {
        return (root, query, criteriaBuilder) ->
                categoryId == null ? null : criteriaBuilder.equal(root.get("category").get("id"), categoryId);
    }

    public Specification<Ad> priceGreaterThanOrEqual(Integer minPrice) {
        return (root, query, criteriaBuilder) ->
                minPrice == null ? null : criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
    }

    public Specification<Ad> priceLessThanOrEqual(Integer maxPrice) {
        return (root, query, criteriaBuilder) ->
                maxPrice == null ? null : criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
    }

    public Specification<Ad> status(Ad.Status status) {
        return (root, query, criteriaBuilder) ->
                status == null ? null : criteriaBuilder.equal(root.get("status"), status);
    }
}

