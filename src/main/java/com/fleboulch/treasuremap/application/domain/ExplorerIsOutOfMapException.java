package com.fleboulch.treasuremap.application.domain;

import com.fleboulch.treasuremap.explorer.Explorer;
import com.fleboulch.treasuremap.map.Dimension;

public class ExplorerIsOutOfMapException extends RuntimeException {

    public ExplorerIsOutOfMapException(Explorer explorer, Dimension dimension) {
        super(String.format("%s is out of '%s'", explorer, dimension));
    }
}
