package com.yevsp8.medicament.data;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.List;

@Entity(tableName = "substance")
public class SubstanceEntity {

    SubstanceEntity(){}

    @Ignore
    SubstanceEntity(int id, String name, List<String> medicamentList){
        this.id=id;
        this.name=name;
        this.medicamentList=medicamentList;
    }

    @PrimaryKey(autoGenerate = false)
    public int id;
    public String name;
    @TypeConverters(StringTypeConverter.class)
    private List<String> medicamentList;

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public List<String> getMedicamentList() {
        return medicamentList;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setMedicamentList(List<String> medicamentList) {
        this.medicamentList = medicamentList;
    }

}
