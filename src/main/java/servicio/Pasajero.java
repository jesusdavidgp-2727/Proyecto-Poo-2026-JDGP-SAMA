/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicio;

/**
 *
 * @author Usaurio
 */
public class Pasajero {
   private String documento;
   private String nombre;
   
   public Pasajero(String documento, String nombre) {
        this.documento = documento;
        this.nombre = nombre;
    }
   
   public Pasajero(){
       this.documento = "";
       this.nombre = "";
   }

    public String getDocumento() {
        return documento;
    }
    public void setDocumento(String documento) {
        this.documento = documento;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "\ndocumento : "+this.documento+
                "\nNombre : "+this.nombre;
    }
}
