package com.shopify.imagerepo.Model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotNull(message = "The Username cannot be blank for user")
    private String userName;

    @OneToMany (cascade = CascadeType.ALL)
    private Set<Image> imageList;

    public void setUserPassword(UserPassword userPassword) {
        this.userPassword = userPassword;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name = "password", column = @Column(nullable = false))
    })
    @NotNull (message = "Password is required.")
    private UserPassword userPassword;

    public User (){
        this.imageList = new HashSet<>();
    }

    public User (String userName, String password) {
        this.userName = userName;
        this.userPassword = new UserPassword(password);
        this.imageList = new HashSet<>();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.userPassword.getPassword();
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
