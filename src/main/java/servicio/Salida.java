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
    private Silla[] sillas;
    
    public Salida(String idSalida, Ruta ruta, Bus bus, String fecha, String hora) {
        this.idSalida = idSalida;
        this.ruta = ruta;
        this.bus = bus;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = ESTADO_PROGRAMADA;
        
        // Inicializamos las sillas según la capacidad del bus asignado
        int capacidad = bus.getCapacidad();
        this.sillas = new Silla[capacidad];
        for (int i = 0; i < capacidad; i++) {
            this.sillas[i] = new Silla(i + 1); // Nacen LIBRES
        }
    }
    public Salida() {
        this.idSalida = "";
        this.ruta = null;
        this.bus = null;
        this.fecha = "";
        this.hora = "";
        this.estado = ESTADO_PROGRAMADA;
        this.sillas = new Silla[0];
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
    
    public Silla[] getSillas() { return sillas; }
    
    public double precioFinal(){
        double pfinal = ruta.getTarifaBase();
        if (bus.getTipoServicio().equalsIgnoreCase(Bus.TIPO_EJECUTIVO)) {
            return pfinal*1.20;
        }else{
            return pfinal;
        }
    }
    
    public boolean gestionOcupacionSilla(int numSilla) {
        if(this.estado.equalsIgnoreCase(ESTADO_CANCELADA) || this.estado.equalsIgnoreCase(ESTADO_FINALIZADA)){
            return false;
        } else {
            int indice = numSilla - 1;
            if(indice >= 0 && indice < sillas.length){ // Usamos 'sillas.length'
                Silla sillaAsignada = sillas[indice];
                
                if(sillaAsignada.isLibre()){ // Usamos el método isLibre() que creamos en Silla
                    sillaAsignada.setEstado(Silla.ESTADO_OCUPADA);
                    return true;
                }
            }
            return false;
        }
    }
    
    // Actualizado
    public int contarSillasDisponibles() {
        int cnt = 0;
        for(Silla s : sillas){
            if(s.isLibre()){
                cnt++;
            }
        }
        return cnt;
    }

    // Nuevo método para saber fácilmente cuántas se han vendido
    public int getCantidadSillasVendidas() {
        return sillas.length - contarSillasDisponibles();
    }
            
    // Actualizado con TU idea para mostrar en las listas de la GUI
    @Override
    public String toString() {
        return "Salida : " + this.idSalida +
               " | Ruta : " + this.ruta.getOrigen() + "-" + ruta.getDestino() +
               " | Bus : " + this.bus.getPlaca() +
               " | Fecha : " + this.fecha + " " + this.hora +
               " | Ocupación : " + getCantidadSillasVendidas() + "/" + sillas.length + // ¡TU IDEA AQUÍ!
               " | Estado : " + this.estado;
    }
                
}
