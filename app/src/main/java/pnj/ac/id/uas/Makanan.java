package pnj.ac.id.uas;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

public class Makanan implements Serializable{

//declare variable
    private String name;
    private String desc;
    private String key;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return " "+name+"\n" +
                " "+desc;
    }

    public Makanan() {
    }

    public Makanan(String nm, String dsc){
        name = nm;
        desc = dsc;
    }
}
