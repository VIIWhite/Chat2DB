package ai.chat2db.server.start;

import ai.chat2db.server.domain.repository.Dbutils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Indexed;

import java.util.concurrent.CompletableFuture;

/**
 * 启动类
 *
 * @author Jiaju Zhuang
 */
@SpringBootApplication
@ComponentScan(value = {"ai.chat2db.server"})
@Indexed
@EnableCaching
@EnableScheduling
@EnableAsync
@Slf4j
public class Application {

    public static void main(String[] args) {
        //ConfigUtils.pid();
        CompletableFuture.runAsync(() -> {
            Dbutils.init();
        });
        SpringApplication.run(Application.class, args);
    }
}
