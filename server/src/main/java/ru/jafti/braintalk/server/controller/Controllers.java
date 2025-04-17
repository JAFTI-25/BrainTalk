package ru.jafti.braintalk.server.controller;

import ru.jafti.braintalk.server.RendezvousPoint;
import ru.jafti.braintalk.server.persist.DbInitializer;
import ru.jafti.braintalk.server.service.TalkerProfileService;
import ru.jafti.braintalk.server.persist.DbConnection;
import ru.jafti.braintalk.server.service.TalkerProfileServiceImpl;
import ru.jafti.braintalk.server.connection.Session;
import ru.jafti.braintalk.server.persist.JdbcConnection;

import java.util.ArrayList;
import java.util.List;

public class Controllers {

    public static final Controllers INSTANCE = new Controllers();

    private final List<Controller> controllers = new ArrayList<>();

    private Controllers() {
        var rendezvousPoint = RendezvousPoint.INSTANCE;

        DbConnection dbConnection = new JdbcConnection();
        dbConnection.connect();
        DbInitializer.initialize(dbConnection);
        var talkerProfileService = new TalkerProfileServiceImpl(dbConnection);

        controllers.add(new RegisterController(talkerProfileService));
        controllers.add(new SendController(rendezvousPoint));
        controllers.add(new LoginController(rendezvousPoint, talkerProfileService));
        controllers.add(new WhoController(rendezvousPoint));
        controllers.add(new AutoLoginController(talkerProfileService, rendezvousPoint));

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
