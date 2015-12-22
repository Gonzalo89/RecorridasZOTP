package com.example.facundo.recorridaszotp._5_Presentation;

import android.app.Activity;
import android.app.DialogFragment;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.activeandroid.ActiveAndroid;
import com.example.facundo.recorridaszotp.R;
import com.example.facundo.recorridaszotp._0_Infraestructure.DBUtils;
import com.example.facundo.recorridaszotp._0_Infraestructure.DatePickerFragment;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._2_DataAccess.GrupoFamiliarDataAccess;
import com.example.facundo.recorridaszotp._2_DataAccess.RanchadaDataAccess;
import com.example.facundo.recorridaszotp._2_DataAccess.VisitaDataAccess;
import com.example.facundo.recorridaszotp._2_DataAccess.ZonaDataAccess;
import com.example.facundo.recorridaszotp._3_Domain.GrupoFamiliar;
import com.example.facundo.recorridaszotp._3_Domain.Ranchada;
import com.example.facundo.recorridaszotp._3_Domain.Visita;
import com.example.facundo.recorridaszotp._3_Domain.Zona;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class PersonaFragment extends Fragment implements OnMapReadyCallback {
    private static View vista;
    private EditText etFechaNacimiento = null;
    private EditText etNombre = null;
    private EditText etApellido = null;
    private EditText etObservaciones = null;
    private EditText etDNI = null;
    private Spinner sGrupoFamiliar = null;
    private Spinner sZona = null;
    private Spinner sRanchada = null;
    private MapFragment mapFragmentPersona = null;
    private Marker marker = null;
    private boolean locationCargada = false;
    ArrayAdapter<String> adaptadorFamilia = null;
    ArrayAdapter<String> adaptadorZona = null;
    ArrayAdapter<String> adaptadorRanchada = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        View v = inflater.inflate(R.layout.fragment_persona, container, false);
        if (vista != null) {
            ViewGroup parent = (ViewGroup) vista.getParent();
            if (parent != null)
                parent.removeView(vista);
        }
        try {
            vista = inflater.inflate(R.layout.fragment_persona, container, false);
        } catch (InflateException e) {

        }

        mapFragmentPersona = (MapFragment) (getChildFragmentManager().findFragmentById(R.id.mapPersona));
        if (mapFragmentPersona == null) {
            mapFragmentPersona = (MapFragment) (getFragmentManager().findFragmentById(R.id.mapPersona));
        }

        mapFragmentPersona.getMap().setMyLocationEnabled(true);
        mapFragmentPersona.getMapAsync(this);
        mapFragmentPersona.getMap().setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (!MainActivity.editandoPersona) {
                    mapFragmentPersona.getMap().clear();
                    marker = mapFragmentPersona.getMap().addMarker(new MarkerOptions().position(new LatLng(
                            latLng.latitude, latLng.longitude)));
                    MainActivity.visitaSeleccionada.setUbicacion(marker.getPosition());
                }
            }
        });

        mapFragmentPersona.getMap().setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {

            @Override
            public void onMyLocationChange(Location myLocation) {
                if (!locationCargada) {
                    mapFragmentPersona.getMap().animateCamera(
                            CameraUpdateFactory.newLatLngZoom(new LatLng(myLocation.getLatitude(),
                                    myLocation.getLongitude()), Utils.ZOOM_STANDAR));
                    locationCargada = true;
                }
                centrarMapa(myLocation);
            }
        });

        ImageButton ib = (ImageButton) vista.findViewById(R.id.bFechaNacimiento);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDataPicker();
            }
        });

        etNombre = (EditText) vista.findViewById(R.id.ETNombre);
        etApellido = (EditText) vista.findViewById(R.id.ETApellido);
        etFechaNacimiento = (EditText) vista.findViewById(R.id.ETFechaNacimiento);
        etObservaciones = (EditText) vista.findViewById(R.id.ETObservaciones);
        etDNI = (EditText) vista.findViewById(R.id.ETDni);
        sGrupoFamiliar = (Spinner) vista.findViewById(R.id.spinner_grupo_familiar);
        sZona = (Spinner) vista.findViewById(R.id.spinner_zona);
        sRanchada = (Spinner) vista.findViewById(R.id.spinner_ranchada);

        //Grupo Familiar
        DBUtils.getGrupoFamiliarTest();//FIXME borrar, solo para prueba
        sGrupoFamiliar = (Spinner) vista.findViewById(R.id.spinner_grupo_familiar);
        final List<GrupoFamiliar> lGrupoFamiliar = GrupoFamiliarDataAccess.get().getAll();//TODO revisar, puede ser null sin internet
        final List<String> familiasString = new ArrayList<String>();
        for (GrupoFamiliar grupoFamiliar : lGrupoFamiliar) {
            familiasString.add(grupoFamiliar.getNombre());
        }
        adaptadorFamilia = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, familiasString);
        adaptadorFamilia.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        sGrupoFamiliar.setAdapter(adaptadorFamilia);

        //Zona
        DBUtils.getZonaTest();//FIXME borrar, solo para prueba
        Spinner sZona = (Spinner) vista.findViewById(R.id.spinner_zona);
        final List<Zona> lZonas = ZonaDataAccess.get().getAll();//TODO revisar, puede ser null sin internet
        final List<String> zonasString = new ArrayList<String>();
        for (Zona zona : lZonas) {
            zonasString.add(zona.getNombre());
        }
        adaptadorZona = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, zonasString);
        adaptadorZona.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        sZona.setAdapter(adaptadorZona);

        //Ranchada
        DBUtils.getRanchadaTest();//FIXME borrar, solo para prueba
        Spinner sRanchada = (Spinner) vista.findViewById(R.id.spinner_ranchada);

        final List<Ranchada> lRanchada = RanchadaDataAccess.get().getAll();//TODO revisar, puede ser null sin internet
        final List<String> ranchadasString = new ArrayList<String>();
        for (Ranchada ranchada : lRanchada) {
            ranchadasString.add(ranchada.getNombre());
        }
        adaptadorRanchada = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, ranchadasString);
        adaptadorRanchada.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        sRanchada.setAdapter(adaptadorRanchada);

        actualizar();
        return vista;
    }

    private void showDataPicker() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), Utils.DATE_PICKER_PERSONA);
    }

    public void cargarFecha(int day, int month, int year) {
        etFechaNacimiento.setText(Integer.toString(day) + "/" +
                Integer.toString(month) + "/" + Integer.toString(year));
    }

    public void actualizar() {
        if (etNombre != null) {
            if (MainActivity.personaSeleccionada.getNombre() != null)
                etNombre.setText(MainActivity.personaSeleccionada.getNombre());
            else
                etNombre.setText("");

            if (MainActivity.personaSeleccionada.getApellido() != null)
                etApellido.setText(MainActivity.personaSeleccionada.getApellido());
            else
                etApellido.setText("");

            if (MainActivity.personaSeleccionada.getObservaciones() != null)
                etObservaciones.setText(MainActivity.personaSeleccionada.getObservaciones());
            else
                etObservaciones.setText("");

            if (MainActivity.personaSeleccionada.getDNI() != null)
                etDNI.setText(MainActivity.personaSeleccionada.getDNI());
            else
                etDNI.setText("");

            if (MainActivity.personaSeleccionada.getGrupoFamiliar() != null) {
                sGrupoFamiliar.setSelection(adaptadorFamilia.getPosition(
                        MainActivity.personaSeleccionada.getGrupoFamiliar().getNombre()));
            }

            if (MainActivity.personaSeleccionada.getZona() != null) {
                sZona.setSelection(adaptadorZona.getPosition(
                        MainActivity.personaSeleccionada.getZona().getNombre()));
            }

            if (MainActivity.personaSeleccionada.getRanchada() != null) {
                sRanchada.setSelection(adaptadorRanchada.getPosition(
                        MainActivity.personaSeleccionada.getRanchada().getNombre()));
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapFragmentPersona.getMap().clear();
        if (MainActivity.personaSeleccionada != null) {
            Visita visita = VisitaDataAccess.get().findUltimaVisita(MainActivity.personaSeleccionada);
            if (visita != null)
                if (visita.getUbicacion() != null) {
                    marker = mapFragmentPersona.getMap().addMarker(new MarkerOptions().position(new LatLng(
                            visita.getUbicacion().latitude,
                            visita.getUbicacion().longitude)));
                    centrarMapa(visita.getUbicacion());
                }
        }
    }

    private void centrarMapa(LatLng ubicacion) {
        if (!locationCargada) {
            mapFragmentPersona.getMap().animateCamera(
                    CameraUpdateFactory.newLatLngZoom(ubicacion, Utils.ZOOM_STANDAR));
            locationCargada = true;
        }
    }

    private void centrarMapa(Location myLocation) {
        if (!locationCargada) {
            mapFragmentPersona.getMap().animateCamera(
                    CameraUpdateFactory.newLatLngZoom(new LatLng(myLocation.getLatitude(),
                            myLocation.getLongitude()), Utils.ZOOM_STANDAR));
            locationCargada = true;
        }
    }

    @Override
    public void onAttach(Activity activity) { //No anda el onAttach(Context context) can API < 23
        super.onAttach(activity);
        ((MainActivity)activity).getAppbar().setTitle(Utils.PERSONA);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ((MainActivity)getActivity()).getAppbar().setTitle(Utils.HOME);
    }
}
