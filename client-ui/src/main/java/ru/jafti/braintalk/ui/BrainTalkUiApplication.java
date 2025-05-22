package ru.jafti.braintalk.ui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;

@ComponentScan(basePackages = {
        "ru.jafti.braintalk.client.lib",
        "ru.jafti.braintalk.client.common",
        "ru.jafti.braintalk.ui.javafx"
})
@SpringBootApplication
public class BrainTalkUiApplication extends Application {

    private static final Logger log = LoggerFactory.getLogger(BrainTalkUiApplication.class);
    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init() {
        applicationContext = new SpringApplicationBuilder(BrainTalkUiApplication.class).run();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(loadFXML(), 640, 480);
        stage.setTitle("BrainTalk");
        stage.setScene(scene);
        stage.show();
        log.info("App show");
        applicationContext.publishEvent(new StageReadyEvent(stage));
    }

    @Override
    public void stop() {
        applicationContext.close();
        Platform.exit();
    }

    private Parent loadFXML() {
        FXMLLoader loader = new FXMLLoader(BrainTalkUiApplication.class.getResource("/fxml/chat-view.fxml"));
        try {
            loader.setControllerFactory(applicationContext::getBean);
            return loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static class StageReadyEvent extends ApplicationEvent {
        public StageReadyEvent(Stage stage) {
            super(stage);
        }
        public Stage getStage() {
            return (Stage) getSource();
        }
    }
}