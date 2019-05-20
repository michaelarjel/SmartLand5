package com.example.smartland;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.smartland.modelos.LoginRequest;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnIngresar = (Button) findViewById(R.id.btnIngresar);
        final EditText usuarioT = (EditText) findViewById(R.id.txtUser);
        final EditText claveT = (EditText) findViewById(R.id.txtPass);


       btnIngresar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               final String usuario = usuarioT.getText().toString();
               final String clave = claveT.getText().toString();
               final Response.Listener<String> respuesta = new Response.Listener<String>() {
                   @Override
                   public void onResponse(String response) {
                       try{
                           JSONObject jsonRespuesta = new JSONObject(response);
                           boolean ok = jsonRespuesta.getBoolean("success");
                           if(ok){
                               String nombre = jsonRespuesta.getString("nombre");
                               int edad = jsonRespuesta.getInt("edad");
                               Intent perfil = new Intent(MainActivity.this, Perfil.class);
                               perfil.putExtra("nombre", nombre);
                               perfil.putExtra("edad", edad);
                               MainActivity.this.startActivity(perfil);
                               MainActivity.this.finish();
                           }else{
                               AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);
                               alerta.setMessage("Falló en el Login").setNegativeButton("Reintentar", null).create().show();
                           }
                       }catch (JSONException e){
                           e.getMessage();
                       }


                   }


               };
               LoginRequest r = new LoginRequest(usuario, clave, respuesta);



               RequestQueue cola = Volley.newRequestQueue(MainActivity.this);
               cola.add(r);
           }
       });


    }
}
