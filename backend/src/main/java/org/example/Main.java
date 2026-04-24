package org.example;

import com.sun.net.httpserver.HttpServer;
import org.example.config.DatabaseConfig;
import org.example.router.RouterHandler;
import org.example.service.UsuarioService;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {
  public static void main(String[] args) throws Exception {
    try {
      HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
      server.createContext("/", new RouterHandler());
      server.setExecutor(null);
      server.start();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    System.out.println("Servidor iniciado en: http://localhost:8080/");
    System.out.println("Documentación: http://localhost:8080/api/docs");
        UsuarioService usuario = new UsuarioService();



    }
    }

