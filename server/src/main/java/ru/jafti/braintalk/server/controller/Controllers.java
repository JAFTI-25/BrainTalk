package ru.jafti.braintalk.server.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.jafti.braintalk.server.connection.Session;

import java.util.ArrayList;
import java.util.List;

@Component
public class Controllers {

    private final List<Controller> controllers = new ArrayList<>();

    public Controllers(
            @Qualifier("RegisterControllerSocketImpl") RegisterController registerController,
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
