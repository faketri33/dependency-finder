package org.faketri.exceptions;

public class NotFindBuildSystemException extends RuntimeException {

    public NotFindBuildSystemException(){
        super("Cannot find build system");
    }

    public NotFindBuildSystemException(String message) {
        super(message);
    }
}
