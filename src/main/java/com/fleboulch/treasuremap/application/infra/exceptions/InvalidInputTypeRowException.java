package com.fleboulch.treasuremap.application.infra.exceptions;

import static com.fleboulch.treasuremap.application.infra.ApplicationFactory.CARET_DELIMITER;

public class InvalidInputTypeRowException extends IllegalArgumentException {

    public InvalidInputTypeRowException(String[] row) {
        super(String.format("the row type '%s' is unknown", String.join(CARET_DELIMITER, row)));
    }
}
