package com.example.dp1;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class Frag1 extends Fragment {

    private String url = "https://pastebin.com/raw/pjfUQZL9";
    private TableLayout tableLayout;
    private RequestQueue requestQueue;
    private final float [] weights = new float[] {20,60,20};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag1_layout,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        // Match de componentes
        tableLayout = (TableLayout) getView().findViewById(R.id.tableLayout2);

        // Setear el table layout 2
        refresh();
    }

    public void refresh(){
        requestQueue = (RequestQueue) Volley.newRequestQueue(getActivity());
        Log.d("TAG","REFRESHING");
        Log.d("TAG","REFRESHING");
        Log.d("TAG","REFRESHING");
        Log.d("TAG","REFRESHING");
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Log.d("Flag","Refreshing");
                            TableRow.LayoutParams lpTable = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.WRAP_CONTENT);
                            for (int i = 0; i < response.length(); ++i) {
                                TableRow row = new TableRow(getActivity());
                                row.setLayoutParams(lpTable);
                                row.setWeightSum(100);
                                JSONObject x = response.getJSONObject(i);
                                TextView []tv = new TextView[3];
                                for(int j = 0; j < 3; ++j) {
                                    tv[j] = new TextView(getActivity());
                                    row.addView(tv[j]);
                                }
                                tv[0].setText(getHour(x.getString("fechaLlegada")));
                                tv[1].setText(x.getString("ciudadOrigen"));
                                tv[2].setText(x.getJSONObject("puerta").getString("numeroPuerta"));
                                for(int j = 0; j < 3; ++j){
                                    TableRow.LayoutParams lp = new TableRow.LayoutParams(0,TableRow.LayoutParams.WRAP_CONTENT,weights[j]);
                                    lp.setMargins(1,20,1,20);
                                    tv[j].setLayoutParams(lp);
                                    tv[j].setTextSize(22);
                                    tv[j].setTypeface(ResourcesCompat.getFont(getActivity(),R.font.oxygen));
                                    tv[j].setGravity(Gravity.CENTER);
                                }
                                tableLayout.addView(row,i);
                            }
                        }catch(JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("BRRRRRRRRRRRRRRRRRRRRRR","onErrorResponse");
                error.printStackTrace();
            }
        });
        requestQueue.add(request);
    }

    private String getHour(String x){
        String ans = "";
        for(int i = 11; i < 16; ++i)
            ans = ans + x.charAt(i);
        return ans;
    }

}
