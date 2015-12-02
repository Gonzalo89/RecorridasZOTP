package com.example.facundo.recorridaszotp._2_DataAccess;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._3_Domain.Persona;
import com.example.facundo.recorridaszotp._3_Domain.Query.VisitaQuery;
import com.example.facundo.recorridaszotp._3_Domain.Visita;
import com.google.android.gms.maps.model.Marker;

import java.util.List;

/**
 * Created by Facundo on 03/10/2015.
 */
public class VisitaDataAccess extends BasicDataAccess<Visita> {

    private static VisitaDataAccess ourInstance = new VisitaDataAccess();

    public static VisitaDataAccess get() {
        return ourInstance;
    }

    private VisitaDataAccess() {
    }

    public Class getClase() {
        return Visita.class;
    }

    @Override
    public int acualizarDB(List<Visita> visitas) throws Exception {
        int resultado = -1;
        ActiveAndroid.beginTransaction();
        try {
            for (Visita visita : visitas) {
                Visita v = new Select()
                        .from(Visita.class)
                        .where("WebId = ?", visita.getWebId())
                        .executeSingle();
                if (v != null && visita.getEstado() == Utils.EST_BORRADO) {
                    v.delete();
                } else if (v != null) {
                    v.mergeFromWeb(visita);
                    v.save();
                } else {
                    visita.save();
                }
            }
            ActiveAndroid.setTransactionSuccessful();
            resultado = 0;
        } finally {
            ActiveAndroid.endTransaction();
        }
        return resultado;
    }

    public Visita find(VisitaQuery query) {
        if (query != null)
            if (query.observaciones != null)
                return new Select()
                        .from(Visita.class)
                        .where("Descripcion = ?", query.observaciones)
                        .executeSingle();

        return null;
    }

    public Visita findUltimaVisita(Persona persona) {
        if (persona.getId() != null)
            return new Select()
                    .from(Visita.class)
                    .where("Persona = ?", persona.getId())
                    .orderBy("Fecha DESC")
                    .executeSingle();
        else
            return null;
    }

    public List<Visita> findUltimasVisita() { //TODO Limitar cantidad de ultimas visitas en el mapa
        return new Select()
                .from(Visita.class)
                .execute();
    }

    public Visita find(Marker marker){
        return new Select()
                .from (Visita.class)
                .where ("Latitud = ?", marker.getPosition().latitude)
                .where ("Longitud = ?", marker.getPosition().longitude)
                .executeSingle();
    }
}
