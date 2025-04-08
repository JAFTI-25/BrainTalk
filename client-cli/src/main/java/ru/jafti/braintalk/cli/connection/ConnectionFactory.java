package ru.jafti.braintalk.cli.connection;

import java.io.BufferedReader;
import java.io.PrintWriter;

public interface ConnectionFactory {

    Connection connect();

    interface Connection extends AutoCloseable {
        BufferedReader getReader();
        PrintWriter getWriter();
    }

    class Impl {

        public static ConnectionFactory get() {
            // TODO (avtseben): по умолчанию работаем в SSL режиме
            // Нужно вынести в настройку клиента какой тип соединения использовать
            if (true) {
                return new SSLConnectionFactory();
            }
            return new RawSocketConnectionFactory();
        }
    }
}
