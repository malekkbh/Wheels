package layout;


import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StreamDownloadTask;
import com.malekk.newdriver.DataSorce.TeachingAreasDataSorce;
import com.malekk.newdriver.R;
import com.malekk.newdriver.Recycler.TeachigAreasRecyclerAdapter;
import com.malekk.newdriver.backToActivity;
import com.malekk.newdriver.models.City;

import java.util.List;

import static android.R.attr.dial;
import static com.malekk.newdriver.R.array.city;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeachingAreas extends Fragment {

    FloatingActionButton fab;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    RecyclerView rv ;

    View v;

    backToActivity mlistener;

    public TeachingAreas() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_teaching_areas, container, false);

        fab = (FloatingActionButton) v.findViewById(R.id.fab);

        mAuth = FirebaseAuth.getInstance();

        mUser = mAuth.getCurrentUser();

        rv = (RecyclerView) v.findViewById(R.id.rvAreas);
        rv.setAdapter(new TeachigAreasRecyclerAdapter(this.getContext()));
        rv.setLayoutManager( new LinearLayoutManager( getContext()));


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                final View dialogView = getLayoutInflater().inflate(R.layout.fragment_add_area, null, false);

                final AlertDialog alertDialog = builder.show();

                final AutoCompleteTextView etCity = (AutoCompleteTextView) dialogView.findViewById(R.id.etCity);
                final Spinner spCity = (Spinner) dialogView.findViewById(R.id.spCityLocation);


                builder.setView(dialogView).setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        final String city = etCity.getText().toString();
                        final String cityLocation = spCity.getSelectedItem().toString();

                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("TeachingAreas").child(mUser.getUid()).child(city + " - " + cityLocation);
                        ref.setValue(new City(city, cityLocation));
                        rv.getAdapter().notifyDataSetChanged();
                        rv.setAdapter(new TeachigAreasRecyclerAdapter(TeachingAreas.this.getContext()));
                        rv.setLayoutManager( new LinearLayoutManager( getContext()));
                        alertDialog.dismiss();


                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                }).setTitle("Add Area ");

                builder.show();

            }
        });
        return v;
    }



}





