/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicio;

/**
 *
 * @author Usaurio
 */
public class Ruta {
    private String codigoRuta;
    private String origen;
    private String destino;
    private double tarifaBase;
    
    public Ruta(String codigoRuta,String origen,String destino,double tarifaBase){
        this.codigoRuta = codigoRuta;
        this.destino = destino;
        this.origen = origen;
        this.tarifaBase = tarifaBase;
    }
    public Ruta(){
        this.codigoRuta = "";
        this.destino = "";
        this.origen = "";
        this.tarifaBase = 0.0;
    }

    public String getCodigoRuta() {
        return codigoRuta;
    }
    public void setCodigoRuta(String codigoRuta) {
        this.codigoRuta = codigoRuta;
    }
    public String getOrigen() {
        return origen;
    }
    public void setOrigen(String origen) {
        this.origen = origen;
    }
    public String getDestino() {
        return destino;
    }
    public void setDestino(String destino) {
        this.destino = destino;
    }
    public double getTarifaBase() {
        return tarifaBase;
    }
    public void setTarifaBase(double tarifaBase) {
        this.tarifaBase = tarifaBase;
    }
    @Override
    public String toString() {
        return "\ncodigoRuta : "+this.codigoRuta+
                "\norigen : "+this.origen+
                "\ndestino : "+this.destino +
                "\ntarifaBase : "+this.tarifaBase;
    }
}