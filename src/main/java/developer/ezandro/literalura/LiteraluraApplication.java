package developer.ezandro.literalura;

import developer.ezandro.literalura.ui.ConsoleMenu;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {
    private final ConsoleMenu consoleMenu;

    public LiteraluraApplication(ConsoleMenu consoleMenu) {
        this.consoleMenu = consoleMenu;
    }

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();
        dotenv.entries().forEach(e ->
                System.setProperty(e.getKey(), e.getValue())
        );
        SpringApplication.run(LiteraluraApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        this.consoleMenu.inputOption();
    }
}