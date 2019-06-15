package com.romanidze.documenti.mappers.mybatis

import com.romanidze.documenti.domain.postgres.FileToUser

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
interface FileToUserDBMapper {

    @Insert("INSERT INTO file_to_user(file_id, user_id) VALUES(#{fileID}, #{userID})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    fun save(fileToUser: FileToUser)

    @Select("SELECT * FROM file_to_user")
    @Results(
            Result(column = "file_id", property = "fileID"),
            Result(column = "user_id", property = "userID")
    )
    fun findAll(): List<FileToUser>

    @Update("UPDATE file_to_user SET file_id = #{fileID}, user_id = #{userID} WHERE id = #{id}")
    fun update(fileToUser: FileToUser)

    @Delete("DELETE FROM file_to_user WHERE id = #{id}")
    fun delete(@Param("id") id: Long)

}