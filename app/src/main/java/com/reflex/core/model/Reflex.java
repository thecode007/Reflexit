package com.reflex.core.model;


import java.io.IOException;
import java.io.Serializable;

public interface Reflex extends Serializable {

    void  doAction(Object ...args) throws IOException;

}


