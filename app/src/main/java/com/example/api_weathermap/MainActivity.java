package com.example.api_weathermap;

import androidx.appcompat.app.AppCompatActivity;



import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
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

    TextView tv_fecha,tv_ciudad,tv_temp,tv_tiempo,tv_min,tv_max,tv_humedad;
    ImageView imageview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_fecha = (TextView) findViewById(R.id.tv_fecha);
        tv_ciudad = (TextView) findViewById(R.id.tv_ciudad);
        tv_temp = (TextView) findViewById(R.id.tv_temp);
        tv_tiempo = (TextView) findViewById(R.id.tv_tiempo);
        tv_min=(TextView)findViewById(R.id.tv_min);
        tv_max=(TextView)findViewById(R.id.tv_max);
        imageview=(ImageView)findViewById(R.id.imageView);
        tv_humedad=(TextView)findViewById(R.id.tv_humedad);

        Calendar calendar= Calendar.getInstance();
        SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
        String formato=sdf.format(calendar.getTime());

        tv_fecha.setText(formato);

        find_weather();
    }

        public void find_weather(){
            String url="https://api.openweathermap.org/data/2.5/weather?q=madrid,es&appid=9e47ce3836d2e08114c659c607fdb4bc&units=metric&lang=es";

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
                        String temp_min=String.valueOf(main_object.getDouble("temp_min"));
                        String temp_max=String.valueOf(main_object.getDouble("temp_max"));
                        String icono=object.getString("icon");
                        String humedad=String.valueOf(main_object.getDouble("humidity"));

                        //Establecer icono en base al tiempo y si es de día o de noche:
                        if(icono.equals("01d")){
                            imageview.setImageResource(R.drawable.i01d);
                        }else if(icono.equals("02d")){
                            imageview.setImageResource(R.drawable.i02d);
                        }else if(icono.equals("03d") || icono.equals("03n")){
                            imageview.setImageResource(R.drawable.i03d);
                        }else if(icono.equals("04d") || icono.equals("04n")){
                            imageview.setImageResource(R.drawable.i04d);
                        }else if(icono.equals("09d") || icono.equals("09n")){
                            imageview.setImageResource(R.drawable.i09d);
                        }else if(icono.equals("10d")){
                            imageview.setImageResource(R.drawable.i10d);
                        }else if(icono.equals("11d") || icono.equals("11n")){
                            imageview.setImageResource(R.drawable.i11d);
                        }else if(icono.equals("13d") || icono.equals("13n")){
                            imageview.setImageResource(R.drawable.i13d);
                        }else if(icono.equals("50d") || icono.equals("50n")){
                            imageview.setImageResource(R.drawable.i50d);
                        }else if(icono.equals("01n")){
                            imageview.setImageResource(R.drawable.i01n);
                        }else if(icono.equals("02n")) {
                            imageview.setImageResource(R.drawable.i02n);
                        }else if(icono.equals("10n")){
                            imageview.setImageResource(R.drawable.i10n);
                        }

                        // Asignamos los valores cogidos anteriormente a los TextView:

                        tv_humedad.setText(humedad.concat("%"));
                        tv_ciudad.setText(ciudad);
                        tv_tiempo.setText(descripcion);

                        //Establecer las temperaturas redondeadas
                        double temp_int=Double.parseDouble(temp_min);
                        double centi=Math.round(temp_int);
                        int i=(int)centi;
                        tv_min.setText(String.valueOf(i).concat("º"));

                        temp_int=Double.parseDouble(temp_max);
                        centi=Math.round(temp_int);
                        i=(int)centi;
                        tv_max.setText(String.valueOf(i).concat("º"));

                        temp_int=Double.parseDouble(temp);
                        centi=Math.round(temp_int);
                        i=(int)centi;
                        tv_temp.setText(String.valueOf(i).concat("º"));

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
