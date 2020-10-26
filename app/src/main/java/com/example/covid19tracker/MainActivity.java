package com.example.covid19tracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
    private ArrayList<CoronaItem> CoronaItemArrayList;

    private RecyclerView recyclerView;


    private RequestQueue requestQueue;
    private TextView dailyDeaths,dailyConfirm,dailyReco,
            dateHeaders,totalDeath,totalConfirm,totalRecovered;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        dailyConfirm=findViewById(R.id.dailyConfirmed);
        dailyDeaths=findViewById(R.id.dailyDeath);
        dailyReco=findViewById(R.id.dailyRecovered);
        dateHeaders=findViewById(R.id.dateHeader);
        totalRecovered=findViewById(R.id.todayRecovered);
        totalConfirm=findViewById(R.id.totalConfirm);
        totalDeath=findViewById(R.id.todayDeath);
        recyclerView = findViewById(R.id.myRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        CoronaItemArrayList=new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);
        jsonParse();
    }
    private void jsonParse(){
        String url="https://api.covid19india.org/v4/data.json";
        final JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                for (Iterator<String> it = response.keys(); it.hasNext(); ) {
                    try {
                        String key = it.next();
                        JSONObject stateWiseArrayJSONObject = response.getJSONObject(key);
                        String active = stateWiseArrayJSONObject.getJSONObject("total").getString("tested");
                        String death = stateWiseArrayJSONObject.getJSONObject("total").getString("deceased");
                        String recovered = stateWiseArrayJSONObject.getJSONObject("total").getString("recovered");
                        String state = key;
                        String confirmed = stateWiseArrayJSONObject.getJSONObject("total").getString("confirmed");
                        String lastUpdated = stateWiseArrayJSONObject.getJSONObject("meta").getString("last_updated");

                        JSONObject deltaObject = stateWiseArrayJSONObject.getJSONObject("delta");
                        String todayActive = deltaObject.getString("confirmed");
                        String todayDeath = deltaObject.getString("deceased");
                        String todayRecovered = deltaObject.getString("recovered");

                        CoronaItem citem = new CoronaItem(state, death, active, recovered, confirmed, lastUpdated
                                , todayDeath, todayRecovered, todayActive);
                        CoronaItemArrayList.add(citem);
                    }catch (JSONException e){

                    }
                }
                RecyclerViewAdapter adapter= new RecyclerViewAdapter(MainActivity.this, CoronaItemArrayList);
                recyclerView.setAdapter(adapter);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(request);

    }


    private String getFormattedDate(String dateHeader) {

        switch (dateHeader.substring(3,5)){
            case "01":
                return dateHeader.substring(0,2)+"Jan";
            case "02":
                return dateHeader.substring(0,2)+"Feb";
            case "03":
                return dateHeader.substring(0,2)+"Mar";
            case "04":
                return dateHeader.substring(0,2)+"Apr";
            case "05":
                return dateHeader.substring(0,2)+"May";
            case "06":
                return dateHeader.substring(0,2)+"Jun";
            case "07":
                return dateHeader.substring(0,2)+"Jul";
            case "08":
                return dateHeader.substring(0,2)+"Aug";
            case "09":
                return dateHeader.substring(0,2)+"Sep";
            case "10":
                return dateHeader.substring(0,2)+"Oct";
            case "11":
                return dateHeader.substring(0,2)+"Nov";
            case "12":
                return dateHeader.substring(0,2)+"Dec";
            default:
                return null;
        }
    }
}