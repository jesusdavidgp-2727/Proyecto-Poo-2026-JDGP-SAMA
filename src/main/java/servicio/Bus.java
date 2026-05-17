/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicio;

/**
 *
 * @author Usaurio
 */
public class Bus {
    //variables para los estados
    public static final String TIPO_NORMAL = "NORMAL";
    public static final String TIPO_EJECUTIVO = "EJECUTIVO";

    public static final String ESTADO_DISPONIBLE = "DISPONIBLE";
    public static final String ESTADO_EN_SERVICIO = "EN_SERVICIO";
    public static final String ESTADO_MANTENIMIENTO = "MANTENIMIENTO";
    
    private String placa;
    private String tipoServicio;
    private String estado;
    private Sillas[] mySillas;
    
    
    public Bus(String placa, String tipoServicio, String estado) {
        this.placa = placa.trim().toUpperCase();
        this.tipoServicio = tipoServicio.trim().toUpperCase();
        this.estado = estado.trim().toUpperCase();

        int capacidad;
        if (this.tipoServicio.equalsIgnoreCase(TIPO_EJECUTIVO)) {
            capacidad = 30;
        } else {
            capacidad = 40;
        }

        this.mySillas = new Sillas[capacidad];
        for (int i = 0; i < capacidad; i++) {
            this.mySillas[i] = new Sillas(i + 1, false);
        }
    }
    public Bus(String placa, String tipoServicio) {
        this(placa, tipoServicio, ESTADO_DISPONIBLE);
    }
    public Bus(){
        this.placa = "";
        this.tipoServicio = "";
        this.estado = "";
        this.mySillas = new Sillas[0];
    }

    public String getPlaca() {return placa;}
    public void setPlaca(String placa) {
        this.placa = placa.trim().toUpperCase();
    }
    public String getTipoServicio() {return tipoServicio;}
    public void setTipoServicio(String tipoServicio) {this.tipoServicio = tipoServicio;}
    
    public String getEstado() {return estado;}
    public void setEstado(String estado) {this.estado = estado;}
    
    public Sillas[] getMySillas() {return mySillas;}
    public void setMySillas(Sillas[] mySillas) {this.mySillas = mySillas;}
    @Override
    public String toString() {
        return "\nPlaca: "+this.placa+
                "\n tipoServicio: "+this.tipoServicio+
                "\n estado: "+this.estado+
                "\n total sillas: "+ mySillas.length;
    }
    
}