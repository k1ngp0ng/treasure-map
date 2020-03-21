package com.fleboulch.treasuremap.kernel;

public class Domain {

    private Domain() {
    }

    public static <T> T validateNotNull(T attribute, String message) {
        if (attribute == null) {
            throw new DomainException(message);
        }
        return attribute;
    }

    public static int validatePositiveOrZero(int attribute, String message) {
        if (attribute < 0) {
            throw new NegativeAttributeException(message);
        }
        return attribute;
    }

    public static int validatePositive(int attribute, String message) {
        if (attribute <= 0) {
            throw new NegativeOrZeroAttributeException(message);
        }
        return attribute;
    }

}
