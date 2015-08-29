package com.example.facundo.recorridaszotp._5_Presentation;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.facundo.recorridaszotp.R;
import com.example.facundo.recorridaszotp._1_Infraestructure.AdaptadorListaPersonas;
import com.example.facundo.recorridaszotp._1_Infraestructure.DBUtils;
import com.example.facundo.recorridaszotp._2_DataAccess.PersonaDataAccess;
import com.example.facundo.recorridaszotp._3_Domain.Persona;

import java.util.ArrayList;
import java.util.List;

public class ListaPersonas extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_personas);

        DBUtils.loadDefaultDB(); //TODO Borrar
        List<Persona> listaPersonas = PersonaDataAccess.getAll();

        AdaptadorListaPersonas adaptador =
                new AdaptadorListaPersonas(getApplicationContext(), listaPersonas);

        ListView lViewPersonas = (ListView)findViewById(R.id.listaPersonas);

        lViewPersonas.setAdapter(adaptador);
        lViewPersonas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> a, View view, int pos, long id) {
                /* C�digo a ejecutar */
                Toast.makeText(getApplicationContext(),
                        "Long Click", Toast.LENGTH_SHORT).show();
                return true;
            }});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lista_personas, menu);
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
}
