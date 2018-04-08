package com.example.admin.lab1_smarttree;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CommentActivity extends AppCompatActivity implements View.OnClickListener {

    private RatingBar ratingBar;
    private Button submit;
    private  DBConnector dbConnector= new DBConnector(this);
    String BASE_URL = "https://cmpe235-lab-smartstreet.herokuapp.com";
    EditText username,usercomment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        //set up the rating bar
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        //set up a listener to listen rating bar changing
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

            }
        });
        //set up the submit button
        submit = (Button) findViewById(R.id.submit);
        //set up the listener on submit button
        submit.setOnClickListener(this);
    }

    //Following function have the implementation of onClick function for the submit button on Comments page
    @Override
    public void onClick(View v) {

        /*if(v.getId() == R.id.submit){

            EditText comment = (EditText) findViewById(R.id.name);
            String feedback = comment.getText().toString();
            String ranking = String.valueOf(ratingBar.getRating());
            String username = getIntent().getStringExtra("username");

            Comment comment1 = new Comment();
            comment1.setComment(feedback);
            comment1.setRanking(ranking);
            comment1.setUsername(username);

            dbConnector.insertComment(comment1);

            Intent intent = new Intent(CommentActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }*/

        usercomment = (EditText) findViewById(R.id.comment_id);
        username = (EditText) findViewById(R.id.name);
        String name = username.getText().toString();
        String comment = usercomment.getText().toString();
        int rating = (int) ratingBar.getRating();

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL) //Setting the Root URL
                .build();

        AppConfig.comment api = adapter.create(AppConfig.comment.class);
        api.usercomments(
                name,
                comment,
                rating,
                new Callback<Response>() {
                    @Override
                    public void success(Response result, Response response) {

                        try {

                            BufferedReader reader = new BufferedReader(new InputStreamReader(result.getBody().in()));
                            String resp;
                            resp = reader.readLine();
                            Log.d("success", "" + resp);

                            JSONObject jObj = new JSONObject(resp);
                            int success = jObj.getInt("success");

                            if(success == 1){
                                Toast.makeText(getApplicationContext(), " Successful, Please Log in", Toast.LENGTH_SHORT).show();;
                                Intent intent = new Intent();
                                intent.setClass(CommentActivity.this, ViewComments.class);
                                startActivity(intent);
                            } else{
                                Toast.makeText(getApplicationContext(), " Fail", Toast.LENGTH_SHORT).show();
                            }


                        } catch (IOException e) {
                            Log.d("Exception", e.toString());
                        } catch (JSONException e) {
                            Log.d("JsonException", e.toString());
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(CommentActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );


    }

    public void viewComments(View view)     {
        Intent intent = new Intent(CommentActivity.this, ViewComments.class);
        startActivity(intent);
    }

  /*  public void addComment (View v){
        usercomment = (EditText) findViewById(R.id.comment_id);
        username = (EditText) findViewById(R.id.name);
        String name = username.getText().toString();
        String comment = usercomment.getText().toString();
        int rating = (int) ratingBar.getRating();

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL) //Setting the Root URL
                .build();

        AppConfig.comment api = adapter.create(AppConfig.comment.class);
        api.usercomments(
                name,
                comment,
                rating,
                new Callback<Response>() {
                    @Override
                    public void success(Response result, Response response) {

                        try {

                            BufferedReader reader = new BufferedReader(new InputStreamReader(result.getBody().in()));
                            String resp;
                            resp = reader.readLine();
                            Log.d("success", "" + resp);

                            JSONObject jObj = new JSONObject(resp);
                            int success = jObj.getInt("success");

                            if(success == 1){
                                Toast.makeText(getApplicationContext(), "Buy Product Successful, Please Log in", Toast.LENGTH_SHORT).show();;
                                Intent intent = new Intent();
                                intent.setClass(CommentActivity.this, ViewComments.class);
                                startActivity(intent);
                            } else{
                                Toast.makeText(getApplicationContext(), "Buy Product Fail", Toast.LENGTH_SHORT).show();
                            }


                        } catch (IOException e) {
                            Log.d("Exception", e.toString());
                        } catch (JSONException e) {
                            Log.d("JsonException", e.toString());
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(CommentActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );

    }*/
}
