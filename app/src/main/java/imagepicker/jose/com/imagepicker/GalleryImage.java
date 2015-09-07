package imagepicker.jose.com.imagepicker;

/**
 * Created on 9/4/15.
 */
public class GalleryImage {

    private String imageUrl;
    private boolean isSelected;

    public GalleryImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
