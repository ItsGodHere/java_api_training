package fr.lernejo.navy_battle.server.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class UrlUtilTest {
    @Test
    void testRegex_with_wrong_input() {
        String url = "http://localhost:notAport";
        String url2 = "http//loalhost:9876";
        Assertions.assertFalse( UrlUtil.useRegex(url) );
        Assertions.assertFalse( UrlUtil.useRegex(url2) );
    }

    @Test
    void testRegex_with_correct_input() {
        String url = "http://localhost:9876";
        Assertions.assertTrue( UrlUtil.useRegex(url) );
    }
}
