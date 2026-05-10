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
        Ruta r01 = new Ruta("R01", "Cucuta", "Bucaramanga", 60000);
        Ruta r02 = new Ruta("R02", "Cucuta", "Bogotá", 160000);
        Ruta r03 = new Ruta("R03", "Cucuta", "Medellin", 180000);
        Ruta r04 = new Ruta("R04", "Cucuta", "Cartagena", 220000);
        listaRutas.add(r01); listaRutas.add(r02); listaRutas.add(r03); listaRutas.add(r04);
        
        Bus b101 = new Bus("KAA-101", "NORMAL", "DISPONIBLE");
        Bus b202 = new Bus("KBB-202", "EJECUTIVO", "DISPONIBLE");
        Bus b303 = new Bus("KCC-303", "NORMAL", "DISPONIBLE");
        Bus b404 = new Bus("KDD-404", "EJECUTIVO", "DISPONIBLE");
        Bus b505 = new Bus("KEE-505", "NORMAL", "MANTENIMIENTO");
        Bus b606 = new Bus("KFF-606", "EJECUTIVO", "DISPONIBLE");
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
    
    
    public void registrarBus(Bus nuevoBus) {this.listaBuses.add(nuevoBus);}

    public void registrarRuta(Ruta nuevaRuta) {this.listaRutas.add(nuevaRuta);}

    public void registrarSalida(Salida nuevaSalida) {this.listaSalidas.add(nuevaSalida);}
    
    public ArrayList<Ruta> getListaRutas() {return listaRutas;}

    public ArrayList<Bus> getListaBuses() {return listaBuses;}

    public ArrayList<Salida> getListaSalidas() {return listaSalidas;}

    public double getDineroEnCaja() { return dineroEnCaja; }
    public void setDineroEnCaja(double dineroEnCaja) { this.dineroEnCaja = dineroEnCaja; }
    public String registrarMetodo(){
        return "Venta";
    }
}
    

