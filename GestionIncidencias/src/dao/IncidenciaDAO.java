package dao;

import conexion.ConexionDB;
import modelo.Incidencia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IncidenciaDAO {

    // Método para guardar una nueva incidencia
    public boolean registrar(Incidencia inc) {
        String sql = "INSERT INTO incidencias (descripcion, estado, id_usuario, id_equipo) VALUES (?, ?, ?, ?)";
        try {
            Connection conn = ConexionDB.conectar();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, inc.getDescripcion());
            pstmt.setString(2, inc.getEstado());
            pstmt.setInt(3, inc.getIdUsuario());
            pstmt.setInt(4, inc.getIdEquipo());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al registrar incidencia: " + e.getMessage());
            return false;
        }
    }

    // Método para leer todas las incidencias
    public List<Incidencia> listar() {
        List<Incidencia> lista = new ArrayList<>();
        String sql = "SELECT * FROM incidencias";
        try {
            Connection conn = ConexionDB.conectar();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Incidencia inc = new Incidencia();
                inc.setId(rs.getInt("id"));
                inc.setDescripcion(rs.getString("descripcion"));
                inc.setEstado(rs.getString("estado"));
                inc.setIdUsuario(rs.getInt("id_usuario"));
                inc.setIdEquipo(rs.getInt("id_equipo"));
                lista.add(inc);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar incidencias: " + e.getMessage());
        }
        return lista;
    }

    // Método para cambiar el estado de una incidencia (Ej: de "Pendiente" a "Resuelta")
    public boolean cambiarEstado(int id, String nuevoEstado) {
        String sql = "UPDATE incidencias SET estado = ? WHERE id = ?";
        try {
            Connection conn = ConexionDB.conectar();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nuevoEstado);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al actualizar estado: " + e.getMessage());
            return false;
        }
    }
}