package org.example.main;

import com.sun.net.httpserver.HttpServer;
import org.example.config.DatabaseConfig;
import org.example.router.RouterHandler;
import org.example.service.UsuarioService;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {
    static void main() throws IOException {
        try {
            DatabaseConfig.getConnection();
            System.out.println("Conexión correcta a PostgreSQL.");
            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
// cada vez que arranca y se pone (http://localhost:8080)
            server.createContext("/", new RouterHandler());
            server.setExecutor(null);
            server.start();
            System.out.println("http://localhost:8080");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        UsuarioService usuario = new UsuarioService();



    }
    }

