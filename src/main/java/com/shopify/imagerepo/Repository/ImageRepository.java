package com.shopify.imagerepo.Repository;

import com.shopify.imagerepo.Model.Image;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ImageRepository extends PagingAndSortingRepository<Image, Integer> {
}
