package layout;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.malekk.newdriver.MainActivity;
import com.malekk.newdriver.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeacherProfessionalInfo extends Fragment {

    CheckBox cbA , cbA1 , cbA2 , cbB , cbC , cbC1 , cbD , cbD1 , cbD2 , cbD3 , cbE , cb1 , cbManual , cbAuto ;
    EditText etTeachingSchool , etPricePerLesson , etPriceForGlobalDeal ;
    Button btnNext ;

    Context applicationContext = MainActivity.getContextOfApplication();
    SharedPreferences ref = applicationContext.getSharedPreferences("USER_INFO" , 0) ;
    SharedPreferences.Editor editor = ref.edit();



    public TeacherProfessionalInfo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=  inflater.inflate(R.layout.fragment_teacher_professional_info, container, false);

        this.cb1 = (CheckBox) v.findViewById(R.id.cb1);
        this.cbA = (CheckBox) v.findViewById(R.id.cbA);
        this.cbA1 = (CheckBox) v.findViewById(R.id.cbA1);
        this.cbA2 = (CheckBox) v.findViewById(R.id.cbA2);
        this.cbB = (CheckBox) v.findViewById(R.id.cbB);
        this.cbC = (CheckBox) v.findViewById(R.id.cbC);
        this.cbC1 = (CheckBox) v.findViewById(R.id.cbC1);
        this.cbD = (CheckBox) v.findViewById(R.id.cbD);
        this.cbD1 = (CheckBox) v.findViewById(R.id.cbD1);
        this.cbD2 = (CheckBox) v.findViewById(R.id.cbD2);
        this.cbD3 = (CheckBox) v.findViewById(R.id.cbD3);
        this.cbE = (CheckBox) v.findViewById(R.id.cbE);
        this.cbManual = (CheckBox) v.findViewById(R.id.cbManual);
        this.cbAuto = (CheckBox) v.findViewById(R.id.cbAuto);

        this.etTeachingSchool = (EditText) v.findViewById(R.id.etTeachingSchool);
        this.etPricePerLesson = (EditText) v.findViewById(R.id.etPricePerLesson);
        this.etPriceForGlobalDeal = (EditText) v.findViewById(R.id.etPriceForGlobalDeal);

        this.btnNext = (Button) v.findViewById(R.id.btnNext) ;

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save() ;
                     SharedPreferences sharedPref = getActivity().getSharedPreferences("USER_INFO", 0);
                      sharedPref.edit().putInt("stage" , MainActivity.TEACHER_DAY).apply();
                      
                getFragmentManager().beginTransaction().replace(R.id.container , new TeacherDay()).commit() ;
            }
        });

        return  v;
    }

    private void save() {

        String teachingSchool = etTeachingSchool.getText().toString() ;
        String pricePerlesson = etPricePerLesson.getText().toString();
        String priceForGlobalDeal = etPriceForGlobalDeal.getText().toString();

        String gearBox =  " "  ;
        String catigories =  " " ;

        if( cbAuto.isChecked() && cbManual.isChecked())
            gearBox= "Automatic + Manual" ;
        else
        if(cbManual.isChecked())
                gearBox= "Manual";
        else
            if(cbAuto.isChecked())
            gearBox = "Automatic" ;

        CheckBox [] categoriesArry = {cb1 , cbA , cbA1 , cbA2 , cbB, cbC , cbC1 , cbD , cbD1 , cbD2 , cbD3 , cbE  };

        for (CheckBox cb:categoriesArry) {

            if (cb.isChecked()) {
                    catigories += " " + cb.getText() ;

            }//if cb is checked
        }//for

       editor.putInt("stage" , MainActivity.TEACHER_DAY) ;
       editor.putString(MainActivity.USER_GEAR , gearBox) ;
       editor.putString(MainActivity.USER_CATEGORY , catigories) ;
       editor.putString(MainActivity.USER_SCHOOL , teachingSchool) ;

       editor.apply();



        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser() ;

        DatabaseReference refVehicleCategories = FirebaseDatabase.getInstance().getReference("profile").child(mUser.getUid()).child("vehicleCategories");
        refVehicleCategories.setValue(catigories);

        DatabaseReference refGearBox = FirebaseDatabase.getInstance().getReference("profile").child(mUser.getUid()).child("gear");
        refGearBox.setValue(gearBox);

        DatabaseReference refTeachingSchool = FirebaseDatabase.getInstance().getReference("profile").child(mUser.getUid()).child("teachingSchool");
        refTeachingSchool.setValue(teachingSchool);

        DatabaseReference refPricePerLesson = FirebaseDatabase.getInstance().getReference("profile").child(mUser.getUid()).child("pricePerLesson");
        refPricePerLesson.setValue(pricePerlesson);

        DatabaseReference refPriceForGlobalDeal = FirebaseDatabase.getInstance().getReference("profile").child(mUser.getUid()).child("priceForGlobalDeal");
        refPriceForGlobalDeal.setValue(priceForGlobalDeal);

        FirebaseDatabase.getInstance().getReference().child("profile").child(mUser.getUid()).child("stage").setValue(MainActivity.TEACHER_DAY) ;


    }//save

}
