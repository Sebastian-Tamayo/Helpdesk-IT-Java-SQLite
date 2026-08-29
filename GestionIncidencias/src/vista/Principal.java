package vista;

import conexion.ConexionDB;
import dao.UsuarioDAO;
import modelo.Usuario;

public class Principal {
    public static void main(String[] args) {
        // 1. Verificamos y creamos las tablas
        ConexionDB.crearTablas();
        
        // 2. Creamos un usuario por defecto para poder hacer las pruebas
        UsuarioDAO dao = new UsuarioDAO();
        Usuario admin = new Usuario(0, "Administrador", "admin@empresa.com", "1234");
        dao.registrar(admin); // Si ya existe en la BD, simplemente no lo duplica
        
        // 3. Abrimos la ventana de Login
        LoginFrame login = new LoginFrame();
        login.setVisible(true);
    }
}