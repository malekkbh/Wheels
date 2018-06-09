package layout;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.malekk.newdriver.MainActivity;
import com.malekk.newdriver.R;
import com.malekk.newdriver.models.Profile;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudentDeal extends Fragment {

    Spinner spGearBox , spCategories , spDeal ;
    TextView tvPrice ;
    Button btnSave ;
    FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
    Context applicationContext = MainActivity.getContextOfApplication();


    public StudentDeal() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_student_deal, container, false);

        spCategories = (Spinner) v.findViewById(R.id.spCatigory);
        spGearBox = (Spinner) v.findViewById(R.id.spGearBox);
        spDeal = (Spinner) v.findViewById(R.id.spDeal);
        tvPrice = (TextView) v.findViewById(R.id.tvPrice);
        btnSave = (Button) v.findViewById(R.id.btnSave) ;

        spDeal.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if( !spDeal.getSelectedItem().equals("Deal?")) {
                    tvPrice.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(), "ok!", Toast.LENGTH_SHORT).show();
                }//if
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              DatabaseReference reff = FirebaseDatabase.getInstance().getReference()
                        .child("profile")
                        .child(mUser.getUid());

              reff.child("vehicleCategories").setValue(spCategories.getSelectedItem().toString());
              reff.child("gear").setValue(spGearBox.getSelectedItem().toString());
              reff.child("payment").setValue(spDeal.getSelectedItem().toString());
              reff.child("stage").setValue(MainActivity.TEACHER_DAY) ;


              FirebaseDatabase.getInstance().getReference().child("profile").child(mUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                  @Override
                  public void onDataChange(DataSnapshot dataSnapshot) {
                      if (dataSnapshot.exists()) {
                          Profile p = dataSnapshot.getValue(Profile.class);

                          SharedPreferences sharedPref = applicationContext.getSharedPreferences("USER_INFO", 0);
                          SharedPreferences.Editor editor = sharedPref.edit();

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
                          editor.putInt(   MainActivity.USER_STAGE , MainActivity.TEACHER_DAY) ;

                          editor.apply();
                      }
                  }

                  @Override
                  public void onCancelled(DatabaseError databaseError) {

                  }
              });

              getFragmentManager().beginTransaction().replace(R.id.container, new TeacherDay()).commit();
            }
        });
         return v ;
    }//onCreat





}
