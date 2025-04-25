package ru.jafti.braintalk.server.snowflake;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import xyz.downgoon.snowflake.Snowflake;

@Component
public class MessageIdGeneratorImpl implements MessageIdGenerator {

    private final Snowflake snowflake;

    public MessageIdGeneratorImpl(
            @Value("${snowflake.datacenterId:1}") Long datacenterId,
            @Value("${snowflake.workerId:2}") Long workerId
    ) {
        this.snowflake = new Snowflake(datacenterId, workerId);
    }

    public long generate() {
        return snowflake.nextId();
    }

}
