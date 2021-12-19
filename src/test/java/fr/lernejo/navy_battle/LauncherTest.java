package fr.lernejo.navy_battle;

import org.junit.jupiter.api.Test;

class LauncherTest {
    @Test
    void launching_with_no_arguments () {
        org.assertj.core.api.Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> Launcher.main(new String[] {}))
            .withMessage("Not enough arguments");
    }

    @Test
    void launching_with_negative_port () {
        org.assertj.core.api.Assertions.assertThatExceptionOfType(ArithmeticException.class)
            .isThrownBy(() -> Launcher.main(new String[] {"-9876"}))
            .withMessage("The number must be positive and greater than 0.");
    }

    @Test
    void launching_with_incorrect_type () {
        org.assertj.core.api.Assertions.assertThatExceptionOfType(NumberFormatException.class)
            .isThrownBy(() -> Launcher.main(new String[] {"ThisIsNotAPort"}))
            .withMessage("The port name must be a number.");
    }

    @Test
    void launching_with_correct_port () {
        org.assertj.core.api.Assertions.assertThatNoException()
            .isThrownBy(() -> Launcher.main(new String[] {"9786"}));
    }
}
