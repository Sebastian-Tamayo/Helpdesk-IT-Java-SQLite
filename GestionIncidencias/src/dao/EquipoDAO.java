package dao;

import conexion.ConexionDB;
import modelo.Equipo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EquipoDAO {

    // Método para guardar (Create) un nuevo equipo
    public boolean registrar(Equipo equipo) {
        String sql = "INSERT INTO equipos (nombre, tipo, estado) VALUES (?, ?, ?)";
        try {
            Connection conn = ConexionDB.conectar();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, equipo.getNombre());
            pstmt.setString(2, equipo.getTipo());
            pstmt.setString(3, equipo.getEstado());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al registrar equipo: " + e.getMessage());
            return false;
        }
    }

    // Método para leer (Read) todos los equipos de la base de datos
    public List<Equipo> listar() {
        List<Equipo> lista = new ArrayList<>();
        String sql = "SELECT * FROM equipos";
        try {
            Connection conn = ConexionDB.conectar();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            
            // Recorremos los resultados y los convertimos en objetos Equipo
            while (rs.next()) {
                Equipo e = new Equipo();
                e.setId(rs.getInt("id"));
                e.setNombre(rs.getString("nombre"));
                e.setTipo(rs.getString("tipo"));
                e.setEstado(rs.getString("estado"));
                lista.add(e);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar equipos: " + e.getMessage());
        }
        return lista;
    }

    // Método para borrar (Delete) un equipo
    public boolean eliminar(int id) {
        String sql = "DELETE FROM equipos WHERE id = ?";
        try {
            Connection conn = ConexionDB.conectar();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar equipo: " + e.getMessage());
            return false;
        }
    }
}