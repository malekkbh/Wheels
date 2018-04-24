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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.malekk.newdriver.MainActivity;
import com.malekk.newdriver.R;
import com.malekk.newdriver.models.Profile;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeacherSingUp extends Fragment {


    private EditText      etName ;
    private EditText      etPhone ;
    private EditText      etEmail ;
    private ImageView     teacherImage ;
    private EditText      etAdress ;
    private Button        btnBrawse ;
    private Button        btnSave ;
    AutoCompleteTextView  etBank ;
    AutoCompleteTextView  etBankBranch ;
    EditText  etBankAccount ;
    private ProgressDialog progressDialog ;


    List<Profile> profiles = new ArrayList<>() ;

    private FirebaseUser mUser ;
    private FirebaseAuth mAuth ;
    private Uri dowloadUrl ;
    private FirebaseStorage storage = FirebaseStorage.getInstance() ;
    private Context context ;
    SharedPreferences sharedPref ;
    SharedPreferences.Editor editor ;




    public TeacherSingUp() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View v= inflater.inflate(R.layout.fragment_teacher_sing_up, container, false);


        etName = (EditText) v.findViewById(R.id.etName);
        etPhone = (EditText) v.findViewById(R.id.etPhone);
        etEmail = (EditText) v.findViewById(R.id.etEmail);
        teacherImage = (ImageView) v.findViewById(R.id.teacherImg) ;
        etAdress = (EditText) v.findViewById(R.id.etAdress);
        etBank = (AutoCompleteTextView) v.findViewById(R.id.etBank);
        etBankBranch = (AutoCompleteTextView) v.findViewById(R.id.etBranch);
        etBankAccount = (EditText) v.findViewById(R.id.etBankaccount);
        btnBrawse = (Button) v.findViewById(R.id.btnBrawse);
        btnSave = (Button) v.findViewById(R.id.btnSave);
        progressDialog = new ProgressDialog(getContext()) ;
        context = getContext() ;
        sharedPref = context.getSharedPreferences("USER_INFO", 0);
        editor = sharedPref.edit();

        // profiles = getProfiles() ;

        mAuth = FirebaseAuth.getInstance();

        mUser = mAuth.getCurrentUser();

        String name = mUser.getDisplayName();

//        editor.putInt("stage" , 1) ;
//        editor.apply();

        if (mUser != null){

            etName.setText(name);
            etEmail.setText(mUser.getEmail());

        }

            btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signUp();
                editor.putInt("stage" , MainActivity.TEACHER_PRO) ;
                editor.apply();

                getFragmentManager().beginTransaction().replace(R.id.container , new TeacherProfessionalInfo()).commit();

            }
        });

        btnBrawse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseAndUploadImage();
            }
        });



        return v ;
    }



    public void chooseAndUploadImage() {
        Intent i = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        Activity av =(Activity) this.getActivity() ;

        startActivityForResult(i, 13);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        //  super.onActivityResult(requestCode, resultCode, data);


        StorageReference storageRef = storage.getReference();

        if ( requestCode == 13 &&  resultCode == RESULT_OK ){

            progressDialog.setMessage("Uploading ..");
            progressDialog.show();

            Uri uri = data.getData() ;

            StorageReference filePath =  storageRef.child("photos").child(mUser.getUid()) ;

                  filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        TeacherSingUp.this.dowloadUrl = taskSnapshot.getDownloadUrl() ;

                        FirebaseDatabase.getInstance().getReference().child("profile").child(TeacherSingUp.this.mUser.getUid())
                                .child("imgUri")
                                .setValue(dowloadUrl.toString());

                        progressDialog.dismiss();

                        Toast.makeText(context  , "uploaded", Toast.LENGTH_LONG ).show();

                        System.out.println("uploded");

                        Picasso.get().load(dowloadUrl).into(teacherImage);

                        editor.putString("ImgURI" , dowloadUrl.toString()) ;
                        editor.apply();

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

        String name = etName.getText().toString() ;
        String img = "" ;
        String phone = etPhone.getText().toString() ;
        String email = etEmail.getText().toString() ;
        String adress = etAdress.getText().toString() ;
        String bank = etBank.getText().toString();
        String bankBranch = etBankBranch.getText().toString();
        String bankAccount = etBankAccount.getText().toString();

        if( dowloadUrl != null){
             img = dowloadUrl.toString() ;
        }


        if(phone.length()!= 10 ){
            Toast.makeText(getActivity(), "Please insert a 10 degits number", Toast.LENGTH_SHORT).show();
        }

        Profile profile = new Profile(name,email,phone,"Teacher",null ,mUser.getUid(),"" ,adress,img,0,0,0,0,0,true ,null , null , "" , "" , "" , mUser.getUid() , MainActivity.TEACHER_PRO);

        editor.putString("UID", profile.getuID());
        editor.putString("StudentTeacher", profile.getTeacherStudent());
        editor.putString(MainActivity.USER_Teacher_ID, profile.getTeacher());
        editor.putString(MainActivity.USER_TEACHER_NAME , name) ;
        editor.putString(MainActivity.USER_EMAIL , email) ;
        editor.putString(MainActivity.USER_IMG_URL , img) ;
        editor.putString(MainActivity.USER_PHONE , phone) ;
        editor.putString(MainActivity.USER_NAME , name) ;
        editor.putString("Name", profile.getName());
        editor.putInt(MainActivity.USER_STAGE , MainActivity.TEACHER_PRO) ;

        editor.apply();


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("profile").child(mUser.getUid());
        ref.setValue(profile);


    }






}
