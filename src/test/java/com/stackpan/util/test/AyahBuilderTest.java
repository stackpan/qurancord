package com.stackpan.util.test;

import com.stackpan.util.AyahBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AyahBuilderTest {
    @Test
    void testThrows() {
        Assertions.assertThrows(NullPointerException.class, () -> AyahBuilder.createBuilder().build());
    }
}
