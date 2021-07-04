package com.example.library.library.exception;

public class LibraryCustomException extends RuntimeException{

    String errorMessage=null;
    public LibraryCustomException(String errorMessage) {
        this.errorMessage=errorMessage;
    }

    @Override
    public String getMessage() {
        return errorMessage;
    }
    
}
