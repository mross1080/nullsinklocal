package io.mross.MyNullSink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
//@Import(io.mross.MyNullSink.MyNullSinkConfiguration.class)
public class MyNullSinkApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyNullSinkApplication.class, args);
	}
}
