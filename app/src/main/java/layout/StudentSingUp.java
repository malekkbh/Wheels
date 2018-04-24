package layout;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.malekk.newdriver.MainActivity;
import com.malekk.newdriver.R;
import com.malekk.newdriver.models.Profile;
import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudentSingUp extends Fragment {

    private FirebaseStorage storage = FirebaseStorage.getInstance() ;
    private EditText etName ;
    private EditText etPhone ;
    private EditText etEmail ;
    private EditText etImage ;
    private EditText etCity ;
    private Spinner  spCity ;
    private Button   btnBrawse ;
    private Button   btnSave ;
    private ProgressDialog progressDialog ;
    private ImageView imgStudent;
    private FirebaseUser mUser ;
    private FirebaseAuth mAuth ;

    private Context context ;
    Uri dowloadUrl ;

    Context applicationContext = MainActivity.getContextOfApplication();


    SharedPreferences ref = applicationContext.getSharedPreferences("USER_INFO" , 0) ;
    SharedPreferences.Editor editor = ref.edit();


    public StudentSingUp() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_student_sing_up, container, false);

        context = getContext() ;
        etName = (EditText) v.findViewById(R.id.etName) ;
        etPhone = (EditText) v.findViewById(R.id.etPhone);
        etEmail = (EditText) v.findViewById(R.id.etEmail) ;
        //etImage = (EditText) v.findViewById(R.id.etImage);
        etCity = (EditText) v.findViewById(R.id.etAdress);
        spCity = (Spinner) v.findViewById(R.id.spCity);
        btnBrawse = (Button) v.findViewById(R.id.btnBrawse);
        btnSave = (Button )  v.findViewById(R.id.btnSave);
        progressDialog = new ProgressDialog(context) ;
        imgStudent = (ImageView) v.findViewById(R.id.imgStudent) ;

        mAuth = FirebaseAuth.getInstance();

        mUser = mAuth.getCurrentUser();

        if (mUser != null){

            etName.setText(mUser.getDisplayName());
            etEmail.setText(mUser.getEmail());

        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

        btnBrawse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseAndUploadImage() ;
            }
        });

        return v;
    }

    public void chooseAndUploadImage() {
        Intent i = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        Activity av =(Activity) this.getActivity() ;

        startActivityForResult(i, 12);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    //  super.onActivityResult(requestCode, resultCode, data);


        StorageReference storageRef = storage.getReference();

        if ( requestCode == 12 &&  resultCode == RESULT_OK ){

            progressDialog.setMessage("Uploading ..");
            progressDialog.show();

            Uri uri = data.getData() ;

             StorageReference filePath =  storageRef.child("photos").child(mUser.getUid()) ;

                   filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                             @Override
                             public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                  StudentSingUp.this.dowloadUrl = taskSnapshot.getDownloadUrl() ;

                                 FirebaseDatabase.getInstance().getReference().child("profile").child(StudentSingUp.this.mUser.getUid())
                                         .child("imgUri")
                                         .setValue(dowloadUrl.toString());

                                         progressDialog.dismiss();

                                        Toast.makeText( context , "uploaded", Toast.LENGTH_LONG ).show();

                                         System.out.println("uploded");

                                 Picasso.get().load(dowloadUrl).error(R.drawable.fui_ic_twitter_bird_white_24dp).into(imgStudent);

                 } // on sucsses
                     }).addOnFailureListener(new OnFailureListener() {
                         @Override
                         public void onFailure(@NonNull Exception e) {
                             System.out.println(e.toString());
                         }
                     });


        }
       // getFragmentManager().beginTransaction().replace(R.id.container, new StudentSingUp()).commit();

    }

    private void signUp() {

        String Name = etName.getText().toString();
        String Phone  = etPhone.getText().toString();
        String Email = etEmail.getText().toString() ;
        String Image ;
        String City = etCity.getText().toString() ;
        String Citylocation ="" ;

        if ( dowloadUrl != null && !dowloadUrl.toString().equals("")){
            Image = dowloadUrl.toString();}
        else
            Image = "" ;


        if(this.spCity.getSelectedItem()!=null) {
             Citylocation = spCity.getSelectedItem().toString();
        }

        if ( Phone.length() != 10 ) {
            Toast.makeText(getActivity(), "Please insert a 10 digets number", Toast.LENGTH_SHORT).show();
            return;
        }




        Profile p = new Profile(Name , Email ,Phone ,"Student" ,null ,null ,Citylocation , City ,Image,0,0,0,0,0,false , null ,null,null , "", "" , mUser.getUid() , MainActivity.TEACHERS);

        editor.putString(MainActivity.USER_NAME , p.getName()) ;
        editor.putString(MainActivity.USER_EMAIL , p.getEmail());
        editor.putString(MainActivity.USER_PHONE , p.getPhoneNumber());
        editor.putString(MainActivity.USER_CATEGORY , p.getVehicleCategories());
        editor.putString(MainActivity.USER_GEAR , p.getGear());
        editor.putString(MainActivity.USER_SCHOOL , p.getTeachingSchool());
        editor.putInt(   MainActivity.USER_LESSONS , p.getLessons());
        editor.putString(MainActivity.USER_Teacher_ID , p.getTeacher());
        editor.putFloat( MainActivity.USER_RATING ,(float) p.getRating());
        editor.putString(MainActivity.USER_UID , mUser.getUid());
        editor.putString(MainActivity.USER_STUDENT_TEACHER , p.getTeacherStudent());
        editor.putString(MainActivity.USER_IMG_URL , p.getImgUri());
        editor.putInt(MainActivity.USER_STAGE , p.getStage()) ;

        editor.apply();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("profile").child(mUser.getUid());
        ref.setValue(p);

        getFragmentManager().beginTransaction().replace(R.id.container, new Teachers()).commit();




    }//signUp

}
