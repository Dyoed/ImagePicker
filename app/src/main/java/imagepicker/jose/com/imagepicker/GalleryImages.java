package imagepicker.jose.com.imagepicker;

import android.app.Activity;
import android.database.Cursor;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created on 9/4/15.
 */
public class GalleryImages {
    private static final String filePrefix = "file://";

    public static ArrayList<GalleryImage> get(Activity activity) {
        ArrayList<GalleryImage> galleryList = new ArrayList<>();

        try {
            final String[] columns = {MediaStore.Images.Media.DATA,
                    MediaStore.Images.Media._ID};
            final String orderBy = MediaStore.Images.Media._ID;

            Cursor imagecursor = activity.managedQuery(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns,
                    null, null, orderBy);

            if (imagecursor != null && imagecursor.getCount() > 0) {

                while (imagecursor.moveToNext()) {
                    int dataColumnIndex = imagecursor
                            .getColumnIndex(MediaStore.Images.Media.DATA);
                    GalleryImage item = new GalleryImage(filePrefix + imagecursor.getString(dataColumnIndex));
                    galleryList.add(item);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // show newest photo at beginning of the list
        Collections.reverse(galleryList);
        return galleryList;
    }
}
