package com.haipham.kennyn.acvitity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.haipham.kennyn.R;
import com.haipham.kennyn.adater.CaterogyAdapter;
import com.haipham.kennyn.app.AppController;
import com.haipham.kennyn.model.Caterogy;
import com.haipham.kennyn.util.CustomProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CaterogyActivity extends AppCompatActivity {

    private static final String TAG = CaterogyActivity.class.getSimpleName();

//    private static final String CHANNEL_ID = "UCDIJPT98nv7gcWgQ8TeJmrg"; // Dann English
    private static final String CHANNEL_ID = "UCYqbdGx72U9L9L0H6KanS7w"; // Kenny English
    private static final int MAX_RESULT = 20;
    private static final String KEY = "AIzaSyAKOtDCpaGQ9624yS8-9pADYH0qFvmK6f8";
    private static final String url = "https://www.googleapis.com/youtube/v3/playlists?part=contentDetails&channelId=" + CHANNEL_ID + "&maxResults=" + MAX_RESULT + "&key=" + KEY;

    private RecyclerView recyclerView;
    private CaterogyAdapter likeContentAdapter;
    private Caterogy caterogy = new Caterogy();
    private List<Caterogy> caterogyList = new ArrayList<>();
    private CustomProgressDialog customProgressDialog;
    private ImageView imvBg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imvBg = (ImageView)findViewById(R.id.imv_bg);
        if(isNetworkConnected()){
            makeJsonObjectRequest();
        } else {
            Toast.makeText(this,"Check internet please !", Toast.LENGTH_LONG).show();
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    private String jsonResponse;
    private void makeJsonObjectRequest() {

        customProgressDialog = new CustomProgressDialog(this);
        customProgressDialog.show();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {

                    String name = response.getString("kind");
                    String email = response.getString("etag");
//                    String nextPageToken = response.getString("nextPageToken");

                    JSONObject phone = response.getJSONObject("pageInfo");
                    int totalResults = phone.getInt("totalResults");
                    int resultsPerPage = phone.getInt("resultsPerPage");

                    JSONArray items = response.getJSONArray("items");

                    for (int i = 0; i < items.length(); i++) {
                        Caterogy caterogy = new Caterogy();
                        JSONObject item = items.getJSONObject(i);
                        JSONObject snippet = item.getJSONObject("contentDetails");
                        Log.d("hailpt ======", "itemCount = " + snippet.getString("itemCount"));
                        Log.d("hailpt ======", "Id = " + item.getString("id"));
                        caterogy.setId(item.getString("id"));
                        caterogy.setItemCount(snippet.getString("itemCount"));
                        if (!item.getString("id").equals("PL7YqrWXed1aaFMn8Rakj0OQb0_dkw71hs")&&!item.getString("id").equals("PL7YqrWXed1aZMHsToX32rksxdvVtczdFN")&&!item.getString("id").equals("PL7YqrWXed1aZMHsToX32rksxdvVtczdFN")&&!item.getString("id").equals("PL7YqrWXed1abbjqN85ERxqsHUHHIxfiKF")) {
                            caterogyList.add(caterogy);
                        }
                    }

                    Log.d("hailpt ======", "SIZE = " + caterogyList.size());
                    Gson gson = new Gson();
                    String json = gson.toJson(caterogyList);

                    Log.d("hailpt", "json ======" + json);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: ------------ " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
                updateLayout();
                hidePDialog();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidePDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    public void updateLayout() {
        imvBg.setVisibility(View.GONE);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        likeContentAdapter = new CaterogyAdapter(this, caterogyList, new CaterogyAdapter.OnRemoveCallBack() {
            @Override
            public void remove(final int pos) {

            }

            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(CaterogyActivity.this, VlogListActitivty.class);
                intent.putExtra("CHANNEL_ID", caterogyList.get(position).getId());
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                startActivity(intent);
                Log.d(TAG, "CHANNEL_ID CHANNEL_ID == " + caterogyList.get(position).getId());
            }
        });

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
//        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(likeContentAdapter);
        recyclerView.setNestedScrollingEnabled(false);
    }

    private void hidePDialog() {
        if (customProgressDialog != null) {
            customProgressDialog.dismiss();
            customProgressDialog = null;
        }
    }
}
