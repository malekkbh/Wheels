package com.malekk.newdriver;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.malekk.newdriver.models.Profile;

import java.util.Arrays;

import layout.AppIntro;
import layout.MyProfile;
import layout.StudentMain;
import layout.StudentSingUp;
import layout.StudentTeacher;
import layout.TeacherDay;
import layout.TeacherMain;
import layout.TeacherProfessionalInfo;
import layout.TeacherSchedule;
import layout.TeacherSingUp;
import layout.Teachers;
import layout.TeachingAreas;

import static com.malekk.newdriver.R.menu.activity_main_drawer;

//import layout.SignUp;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private static final int RC_SIGN_IN = 1;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    SharedPreferences sharedPref_USER_INFO ;
    public static SharedPreferences.Editor editor ;
    static TextView etNavName ;
    TextView etNavStudentTeacher ;

    public static int TEACHER_SIGNUP = 122 ;
    public static int TEACHER_PRO = 123 ;
    public static int TEACHER_DAY = 222 ;
    public static int STUDENT_SIGNUP = 111 ;
    public static int STUDENT_DEAL = 133 ;
    public static int STUDENT_TEACHER = 0 ;
    public static int TEACHERS = 144 ;
    public static int APP_INTRO = 101 ;


    public static String USER_NAME = "Name" ;
    public static String USER_EMAIL = "Email" ;
    public static String USER_PHONE = "Phone" ;
    public static String USER_CATEGORY = "category" ;
    public static String USER_GEAR = "Gear" ;
    public static String USER_SCHOOL = "School" ;
    public static String USER_LESSONS = "Lessons" ;
    public static String USER_Teacher_ID = "Teacher" ;
    public static String USER_TEACHER_NAME = "TeacherName" ;
    public static String USER_RATING = "Rating" ;
    public static String USER_UID = "UID" ;
    public static String USER_STUDENT_TEACHER = "StudentTeacher" ;
    public static String USER_IMG_URL = "ImgURI" ;
    public static String USER_STAGE = "stage" ;
    public static ProgressDialog ABC ;


    public static int FRAGMENT_CONTAINER = R.id.container ;

    public static Context contextOfApplication;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        System.out.println("token : " + FirebaseInstanceId.getInstance().getToken());
        super.onCreate(savedInstanceState);
        mUser = FirebaseAuth.getInstance().getCurrentUser() ;
        mAuth = FirebaseAuth.getInstance() ;
        sharedPref_USER_INFO = getSharedPreferences("USER_INFO", 0);
        editor = sharedPref_USER_INFO.edit();

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        contextOfApplication = getApplicationContext() ;
        ABC = new ProgressDialog(contextOfApplication) ;


        if ( mUser != null){
            refreshSharedPref();
        }

