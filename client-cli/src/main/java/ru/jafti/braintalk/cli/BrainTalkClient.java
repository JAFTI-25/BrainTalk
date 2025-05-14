package ru.jafti.braintalk.cli;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import ru.jafti.braintalk.cli.command.Commands;
import ru.jafti.braintalk.cli.out.UserOutput;

import java.util.Scanner;

@ComponentScan(basePackages = {
        "ru.jafti.braintalk.cli",
        "ru.jafti.braintalk.client.lib",
        "ru.jafti.braintalk.client.common"
})
@SpringBootApplication
public class BrainTalkClient {

    @Bean
    public BrainTalkClient boot(Commands commands, UserOutput userOutput) {
        userOutput.print("Cli app started");
        var app = new BrainTalkClient();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            commands.execute(scanner.nextLine());
        }

        return app;
    }

    public static void main(String[] args) {
        SpringApplication.run(BrainTalkClient.class, args);
    }
}
