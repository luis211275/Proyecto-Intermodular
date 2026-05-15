package org.example.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Configuración de la base de datos.
 */
public class DatabaseConfig {
  private static final String URL = "jdbc:postgresql://localhost:5432/intermodular";
  private static final String USERNAME = "postgres";
  private static final String PASSWORD = "postgres";

  /**
   * Obtiene la conexión a la base de datos.
   *
   * @return la conexión a la base de datos.
   * @throws SQLException si ocurre un error al conectar.
   */
  public static Connection getConnection() throws SQLException {
    try {
      Class.forName("org.postgresql.Driver");
    } catch (ClassNotFoundException e) {
      throw new SQLException("No se encontró el driver de PostgreSQL", e);
    }
    return DriverManager.getConnection(URL, USERNAME, PASSWORD);
  }
}