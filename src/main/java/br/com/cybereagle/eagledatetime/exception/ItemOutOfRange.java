package br.com.cybereagle.eagledatetime.exception;

public class ItemOutOfRange extends RuntimeException {

    private static final long serialVersionUID = 4760138291907517660L;

    public ItemOutOfRange(String aMessage) {
        super(aMessage);
    }

}
