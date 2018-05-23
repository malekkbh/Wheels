package layout;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.HttpAuthHandler;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StreamDownloadTask;
import com.malekk.newdriver.DataSorce.TeachingAreasDataSorce;
import com.malekk.newdriver.MainActivity;
import com.malekk.newdriver.R;
import com.malekk.newdriver.Recycler.TeachigAreasRecyclerAdapter;
import com.malekk.newdriver.backToActivity;
import com.malekk.newdriver.models.City;
import com.malekk.newdriver.models.HttpHandler;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
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

    RecyclerView rv;
    ProgressDialog pd;

    List<String> resList;

    private String TAG = MainActivity.class.getSimpleName();

    private static String url =

            //"https://api.androidhive.info/contacts/" ;
            "https://raw.githubusercontent.com/royts/israel-cities/master/israel-cities.json";

    String[] JessonArry;


   // View v;

    backToActivity mlistener;

    public TeachingAreas() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_teaching_areas, container, false);

        fab = (FloatingActionButton) v.findViewById(R.id.fab);

        mAuth = FirebaseAuth.getInstance();

        mUser = mAuth.getCurrentUser();

        rv = (RecyclerView) v.findViewById(R.id.rvAreas);


        rv.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rv.setAdapter(new TeachigAreasRecyclerAdapter(this.getContext()));


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                final View dialogView = getLayoutInflater().inflate(R.layout.fragment_add_area, null, false);

                final AlertDialog alertDialog = builder.show();


                final AutoCompleteTextView etCity = (AutoCompleteTextView) dialogView.findViewById(R.id.etCity);
                final Spinner spCity = (Spinner) dialogView.findViewById(R.id.spCityLocation);

                new getCity(dialogView).execute();


                builder.setView(dialogView).setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        final String city = etCity.getText().toString();
                        final String cityLocation = spCity.getSelectedItem().toString();

                        if ( city.equals("") ) {
                            alertDialog.dismiss();
                            Toast.makeText(getActivity(), "Please choos a City ", Toast.LENGTH_SHORT).show();
                            AlertDialog.Builder ad = new AlertDialog.Builder(getContext()) ;
                            Drawable icon = ContextCompat.getDrawable(getContext(), android.R.drawable.ic_dialog_alert).mutate();
                            ad.setTitle("Please choose a City")
                                    .setPositiveButton("ok" , null).setIcon(R.drawable.icons8_stop_sign);
                            ad.show().show();
                        } // if city null
                        else {
                            if ( spCity.getSelectedItem().equals("where in the city ?") ) {
                                alertDialog.dismiss();
                                Toast.makeText(getActivity(), "Please choos a City Area ", Toast.LENGTH_SHORT).show();
                                AlertDialog.Builder ad = new AlertDialog.Builder(getContext()) ;
                                Drawable icon = ContextCompat.getDrawable(getContext(), android.R.drawable.ic_dialog_alert).mutate();
                                ad.setTitle("Please choose a City Area")
                                        .setPositiveButton("ok" , null).setIcon(R.drawable.icons8_stop_sign);
                                ad.show().show();
                            }
                            else {
                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("TeachingAreas").child(mUser.getUid()).child(city + " - " + cityLocation);
                            ref.setValue(new City(city, cityLocation));

                            rv.getAdapter().notifyDataSetChanged();
                            rv.setAdapter(new TeachigAreasRecyclerAdapter(TeachingAreas.this.getContext()));
                            rv.setLayoutManager(new LinearLayoutManager(getContext()));
                            alertDialog.dismiss();
                        }
                        }

                       // alertDialog.show();
                    }
                })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                });

                builder.show().getWindow().setBackgroundDrawableResource(R.color.fui_transparent);

                //builder.show().show();

            }
        });


        return v;
    }

    private class getCity extends AsyncTask<Void, Void, ArrayList<String>> {

        //properties:
        View dialogView;

        //ctor:
        public getCity(View dialogView) {
            this.dialogView = dialogView;
        }



        //Show progress:
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(TeachingAreas.this.getContext());
            pd.setMessage("Loading..");
            pd.setCancelable(false);
            pd.show();
        }


        //runs in the background thread.
        //Thread job -> ArrayList
        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
            ArrayList<String> result = new ArrayList<>();
            HttpHandler sh = new HttpHandler();

            String jsonStr =
//                    "{\n "
//                     + "\"israel-cities\"" +":"
//                    +
                    sh.makeServiceCall(url);


            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONArray cities = new JSONArray(jsonStr);

                    // looping through All Contacts
                    for (int i = 0; i < cities.length(); i++) {
                        JSONObject c = cities.getJSONObject(i);
                        result.add((String) c.get("english_name"));
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
            }

            return result;
        }

        //runs on the UI Thread
        @Override
        protected void onPostExecute(ArrayList<String> result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pd.isShowing())
                pd.dismiss();
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                    android.R.layout.simple_dropdown_item_1line, result);

            //   final View dialogView = getLayoutInflater().inflate(R.layout.fragment_add_area, null, false);

            AutoCompleteTextView textView = (AutoCompleteTextView)
                    dialogView.findViewById(R.id.etCity);
            textView.setAdapter(adapter);
        }

    }//classGetCity
}


//             return null;
//        }
//
//    }
//
//
//}





