package layout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;

import com.github.paolorotolo.appintro.AppIntroFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.malekk.newdriver.MainActivity;
import com.malekk.newdriver.R;
import com.malekk.newdriver.models.SampleSlide;

import java.util.List;
import java.util.Vector;

/**
 * Created by malekkbh on 06/05/2018.
 */

public class AppIntro extends com.github.paolorotolo.appintro.AppIntro {

    SharedPreferences sharedPref ;
    SharedPreferences.Editor editor ;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPref = getApplicationContext().getSharedPreferences("USER_INFO", 0);
        editor = sharedPref.edit();
       String Ts =  sharedPref.getString(MainActivity.USER_STUDENT_TEACHER , "") ;
        final List<Fragment> fragmentList = this.fragments;
//        final PagerAdapter mm = this.mPagerAdapter ;


        if (Ts.equals("Teacher")) {
           // addSlide(new SampleSlide().newInstance(R.layout.ok));
         //  fragmentList.
                   addSlide(new AppIntroFragment().newInstance("Fun And Esey!",
                    "This is going to be You Home Page, Lessons for this Day.\n As a Teacher You will be Able to modify youer Scedule as u wish ! such as cancel , add , replace between lessons or just keep it as Your Break "
                    , R.drawable.ssss, R.color.intro1));
          //  fragmentList.
                    addSlide(new AppIntroFragment().newInstance("Once , but not for All!",
                    "Here you will be able to declare Your schedul , as We said , you can Change when ever You want :)", R.drawable.teacher_schedul_intro, R.color.intro2));

            //fragmentList.
                    addSlide(new AppIntroFragment().newInstance("and For The Dessert ...", "Please fill up every location, it will increase Your work UP. trust US ;) ", R.drawable.areas_intro, R.color.intro3));

      //      mm.notifyDataSetChanged();
        }else
           if(Ts.equals("Student") )
            {

             //   fragmentList.
                        addSlide( new AppIntroFragment().newInstance ("Heey Student , Wish You The Best on Your Way :) "  ,
                    "You may Assign and Cancel Lessones For You as Your Teacher Schedule allows " ,
                    R.drawable.ssss , R.color.intro1));

             //   fragmentList.
                        addSlide( new AppIntroFragment().newInstance("Watch and Edit Your Profile As You Wish !" , " You May Change Your Profile Picture ,Personal Details or Even Your Teacher!" , R.drawable.untitled , R.color.intro2));

               // mm.notifyDataSetChanged();
        }

    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);

//        final ProgressDialog p = new ProgressDialog(this) ;
//        p.setMessage("Loading");
//        p.show();
//        FirebaseDatabase.getInstance().getReference().child("profile").child(sharedPref.getString(MainActivity.USER_UID , ""))
//                .child("stage").setValue(MainActivity.TEACHER_SIGNUP).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
////                editor.putInt(MainActivity.USER_STAGE , MainActivity.TEACHER_SIGNUP) ;
////                editor.commit() ;
//                p.dismiss();
//                Intent intent = new Intent(AppIntro.this , MainActivity.class) ;
//                startActivity(intent);
//                finish();
//            }
//        }) ;\


        Intent intent = new Intent(AppIntro.this , MainActivity.class) ;
                startActivity(intent);
                finish();
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
//        final ProgressDialog p = new ProgressDialog(this) ;
//        p.setMessage("Loading");
//        p.show();
//
//        FirebaseDatabase.getInstance().getReference().child("profile").child(sharedPref.getString(MainActivity.USER_UID , ""))
//                .child("stage").setValue(MainActivity.TEACHER_SIGNUP).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
////                editor.putInt(MainActivity.USER_STAGE , MainActivity.TEACHER_SIGNUP) ;
////                editor.commit() ;
////                p.dismiss();
////                Intent intent = new Intent(AppIntro.this , MainActivity.class) ;
////                startActivity(intent);
//                finish();
//            }
//        }) ;


        Intent intent = new Intent(AppIntro.this , MainActivity.class) ;
                startActivity(intent);
                finish();

    }



}
