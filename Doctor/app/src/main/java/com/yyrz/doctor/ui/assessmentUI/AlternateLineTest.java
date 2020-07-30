package com.yyrz.doctor.ui.assessmentUI;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.yyrz.doctor.R;
import com.yyrz.doctor.Util.myhttp.RequestRepository;
import com.yyrz.doctor.Util.viewmodel.CommonViewModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;
import static com.yyrz.doctor.MainActivity.REQUEST_TAKE_PHOTO;

public class AlternateLineTest extends Fragment {
    private CommonViewModel commonViewModel;
    private ImageView imageView;
    private RadioGroup radioGroup;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.alternate_line_test, container, false);
        commonViewModel=CommonViewModel.getInstance();
        Button button = root.findViewById(R.id.alternate_button);
        imageView=root.findViewById(R.id.alternate_imageView);
        imageView.setVisibility(View.VISIBLE);
        radioGroup= root.findViewById(R.id.alternate_radioGroup);
        ImageView voice=root.findViewById(R.id.alternate_imageButton);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
               if(i==R.id.alternate_radioButton1) commonViewModel.getRecord().setLine(0);
               else commonViewModel.getRecord().setLine(1);
        }});

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(commonViewModel.getBitmap().getValue()!=null){
                    Bundle bundle=new Bundle();
                    bundle.putString("flag","1");
                    NavController navController=Navigation.findNavController(view);
                    navController.navigate(R.id.action_showViewPager_to_image,bundle);
                }
            }
        });

        voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String alias=commonViewModel.getCurrentPaccount();
                String daccount=commonViewModel.getAccount();
                RequestRepository.getInstance().currentState(alias,"01_speech_1",CommonViewModel.CON_ASSESSMENT,CommonViewModel.TYPE_INSTRUCTIONS,daccount);

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(Objects.requireNonNull(getActivity()).getPackageManager()) != null) {
                    File photoFile = createImageFile();
                    Uri photoURI = FileProvider.getUriForFile(commonViewModel.getContext(),
                            "com.yyrz.doctor.fileprovider",
                            photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                }
            }
        });
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(commonViewModel.getIsQueryEnd().getValue()==1&&commonViewModel.getHasRead()[0]==0){
            commonViewModel.getHasRead()[0]=-1;
            if (commonViewModel.getRecord().getLine() == 0) radioGroup.check(R.id.alternate_radioButton1);
            else radioGroup.check(R.id.alternate_radioButton2);
            if(commonViewModel.getBitmap().getValue()!=null){
                imageView.setImageBitmap(commonViewModel.getBitmap().getValue());
            }
        }
        commonViewModel.getBitmap().observe(this, new Observer<Bitmap>() {
            @Override
            public void onChanged(Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            BitmapFactory.Options options2 = new BitmapFactory.Options();
            options2.inPreferredConfig = Bitmap.Config.RGB_565;
            Bitmap bitmap=BitmapFactory.decodeFile(commonViewModel.getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)+"/"+commonViewModel.getCurrentPaccount()+".jpg",options2);
            commonViewModel.getBitmap().setValue(ThumbnailUtils.extractThumbnail(bitmap,imageView.getWidth(),imageView.getHeight()));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
            byte[] bytes = baos.toByteArray();
            Base64.Encoder encoder=Base64.getEncoder();
            String xx=encoder.encodeToString(bytes);
            try {
                commonViewModel.getRecord().setPhoto(URLEncoder.encode(xx,"utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    private File createImageFile(){
        String imageFileName = commonViewModel.getCurrentPaccount();
        File storageDir = commonViewModel.getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return new File(storageDir+"/"+imageFileName+".jpg");
    }
}
