package com.enchere.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DureeMoyenne {
    Double duree;

    public DureeMoyenne(List<Enchere> enchereList) {
        double moyenne=0;
        for(Enchere enchere:enchereList){
            moyenne+=enchere.getDuree();
        }
        if(enchereList.size()==0){
            moyenne=0;
        }
        moyenne=moyenne/enchereList.size();
        setDuree(moyenne);
    }
    public void setDuree(Double duree) {
        this.duree = duree;
    }
}
