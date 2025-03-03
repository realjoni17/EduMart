package com.joni.edumart.data.offline

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.joni.edumart.data.api.dto.CourseDto
import com.joni.edumart.domain.models.Course

@Dao
interface CourseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(products: List<CourseDto>)

    @Query("SELECT * FROM coursedto")
    suspend fun getAllCourse(): List<CourseDto>
}