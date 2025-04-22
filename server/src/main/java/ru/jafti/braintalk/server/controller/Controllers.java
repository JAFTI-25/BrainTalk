package ru.jafti.braintalk.server.controller;

import org.springframework.stereotype.Component;
import ru.jafti.braintalk.server.RendezvousPoint;
import ru.jafti.braintalk.server.persist.DbInitializer;
import ru.jafti.braintalk.server.service.TalkerProfileService;
import ru.jafti.braintalk.server.persist.DbConnection;
import ru.jafti.braintalk.server.service.TalkerProfileServiceImpl;
import ru.jafti.braintalk.server.connection.Session;
import ru.jafti.braintalk.server.persist.JdbcConnection;

import java.util.ArrayList;
import java.util.List;

@Component
public class Controllers {

    private final List<Controller> controllers = new ArrayList<>();

    public Controllers(
            RegisterController registerController,
            SendController sendController,
            LoginController loginController,
            WhoController whoController,
            AutoLoginController autoLoginController,
            DefaultController defaultController
    ) {

        controllers.add(registerController);
        controllers.add(sendController);
        controllers.add(loginController);
        controllers.add(whoController);
        controllers.add(autoLoginController);

        //Должен быть последним Default
        controllers.add(defaultController);
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
