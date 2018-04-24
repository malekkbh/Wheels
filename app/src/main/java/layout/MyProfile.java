package layout;


import android.app.Activity;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.BoringLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
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
import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyProfile extends Fragment implements  View.OnClickListener {

    EditText etName ;
    EditText etPhone ;
    EditText etEmail ;

    TextView tvCAtegoryah ;
    TextView tvGear ;
    TextView tvSchool ;
    TextView tvLessons ;
    TextView tvTeacher ;
    TextView tvTeacherProfile ;
    TextView tvTeacherStudent ;
    RatingBar RPRating ;
    ProgressDialog PPD ;

    FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser() ;

    ImageView imgProfile ;

    String befor = "" ;
    Uri dImgUri ;

    boolean flag = true ;

    private FirebaseStorage storage = FirebaseStorage.getInstance() ;





    Context applicationContext = MainActivity.getContextOfApplication();


    SharedPreferences ref = applicationContext.getSharedPreferences("USER_INFO" , 0) ;
    SharedPreferences.Editor editor = ref.edit();



    public MyProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_my_profile, container, false);


        etName = (EditText) v.findViewById(R.id.etPName);
        etPhone  = (EditText) v.findViewById(R.id.etPPhone);
        etEmail = (EditText) v.findViewById(R.id.etPEmail) ;

        tvCAtegoryah = (TextView) v.findViewById(R.id.tvPCategory);
        tvGear  = (TextView) v.findViewById(R.id.tvPGear);
        tvSchool = (TextView) v.findViewById(R.id.tvPSchool) ;
        tvLessons  = (TextView) v.findViewById(R.id.tvPLessons);
        tvTeacher  = (TextView) v.findViewById(R.id.tvPTeacher);
        tvTeacherProfile = (TextView) v.findViewById(R.id.tvTeacherProfile) ;
        tvTeacherStudent = (TextView) v.findViewById(R.id.tvPTeacherStudent) ;
        RPRating  = (RatingBar) v.findViewById(R.id.RBprofile) ;

        imgProfile  = (ImageView) v.findViewById(R.id.imgProfile) ;

        dImgUri = Uri.parse(ref.getString("ImgURI" , ""));

        final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("profile").child(FirebaseAuth.getInstance().getCurrentUser().getUid());


        if (!dImgUri.toString().equals("")){
            Picasso.get().load(ref.getString("ImgURI" , "")).into(imgProfile);
        }

        float aa = ref.getFloat(MainActivity.USER_RATING ,  0.0f) ;


        etName.setText(getString(MainActivity.USER_NAME));
        etEmail.setText(getString(MainActivity.USER_EMAIL));
        etPhone.setText(getString(MainActivity.USER_PHONE));
        tvCAtegoryah.setText(getString(MainActivity.USER_CATEGORY));
        tvGear.setText(getString(MainActivity.USER_GEAR));
        tvSchool.setText(getString(MainActivity.USER_SCHOOL));
        tvLessons.setText(ref.getInt(MainActivity.USER_LESSONS , 0 ) + "");
        tvTeacher.setText(getString(MainActivity.USER_TEACHER_NAME));
        tvTeacherStudent.setText(getString(MainActivity.USER_STUDENT_TEACHER));
        RPRating.setRating(aa);
        PPD = new ProgressDialog(getContext()) ;


        if (ref.getString(MainActivity.USER_STUDENT_TEACHER , "").equals("Teacher")){
            tvTeacherProfile.setVisibility(View.INVISIBLE);
            tvTeacher.setVisibility(View.INVISIBLE);
        }

        etName.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        if ( flag) {
                            befor = s.toString();
                            flag = false ;
                        }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if ( s != null && !s.toString().equals("")) {
                    editor.putString(MainActivity.USER_NAME , s.toString()) ;
                    editor.apply();
                    dbRef.child("name").setValue(s.toString()) ;
                    return;
                }

            }
        }) ;

        reFullEditText(etName);


        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if ( flag) {
                    befor = s.toString();
                    flag = false ;
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {


                if ( s != null && !s.toString().equals("")) {
                    editor.putString(MainActivity.USER_EMAIL , s.toString()) ;
                    editor.apply();
                    dbRef.child("email").setValue(s.toString()) ;
                    return;
                }


            }
        }) ;

        reFullEditText(etEmail);

        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if ( flag) {
                    befor = s.toString();
                    flag = false ;
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if ( s.toString().length() == 10 ){
                    editor.putString(MainActivity.USER_PHONE , s.toString()) ;
                    editor.apply();
                    dbRef.child("phoneNumber").setValue(s.toString()) ;
                    return;
                }

            }
        });

        etPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if ( !hasFocus && etPhone.getText().toString().length() != 10) {
                    etPhone.setText(befor);
                    flag = true ;
                    befor = "" ;
                }
                else
                {
                    flag = true ;
                    befor = "" ;
                }
            }
        });


        tvCAtegoryah .setOnClickListener(this);
        tvGear.setOnClickListener(this);
        tvSchool.setOnClickListener(this);
        tvLessons.setOnClickListener(this);
        tvTeacher.setOnClickListener(this);
        tvTeacherProfile.setOnClickListener(this);
        tvTeacherStudent .setOnClickListener(this);
        imgProfile.setOnClickListener(this);

        return v ;
    } //onCreat




    public  void reFullEditText (final EditText et ) {

        et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus ){
                    if(et.getText().toString().equals("") || et.getText() == null){
                        et.setText(befor);
                        flag = true ;
                        befor = "" ;
                    }
                    else {
                        flag = true ;
                        befor = "" ;
                     }
                }
            }
        });
    } //reFullET


    public String getString (String s) {
        return ref.getString(s, "No Data! : " + s.toString()) ;
    }




    public void chooseAndUploadImage() {
        Intent i = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        Activity av =(Activity) this.getActivity() ;

        startActivityForResult(i, 14);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {

        //  super.onActivityResult(requestCode, resultCode, data);


        StorageReference storageRef = storage.getReference();

        if ( requestCode == 14 &&  resultCode == RESULT_OK ){

            PPD.setMessage("Uploading ..");
            PPD.show();

            Uri uri = data.getData() ;

            StorageReference filePath =  storageRef.child("photos").child(mUser.getUid()) ;

            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    MyProfile.this.dImgUri = taskSnapshot.getDownloadUrl() ;

                    FirebaseDatabase.getInstance().getReference().child("profile").child(MyProfile.this.mUser.getUid())
                            .child("imgUri")
                            .setValue(dImgUri.toString());

                    PPD.dismiss();

                    Toast.makeText(getActivity()  , "uploaded", Toast.LENGTH_LONG ).show();

                    System.out.println("uploded");

                    Picasso.get().load(dImgUri).into(imgProfile);

                    editor.putString("ImgURI" , dImgUri.toString()) ;
                    editor.putInt("stage" , MainActivity.TEACHER_DAY) ;
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

    @Override
    public void onClick(View v) {

        FragmentActivity activity = (FragmentActivity) getActivity() ;

       int VID = v.getId() ;

        if ( v.getId() == R.id.tvPTeacherStudent){
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.container , new StudentTeacher()).commit() ;
        }

        if( v.getId() == R.id.imgProfile ){

            editor.putInt("stage" , 2304)   ;
            editor.apply();
            chooseAndUploadImage();
        }

        if(v.getId() == R.id.tvPGear || VID == R.id.tvPCategory){
            if(ref.getString(MainActivity.USER_STUDENT_TEACHER , "").equals("Teacher")) {
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container , new TeacherProfessionalInfo()).commit() ;
            }else
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.container , new StudentDeal()).commit() ;
        }

        if ( VID == R.id.tvPTeacher || VID == R.id.tvPSchool){
            if(ref.getString(MainActivity.USER_STUDENT_TEACHER , "").equals("Teacher")) {
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container , new TeacherProfessionalInfo()).commit() ;
            }else
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container , new Teachers()).commit() ;
        }



    }


}
