package com.example.facundo.recorridaszotp._3_Domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.example.facundo.recorridaszotp._0_Infraestructure.JsonUtils.PersonaJsonUtils;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._2_DataAccess.FamiliaDataAccess;
import com.example.facundo.recorridaszotp._2_DataAccess.RanchadaDataAccess;
import com.example.facundo.recorridaszotp._2_DataAccess.ZonaDataAccess;

import java.util.List;


@Table(name = "Personas")
public class Persona extends Model {
    @Column(name = "WebId")
    private int webId = -1; // -1 si es una persona no guardada en la BDWeb
    @Column(name = "Nombre")
    private String nombre;
    @Column(name = "Apellido")
    private String apellido;
    @Column(name = "Estado")
    private int estado;
    @Column(name = "Zona")
    private Zona zona;
    @Column(name = "FechaNacimiento")
    private String fechaNacimiento;
    @Column(name = "Pantalon")
    private String pantalon;
    @Column(name = "Remera")
    private String remera;
    @Column(name = "Zapatillas")
    private String zapatillas;
    @Column(name = "Observaciones")
    private String observaciones;
    @Column(name = "DNI")
    private String DNI;
    @Column(name = "Telefono")
    private String telefono;
    @Column(name = "Familia")
    private Familia familia = null;
    @Column(name = "Ranchada")
    private Ranchada ranchada = null;


    private String ultMod;

    public Persona() {
        super();
    }

    public Persona(String nombre, String apellido, int estado, int webId) {
        this(nombre, apellido, estado);
        this.webId = webId;
    }

    public Persona(String nombre, String apellido, int estado) {
        this(nombre, apellido);
        this.estado = estado;
    }

    public Persona(String nombre, String apellido) {
        this(nombre);
        this.apellido = apellido;
        this.estado = Utils.EST_ACTUALIZADO;
    }

    public Persona(String nombre) {
        super();
        this.nombre = nombre;
        this.estado = Utils.EST_ACTUALIZADO;
    }

    public void mergeFromWeb(Persona persona) throws Exception {
        if (persona.webId != this.getWebId()) {
            throw new Exception("MergeConDiferenteWebId");
        }
        this.nombre = persona.getNombre();
        this.apellido = persona.getApellido();
        this.estado = persona.getEstado();
        this.zona = persona.getZona();
        this.fechaNacimiento = persona.getFechaNacimiento();
        this.pantalon = persona.getPantalon();
        this.remera = persona.getRemera();
        this.zapatillas = persona.getZapatillas();
        this.observaciones = persona.getObservaciones();
        this.DNI = persona.getDNI();
        this.telefono = persona.getTelefono();
        this.familia = persona.getFamilia();
        this.ranchada = persona.getRanchada();
     }

    public List<Visita> visitas() {
        return getMany(Visita.class, "Persona");
    }

    public String getNombre() {
        return this.nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Familia getFamilia() {
        return familia;
    }

    public void setFamilia(Familia familia) {
        this.familia = familia;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getEstado() {
        return this.estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getWebId() {
        return this.webId;
    }

    public void setWebId(int webId) {
        this.webId = webId;
    }

    public Zona getZona() {
        return zona;
    }

    public void setZona(Zona zona) {
        this.zona = zona;
    }

    public String getUltMod() {
        return ultMod;
    }

    public void setUltMod(String ultMod) {
        this.ultMod = ultMod;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getPantalon() {
        return pantalon;
    }

    public void setPantalon(String pantalon) {
        this.pantalon = pantalon;
    }

    public String getRemera() {
        return remera;
    }

    public void setRemera(String remera) {
        this.remera = remera;
    }

    public String getZapatillas() {
        return zapatillas;
    }

    public void setZapatillas(String zapatillas) {
        this.zapatillas = zapatillas;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getFechaNacimientoMostrar() {
        String[] array = fechaNacimiento.split("-");
        return array[2] + "/" + array[1] + "/" + array[0];
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setFechaNacimientoDesdeMob(String fechaNacimiento) {
        String[] array = fechaNacimiento.split("/");
        if (array.length == 3)
            this.fechaNacimiento = array[2] + "-" + array[1] + "-" + array[0];
    }

    public Familia getGrupoFamiliar() {
        return familia;
    }

    public void setGrupoFamiliar(Familia familia) {
        this.familia = familia;
    }

    public Ranchada getRanchada() {
        return ranchada;
    }

    public void setRanchada(Ranchada ranchada) {
        this.ranchada = ranchada;
    }

    @Override
    public boolean equals(Object obj) {
        Persona other = (Persona) obj;
        try {//TODO Revisar equal Persona
            return (PersonaJsonUtils.get().toJSonValue(other).equals(PersonaJsonUtils.get().toJSonValue(this)));
        } catch (Exception e) {
            return false;
        }
    }

    public void setGrupoFamiliar(String grupoFamiliar) {
        this.familia = FamiliaDataAccess.get().find(grupoFamiliar);
    }

    public void setZona(String unaZona) {
        this.zona = ZonaDataAccess.get().find(unaZona);
    }

    public void setRanchada(String ranchada) {
        this.ranchada = RanchadaDataAccess.get().find(ranchada);
    }

    public void setZonaByWebId(int zonaWebId) {
        this.zona = ZonaDataAccess.get().findByWebId(zonaWebId);
    }

    public void setRanchadaByWebId(int ranchadaWebId) {
        this.ranchada = RanchadaDataAccess.get().findByWebId(ranchadaWebId);
    }

    public void setFamiliaByWebId(int familiaWebId) {
        this.familia = FamiliaDataAccess.get().findByWebId(familiaWebId);
    }

    public String toString() {
        return this.getNombre();
    }
}
