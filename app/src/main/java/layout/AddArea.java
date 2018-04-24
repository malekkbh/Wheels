package layout;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.malekk.newdriver.R;
import com.malekk.newdriver.models.City;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.dial;
import static android.R.attr.id;
import static com.firebase.ui.auth.R.id.container;
import static com.malekk.newdriver.R.id.addAreaDialoge;
import static com.malekk.newdriver.R.id.save;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddArea extends  DialogFragment {

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    AutoCompleteTextView etCity;
    Spinner spCityLocation;
    FloatingActionButton fab;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_student_sing_up, container, false);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        etCity = (AutoCompleteTextView) v.findViewById(R.id.etCity);
        spCityLocation = (Spinner) v.findViewById(R.id.spCityLocation);
        fab = (FloatingActionButton) v.findViewById(R.id.fab) ;

        return v ;

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        AlertDialog dialog =  builder.create() ;


        etCity = (AutoCompleteTextView) dialog.findViewById(R.id.etCity);
        spCityLocation = (Spinner) dialog.findViewById(R.id.spCityLocation);



        builder.setTitle("Add a city").setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("TeachingAreas").child(mUser.getUid());
                ref.setValue(etCity.getText().toString());


            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dismiss();
            }
        }) ;


        return dialog;

    }

}

