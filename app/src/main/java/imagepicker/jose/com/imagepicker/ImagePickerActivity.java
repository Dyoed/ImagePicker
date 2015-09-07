package imagepicker.jose.com.imagepicker;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.util.List;

public class ImagePickerActivity extends AppCompatActivity implements ImagePickerFragment.OnCameraButtonClicked,
        ImagePickerFragment.OnSinglePhotoClicked {

    private ImagePickerFragment mPickerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initImageLoader();
        setContentView(R.layout.activity_image_picker);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image_picker, menu);
        return true;
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        if(fragment instanceof ImagePickerFragment){
            mPickerFragment = (ImagePickerFragment) fragment;
        }

        super.onAttachFragment(fragment);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initImageLoader() {
        try {
            String CACHE_DIR = Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + "/.temp_tmp";
            new File(CACHE_DIR).mkdirs();

            File cacheDir = StorageUtils.getOwnCacheDirectory(getBaseContext(),
                    CACHE_DIR);

            DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                    .cacheOnDisc(true).imageScaleType(ImageScaleType.EXACTLY).cacheInMemory(true)
                    .bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true).build();
            ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(
                    getBaseContext())
                    .defaultDisplayImageOptions(defaultOptions)
                    .tasksProcessingOrder(QueueProcessingType.LIFO)
                    .threadPoolSize(5)
                    .discCache(new UnlimitedDiskCache(cacheDir))
                    .memoryCacheSizePercentage(20);

            ImageLoaderConfiguration config = builder.build();
            ImageLoader.getInstance().init(config);

        } catch (Exception e) {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public List<GalleryImage> getAllSelectedImages(){
        List<GalleryImage> images = mPickerFragment.getImagePickerAdapter().getSelectedImages();
        if(images.size() != 0){
            return images;
        }
        else{
            return null;
        }
    }

    @Override
    public void onCameraButtonClicked() {

    }

    @Override
    public void onSinglePhotoClicked(GalleryImage galleryImage) {

    }
}
