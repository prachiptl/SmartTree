package com.example.admin.lab1_smarttree;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ViewComments extends AppCompatActivity {
    TextView nam,comm,rate=null;
    String name,comment,rating;
    String BASE_URL = "https://cmpe235-lab-smartstreet.herokuapp.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_comments);
        nam = (TextView) (findViewById(R.id.name));
        comm = (TextView) (findViewById(R.id.comment));
        rate = (TextView) (findViewById(R.id.rating));
        getResults();
    }

    public void getResults(){
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL) //Setting the Root URL
                .build();

        AppConfig.read api = adapter.create(AppConfig.read.class);
        api.readData(new Callback<Response>() {
                         @Override
                         public void success(Response result, Response response) {

                             try {

                                 BufferedReader reader = new BufferedReader(new InputStreamReader(result.getBody().in()));
                                 String resp;
                                 resp = reader.readLine();
                                 Log.d("success", "" + resp);

                                 JSONObject jObj = new JSONObject(resp);
                                 int success = jObj.getInt("success");

                                 JSONArray obj = jObj.getJSONArray("obj");

                                 int i;
                                 for (i=0;i<obj.length();i++){
                                     JSONObject jObject = obj.getJSONObject(i);
                                     if(jObject!=null) {
                                         name += jObject.getString("name") + "\n\n";
                                         comment += jObject.getString("comment") + "\n\n";
                                         rating += jObject.getString("rating") + "\n\n";

                                         nam.setText(name);
                                         comm.setText(comment);
                                         rate.setText(rating);
                                     }
                                 }

//                                       while(i <obj.length())
//                                       {
//                                           JSONObject jObject = obj.getJSONObject(i);
//                                           if(jObject!=null) {
//                                               name += jObject.getString("name") + "\n\n";
//                                               comment += jObject.getString("comment") + "\n\n";
//                                               rating += jObject.getString("rating") + "\n\n";
//
//                                               nam.setText(name);
//                                               comm.setText(comment);
//                                               rate.setText(rating);
//                                           }
//                                           i++;
//                                       }


//                                   } else{
//                                       // Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
//                                   }


                             } catch (IOException e) {
                                 Log.d("Exception", e.toString());
                             } catch (JSONException e) {
                                 Log.d("JsonException", e.toString());
                             }
                         }

                         @Override
                         public void failure(RetrofitError error) {
                             Toast.makeText(ViewComments.this, error.toString(), Toast.LENGTH_LONG).show();
                         }
                     }
        );
    }
}
