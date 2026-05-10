/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicio;

/**
 *
 * @author Usaurio
 */
public class Sillas {
   private int numAsiento;
   private boolean estado;
   
   public Sillas(int numAsiento, boolean estado) {
        this.numAsiento = numAsiento;
        this.estado = estado;
    }
   
   public Sillas(){
       this.numAsiento = 0;
       this.estado = false;//false vacia true llena
   }

    public int getNumAsiento() {
        return numAsiento;
    }
    public void setNumAsiento(int numAsiento) {
        this.numAsiento = numAsiento;
    }
    public boolean isEstado() {
        return estado;
    }
    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "\nnumAsiento : "+this.numAsiento+
                "\nEstado : "+this.estado;
    }
}