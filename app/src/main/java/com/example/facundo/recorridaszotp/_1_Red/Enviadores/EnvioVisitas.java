package com.example.facundo.recorridaszotp._1_Red.Enviadores;

import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.example.facundo.recorridaszotp._0_Infraestructure.JsonUtils.PersonaJsonUtils;
import com.example.facundo.recorridaszotp._0_Infraestructure.JsonUtils.VisitaJsonUtils;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._1_Red.Delegates.AsyncDelegate;
import com.example.facundo.recorridaszotp._1_Red.EnvioPost;
import com.example.facundo.recorridaszotp._3_Domain.Persona;
import com.example.facundo.recorridaszotp._3_Domain.Visita;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Facundo on 17/10/2015.
 */
public class EnvioVisitas extends BasicEnvio<Visita> {
    private AsyncDelegate delegate;

    public EnvioVisitas(List<Visita> visitas) {
        this(visitas, null);
    }

    public EnvioVisitas(List<Visita> visitas, AsyncDelegate delegate) {
        super(VisitaJsonUtils.get(), visitas);
        this.delegate = delegate;
    }

    @Override
    protected void onPostExecute(String result) {
        try {
            this.respuesta = new JSONObject(result);
        } catch (Exception ex) {
            Log.e(Utils.APPTAG, "Envio visitas respuestaJsonInvalida: " + ex.getMessage());
        }

        ActiveAndroid.beginTransaction();
        try {
            for (Visita visita : this.ts) {
                visita.setWebId(this.respuesta.getJSONObject("datos").optInt(visita.getId().toString()));
                visita.save();
            }
            ActiveAndroid.setTransactionSuccessful();
            if (this.delegate != null) {
                delegate.executionFinished(this.respuesta.toString());
            }
        } catch (Exception ex) {
            Log.e(Utils.APPTAG, "falloEnviarVisitas: " + ex.getMessage());
        } finally {
            ActiveAndroid.endTransaction();
        }
    }

}