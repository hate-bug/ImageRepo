package com.shopify.imagerepo.Repository;

import com.shopify.imagerepo.Model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findUserByUserName (String userName);

}
