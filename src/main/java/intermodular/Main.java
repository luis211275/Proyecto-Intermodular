package intermodular;

import com.sun.net.httpserver.HttpServer;
import intermodular.config.ConnectionDataBase;
import intermodular.router.RouterHandler;

import java.net.InetSocketAddress;

public class Main {
  public static void main(String[] args) throws Exception {
    HttpServer server = HttpServer.create(new InetSocketAddress(7050), 0);
    server.createContext("/", new RouterHandler());
    server.setExecutor(null);
    server.start();

    System.out.println("Servidor iniciado en: http://localhost:7050/");
    System.out.println("Documentacion: http://localhost:7050/docs");

    try {
      ConnectionDataBase.getConnection();
      System.out.println("Conexion correcta a PostgreSQL.");
    } catch (Exception e) {
      System.out.println("Error de conexion: " + e.getMessage());
    }
  }
}