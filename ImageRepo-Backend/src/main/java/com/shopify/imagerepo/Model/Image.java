package com.shopify.imagerepo.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Image {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotNull(message = "Image name cannot be empty")
    private String name;

    @Lob
    @NotNull(message = "Image content cannot be empty")
    @JsonIgnore
    private byte[] content;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createDate;

    @PrePersist
    private void onCreate () {
        this.createDate = new Date();
    }

    @NotNull(message = "Image accessibility cannot be empty")
    private boolean isPublic;

    @NotNull(message = "Image type cannot be empty")
    private String type;

    @ManyToOne (fetch = FetchType.LAZY)
    @NotNull(message = "User not found.")
    @JsonIgnore
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public Long getId() {
        return this.Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
