package org.faketri.exceptions;

public class ParserNotFindException extends RuntimeException{
    public ParserNotFindException() {
        super("Not find parser for this system");
    }
}
