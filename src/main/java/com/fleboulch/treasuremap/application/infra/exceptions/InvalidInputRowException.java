package com.fleboulch.treasuremap.application.infra.exceptions;

import static com.fleboulch.treasuremap.application.infra.ApplicationFactory.CARET_DELIMITER;

public class InvalidInputRowException extends RuntimeException {

    public InvalidInputRowException(String[] row) {
        super(String.format("the row '%s' is invalid (incorrect number of properties)", String.join(CARET_DELIMITER, row)));
    }
}
