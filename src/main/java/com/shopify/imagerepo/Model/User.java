package com.shopify.imagerepo.Model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank (message = "The Username cannot be blank for user")
    private String userName;

    @OneToMany (cascade = CascadeType.ALL)
    private Set<Image> imageList;

    public User (){
        this.imageList = new HashSet<>();
    }

}
