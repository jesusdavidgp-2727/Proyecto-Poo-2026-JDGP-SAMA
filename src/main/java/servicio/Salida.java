/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicio;

/**
 *
 * @author Usaurio
 */
public class Salida {
    //variables para estado
    public static final String ESTADO_PROGRAMADA = "PROGRAMADA";
    public static final String ESTADO_EN_VIAJE = "EN_VIAJE";
    public static final String ESTADO_FINALIZADA = "FINALIZADA";
    public static final String ESTADO_CANCELADA = "CANCELADA";
    
    private String idSalida;
    private Ruta ruta;
    private Bus bus;
    private String fecha;
    private String hora;
    private String estado;
    
    
    public Salida(String idSalida,Ruta ruta,Bus bus,String fecha,String hora){
        this.idSalida = idSalida;
        this.ruta = ruta;
        this.bus = bus;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = ESTADO_PROGRAMADA;
    }
    public Salida() {
        this.idSalida = "";
        this.ruta = null;
        this.bus = null;
        this.fecha = "";
        this.hora = "";
        this.estado = ESTADO_PROGRAMADA;
    }
    
    public String getIdSalida() { return idSalida; }
    public void setIdSalida(String idSalida) { this.idSalida = idSalida; }

    public Ruta getRuta() { return ruta; }
    public void setRuta(Ruta ruta) { this.ruta = ruta; }

    public Bus getBus() { return bus; }
    public void setBus(Bus bus) { this.bus = bus; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado.trim().toUpperCase(); }
    
    public double precioFinal(){
        double pfinal = ruta.getTarifaBase();
        if (bus.getTipoServicio().equalsIgnoreCase(Bus.TIPO_EJECUTIVO)) {
            return pfinal*1.20;
        }else{
            return pfinal;
        }
    }
    
    public boolean gestionOcupacionSilla(int numSilla){
        if(this.estado.equalsIgnoreCase(ESTADO_CANCELADA) || this.estado.equalsIgnoreCase(ESTADO_FINALIZADA)){
            return false;
        }else{
            int indice = numSilla - 1;
            if(indice >= 0 && indice < bus.getMySillas().length){
                Sillas sillaAsignada = bus.getMySillas()[indice];
                
                if(!sillaAsignada.isEstado()){
                    sillaAsignada.setEstado(true);
                    return true;
                }
            }
            return false;
        }
    }
    
    public int contarSillasDisponibles(){
        int cnt = 0;
        for(Sillas s : bus.getMySillas()){
            if(!s.isEstado()){
                cnt++;
            }
        }
        return cnt;
    }
            
    @Override
    public String toString() {
        return "\nSalida : "+this.idSalida+
                "\nRuta : "+this.ruta.getOrigen() + "-" + ruta.getDestino()+
                "\nBus : "+this.bus.getPlaca()+
                "\nFecha : "+this.fecha+ "\nHora : "+this.hora+
                "\nEstado : "+this.estado;
    }
}
