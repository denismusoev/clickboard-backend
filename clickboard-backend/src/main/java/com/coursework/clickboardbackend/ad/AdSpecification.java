package com.coursework.clickboardbackend.ad;

import com.coursework.clickboardbackend.ad.model.Ad;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AdSpecification {

    public Specification<Ad> titleContains(String title) {
        return (root, query, criteriaBuilder) ->
                title == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }

    public Specification<Ad> categoryEquals(Integer categoryId) {
        return (root, query, criteriaBuilder) ->
                categoryId == null ? null : criteriaBuilder.equal(root.get("category").get("id"), categoryId);
    }

    public Specification<Ad> attributesMatch(Map<String, String> attributes) {
        return (root, query, criteriaBuilder) -> {
            if (attributes == null || attributes.isEmpty()) {
                return null;
            }

            // Join with adAttributes table
            var join = root.join("adAttributes");
            var predicates = attributes.entrySet().stream()
                    .map(entry -> criteriaBuilder.and(
                            criteriaBuilder.equal(join.get("attribute").get("name"), entry.getKey()),
                            criteriaBuilder.equal(join.get("value"), entry.getValue())
                    ))
                    .toArray(Predicate[]::new);

            return criteriaBuilder.and(predicates);
        };
    }
}

