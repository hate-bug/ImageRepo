package com.shopify.imagerepo.Repository;

import com.shopify.imagerepo.Model.Image;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface ImageRepository extends PagingAndSortingRepository<Image, Long> {

    Optional<Image> findById (Long id);
}
