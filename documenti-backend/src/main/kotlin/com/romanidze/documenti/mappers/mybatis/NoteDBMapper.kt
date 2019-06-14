package com.romanidze.documenti.mappers.mybatis

import com.romanidze.documenti.domain.postgres.Note

import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.Update
import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Options
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Results
import org.apache.ibatis.annotations.Result

@Mapper
interface NoteDBMapper {

    @Insert("INSERT INTO notes(user_id, title, description, created_time) " +
            "VALUES(#{userID}, #{title}, #{description}, #{createdTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    fun save(note: Note)

    @Select("SELECT * FROM notes")
    @Results(
       Result(column = "created_time", property = "createdTime")
    )
    fun findAll(): List<Note>

    @Update("UPDATE notes SET user_id = #{userID}, title = #{title}, description = #{description}" +
            "WHERE id = #{id}")
    fun update(note: Note)

    @Delete("DELETE FROM notes WHERE id = #{id}")
    fun delete(@Param("id") id: Long)

    @Select("SELECT * FROM notes WHERE user_id = #{user_id}")
    fun findByUserID(@Param("user_id") userID: Long): List<Note>

}