//        boolean a = sharedPref_USER_INFO.getInt(MainActivity.USER_STAGE , 0) == 0 ;
//         if ( sharedPref_USER_INFO.getInt(MainActivity.USER_STAGE , 0) == 0 ){
//
//            Intent intent = new Intent(this, AppIntro.class);
//            startActivity(intent);
//            finish();
//        }


       // }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();



        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerLayout = navigationView.getHeaderView(0);
              //  inflateHeaderView(R.layout.nav_header_main);
         etNavName = (TextView) headerLayout.findViewById(R.id.tvNAVname);
         etNavStudentTeacher = (TextView) headerLayout.findViewById(R.id.tvNAVemail);


        etNavName.setText(sharedPref_USER_INFO.getString(USER_NAME, ""));
        etNavStudentTeacher.setText(sharedPref_USER_INFO.getString(USER_STUDENT_TEACHER ,""));

        Menu menu = navigationView.getMenu() ;

        MenuItem TD = menu.findItem(R.id.nav_manage) ;

       // MenuItem TD = (MenuItem) findViewById(R.id.nav_manage);

        if ( sharedPref_USER_INFO.getString(USER_STUDENT_TEACHER , "").equals("Teacher")) {
            TD.setTitle("My Day");
            menu.findItem(R.id.nav_teacher_scedule).setVisible(true) ;
            menu.findItem(R.id.nav_Teaching_Area).setVisible(true) ;
            menu.findItem(R.id.nav_my_classes).setVisible(true).setTitle("My Students");
        }//ifTeacher
        else
        {
            menu.findItem(R.id.nav_teacher_scedule).setVisible(false) ;
            menu.findItem(R.id.nav_Teaching_Area).setVisible(false) ;
            menu.findItem(R.id.nav_my_classes).setVisible(false);

            TD.setTitle("Sign up for calss") ;
        }//ifStudent



    }//onCreate

    public void refreshSharedPref() {
        if( FirebaseAuth.getInstance().getCurrentUser() != null)
        FirebaseDatabase.getInstance().getReference().child("profile").child(mUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    Profile p = dataSnapshot.getValue(Profile.class) ;

                    editor.putString(USER_NAME , p.getName()) ;
                    editor.putString(USER_EMAIL , p.getEmail());
                    editor.putString(USER_PHONE , p.getPhoneNumber());
                    editor.putString(USER_CATEGORY , p.getVehicleCategories());
                    editor.putString(USER_GEAR , p.getGear());
                    editor.putString(USER_SCHOOL , p.getTeachingSchool());
                    editor.putInt(USER_LESSONS , p.getLessons());
                    editor.putString(USER_Teacher_ID , p.getTeacher());
                    editor.putFloat(USER_RATING ,(float) p.getRating());
                    editor.putString(USER_UID , mUser.getUid());
                    editor.putString(USER_STUDENT_TEACHER , p.getTeacherStudent());
                    editor.putString(USER_IMG_URL , p.getImgUri());
                    editor.putInt(USER_STAGE , p.getStage()) ;

                    editor.apply();

                    initWithUser();

                }

                else {
//                    if(sharedPref_USER_INFO.getInt(USER_STAGE , 0 ) != TEACHER_SIGNUP &&
//                            sharedPref_USER_INFO.getInt(USER_STAGE , 0 ) != STUDENT_SIGNUP) {
                        editor.clear();
                        editor.commit();
                        initWithUser();
                    }
               // }

   //             initWithUser();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        SharedPreferences prefs = getSharedPreferences("pushToken", MODE_PRIVATE);
        String token = prefs.getString("token" , "") ;

        FirebaseDatabase.getInstance().getReference().child("userTokens").child(mUser.getUid()).child("token").setValue(token) ;

        System.out.println("SharedPreferences is up to date ");

    }

    public static Context getContextOfApplication() {
        return contextOfApplication;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //add the listener in onResume.
        mAuth.addAuthStateListener(mAuthListener);



        if (mUser != null) {
            etNavName.setText(sharedPref_USER_INFO.getString(USER_NAME, ""));
            etNavStudentTeacher.setText(sharedPref_USER_INFO.getString(USER_STUDENT_TEACHER ,""));
            initWithUser();
        }


    }

    @Override
    protected void onPause() {
        super.onPause();
        //remove the listener.
        mAuth.removeAuthStateListener(mAuthListener);
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu ) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

//        MenuItem Td = (MenuItem) menu.findItem(R.id.nav_manage) ;
//
//        if ( sharedPref_USER_INFO.getString(USER_STUDENT_TEACHER , "").equals("Teacher") ) {
//            Td.setTitle("My Day");
//        }



        super.onCreateOptionsMenu(menu) ;

        return true ;

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

