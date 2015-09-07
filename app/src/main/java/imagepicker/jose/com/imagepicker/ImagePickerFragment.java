package imagepicker.jose.com.imagepicker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created on 9/4/15.
 */
public class ImagePickerFragment extends Fragment {

    public static final int SINGLE_PHOTO = 0x001;
    public static final int MULTIPLE_PHOTO = 0x002;

    private RecyclerView mImageRecycler;
    private GridLayoutManager mGridLayoutManager;
    private ImagePickerAdapter mImagePickerAdapter;

    public ImagePickerAdapter getImagePickerAdapter() {
        return mImagePickerAdapter;
    }

    public interface OnCameraButtonClicked {
        void onCameraButtonClicked();
    }

    public interface OnSinglePhotoClicked {
        void onSinglePhotoClicked(GalleryImage galleryImage);
    }

    public void addNewPhoto(GalleryImage newGalleryImage){
        mImagePickerAdapter.add(0, newGalleryImage);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_image_source, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        mGridLayoutManager = new GridLayoutManager(view.getContext(), 3);
        mImageRecycler = (RecyclerView) view.findViewById(R.id.image_recycler);
        mImageRecycler.setLayoutManager(mGridLayoutManager);

        mImagePickerAdapter = new ImagePickerAdapter(GalleryImages.get((Activity) view.getContext()), true, 6);
        mImageRecycler.setAdapter(mImagePickerAdapter);
        mImageRecycler.setHasFixedSize(true);
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
