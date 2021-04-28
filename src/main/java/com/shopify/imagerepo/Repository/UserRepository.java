package com.shopify.imagerepo.Repository;

import com.shopify.imagerepo.Model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}
