package com.yevsp8.medicament.data;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "medicament")
public class MedicamentEntity {

    MedicamentEntity(){}

    @Ignore
    MedicamentEntity(String name, String ogyi_key)
    {
        this.name=name;
        this.ogyi_key = ogyi_key;
    }

    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public String ogyi_key;

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getOgyi_key() {
        return ogyi_key;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setOgyi_key(String ogyi_key) {
        this.ogyi_key = ogyi_key;
    }

}
