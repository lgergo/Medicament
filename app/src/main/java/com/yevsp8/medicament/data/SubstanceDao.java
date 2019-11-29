package com.yevsp8.medicament.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface SubstanceDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insertSubstance(SubstanceEntity item);

    @Query("SELECT COUNT(*) FROM 'substance'")
    int substancesCount();

    @Query("SELECT * FROM 'substance' WHERE name LIKE :name")
    SubstanceEntity getSubstanceByName(String name);
}
