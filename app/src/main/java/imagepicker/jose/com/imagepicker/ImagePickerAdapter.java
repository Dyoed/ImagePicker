package imagepicker.jose.com.imagepicker;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 9/4/15.
 */
public class ImagePickerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<GalleryImage> mImgList;
    private Object mLock = new Object();
    private boolean multiSelecttion = false;
    private int limit;
    private Toast toast;
    private static final int BUTTON_TYPE = 0;
    private static final int IMAGE_TYPE = 1;

    /**
     *
     * @param imgList
     * @param multiSelecttion
     * @param limit
     */
    public ImagePickerAdapter(List<GalleryImage> imgList, boolean multiSelecttion, int limit) {
        mImgList = imgList;
        this.multiSelecttion = multiSelecttion;
        this.limit = limit;
    }

    public GalleryImage getItem(int index){
        synchronized (mLock){
            return mImgList.get(index);
        }
    }

    public List<GalleryImage> getSelectedImages(){
        synchronized (mLock){
            List<GalleryImage> galleryImages = new ArrayList<>();
            for(int i=0;i<mImgList.size(); i++){
                GalleryImage img = mImgList.get(i);
                if(img.isSelected()){
                    galleryImages.add(img);
                }
            }
            return galleryImages;
        }
    }


    public void setMultiSelecttion(boolean multiSelecttion) {
        this.multiSelecttion = multiSelecttion;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getSelectedImgCount(){
        int count = 0;
        synchronized (mLock){
            for(int i=0; i<mImgList.size(); i++){
                if(mImgList.get(i).isSelected()){
                    count++;
                }
            }
        }
        return count;
    }

    public void add(int index, GalleryImage image){
        synchronized (mLock){
            mImgList.add(index, image);
        }
    }

    public boolean shouldToggleCheckedStatus(int visibility){
        if(multiSelecttion){
            if(limit > getSelectedImgCount() && visibility != View.VISIBLE){
                return true;
            }
            else if(visibility == View.VISIBLE){
                return true;
            }
        }
        else{
            if(getSelectedImgCount() == 0){
                return true;
            }
            else if(visibility == View.VISIBLE){
                return true;
            }
        }
        return false;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == IMAGE_TYPE){
            return new ImagePickerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_image, parent, false));
        }
        else{
            return new ButtonViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_add_btn, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) == IMAGE_TYPE){
            ImagePickerViewHolder viewHolder = (ImagePickerViewHolder) holder;
            viewHolder.bindImage(getItem(position));
        }
        else{
            ButtonViewHolder viewHolder = (ButtonViewHolder) holder;
            viewHolder.bind();
        }
    }

    @Override
    public int getItemCount() {
        return mImgList != null ? mImgList.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? BUTTON_TYPE :  IMAGE_TYPE;
    }

    public class ButtonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView img;

        public ButtonViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.image);
            itemView.setOnClickListener(this);
        }

        public void bind(){
        }

        @Override
        public void onClick(View v) {
            ((ImagePickerFragment.OnCameraButtonClicked)(v.getContext())).onCameraButtonClicked();
        }
    }

    public class ImagePickerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView img;
        private View checkedView;
        private GalleryImage mGalleryImage;

        public ImagePickerViewHolder(View itemView) {
            super(itemView);
            checkedView = itemView.findViewById(R.id.checked_view);
            img = (ImageView) itemView.findViewById(R.id.image);
            itemView.setOnClickListener(this);
        }

        public void bindImage(GalleryImage galleryImage) {
            mGalleryImage = galleryImage;
            img.setImageDrawable(null);
            checkedView.setVisibility(galleryImage.isSelected() ? View.VISIBLE : View.GONE);
            ImageLoader.getInstance().displayImage(galleryImage.getImageUrl(), img);
        }

        @Override
        public void onClick(View v) {
            if(shouldToggleCheckedStatus(checkedView.getVisibility())){
                toggleCheckedStatus();
                if(!multiSelecttion){
                    ((ImagePickerFragment.OnSinglePhotoClicked)(v.getContext())).onSinglePhotoClicked(getItem(getAdapterPosition()));
                }
            }
            else{
                if(toast != null){
                    toast.cancel();
                }

                if(multiSelecttion){

                }
            }
        }

        private void toggleCheckedStatus(){
            checkedView.setVisibility(checkedView.getVisibility() == View.GONE ? View.VISIBLE: View.GONE);
            mGalleryImage.setIsSelected(checkedView.getVisibility() == View.VISIBLE ? true : false);
        }
    }

}
