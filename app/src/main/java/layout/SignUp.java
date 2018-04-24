//package layout;
//
//
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.AutoCompleteTextView;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.EditText;
//import android.widget.Spinner;
//import android.widget.Toast;
//
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//import com.malekk.newdriver.R;
//import com.malekk.newdriver.main;
//import com.malekk.newdriver.models.PersonalProfile;
//import com.malekk.newdriver.models.Student;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.Set;
//
//
///**
// * A simple {@link Fragment} subclass.
// */
//public class SignUp extends Fragment {
//
//    EditText tvFirstName;
//    EditText tvPass;
//    EditText tvCPass;
//    EditText tvlastName;
//    EditText tvEmail;
//    EditText tvPhone;
//    AutoCompleteTextView tvTeacher;
//    Spinner spStudentTeacher;
//    Spinner spPayment;
//    CheckBox cbTeorayh;
//    Button save;
//
//    Set<String> teacherNames = new HashSet<>();
//
//    FirebaseUser mUser;
//    FirebaseAuth mAuth;
//
//
//    public SignUp() {
//        // Required empty public constructor
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View v = inflater.inflate(R.layout.fragment_sign_up, container, false);
//
//
//        mAuth = FirebaseAuth.getInstance();
//
//        mUser = mAuth.getCurrentUser();
//
//        tvFirstName = (EditText) v.findViewById(R.id.tvFName);
//        tvPass = (EditText) v.findViewById(R.id.tvPass);
//        tvCPass = (EditText) v.findViewById(R.id.tvCpass);
//        tvlastName = (EditText) v.findViewById(R.id.tvLName);
//        tvEmail = (EditText) v.findViewById(R.id.tvEmail);
//        tvPhone = (EditText) v.findViewById(R.id.tvPhone);
//        tvTeacher = (AutoCompleteTextView) v.findViewById(R.id.tvTeacher);
//        spStudentTeacher = (Spinner) v.findViewById(R.id.StudentTeacher);
//        spPayment = (Spinner) v.findViewById(R.id.Payment);
//        cbTeorayh = (CheckBox) v.findViewById(R.id.checkBox);
//        save = (Button) v.findViewById(R.id.save);
//
//        if (mUser !=null)
//        tvEmail.setText(mUser.getEmail());
//
//
//        // the save button
//        save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                signUp();
//            }
//        });
//
//
//        FirebaseDatabase.getInstance().getReference().child("Teachers").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot child : dataSnapshot.getChildren()) {
//                    String tName = child.getValue(String.class);
//                    teacherNames.add(tName);
//                }
//
//                //load the teachersList from the DB
//
//                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
//                        android.R.layout.simple_dropdown_item_1line, new ArrayList<>(teacherNames));
//
//                tvTeacher.setAdapter(adapter);
//                // fill the auoto complite of the teachers
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//
//        return v;
//
//    }//onCreat
//
//
//    public void signUp() {
//
//        String firstName = this.tvFirstName.getText().toString();
//        String pass = this.tvPass.getText().toString();
//        String cPass = this.tvCPass.getText().toString();
//        String lastName = this.tvlastName.getText().toString();
//        String email = this.tvEmail.getText().toString();
//        String phone = this.tvPhone.getText().toString();
//        boolean teacher = teacherNames.add(this.tvTeacher.getText().toString());
//        String teacherY = "";
//        boolean teoryah = this.cbTeorayh.isChecked();
//        String payment = this.spPayment.getSelectedItem().toString();
//        String teacherStudent = this.spStudentTeacher.getSelectedItem().toString();
//
//        if (teacherStudent.equals("Teacher")) {
//
//            if (teacher) {
//                teacherY = this.tvTeacher.getText().toString();
//                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Teachers").child(teacherY);
//                ref.setValue(teacherY);
//
//
//            } else {
//                Toast.makeText(getActivity(), "Teacher name exists , please choose anoter name ", Toast.LENGTH_SHORT).show();
//                return;
//            }
//        }
//
//        //if student
//        else{
//            teacherY= tvTeacher.getText().toString() ;
//            Student student = new Student(mUser.getDisplayName() , payment ,phone , 0 , 0.0);
//            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("TeacherStudent").child(teacherY).child(mUser.getDisplayName());
//            ref.setValue(student);
//        }
//
//        if (teacherStudent.equals("Teacher/Student ?")) {
//
//            Toast.makeText(getActivity(), "please fel the 'Teacher/Student ?' filde ", Toast.LENGTH_LONG).show();
//            return;
//        }
//
//        if (payment.equals("Payment ?")) {
//
//            Toast.makeText(getActivity(), "please fel the 'Payment ?' filde ", Toast.LENGTH_LONG).show();
//            return;
//        }
//
//        if (phone.length() < 10) {
//
//            Toast.makeText(getActivity(), "please enter a 10 digets number ", Toast.LENGTH_LONG).show();
//            return;
//
//        }
//
//
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//        if (teacherStudent.equals("Teacher")) {
//
//             PersonalProfile profile = new PersonalProfile(firstName, lastName, email, phone, pass, teacherStudent, payment, teoryah);
//             DatabaseReference ref = FirebaseDatabase.getInstance().getReference("profile").child(user.getUid());
//             ref.setValue(profile);
//
//             getFragmentManager().
//                     beginTransaction().replace(R.id.container, new main()).commit();
//
//        }
//         else {
//
//            PersonalProfile profile = new PersonalProfile(firstName, lastName, email, phone, pass, teacherStudent, payment, teacherY, teoryah);
//
//            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("profile").child(user.getUid());
//            ref.setValue(profile);
//
//            getFragmentManager().
//                    beginTransaction().replace(R.id.container, new main()).commit();
//
//        }
//    }
//
//}
