package servicio;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class Empresa {
    private static final DateTimeFormatter FORMATO_FECHA_HORA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private ArrayList<Bus> listaBuses;
    private ArrayList<Ruta> listaRutas;
    private ArrayList<Salida> listaSalidas;
    private ArrayList<Tiquete> listaTiquetes;
    private ArrayList<String> listaDestinosAnadidos = new ArrayList<>();
    private double dineroEnCaja;
    private double totalVendido;
    private double totalReembolsado;

    public Empresa() {
        this.listaBuses = new ArrayList<>();
        this.listaRutas = new ArrayList<>();
        this.listaSalidas = new ArrayList<>();
        this.listaTiquetes = new ArrayList<>();
        this.dineroEnCaja = 0.0;
        this.totalVendido = 0.0;
        this.totalReembolsado = 0.0;
        cargarDatosIniciales();
    }

    private void cargarDatosIniciales() {
        Ruta r01 = new Ruta("R01", "Bucaramanga", "6", 80000);
        Ruta r02 = new Ruta("R02", "Bogota", "15", 160000);
        Ruta r03 = new Ruta("R03", "Medellin", "15", 180000);
        Ruta r04 = new Ruta("R04", "Cartagena", "18", 220000);
        listaRutas.add(r01);
        listaRutas.add(r02);
        listaRutas.add(r03);
        listaRutas.add(r04);

        Bus b101 = new Bus("KAA-101", Bus.TIPO_NORMAL, Bus.ESTADO_DISPONIBLE);
        Bus b202 = new Bus("KBB-202", Bus.TIPO_EJECUTIVO, Bus.ESTADO_DISPONIBLE);
        Bus b303 = new Bus("KCC-303", Bus.TIPO_NORMAL, Bus.ESTADO_DISPONIBLE);
        Bus b404 = new Bus("KDD-404", Bus.TIPO_EJECUTIVO, Bus.ESTADO_DISPONIBLE);
        Bus b505 = new Bus("KEE-505", Bus.TIPO_NORMAL, Bus.ESTADO_MANTENIMIENTO);
        Bus b606 = new Bus("KFF-606", Bus.TIPO_NORMAL, Bus.ESTADO_DISPONIBLE, 30);
        listaBuses.add(b101);
        listaBuses.add(b202);
        listaBuses.add(b303);
        listaBuses.add(b404);
        listaBuses.add(b505);
        listaBuses.add(b606);

        listaSalidas.add(new Salida("S001", r01, b101, "15/03/2026", "06:00"));
        listaSalidas.add(new Salida("S002", r01, b202, "15/03/2026", "14:00"));
        listaSalidas.add(new Salida("S003", r02, b303, "16/03/2026", "07:00"));
        listaSalidas.add(new Salida("S004", r02, b404, "16/03/2026", "20:00"));
        listaSalidas.add(new Salida("S005", r03, b606, "17/03/2026", "05:30"));
        listaSalidas.add(new Salida("S006", r03, b101, "17/03/2026", "18:00"));
        listaSalidas.add(new Salida("S007", r04, b303, "18/03/2026", "06:30"));
        listaSalidas.add(new Salida("S008", r04, b202, "18/03/2026", "19:30"));

        listaDestinosAnadidos.add("Bucaramanga");
        listaDestinosAnadidos.add("Bogota");
        listaDestinosAnadidos.add("Medellin");
        listaDestinosAnadidos.add("Cartagena");
    }

    public String registrarBus(String placa, String tipoServicio) {
        if (placa == null || placa.isBlank()) {
            return "ERROR: La placa no puede estar vacia.";
        }
        if (tipoServicio == null || tipoServicio.isBlank()) {
            return "ERROR: El tipo de servicio no puede estar vacio.";
        }
        if (buscarBusPlaca(placa) != null) {
            return "ERROR: No se pudo crear el bus: la placa ya existe.";
        }

        Bus nuevoBus = new Bus(placa, tipoServicio);
        listaBuses.add(nuevoBus);
        return "EXITO: Bus con placa " + nuevoBus.getPlaca() + " creado correctamente.";
    }

    public String actualizarEstadoBus(String placa, String nuevoEstado) {
        Bus busAEditar = buscarBusPlaca(placa);
        if (busAEditar == null) {
            return "ERROR: No se encontro el bus seleccionado.";
        }
        if (nuevoEstado == null || nuevoEstado.isBlank()) {
            return "ERROR: El nuevo estado del bus no puede estar vacio.";
        }

        String estadoNormalizado = nuevoEstado.trim().toUpperCase();
        busAEditar.setEstado(estadoNormalizado);
        StringBuilder reporte = new StringBuilder("EXITO: El bus con placa ")
                .append(busAEditar.getPlaca())
                .append(" cambio su estado a: ")
                .append(estadoNormalizado)
                .append("\n");

        if (estadoNormalizado.equalsIgnoreCase(Bus.ESTADO_MANTENIMIENTO)) {
            for (Salida salidaAfectada : listaSalidas) {
                if (!salidaAfectada.getBus().getPlaca().equalsIgnoreCase(busAEditar.getPlaca()) || salidaInactiva(salidaAfectada)) {
                    continue;
                }

                reporte.append("INFO: La salida ")
                        .append(salidaAfectada.getIdSalida())
                        .append(" se vio afectada. Buscando reemplazo...\n");

                Bus busReemplazo = buscarBusReemplazoParaSalida(salidaAfectada, busAEditar.getPlaca());
                if (busReemplazo != null) {
                    salidaAfectada.setBus(busReemplazo);
                    reporte.append("EXITO: Salida ")
                            .append(salidaAfectada.getIdSalida())
                            .append(" reasignada al bus ")
                            .append(busReemplazo.getPlaca())
                            .append(".\n");
                } else {
                    reporte.append("ERROR: No hay buses disponibles para la salida ")
                            .append(salidaAfectada.getIdSalida())
                            .append(". Requiere atencion manual.\n");
                }
            }
        }

        return reporte.toString().trim();
    }

    public String obtenerEstadoBus(String placa) {
        Bus bus = buscarBusPlaca(placa);
        if (bus == null) {
            return "";
        }
        return bus.getEstado();
    }

    public String registrarRuta(String destino, String tiempoEst, double tarifa) {
        if (destino == null || destino.isBlank()) {
            return "ERROR: El destino no puede estar vacio.";
        }
        if (tiempoEst == null || tiempoEst.isBlank()) {
            return "ERROR: El tiempo estimado no puede estar vacio.";
        }
        if (tarifa < 0) {
            return "ERROR: La tarifa no puede ser un valor negativo.";
        }

        int duracion = obtenerDuracionHoras(tiempoEst);
        if (duracion <= 0) {
            return "ERROR: El tiempo estimado debe ser un numero de horas mayor que cero.";
        }

        for (Ruta r : listaRutas) {
            if (r.getDestino().equalsIgnoreCase(destino.trim())) {
                return "ERROR: Ya existe una ruta asignada hacia " + destino.trim() + ".";
            }
        }

        String cod = generarCodigoRuta();
        Ruta nueva = new Ruta(cod, destino.trim(), String.valueOf(duracion), tarifa);
        listaRutas.add(nueva);
        return "EXITO: Ruta con codigo " + cod + " hacia " + destino.trim() + " creada correctamente.";
    }

    public String actualizarTarifaRuta(String codigoRuta, double nTarifa) {
        if (nTarifa < 0) {
            return "ERROR: La tarifa no puede ser un valor negativo.";
        }

        Ruta rutaAEditar = buscarRutaCodigo(codigoRuta);
        if (rutaAEditar == null) {
            return "ERROR: No se encontro la ruta seleccionada.";
        }

        rutaAEditar.setTarifaBase(nTarifa);
        return "EXITO: Tarifa de la ruta " + codigoRuta + " actualizada a $" + nTarifa;
    }

    public String registrarSalida(String codigoRuta, String placaBus, String fecha, String hora) {
        Ruta rutaEncontrada = buscarRutaCodigo(codigoRuta);
        if (rutaEncontrada == null) return "ERROR: Ruta no encontrada.";

        Bus busEncontrado = buscarBusPlaca(placaBus);
        if (busEncontrado == null) return "ERROR: Bus no encontrado.";

        if (busEncontrado.getEstado().equalsIgnoreCase(Bus.ESTADO_MANTENIMIENTO)) {
            return "ERROR: No se puede crear la salida: el bus " + placaBus + " esta en mantenimiento.";
        }

        LocalDateTime nuevaHoraSalida;
        try {
            nuevaHoraSalida = crearFechaHora(fecha, hora);
        } catch (DateTimeParseException e) {
            return "ERROR: La fecha u hora de la salida no tiene un formato valido.";
        }

        int nuevaDuracion = obtenerDuracionHoras(rutaEncontrada);
        if (nuevaDuracion <= 0) return "ERROR: La ruta seleccionada tiene un tiempo estimado invalido.";

        // TIEMPO NORMAL: Solo sumamos las horas que dura el viaje
        LocalDateTime nuevaHoraLlegada = nuevaHoraSalida.plusHours(nuevaDuracion);

        for (Salida sExistente : listaSalidas) {
            if (salidaInactiva(sExistente)) continue;

            LocalDateTime extHoraSalida = crearFechaHora(sExistente);
            // TIEMPO NORMAL DEL VIAJE EXISTENTE
            LocalDateTime extHoraLlegada = extHoraSalida.plusHours(obtenerDuracionHoras(sExistente.getRuta()));

            if (sExistente.getRuta().getCodigoRuta().equalsIgnoreCase(codigoRuta) && extHoraSalida.equals(nuevaHoraSalida)) {
                return "ERROR: Ya existe una salida programada para la ruta " + codigoRuta + " el " + fecha + " a las " + hora + ".";
            }

            // VALIDACIÓN CLAVE: Si es el mismo bus, verificamos que los horarios no se pisen
            if (sExistente.getBus().getPlaca().equalsIgnoreCase(placaBus)
                    && horariosSeCruzan(nuevaHoraSalida, nuevaHoraLlegada, extHoraSalida, extHoraLlegada)) {
                
                return "ERROR: El bus " + placaBus + " no puede ser asignado. Estara ocupado en el viaje " 
                        + sExistente.getIdSalida() + " hasta el " + extHoraLlegada.format(FORMATO_FECHA_HORA) + ".";
            }
        }

        String idSalida = generarIdSalida();
        Salida nuevaSalida = new Salida(idSalida, rutaEncontrada, busEncontrado, fecha, hora);
        listaSalidas.add(nuevaSalida);
        return "EXITO: Salida con id " + idSalida + " creada correctamente.";
    }

    public String registrarNuevoDestino(String destino) {
        if (destino == null || destino.isBlank()) {
            return "ERROR: El destino no puede estar vacio.";
        }

        String destinoLimpio = destino.trim();
        for (String d : listaDestinosAnadidos) {
            if (d.equalsIgnoreCase(destinoLimpio)) {
                return "ERROR: El destino '" + destinoLimpio + "' ya se encuentra registrado.";
            }
        }

        listaDestinosAnadidos.add(destinoLimpio);
        return "EXITO: Destino '" + destinoLimpio + "' agregado correctamente.";
    }

    public String venderTiquete(String idSalida, String documento, String nombre, int silla) {
        Salida salida = buscarSalidaId(idSalida);
        if (salida == null) {
            return "ERROR: Salida no encontrada.";
        }
        String validacion = validarDatosVenta(salida, documento, nombre, silla);
        if (validacion != null) {
            return validacion;
        }

        Pasajero pasajero = new Pasajero(documento.trim(), nombre.trim());
        double valor = salida.precioFinal();
        Tiquete tiquete = crearTiquete(pasajero, salida, silla, valor);
        registrarPago(valor);
        return construirReciboVenta(tiquete);
    }

    public String venderIdaVuelta(String idSalidaIda, int sillaIda, String idSalidaRegreso, int sillaRegreso, String documento, String nombre) {
        Salida salidaIda = buscarSalidaId(idSalidaIda);
        Salida salidaRegreso = buscarSalidaId(idSalidaRegreso);

        if (salidaIda == null || salidaRegreso == null) {
            return "ERROR: Debe seleccionar una salida de ida y una salida de regreso validas.";
        }
        if (salidaIda == salidaRegreso) {
            return "ERROR: La salida de regreso debe ser diferente a la salida de ida.";
        }
        if (!salidaIda.getRuta().getCodigoRuta().equalsIgnoreCase(salidaRegreso.getRuta().getCodigoRuta())) {
            return "ERROR: La venta ida y vuelta debe usar salidas de la misma ruta.";
        }

        String validacionIda = validarDatosVenta(salidaIda, documento, nombre, sillaIda);
        if (validacionIda != null) {
            return validacionIda;
        }
        String validacionRegreso = validarDatosVenta(salidaRegreso, documento, nombre, sillaRegreso);
        if (validacionRegreso != null) {
            return validacionRegreso;
        }

        Pasajero pasajero = new Pasajero(documento.trim(), nombre.trim());
        double valorIdaBase = salidaIda.precioFinal();
        double valorRegresoBase = salidaRegreso.precioFinal();
        double subtotal = valorIdaBase + valorRegresoBase;
        double descuento = subtotal * 0.10;
        double valorIda = valorIdaBase * 0.90;
        double valorRegreso = valorRegresoBase * 0.90;
        double total = valorIda + valorRegreso;

        Tiquete tiqueteIda = crearTiquete(pasajero, salidaIda, sillaIda, valorIda);
        Tiquete tiqueteRegreso = crearTiquete(pasajero, salidaRegreso, sillaRegreso, valorRegreso);
        registrarPago(total);
        return construirReciboIdaVuelta(tiqueteIda, tiqueteRegreso, subtotal, descuento, total);
    }

    public ArrayList<Salida> getSalidasProgramadas() {
        ArrayList<Salida> programadas = new ArrayList<>();
        for (Salida s : listaSalidas) {
            if (s.getEstado().equalsIgnoreCase(Salida.ESTADO_PROGRAMADA)) {
                programadas.add(s);
            }
        }
        return programadas;
    }

    public Salida obtenerSalidaPorId(String idSalida) {
        return buscarSalidaId(idSalida);
    }

    public String verificarEstadoSalidasEnTiempoReal() {
        LocalDateTime ahora = LocalDateTime.now();
        StringBuilder reporte = new StringBuilder("INFO: Ejecutando control de trafico automatico (")
                .append(ahora.format(FORMATO_FECHA_HORA)).append(")...\n");

        for (Bus b : listaBuses) {
            if (!b.getEstado().equalsIgnoreCase(Bus.ESTADO_MANTENIMIENTO)) {
                b.setEstado(Bus.ESTADO_DISPONIBLE);
            }
        }

        for (Salida s : listaSalidas) {
            try {
                if (s.getEstado().equalsIgnoreCase(Salida.ESTADO_CANCELADA)) {
                    continue;
                }

                LocalDateTime inicio = crearFechaHora(s);
                int horasDeViaje = obtenerDuracionHoras(s.getRuta());
                if (horasDeViaje <= 0) continue;

                LocalDateTime fin = inicio.plusHours(horasDeViaje);

                // Si ya llegó la hora de salir o estamos en pleno viaje
                if (!ahora.isBefore(inicio) && ahora.isBefore(fin)) {
                    
                    // REGLA DE NEGOCIO: Antes de iniciar el viaje, validar las 5 sillas
                    if (s.getEstado().equalsIgnoreCase(Salida.ESTADO_PROGRAMADA)) {
                        if (s.getCantidadSillasVendidas() < 5) {
                            s.setEstado(Salida.ESTADO_CANCELADA);
                            procesarReembolsosPorCancelacion(s); // Llama al método de abajo
                            reporte.append("AVISO: La salida ").append(s.getIdSalida())
                                   .append(" se CANCELO por no alcanzar el minimo de 5 pasajeros.\n");
                            continue; // Saltamos para que no lo ponga EN_VIAJE
                        }
                    }
                    
                    s.setEstado(Salida.ESTADO_EN_VIAJE);
                    if (!s.getBus().getEstado().equalsIgnoreCase(Bus.ESTADO_MANTENIMIENTO)) {
                        s.getBus().setEstado(Bus.ESTADO_EN_SERVICIO);
                    }
                } else if (!ahora.isBefore(fin)) {
                    if (!s.getEstado().equalsIgnoreCase(Salida.ESTADO_FINALIZADA)) {
                        s.setEstado(Salida.ESTADO_FINALIZADA);
                        reporte.append("INFO: El viaje de la salida ").append(s.getIdSalida()).append(" ha concluido.\n");
                    }
                } else {
                    s.setEstado(Salida.ESTADO_PROGRAMADA);
                }
            } catch (Exception ex) {
                // ... manejo de excepciones
            }
        }
        reporte.append("EXITO: Tablas y estados de trafico actualizados.");
        return reporte.toString();
    }

    // NUEVO MÉTODO AUXILIAR: Para hacer el reembolso automático de la plata
    private void procesarReembolsosPorCancelacion(Salida salidaCancelada) {
        for (Tiquete t : listaTiquetes) {
            // Buscamos los tiquetes de esta salida que estén vigentes
            if (t.getSalida().getIdSalida().equals(salidaCancelada.getIdSalida()) 
                && t.getEstado().equals(Tiquete.ESTADO_VIGENTE)) {
                
                t.setEstado(Tiquete.ESTADO_REEMBOLSADO);
                this.totalReembolsado += t.getValorPagado();
                this.dineroEnCaja -= t.getValorPagado(); // Sacamos la plata de la caja
            }
        }
    }

    private String validarDatosVenta(Salida salida, String documento, String nombre, int silla) {
        if (!salida.getEstado().equalsIgnoreCase(Salida.ESTADO_PROGRAMADA)) {
            return "ERROR: Solo se pueden vender tiquetes para salidas PROGRAMADAS.";
        }
        if (documento == null || documento.isBlank()) {
            return "ERROR: El documento del pasajero no puede estar vacio.";
        }
        if (nombre == null || nombre.isBlank()) {
            return "ERROR: El nombre del pasajero no puede estar vacio.";
        }
        if (!sillaExiste(salida, silla)) {
            return "ERROR: La silla " + silla + " no existe para el bus asignado.";
        }
        if (sillaOcupada(salida, silla)) {
            return "ERROR: La silla " + silla + " ya esta ocupada en la salida " + salida.getIdSalida() + ".";
        }
        return null;
    }

    private Tiquete crearTiquete(Pasajero pasajero, Salida salida, int silla, double valorPagado) {
        salida.gestionOcupacionSilla(silla);
        Tiquete tiquete = new Tiquete(generarCodigoTiquete(), pasajero, salida, silla, valorPagado);
        listaTiquetes.add(tiquete);
        return tiquete;
    }

    private void registrarPago(double valor) {
        totalVendido += valor;
        dineroEnCaja += valor;
    }

    private String generarCodigoTiquete() {
        return String.format("TQ-%05d", listaTiquetes.size() + 1);
    }

    private boolean sillaExiste(Salida salida, int silla) {
        return silla >= 1 && silla <= salida.getSillas().length;
    }

    private boolean sillaOcupada(Salida salida, int silla) {
        if (!sillaExiste(salida, silla)) {
            return true;
        }//?????????????????????????????????????????????????
        return !salida.getSillas()[silla - 1].isLibre();
    }

    private String construirReciboVenta(Tiquete tiquete) {
        Salida salida = tiquete.getSalida();
        Ruta ruta = salida.getRuta();
        Bus bus = salida.getBus();
        Pasajero pasajero = tiquete.getPasajero();

        return "VENTA EXITOSA\n"
                + "Tiquete: " + tiquete.getCodigo() + "\n"
                + "Pasajero: " + pasajero.getDocumento() + " - " + pasajero.getNombre() + "\n"
                + "Salida: " + salida.getIdSalida() + " (" + ruta.getOrigen() + " -> " + ruta.getDestino() + ") "
                + salida.getFecha() + " " + salida.getHora() + "\n"
                + "Bus: " + bus.getPlaca() + " (" + bus.getTipoServicio() + ") Capacidad: " + bus.getCapacidad() + "\n"
                + "Silla: " + String.format("%02d", tiquete.getSilla()) + "\n"
                + "Valor pagado: $" + String.format("%.0f", tiquete.getValorPagado()) + "\n"
                + "Estado tiquete: " + tiquete.getEstado();
    }

    private String construirReciboIdaVuelta(Tiquete tiqueteIda, Tiquete tiqueteRegreso, double subtotal, double descuento, double total) {
        return "VENTA IDA Y VUELTA EXITOSA\n"
                + construirLineaTiquete("Ida", tiqueteIda) + "\n"
                + construirLineaTiquete("Regreso", tiqueteRegreso) + "\n"
                + "Subtotal: $" + String.format("%.0f", subtotal) + "\n"
                + "Descuento 10%: $" + String.format("%.0f", descuento) + "\n"
                + "Total pagado: $" + String.format("%.0f", total) + "\n"
                + "Estado tiquetes: " + Tiquete.ESTADO_VIGENTE;
    }

    private String construirLineaTiquete(String etiqueta, Tiquete tiquete) {
        Salida salida = tiquete.getSalida();
        Ruta ruta = salida.getRuta();
        return etiqueta + ": " + tiquete.getCodigo()
                + " | " + salida.getIdSalida()
                + " (" + ruta.getOrigen() + " -> " + ruta.getDestino() + ") "
                + salida.getFecha() + " " + salida.getHora()
                + " | Silla " + String.format("%02d", tiquete.getSilla())
                + " | Valor $" + String.format("%.0f", tiquete.getValorPagado());
    }

    public String generarCodigoRuta() {
        int mayor = 0;
        for (Ruta r : listaRutas) {
            int numero = Integer.parseInt(r.getCodigoRuta().replace("R", ""));
            if (numero > mayor) {
                mayor = numero;
            }
        }
        return String.format("R%02d", mayor + 1);
    }

    public String generarIdSalida() {
        int mayor = 0;
        for (Salida s : listaSalidas) {
            int numero = Integer.parseInt(s.getIdSalida().replace("S", ""));
            if (numero > mayor) {
                mayor = numero;
            }
        }
        return String.format("S%03d", mayor + 1);
    }

    private Bus buscarBusPlaca(String placa) {
        if (placa == null || placa.isBlank()) {
            return null;
        }
        for (Bus b : listaBuses) {
            if (b.getPlaca().equalsIgnoreCase(placa.trim())) {
                return b;
            }
        }
        return null;
    }

    private Ruta buscarRutaCodigo(String codigoRuta) {
        if (codigoRuta == null || codigoRuta.isBlank()) {
            return null;
        }
        for (Ruta r : listaRutas) {
            if (r.getCodigoRuta().equalsIgnoreCase(codigoRuta.trim())) {
                return r;
            }
        }
        return null;
    }

    private Salida buscarSalidaId(String idSalida) {
        if (idSalida == null || idSalida.isBlank()) {
            return null;
        }
        for (Salida s : listaSalidas) {
            if (s.getIdSalida().equalsIgnoreCase(idSalida.trim())) {
                return s;
            }
        }
        return null;
    }

    private Bus buscarBusReemplazoParaSalida(Salida salidaObjetivo, String placaRetirada) {
        for (Bus b : listaBuses) {
            if (busPuedeCubrirSalida(b, salidaObjetivo, placaRetirada)) {
                return b;
            }
        }
        return null;
    }

    private boolean busPuedeCubrirSalida(Bus bus, Salida salidaObjetivo, String placaRetirada) {
        if (bus.getPlaca().equalsIgnoreCase(placaRetirada)
                || bus.getEstado().equalsIgnoreCase(Bus.ESTADO_MANTENIMIENTO)) {
            return false;
        }

        LocalDateTime inicioObjetivo = crearFechaHora(salidaObjetivo);
        // TIEMPO NORMAL
        LocalDateTime finObjetivo = inicioObjetivo.plusHours(obtenerDuracionHoras(salidaObjetivo.getRuta()));

        for (Salida s : listaSalidas) {
            if (s == salidaObjetivo || salidaInactiva(s) || !s.getBus().getPlaca().equalsIgnoreCase(bus.getPlaca())) {
                continue;
            }

            LocalDateTime inicioExistente = crearFechaHora(s);
            // TIEMPO NORMAL
            LocalDateTime finExistente = inicioExistente.plusHours(obtenerDuracionHoras(s.getRuta()));
            
            // Si el bus candidato ya tiene un viaje que se cruza con el viaje que intentamos cubrir, lo descartamos
            if (horariosSeCruzan(inicioObjetivo, finObjetivo, inicioExistente, finExistente)) {
                return false;
            }
        }

        return true;
    }

    private boolean salidaInactiva(Salida salida) {
        return salida.getEstado().equalsIgnoreCase(Salida.ESTADO_CANCELADA)
                || salida.getEstado().equalsIgnoreCase(Salida.ESTADO_FINALIZADA);
    }

    private LocalDateTime crearFechaHora(Salida salida) {
        return crearFechaHora(salida.getFecha(), salida.getHora());
    }

    private LocalDateTime crearFechaHora(String fecha, String hora) {
        return LocalDateTime.parse(fecha.trim() + " " + hora.trim(), FORMATO_FECHA_HORA);
    }

    private int obtenerDuracionHoras(Ruta ruta) {
        return obtenerDuracionHoras(ruta.getTiempoEst());
    }

    private int obtenerDuracionHoras(String tiempoEst) {
        try {
            return Integer.parseInt(tiempoEst.trim());
        } catch (Exception e) {
            return -1;
        }
    }

    private boolean horariosSeCruzan(LocalDateTime inicioA, LocalDateTime finA, LocalDateTime inicioB, LocalDateTime finB) {
        return inicioA.isBefore(finB) && finA.isAfter(inicioB);
    }

    public ArrayList<Ruta> getListaRutas() {
        return listaRutas;
    }

    public ArrayList<Bus> getListaBuses() {
        return listaBuses;
    }

    public ArrayList<Salida> getListaSalidas() {
        return listaSalidas;
    }

    public ArrayList<Tiquete> getListaTiquetes() {
        return listaTiquetes;
    }

    public ArrayList<String> getListaDestinos() {
        return listaDestinosAnadidos;
    }

    public double getDineroEnCaja() {
        return dineroEnCaja;
    }

    public double getTotalVendido() {
        return totalVendido;
    }

    public double getTotalReembolsado() {
        return totalReembolsado;
    }

    public void setDineroEnCaja(double dineroEnCaja) {
        this.dineroEnCaja = dineroEnCaja;
    }

    public String registrarMetodo() {
        return "Venta";
    }
    
    // 1. Matriz para la Tabla de Rutas
    public String[][] obtenerMatrizRutas() {
        String[][] matriz = new String[listaRutas.size()][5];
        for (int i = 0; i < listaRutas.size(); i++) {
            Ruta r = listaRutas.get(i);
            matriz[i][0] = r.getCodigoRuta();
            matriz[i][1] = r.getOrigen();
            matriz[i][2] = r.getDestino();
            matriz[i][3] = r.getTiempoEst();
            matriz[i][4] = String.valueOf(r.getTarifaBase());
        }
        return matriz;
    }

    // 2. Matriz para la Tabla de Buses
    public String[][] obtenerMatrizBuses() {
        String[][] matriz = new String[listaBuses.size()][4];
        for (int i = 0; i < listaBuses.size(); i++) {
            Bus b = listaBuses.get(i);
            matriz[i][0] = b.getTipoServicio();
            matriz[i][1] = b.getPlaca();
            matriz[i][2] = String.valueOf(b.getCapacidad());
            matriz[i][3] = b.getEstado();
        }
        return matriz;
    }

    // 3. Matriz para la Tabla de Salidas
    public String[][] obtenerMatrizSalidas() {
        String[][] matriz = new String[listaSalidas.size()][8];
        for (int i = 0; i < listaSalidas.size(); i++) {
            Salida s = listaSalidas.get(i);
            matriz[i][0] = s.getIdSalida();
            matriz[i][1] = s.getRuta().getCodigoRuta();
            matriz[i][2] = s.getBus().getPlaca();
            matriz[i][3] = s.getHora();
            matriz[i][4] = s.getFecha();
            matriz[i][5] = s.getEstado();
            matriz[i][6] = String.valueOf(s.precioFinal());
            // Tu excelente idea de las sillas vendidas vs totales
            matriz[i][7] = s.getCantidadSillasVendidas() + " / " + s.getSillas().length;
        }
        return matriz;
    }

    // 4. Arreglos simples para llenar los JComboBox
    public String[] obtenerPlacasBuses() {
        String[] placas = new String[listaBuses.size()];
        for (int i = 0; i < listaBuses.size(); i++) {
            placas[i] = listaBuses.get(i).getPlaca();
        }
        return placas;
    }

    public String[] obtenerCodigosRutas() {
        String[] codigos = new String[listaRutas.size()];
        for (int i = 0; i < listaRutas.size(); i++) {
            codigos[i] = listaRutas.get(i).getCodigoRuta();
        }
        return codigos;
    }
    
    public String[] obtenerSalidasProgramadasFormateadas() {
        ArrayList<Salida> programadas = getSalidasProgramadas();
        String[] formateadas = new String[programadas.size()];
        
        for (int i = 0; i < programadas.size(); i++) {
            Salida s = programadas.get(i);
            // Formato exacto que tenías en tu GUI
            formateadas[i] = s.getIdSalida() + " - " + s.getRuta().getCodigoRuta() + " "
                           + s.getRuta().getOrigen() + " -> " + s.getRuta().getDestino()
                           + " | " + s.getFecha() + " " + s.getHora()
                           + " | Bus " + s.getBus().getPlaca();
        }
        return formateadas;
    }

    // 2. Devuelve un arreglo booleano donde 'true' es ocupada y 'false' es libre
    public boolean[] obtenerEstadoSillasSalida(String idSalida) {
        Salida salida = buscarSalidaId(idSalida);
        if (salida == null) return new boolean[0]; // Retorna vacío si no hay salida
        
        Silla[] sillas = salida.getSillas();
        boolean[] estados = new boolean[sillas.length];
        
        for (int i = 0; i < sillas.length; i++) {
            // isLibre() es el método que creamos en la clase Silla. Si no es libre, es true (ocupada)
            estados[i] = !sillas[i].isLibre(); 
        }
        return estados;
    }
    @Override
    public String toString() {
        return "=== REPORTE DE LA EMPRESA ==="
                + "\nTotal Rutas: " + listaRutas.size()
                + "\nTotal Buses: " + listaBuses.size()
                + "\nTotal Salidas Programadas: " + listaSalidas.size()
                + "\nDinero en Caja: $" + dineroEnCaja;
    }
}
