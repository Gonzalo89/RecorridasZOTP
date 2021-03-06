package com.example.facundo.recorridaszotp._2_DataAccess;

import android.test.AndroidTestCase;

import com.activeandroid.ActiveAndroid;
import com.example.facundo.recorridaszotp._0_Infraestructure.DBUtils;
import com.example.facundo.recorridaszotp._3_Domain.Persona;
import com.example.facundo.recorridaszotp._3_Domain.Query.VisitaQuery;
import com.example.facundo.recorridaszotp._3_Domain.Visita;

import java.util.List;


/**
 * Created by Facundo on 03/10/2015.
 */
public class VisitaDataAccessTest extends AndroidTestCase {
    private Persona personaTest = null;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        ActiveAndroid.initialize(getContext());
        DBUtils.loadDefaultDB();
        personaTest = new Persona("Juan2");
        PersonaDataAccess.get().save(personaTest);
    }

    public void testGuardarVisita() throws Exception {
        Visita visita = new Visita(personaTest);
        visita.setDescripcion("TestObservaciones");
        visita.setLatitud(0.5);
        visita.setLongitud(0.88);
        visita.setFecha(9948632701L);
        VisitaDataAccess.get().save(visita);

        VisitaQuery query = new VisitaQuery();
        query.observaciones = "TestObservaciones";

        Visita visita2 = VisitaDataAccess.get().find(query);
        assertEquals("Visita grabada incorrectamente(Descripcion)", visita.getDescripcion(), visita2.getDescripcion());
        assertEquals("Visita grabada incorrectamente(Fecha)", visita.getFecha(), visita2.getFecha());
        assertEquals("Visita grabada incorrectamente(Latitud)", visita.getLatitud(), visita2.getLatitud());
        assertEquals("Visita grabada incorrectamente(Longitud)", visita.getLongitud(), visita2.getLongitud());
    }

    public void testUltimaVisita() throws Exception {
        Visita visita = new Visita(personaTest);
        visita.setDescripcion("TestObservaciones");
        visita.setLatitud(0.5);
        visita.setLongitud(0.88);
        visita.setFecha(9948632701L);
        VisitaDataAccess.get().save(visita);

        Visita unaVisita = VisitaDataAccess.get().findUltimaVisita(personaTest);
        assertEquals("Ultima visita no encontrada", 9948632701L, unaVisita.getFecha());
    }

public void testBorradoLogico() throws Exception {
    /*Se borran todas las visitas de una persona*/
        Persona unaPersona = PersonaDataAccess.get().findByWebId(1000);
        VisitaDataAccess.get().deleteLogico(unaPersona);

        List<Visita> visitasCero = VisitaDataAccess.get().findTodasVisitas(unaPersona);
        assertEquals("No se borraron las visitas en forma logica", 0, visitasCero.size());
    }
}
