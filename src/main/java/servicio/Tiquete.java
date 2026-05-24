package servicio;

public class Tiquete {
    public static final String ESTADO_VIGENTE = "VIGENTE";
    public static final String ESTADO_REPROGRAMADO = "REPROGRAMADO";
    public static final String ESTADO_REEMBOLSADO = "REEMBOLSADO";

    private String codigo;
    private Pasajero pasajero;
    private Salida salida;
    private int silla;
    private double valorPagado;
    private String estado;

    public Tiquete(String codigo, Pasajero pasajero, Salida salida, int silla, double valorPagado) {
        this.codigo = codigo;
        this.pasajero = pasajero;
        this.salida = salida;
        this.silla = silla;
        this.valorPagado = valorPagado;
        this.estado = ESTADO_VIGENTE;
    }

    public String getCodigo() {
        return codigo;
    }

    public Pasajero getPasajero() {
        return pasajero;
    }

    public Salida getSalida() {
        return salida;
    }

    public int getSilla() {
        return silla;
    }

    public double getValorPagado() {
        return valorPagado;
    }

    public void setValorPagado(double valorPagado) {
        this.valorPagado = valorPagado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado.trim().toUpperCase();
    }
}
