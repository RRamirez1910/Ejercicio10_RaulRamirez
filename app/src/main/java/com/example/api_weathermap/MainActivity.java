package com.example.api_weathermap;

import androidx.appcompat.app.AppCompatActivity;



import android.os.Bundle;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    TextView tv_fecha,tv_ciudad,tv_temp,tv_tiempo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_fecha = (TextView) findViewById(R.id.tv_fecha);
        tv_ciudad = (TextView) findViewById(R.id.tv_ciudad);
        tv_temp = (TextView) findViewById(R.id.tv_temp);
        tv_tiempo = (TextView) findViewById(R.id.tv_tiempo);

        find_weather();
    }

        public void find_weather(){
            String url="http://api.openweathermap.org/data/2.5/weather?q=madrid,es&appid=9e47ce3836d2e08114c659c607fdb4bc";

            JsonObjectRequest jor=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try{
                        JSONObject main_object=response.getJSONObject("main");
                        JSONArray array=response.getJSONArray("weather");
                        JSONObject object=array.getJSONObject(0);
                        String temp=String.valueOf(main_object.getDouble("temp"));
                        String descripcion=object.getString("description");
                        String ciudad=response.getString("name");

                        // tv_temp.setText(temp);
                        tv_ciudad.setText(ciudad);
                        tv_tiempo.setText(descripcion);

                        Calendar calendar= Calendar.getInstance();
                        SimpleDateFormat sdf=new SimpleDateFormat("EEEE=MM-dd");
                        String formato=sdf.format(calendar.getTime());

                        tv_fecha.setText(formato);

                        double temp_int=Double.parseDouble(temp);
                        double centi=temp_int-273;
                        centi=Math.round(centi);
                        int i=(int)centi;
                        tv_temp.setText(String.valueOf(i));

                    }catch(JSONException e){
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }
            );
            RequestQueue queue= Volley.newRequestQueue(this);
            queue.add(jor);

        }


}
