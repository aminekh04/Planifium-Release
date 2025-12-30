package com.diro.ift2255;

import com.diro.ift2255.config.Routes;
import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create(cfg -> {
            // Javalin 6.x → config HTTP ici
            cfg.http.defaultContentType = "application/json";
        })
                ;

        Routes.configure(app);

        app.start(7000);
        System.out.println("✅ Serveur démarré sur http://localhost:7000");

    }

}
