package org.example;

import com.sun.net.httpserver.HttpServer;
import config.ConnectionBBDD;
import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(7050), 0);
        server.createContext("/", new router.RouterHandler());
        server.setExecutor(null);
        server.start();

        System.out.println("Servidor iniciado en: http://localhost:8080/");
        System.out.println("Documentacion: http://localhost:8080/docs");

        try {
            ConnectionBBDD.getConnection();
            System.out.println("Conexion correcta a PostgreSQL.");
        } catch (Exception e) {
            System.out.println("Error de conexion: " + e.getMessage());
        }
    }
}