package modelo;

public class Incidencia {
    private int id;
    private String descripcion;
    private String estado;
    private int idUsuario;
    private int idEquipo;

    public Incidencia() {}

    public Incidencia(int id, String descripcion, String estado, int idUsuario, int idEquipo) {
        this.id = id;
        this.descripcion = descripcion;
        this.estado = estado;
        this.idUsuario = idUsuario;
        this.idEquipo = idEquipo;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
    public int getIdEquipo() { return idEquipo; }
    public void setIdEquipo(int idEquipo) { this.idEquipo = idEquipo; }
}