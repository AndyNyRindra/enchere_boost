package com.enchere.recherche;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConstructionRequete {
    private Date date_min = null;
    private Date date_max = null;
    private String key_word = "";
    private int[] categorie = null;
    private Double prix_min;
    private Double prix_max;
    private Integer status;

    private String getConditionCategorie(){
        String condition = "";
        if ((categorie == null) || (categorie.length<=0)) {
            return " 1=1 ";
        }
        condition = "(";
        for (int i=0;i<categorie.length;i++) {
            if (i==categorie.length-1){
                condition+="idcategorie="+categorie[i]+")";
                break;
            }
            condition+="idcategorie="+categorie[i]+" or ";
        }
        return condition;
    }

    private String getConditionDate(){
        if ((date_min == null) && (date_max==null)){
            return " 2=2 ";
        }
        if (date_min==null)
            return  " (datefin<='"+date_max+"') ";
        if (date_max==null)
            return  " (datefin>='"+date_min+"') ";
        return " ( datefin between '"+date_min+"' and '"+date_max+"' ) ";
    }

    private String getConditionPrix(){
        if ((prix_min == null) && (prix_max==null)){
            return " 3=3 ";
        }
        if (prix_min==null)
            return  " (prixminimal<="+prix_max+") ";
        if (prix_max==null)
            return  " (prixminimal>="+prix_min+") ";
        return " ( prixminimal between "+prix_min+" and "+prix_max+" ) ";
    }

    private String getConditionStatus(){
        String req = "";
        if (status == null){
            req = " 4=4 ";
        }
        if (status==0)
            req =  " (datefin>now()) ";
        if (status==1)
            req =  " (datefin<now()) ";
        return req;
    }

    public String creerRequete(){
        String sql = "select * from Enchere where description ilike '%"+getKey_word()+"%' and 3=3 and "+getConditionPrix()+" and "+getConditionStatus()+" and "+getConditionCategorie()+" and "+getConditionDate();
        System.out.println(sql);
        return sql;
    }
}
