package dao;

import conexion.ConexionDB;
import modelo.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    // Método para insertar un usuario en la base de datos
    public boolean registrar(Usuario u) {
        String sql = "INSERT INTO usuarios (nombre, email, password) VALUES (?, ?, ?)";
        try {
            Connection conn = ConexionDB.conectar();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, u.getNombre());
            pstmt.setString(2, u.getEmail());
            pstmt.setString(3, u.getPassword());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            // Si el correo ya existe, fallará de forma silenciosa y devolverá false
            return false;
        }
    }

    // Método para comprobar si el email y la contraseña son correctos
    public Usuario login(String email, String password) {
        String sql = "SELECT * FROM usuarios WHERE email = ? AND password = ?";
        try {
            Connection conn = ConexionDB.conectar();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            
            // Si hay un resultado, es que las credenciales son correctas
            if (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setNombre(rs.getString("nombre"));
                u.setEmail(rs.getString("email"));
                u.setPassword(rs.getString("password"));
                return u; // Devolvemos el usuario completo
            }
        } catch (SQLException e) {
            System.out.println("Error en login: " + e.getMessage());
        }
        return null; // Si no lo encuentra, devuelve null
    }
}