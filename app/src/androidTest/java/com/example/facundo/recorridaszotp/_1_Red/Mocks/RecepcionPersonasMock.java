package com.example.facundo.recorridaszotp._1_Red.Mocks;

import com.example.facundo.recorridaszotp._1_Red.Delegates.AsyncDelegate;
import com.example.facundo.recorridaszotp._1_Red.RecepcionPersonas;

import org.json.JSONObject;

/**
 * Created by Facundo on 14/09/2015.
 */
public class RecepcionPersonasMock extends RecepcionPersonas {

    public RecepcionPersonasMock(AsyncDelegate delegate, JSONObject respuesta) {
        this.delegate = delegate;
        this.respuesta = respuesta;
    }

    @Override
    protected String doInBackground(String... params) {
        return this.getRespuesta().toString();
    }

}
