package com.romanidze.documenti.mappers.mybatis

import com.romanidze.documenti.domain.postgres.FileToNote

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
interface FileToNoteDBMapper {

    @Insert("INSERT INTO file_to_note(note_id, file_id) VALUES(#{noteID}, #{fileID}")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    fun save(fileToNote: FileToNote)

    @Select("SELECT * FROM file_to_note")
    @Results(
            Result(column = "file_id", property = "fileID"),
            Result(column = "note_id", property = "noteID")
    )
    fun findAll(): List<FileToNote>

    @Update("UPDATE file_to_note SET note_id = #{noteID}, file_id = #{fileID} WHERE id = #{id}")
    fun update(fileToNote: FileToNote)

    @Delete("DELETE FROM file_to_note WHERE id = #{id}")
    fun delete(@Param("id") id: Long)

    @Delete("DELETE FROM file_to_note WHERE file_id = #{id}")
    fun deleteByFile(@Param("id") id: Long)

}