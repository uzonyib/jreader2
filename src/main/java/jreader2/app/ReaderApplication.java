package jreader2.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "jreader2")
public class ReaderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReaderApplication.class, args);
    }

}
