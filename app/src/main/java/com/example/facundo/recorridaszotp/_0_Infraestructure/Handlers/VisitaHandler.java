package com.example.facundo.recorridaszotp._0_Infraestructure.Handlers;

import android.app.Activity;
import android.app.FragmentTransaction;

import com.example.facundo.recorridaszotp.R;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._2_DataAccess.Config;
import com.example.facundo.recorridaszotp._2_DataAccess.VisitaDataAccess;
import com.example.facundo.recorridaszotp._3_Domain.Persona;
import com.example.facundo.recorridaszotp._3_Domain.Visita;
import com.example.facundo.recorridaszotp._5_Presentation.MainActivity;
import com.example.facundo.recorridaszotp._5_Presentation.VisitaFragment;
import com.example.facundo.recorridaszotp._7_Interfaces.iFragmentChanger;
import com.example.facundo.recorridaszotp._7_Interfaces.iVisitaHandler;

import static com.example.facundo.recorridaszotp._5_Presentation.MainActivity.menuGuardar;

/**
 * Created by gonzalo on 23/05/16.
 */
public class VisitaHandler implements iVisitaHandler {

    @Override
    public void mostrarVisita(Persona persona, iFragmentChanger fragmentChanger) {
       Visita nuevaVisita = new Visita(persona);
       Visita ultimaVisita = VisitaDataAccess.get().findUltimaVisita(persona);
       if (ultimaVisita != null)
           nuevaVisita.setUbicacion(ultimaVisita.getUbicacion());
       Config.getInstance().setIsEditing(false);

       menuGuardar(true);
       MainActivity.visitaSeleccionada = nuevaVisita;
       VisitaFragment frag = new VisitaFragment();
       frag.actualizar();

       fragmentChanger.changeFragment(frag, Utils.FRAG_VISITA, true);

    }

}