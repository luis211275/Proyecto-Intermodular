package org.example;

import com.sun.net.httpserver.HttpServer;
import org.example.router.RouterHandler;

import java.net.InetSocketAddress;

/**
 * Clase principal de la aplicación.
 */
public class Main {
  /**
   * Punto de entrada de la aplicación.
   *
   * @param args los argumentos de entrada.
   * @throws Exception si ocurre un error al iniciar el servidor.
   */
  public static void main(String[] args) throws Exception {
    try {
      HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
      server.createContext("/", new RouterHandler());
      server.setExecutor(null);
      server.start();

    } catch (Exception e) {
      System.out.println("Error: " + e.getMessage());
    }
    System.out.println("\n==Bienvenidos a BSCars - Compra y venta de Coches==");
    System.out.println("Servidor iniciado en: http://localhost:8080/home.html");
    System.out.println("Documentación: http://localhost:8080/api/docs");
  }
}