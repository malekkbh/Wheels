package layout;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.malekk.newdriver.MainActivity;
import com.malekk.newdriver.R;
import com.malekk.newdriver.main;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudentTeacher extends Fragment {

    private Button btnStudent ;
    private Button btnTeacher ;

    SharedPreferences sharedPref ;
    SharedPreferences.Editor editor ;


    public StudentTeacher() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_student_teacher, container, false);

        sharedPref = getContext().getSharedPreferences("USER_INFO", 0);
        editor = sharedPref.edit();

        btnStudent = (Button) v.findViewById(R.id.btnStudent);
        btnTeacher = (Button) v.findViewById(R.id.btnTeacher);

        btnTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                editor.putInt("stage" , 122);
//                editor.apply();

                editor.putString("StudentTeacher" , "Teacher") ;
                editor.putInt(MainActivity.USER_STAGE , MainActivity.TEACHER_SIGNUP) ;

                editor.apply();
                getFragmentManager().
                        beginTransaction().replace(R.id.container, new TeacherSingUp()).commit();


            }
        });

        btnStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                editor.putInt("stage", 111) ;
//                editor.apply();

                editor.putString("StudentTeacher" , "Student") ;
                editor.putInt(MainActivity.USER_STAGE , MainActivity.STUDENT_SIGNUP) ;

                editor.apply();
                getFragmentManager().
                        beginTransaction().replace(R.id.container, new StudentSingUp()).commit();
            }
        });




        // Inflate the layout for this fragment
        return v;




    }





}
