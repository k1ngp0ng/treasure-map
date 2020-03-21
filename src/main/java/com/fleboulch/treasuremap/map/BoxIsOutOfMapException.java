package com.fleboulch.treasuremap.map;

public class BoxIsOutOfMapException extends RuntimeException {

    public BoxIsOutOfMapException(PlainsBox box, Dimension dimension) {
        super(String.format("'%s' is out of %s", box, dimension));
    }

}
