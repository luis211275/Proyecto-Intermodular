package org.example.config;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionDataBase {

  private static final String URL = "jdbc:postgresql://localhost:5432/intermodular";
  private static final String USERNAME = "postgres";
  private static final String PASSWORD = "postgres";


  // Método donde se realiza la conexión a la Base de Datos
  public static Connection obtenerConexion() throws Exception {
    return DriverManager.getConnection(URL, USERNAME, PASSWORD);
  }
}
