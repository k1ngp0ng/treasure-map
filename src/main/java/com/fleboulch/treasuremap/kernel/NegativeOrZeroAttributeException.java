package com.fleboulch.treasuremap.kernel;

public class NegativeOrZeroAttributeException extends RuntimeException {

    public NegativeOrZeroAttributeException(String message) {
        super(message);
    }
}
