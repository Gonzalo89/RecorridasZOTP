package com.example.facundo.recorridaszotp._0_Infraestructure;

import android.test.AndroidTestCase;

import com.activeandroid.query.Select;
import com.example.facundo.recorridaszotp._2_DataAccess.PersonaDataAccess;
import com.example.facundo.recorridaszotp._3_Domain.Persona;
import com.example.facundo.recorridaszotp._3_Domain.Visita;

import java.util.List;

/**
 * Created by Facundo on 22/08/2015.
 */
public class DataBaseTest extends AndroidTestCase {

    public void testGuardarPersona() throws Exception {
        Persona persona = new Persona();
        persona.setNombre("Juan");
        persona.setApellido("Perez");

        persona.save();

        Persona result = new Select()
                .from(Persona.class)
                .where("Id = ?", persona.getId())
                .executeSingle();

        assertEquals(persona.getNombre(), result.getNombre());
        assertEquals(persona.getApellido(), result.getApellido());
    }

    public void testLoadDefaultDB() throws Exception {
        DBUtils.loadDefaultDB();

        assertEquals("Cantidad de personas defaultDB incorrecta", 4, PersonaDataAccess.getAll().size());
    }

    public void testGuardarVisita() {
        DBUtils.loadDefaultDB();

        Visita visita = new Select()
                .from(Visita.class)
                .where("fecha = ?", 1425990960000L)
                .executeSingle();

        assertEquals(visita.getFecha(), (Long) 1425990960000L);
        assertEquals(Utils.getDateTime(visita.getFecha()), "2015-03-10T09:36:00-0300");
    }

    public void testVisitasGuardadas() throws Exception {
        DBUtils.loadDefaultDB();

        Persona persona = PersonaDataAccess.findByWebId(DBUtils.getPersonasTest().get(0).getWebId());

        List<Visita> visitas = persona.visitas();

        assertEquals("fallo cantidad de visitas", 2, visitas.size());
        assertEquals((Long)1425990960000L, visitas.get(0).getFecha());
        assertEquals((Long)1425992960000L, visitas.get(1).getFecha());
    }
}
