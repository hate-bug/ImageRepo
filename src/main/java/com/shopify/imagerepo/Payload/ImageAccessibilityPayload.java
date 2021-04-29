package com.shopify.imagerepo.Payload;

public class ImageAccessibilityPayload {

    private Long imageId;
    private boolean isPublic;

    public ImageAccessibilityPayload (Long imageId, boolean isPublic) {
        this.imageId = imageId;
        this.isPublic = isPublic;
    }

    public Long getImageId() {
        return imageId;
    }

    public boolean isPublic() {
        return isPublic;
    }
}
