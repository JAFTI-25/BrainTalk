package ru.jafti.braintalk.server.snowflake;
import org.springframework.stereotype.Component;
import xyz.downgoon.snowflake.Snowflake;

@Component
public class MessageIdGeneratorImpl implements MessageIdGenerator {

    static Snowflake snowflake = new Snowflake(1, 2);


    public long generate(){
        return snowflake.nextId();
    }

}
