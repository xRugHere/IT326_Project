package com.bitfit.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class DevTokenVerifierTest {

    private final DevTokenVerifier verifier = new DevTokenVerifier();

    @Test
    void acceptsDevTokens() {

    }

    @Test
    void rejectsGarbage() {

    }
}
