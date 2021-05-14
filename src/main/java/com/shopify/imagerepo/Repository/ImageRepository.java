package com.shopify.imagerepo.Repository;

import com.shopify.imagerepo.Model.Image;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import java.util.Optional;

public interface ImageRepository extends PagingAndSortingRepository<Image, Long> {

    Optional<Image> findById (Long id);

    @Query(value = "SELECT * FROM Image I WHERE I.USER_ID = ?1 OR I.IS_PUBLIC = TRUE", nativeQuery = true)
    Iterable<Image> findAllByUserOrisPublic (Long userId);

    @Query(value = "SELECT * FROM Image I WHERE ((I.USER_ID = ?1 OR I.IS_PUBLIC = TRUE) AND I.NAME = ?2)", nativeQuery = true)
    Iterable<Image> findImagesByName (Long userId, String imageName);
}
