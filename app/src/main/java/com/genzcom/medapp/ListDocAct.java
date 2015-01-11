package com.genzcom.medapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.URLEncoder;
import java.util.ArrayList;

public class ListDocAct extends Activity {

    String myurl = "https://sphirelabs-findme-v1.p.mashape.com/findme.php?";
    private static final String KEY = "0FkrfCIwdnmshIh71aqs0U0zSrZ0p1ObXymjsnSgN4ilIQibla";
    private static final String param_type = "doctor";
    ArrayList<Doctor> doctorsLst = new ArrayList<Doctor>();
    ListView doclist;
    Doctor doctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doclist);
        Intent intent = getIntent();
        String loc = intent.getStringExtra("location");
        Log.v("List Doc","done");
        doclist = (ListView) findViewById(R.id.list);
        try {
            Log.v("List Doc ..","1 done");


            String REQUEST_URL = myurl + "city=" + loc + "&type=" + param_type + "&mashape-key=" + KEY;
            Log.i("url",REQUEST_URL);
            new FetchData().execute(REQUEST_URL);

        } catch (Exception ex) {
            ex.printStackTrace();
        }


        doclist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                //selDoctor = new Doctor();
                doctor = (Doctor)parent.getItemAtPosition(pos);
                Toast.makeText(ListDocAct.this,"You have selected"+doctor.getName(),Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_doc, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class FetchData extends AsyncTask<String, Void, String> {

        HttpClient httpClient = null;
        HttpResponse httpResponse = null;
        InputStream inputStream = null;
        String result;

        @Override
        protected String doInBackground(String... param) {
            result = "";
            try {
                httpClient = new DefaultHttpClient();
                httpResponse = httpClient.execute(new HttpGet(param[0]));
                inputStream = httpResponse.getEntity().getContent();
                result = convertToString(inputStream);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject jason = new JSONObject(result);
                JSONArray arrList = jason.getJSONArray("data");

                JSONObject jsonDoc = null;

                for (int i = 0; i < arrList.length(); i++) {
                    doctor = new Doctor();
                    jsonDoc = new JSONObject();
                    jsonDoc = arrList.getJSONObject(i);
                    doctor.setName(jsonDoc.getString("storename"));
                    doctor.setAddress(jsonDoc.getString("address"));
                    doctor.setPhone(jsonDoc.getString("phone"));
                    doctorsLst.add(doctor);
                    Log.i("Name",jsonDoc.getString("storename"));
                }

                DListAdapter myListAdapter = new DListAdapter(ListDocAct.this, R.layout.doc_item, doctorsLst);
                doclist.setAdapter(myListAdapter);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        private String convertToString(InputStream inputStream) {
            try {
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(inputStream));
                String line = "";
                String strdata = "";
                while ((line = bufferedReader.readLine()) != null)
                    strdata += line;

                inputStream.close();
                return strdata;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }
    }
}
