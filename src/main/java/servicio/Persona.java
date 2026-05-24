package servicio;

public abstract class Persona {
    private String documento;
    private String nombre;

    public Persona(String documento, String nombre) {
        this.documento = documento;
        this.nombre = nombre;
    }

    public Persona() {
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
        return "documento : " + documento
                + "\nNombre : " + nombre;
    }
}
