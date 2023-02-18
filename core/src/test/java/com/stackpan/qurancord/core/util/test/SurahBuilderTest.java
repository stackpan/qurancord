package com.stackpan.qurancord.core.util.test;

import com.stackpan.qurancord.core.util.SurahBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SurahBuilderTest {
    @Test
    void testThrows() {
        Assertions.assertThrows(NullPointerException.class, () -> SurahBuilder.createBuilder().build());
    }
}
