package com.yevsp8.medicament.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MedicamentDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insertMedicament(MedicamentEntity item);

    @Query("SELECT * FROM 'medicament' WHERE name LIKE :name")
    List<MedicamentEntity> getMedicamentListBySimilarName(String name);

    @Query("SELECT COUNT(*) FROM 'medicament'")
    int medicamentsCount();

    @Query("SELECT * FROM 'medicament' WHERE name = :name")
    MedicamentEntity getMedicamentByName(String name);
}
