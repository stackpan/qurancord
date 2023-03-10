package com.stackpan.qurancord.core.util.test;

import com.stackpan.qurancord.core.util.AyahBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AyahBuilderTest {
    @Test
    void testThrows() {
        Assertions.assertThrows(NullPointerException.class, () -> AyahBuilder.createBuilder().build());
    }
}
