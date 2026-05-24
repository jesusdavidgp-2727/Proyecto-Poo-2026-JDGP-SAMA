/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicio;

/**
 *
 * @author Usaurio
 */
public class Silla {
    public static final String ESTADO_LIBRE = "LIBRE";
    public static final String ESTADO_OCUPADA = "OCUPADA";
    
   private int numAsiento;
   private String estado;
   
   public Silla(int numAsiento) {
        this.numAsiento = numAsiento;
        this.estado = ESTADO_LIBRE;
    }
   
   public Silla(){
       this.numAsiento = 0;
       this.estado = ESTADO_LIBRE;//false vacia true llena
   }

    public int getNumAsiento() {
        return numAsiento;
    }
    public void setNumAsiento(int numAsiento) {
        this.numAsiento = numAsiento;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public boolean isLibre() {
        return this.estado.equals(ESTADO_LIBRE);
    }
    
    @Override
    public String toString() {
        return "\nSilla : "+this.numAsiento+
                "\nEstado : "+this.estado;
    }
}