package fr.lernejo.navy_battle;

import fr.lernejo.navy_battle.server.SimpleHttpServer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.IllegalCharsetNameException;

class LauncherTest {
    @Test
    void launching_with_no_arguments () {
        org.assertj.core.api.Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new Launcher().main(new String[] {}))
            .withMessage("Not enough arguments");
    }

    @Test
    void launching_with_negative_port () {
        org.assertj.core.api.Assertions.assertThatExceptionOfType(ArithmeticException.class)
            .isThrownBy(() -> new Launcher().main(new String[] {"-9876"}))
            .withMessage("The number must be positive and greater than 0.");
    }

    @Test
    void launching_with_incorrect_type () {
        org.assertj.core.api.Assertions.assertThatExceptionOfType(NumberFormatException.class)
            .isThrownBy(() -> new Launcher().main(new String[] {"ThisIsNotAPort"}))
            .withMessage("The port name must be a number.");
    }

    @Test
    void launching_with_correct_port () {
        org.assertj.core.api.Assertions.assertThatNoException()
            .isThrownBy(() -> new Launcher().main(new String[] {"9786"}));
    }

    @Test
    void launching_with_wrong_url() {
        org.assertj.core.api.Assertions.assertThatExceptionOfType(IllegalCharsetNameException.class)
            .isThrownBy(() -> new Launcher().main(new String[] {"9826", "http://localhot:wrong_url"}))
            .withMessage("Wrong url");
    }

    @Test
    void launching_with_two_arguments() throws IOException {
        SimpleHttpServer server = new SimpleHttpServer("9877");
        server.Start();
        org.assertj.core.api.Assertions.assertThatNoException()
            .isThrownBy(() -> new Launcher().main(new String[] {"9876", "http://localhost:9877"}));
        server.Stop();
    }
}
