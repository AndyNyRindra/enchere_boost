package com.enchere.model;

import com.enchere.exception.CustomException;

public abstract class HasFK<FK> extends HasId {
    public abstract void setFK(FK fk) throws CustomException;
}
