package com.mycompany.mavenproject3.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Unico lugar donde viven los datos de conexion a la base de datos.
 * Si cambia el servidor, la contrasena o el nombre de la BD,
 * solo se modifica este archivo y todo lo demas sigue funcionando igual.
 */
public class DatabaseConfig {

    // Datos de conexion
    private static final String HOST     = "sql.freedb.tech";
    private static final String PUERTO   = "3306";
    private static final String NOMBRE   = "freedb_aoAc76qo";
    private static final String USUARIO  = "u_K9qAxD";
    private static final String CLAVE    = "9zb3UiU0H7mJ";

    private static final String URL =
        "jdbc:mysql://" + HOST + ":" + PUERTO + "/" + NOMBRE
        + "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";

    static {
        try {
            // Registrar driver MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Crear tablas automaticamente
            crearTablas();

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(
                "Driver MySQL no encontrado. Verificar mysql-connector-j en pom.xml.",
                e
            );
        }
    }

    public static Connection getConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CLAVE);
    }

    public static String getEstadoConexion() {
        try (Connection con = getConexion()) {
            return "Conexion exitosa al servidor: " + HOST;
        } catch (SQLException e) {
            return "Error al conectar: " + e.getMessage();
        }
    }

    /**
     * Crea las tablas si no existen.
     */
    private static void crearTablas() {

        String sqlProductos =
            "CREATE TABLE IF NOT EXISTS productos ("
            + "id INT PRIMARY KEY AUTO_INCREMENT,"
            + "nombre VARCHAR(100) NOT NULL,"
            + "precio DOUBLE NOT NULL,"
            + "cantidad INT NOT NULL"
            + ")";

        String sqlEstudiantes =
            "CREATE TABLE IF NOT EXISTS estudiantes ("
            + "id INT PRIMARY KEY AUTO_INCREMENT,"
            + "nombre VARCHAR(100) NOT NULL,"
            + "edad INT NOT NULL,"
            + "nota DOUBLE NOT NULL"
            + ")";

        try (
            Connection con = getConexion();
            Statement stmt = con.createStatement()
        ) {

            stmt.execute(sqlProductos);
            stmt.execute(sqlEstudiantes);

            System.out.println("Tablas verificadas correctamente.");

        } catch (SQLException e) {

            System.out.println("Error creando tablas: " + e.getMessage());

        }
    }
}