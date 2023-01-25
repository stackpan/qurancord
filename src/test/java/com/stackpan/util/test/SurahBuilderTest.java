package com.stackpan.util.test;

import com.stackpan.util.SurahBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SurahBuilderTest {
    @Test
    void testThrows() {
        Assertions.assertThrows(NullPointerException.class, () -> SurahBuilder.createBuilder().build());
    }
}
