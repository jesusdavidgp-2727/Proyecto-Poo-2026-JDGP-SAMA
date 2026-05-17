/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicio;
import java.util.ArrayList;

/**
 *
 * @author Usaurio
 */
public class Empresa {
    private ArrayList<Bus> listaBuses;
    private ArrayList<Ruta> listaRutas;
    private ArrayList<Salida> listaSalidas;
    private double dineroEnCaja;
    
    public Empresa(){
        this.listaBuses = new ArrayList<>();
        this.listaRutas = new ArrayList<>();
        this.listaSalidas = new ArrayList<>();
        
        cargarDatosIniciales();
    }
    private void cargarDatosIniciales(){
        Ruta r01 = new Ruta("R01", "Cucuta", "Bucaramanga", 80000);
        Ruta r02 = new Ruta("R02", "Cucuta", "Bogota", 160000);
        Ruta r03 = new Ruta("R03", "Cucuta", "Medellin", 180000);
        Ruta r04 = new Ruta("R04", "Cucuta", "Cartagena", 220000);
        listaRutas.add(r01); listaRutas.add(r02); listaRutas.add(r03); listaRutas.add(r04);
        
        Bus b101 = new Bus("KAA-101", Bus.TIPO_NORMAL, Bus.ESTADO_DISPONIBLE);
        Bus b202 = new Bus("KBB-202", Bus.TIPO_EJECUTIVO, Bus.ESTADO_DISPONIBLE);
        Bus b303 = new Bus("KCC-303", Bus.TIPO_NORMAL, Bus.ESTADO_DISPONIBLE);
        Bus b404 = new Bus("KDD-404", Bus.TIPO_EJECUTIVO, Bus.ESTADO_DISPONIBLE);
        Bus b505 = new Bus("KEE-505", Bus.TIPO_NORMAL, Bus.ESTADO_MANTENIMIENTO);
        Bus b606 = new Bus("KFF-606", Bus.TIPO_NORMAL, Bus.ESTADO_DISPONIBLE);
        listaBuses.add(b101); listaBuses.add(b202); listaBuses.add(b303); listaBuses.add(b404); listaBuses.add(b505); listaBuses.add(b606);
        
        listaSalidas.add(new Salida("S001", r01, b101, "15/03/2026", "06:00"));
        listaSalidas.add(new Salida("S002", r01, b202, "15/03/2026", "14:00"));
        listaSalidas.add(new Salida("S003", r02, b303, "16/03/2026", "07:00"));
        listaSalidas.add(new Salida("S004", r02, b404, "16/03/2026", "20:00"));
        listaSalidas.add(new Salida("S005", r03, b606, "17/03/2026", "05:30"));
        listaSalidas.add(new Salida("S006", r03, b101, "17/03/2026", "18:00"));
        listaSalidas.add(new Salida("S007", r04, b303, "18/03/2026", "06:30"));
        listaSalidas.add(new Salida("S008", r04, b202, "18/03/2026", "19:30"));
    }
    
    //Registrar cosas
    public boolean registrarBus(Bus nuevoBus) {
        if (nuevoBus == null || nuevoBus.getPlaca() == null || nuevoBus.getPlaca().isBlank()) {
            return false;
        }
        if(buscarBusPlaca(nuevoBus.getPlaca())!= null){
            return false;
        }
        this.listaBuses.add(nuevoBus);
        return true;
    }
    public boolean registrarRuta(Ruta nuevaRuta) {
        if (nuevaRuta == null || nuevaRuta.getCodigoRuta().isBlank()) {
            return false;
        }
        if (buscarRutaCodigo(nuevaRuta.getCodigoRuta()) != null) {
            return false;
        }
        this.listaRutas.add(nuevaRuta);
        return true;
    }
    public boolean registrarSalida(Salida nuevaSalida) {
        if (nuevaSalida == null || nuevaSalida.getIdSalida().isBlank()) {
            return false;
        }
        if (buscarSalidaId(nuevaSalida.getIdSalida()) != null) {
            return false;
        }
        this.listaSalidas.add(nuevaSalida);
        return true;
    }
    
    //Generacion automatica de codigos o id's
    public String generarCodigoRuta() {
        int mayor = 0;
    for (Ruta r : listaRutas) {
        String codigo = r.getCodigoRuta().replace("R", "");

        int numero = Integer.parseInt(codigo);

        if (numero > mayor) {
            mayor = numero;
        }
    }
        return String.format("R%02d", mayor + 1);
    }
    public String generarIdSalida() {
        int mayor = 0;
        for (Salida s : listaSalidas) {
            String id = s.getIdSalida().replace("S", "");

            int numero = Integer.parseInt(id);

            if (numero > mayor) {
                mayor = numero;
            }
        }
        return String.format("S%03d", mayor + 1);
    }
    //BUSQUEDAS
    public Ruta buscarRutaCodigo(String codigoRuta){
        if (codigoRuta == null || codigoRuta.isBlank()) {
            return null;
        }
        for (Ruta r : listaRutas) {
            if (r.getCodigoRuta().equalsIgnoreCase(codigoRuta)) {
                return r;
            }
        }
        return null;
    }
    public Salida buscarSalidaId(String idSalida) {
        if (idSalida == null || idSalida.isBlank()) {
            return null;
        }
        for (Salida s : listaSalidas) {
            if (s.getIdSalida().equalsIgnoreCase(idSalida)) {
                return s;
            }   
        }
        return null;
    }
    public Bus buscarBusPlaca (String placa){
        if (placa == null || placa.isBlank()) {
            return null;
        }
        for(Bus b: listaBuses){
            if(b.getPlaca().equalsIgnoreCase(placa)){
                return b;
            }
        }
        return null;
    }
    //getters
    public ArrayList<Ruta> getListaRutas() {return listaRutas;}

    public ArrayList<Bus> getListaBuses() {return listaBuses;}

    public ArrayList<Salida> getListaSalidas() {return listaSalidas;}
    //ToString
    public double getDineroEnCaja() { return dineroEnCaja; }
    public void setDineroEnCaja(double dineroEnCaja) { this.dineroEnCaja = dineroEnCaja; }
    public String registrarMetodo(){
        return "Venta";
    }
}
    