//        MenuItem Td = (MenuItem) menu.findItem(R.id.nav_manage) ;
//
//        if ( sharedPref_USER_INFO.getString(USER_STUDENT_TEACHER , "").equals("Teacher") ) {
//            menu.findItem(R.id.nav_manage).setTitle("My Day");
//        }

      return  super.onPrepareOptionsMenu(menu);



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.sign_out) {
            editor = editor.clear() ;
            editor.apply();
            AuthUI.getInstance().signOut(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    FirebaseAuth.AuthStateListener mAuthListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            mUser = firebaseAuth.getCurrentUser();

            if (mUser == null) {

                editor.clear().apply();
                editor.apply();


                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setAvailableProviders(
                                        Arrays.asList(

                                                new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build(),
                                                new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build()
                                        )
                                ).build(),
                        RC_SIGN_IN);


            }//if user == null
            else{
                if(sharedPref_USER_INFO.getInt(USER_STAGE , 0 ) == TEACHER_DAY )
                refreshSharedPref();
            }

        }
    };

    public void updateSharedPrefensses() {

        SharedPreferences prefs = getSharedPreferences("pushToken", MODE_PRIVATE);
        String pushToken = prefs.getString("token", "");


        FirebaseDatabase.getInstance().getReference().child("userTokens").child(mUser.getUid()).child("token").setValue(pushToken);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("profile").child(mUser.getUid());


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Profile value = dataSnapshot.getValue(Profile.class);

                //SharedPreferences.Editor editor = sharedPref_USER_INFO.edit();
                editor.putString("UID", value.getuID());
                editor.putString("StudentTeacher", value.getTeacherStudent());
                editor.putString("Teacher", value.getTeacher());
                editor.putString("Name", value.getName());
                editor.apply();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
 ;


    public void initWithUser() {

        System.out.println("USER: " + sharedPref_USER_INFO.getInt("stage" , 0));
        Log.d("user" , String.valueOf(sharedPref_USER_INFO.getInt("stage" , 0))) ;

//        if (sharedPref_USER_INFO.getInt("stage" , 0) == 0 ) {
//
////            getSupportFragmentManager().
////                    beginTransaction().replace(R.id.container, new StudentTeacher()).commit();
//            editor.putInt(USER_STAGE , 1) ;
//            editor.commit() ;
//            Intent intent = new Intent(this , AppIntro.class) ;
//            startActivity(intent);
//            finish();
//        }
       // else

        if(sharedPref_USER_INFO.getInt(USER_STAGE , 0) == STUDENT_TEACHER){
            getSupportFragmentManager().
                    beginTransaction().replace(R.id.container, new StudentTeacher()).commit() ;
        }

//        if (sharedPref_USER_INFO.getInt("stage" , 0) == TEACHER_SIGNUP ) { //teacher
//
//            getSupportFragmentManager().
//                    beginTransaction().addToBackStack("StudentTeacher").replace(R.id.container, new TeacherSingUp()).commit();
//        }

//        if (sharedPref_USER_INFO.getInt("stage" , 0) == STUDENT_SIGNUP ) { //Student
//
//            getSupportFragmentManager().
//                    beginTransaction().replace(R.id.container, new StudentSingUp()).commit();
//        }
        //else
        if ( sharedPref_USER_INFO.getInt("stage" , 0) == TEACHER_DAY) {
            getSupportFragmentManager().
                    beginTransaction().replace(R.id.container, new TeacherDay()).commit() ;

        }

        if (sharedPref_USER_INFO.getInt("stage" , 0 ) == TEACHER_PRO) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new TeacherProfessionalInfo()).commit();
        }

        if (sharedPref_USER_INFO.getInt("stage" , 0 ) == STUDENT_SIGNUP) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new StudentSingUp()).commit();

        }

        if (sharedPref_USER_INFO.getInt("stage" , 0 ) == TEACHER_SIGNUP) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new TeacherSingUp()).commit();
                }


        if (sharedPref_USER_INFO.getInt("stage" , 0 ) == TEACHERS) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new Teachers()).commit();
                }


//        if (sharedPref_USER_INFO.getInt("stage" , 0 ) == APP_INTRO) {
//            Intent intent = new Intent( this , AppIntro.class) ;
//            startActivity(intent);
//        }


//       else
//            getSupportFragmentManager().beginTransaction().replace(R.id.container, new StudentTeacher()).commit();


    }



    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_Teaching_Area) {
            if ( sharedPref_USER_INFO.getString(USER_STUDENT_TEACHER , "").equals("Teacher")) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new TeachingAreas()).commit();
            }else {
                item.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
            }

//        } else if (id == R.id.nav_teachers) {
//
//            getSupportFragmentManager().beginTransaction().replace(R.id.container, new Teachers()).commit();

        } else if (id == R.id.nav_teacher_scedule) {
            if ( sharedPref_USER_INFO.getString(USER_STUDENT_TEACHER , "").equals("Teacher")) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new TeacherSchedule()).commit();
            }else {
                item.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
            }

        } else if (id == R.id.nav_manage) {

            getSupportFragmentManager().beginTransaction().replace(R.id.container, new TeacherDay()).commit();

        }
        else if (id == R.id.nav_profile){
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new MyProfile()).commit();
        }

        else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        //super.onActivityResult(requestCode, resultCode, data);
//
//       // if ( resultCode == RESULT_OK) {
//      //      getSupportFragmentManager().beginTransaction().replace(R.id.container, new StudentSingUp()).commit();
//       // }
//    }


}
