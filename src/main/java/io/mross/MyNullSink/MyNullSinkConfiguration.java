package io.mross.MyNullSink;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.NullChannel;
import org.springframework.messaging.Message;

/**
 * Created by mross on 10/3/16.
 */


@EnableBinding(Sink.class)
public class MyNullSinkConfiguration {

    @Autowired
    NullChannel nullChannel;

    @ServiceActivator(inputChannel = Sink.INPUT)
    public void nullSinkHandler(Message<?> payload) {
        nullChannel.send(payload);

    }

}
