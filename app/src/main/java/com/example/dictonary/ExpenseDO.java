package com.example.dictonary;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public
interface ExpenseDO {
    @Query("select * from expences")
    List<Expenses> getAllExpense();

    @Insert
    void addTx(Expenses expenses);

    @Update
    void updateTx(Expenses expenses);

    @Delete
    void deleteTx(Expenses expenses);
}
