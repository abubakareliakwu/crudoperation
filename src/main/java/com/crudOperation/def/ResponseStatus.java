package com.crudOperation.def;

public enum ResponseStatus {

    SUCCESS(200),
    SQL_ERROR(201),
    ENTITY_NOT_FOUND(202),
    ENTITY_ALREADY_EXISTS(203),
    NO_CONTENT(204);


    private final int status;

    ResponseStatus(int status){
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
