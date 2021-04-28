package com.shopify.imagerepo.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Image {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Image name cannot be empty")
    private String name;

    @Lob
    @NotNull(message = "Image content cannot be empty")
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
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
