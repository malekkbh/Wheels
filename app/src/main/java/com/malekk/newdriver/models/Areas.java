package com.malekk.newdriver.models;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.malekk.newdriver.MainActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by malekkbh on 01/05/2018.
 */

public class Areas {

    Button btnHit;
    TextView txtJson;
    ProgressDialog pd;
    String last ;


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        btnHit = (Button) findViewById(R.id.btnHit);
//        txtJson = (TextView) findViewById(R.id.tvJsonItem);
//
//        btnHit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new JsonTask().execute("Url address here");
//            }
//        });
//
//
//    }

    public void getTheData (URL url){
        new JsonTask().execute(url.toString()) ;
    }




    public class JsonTask extends AsyncTask<String, String, String> {


        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(MainActivity.contextOfApplication);
            pd.setMessage("Please wait");
            pd.setCancelable(false);
            pd.show();
        }

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

                }

                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (pd.isShowing()){
                pd.dismiss();
            }
            txtJson.setText(result);
        }
    }

}
