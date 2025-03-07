package ru.jafti.braintalk.server.controller;

import ru.jafti.braintalk.server.RendezvousPoint;
import ru.jafti.braintalk.server.connection.Session;

import java.util.ArrayList;
import java.util.List;

public class Controllers {

    public static final Controllers INSTANCE = new Controllers();

    private final List<Controller> controllers = new ArrayList<>();

    private Controllers() {
        var rendezvousPoint = RendezvousPoint.INSTANCE;
        controllers.add(new SendController(rendezvousPoint));
        controllers.add(new LoginController(rendezvousPoint));
        controllers.add(new WhoController(rendezvousPoint));

        controllers.add(new DefaultController()); // Должен быть последним
    }

    public void apply(String inputLine, Session out) {
        for (Controller controller : controllers) {
            if (controller.isApplicable(inputLine)) {
                controller.apply(inputLine, out);
                break;
            }
        }
    }
}
