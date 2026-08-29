package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionDB {
    // Ruta donde se creará el archivo de la base de datos automáticamente
    private static final String URL = "jdbc:sqlite:incidencias.db";
    private static Connection conexion = null;

    public static Connection conectar() {
        try {
            if (conexion == null || conexion.isClosed()) {
                conexion = DriverManager.getConnection(URL);
                System.out.println("Conexion a SQLite establecida con exito.");
            }
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }
        return conexion;
    }

    public static void desconectar() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("Conexion a la base de datos cerrada.");
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
    public static void crearTablas() {
        Connection conn = conectar();
        if (conn != null) {
            try {
                Statement stmt = conn.createStatement();
                
                // 1. Tabla Usuarios
                String sqlUsuarios = "CREATE TABLE IF NOT EXISTS usuarios ("
                        + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + "nombre TEXT NOT NULL, "
                        + "email TEXT UNIQUE NOT NULL, "
                        + "password TEXT NOT NULL"
                        + ");";
                stmt.execute(sqlUsuarios);

                // 2. Tabla Equipos
                String sqlEquipos = "CREATE TABLE IF NOT EXISTS equipos ("
                        + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + "nombre TEXT NOT NULL, "
                        + "tipo TEXT NOT NULL, "
                        + "estado TEXT NOT NULL"
                        + ");";
                stmt.execute(sqlEquipos);

                // 3. Tabla Incidencias (Relacionada con usuarios y equipos)
                String sqlIncidencias = "CREATE TABLE IF NOT EXISTS incidencias ("
                        + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + "descripcion TEXT NOT NULL, "
                        + "estado TEXT NOT NULL, "
                        + "id_usuario INTEGER, "
                        + "id_equipo INTEGER, "
                        + "FOREIGN KEY(id_usuario) REFERENCES usuarios(id), "
                        + "FOREIGN KEY(id_equipo) REFERENCES equipos(id)"
                        + ");";
                stmt.execute(sqlIncidencias);

                System.out.println("Tablas creadas o verificadas correctamente.");
                stmt.close();
            } catch (SQLException e) {
                System.out.println("Error al crear las tablas: " + e.getMessage());
            }
        }
    }
}