package intermodular;

import com.sun.net.httpserver.HttpServer;
import intermodular.config.ConnectionDataBase;
import intermodular.router.RouterHandler;

import java.net.InetSocketAddress;

public class Main {
  public static void main(String[] args) throws Exception {
    try {
      HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
      server.createContext("/", new RouterHandler());
      server.setExecutor(null);
      server.start();

      System.out.println("Servidor iniciado en: http://localhost:8080/");
      System.out.println("Documentación: http://localhost:8080/api/docs");
    } catch (java.net.BindException e) {
      System.err.println("Error: El puerto 8080 ya está en uso.");
      System.exit(1);
    } catch (Exception e) {
      System.err.println("Error al iniciar el servidor: " + e.getMessage());
      System.exit(1);
    }

    try {
      ConnectionDataBase.obtenerConexion();
      System.out.println("Conexion correcta a PostgreSQL.");
    } catch (Exception e) {
      System.out.println("Error de conexion: " + e.getMessage());
    }
  }
}
