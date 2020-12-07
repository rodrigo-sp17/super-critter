package com.udacity.jdnd.course3.critter.pet.exception;

public class PetNotFoundException extends RuntimeException{

    public PetNotFoundException() {
    }

    public PetNotFoundException(String message) {
        super(message);
    }
}
