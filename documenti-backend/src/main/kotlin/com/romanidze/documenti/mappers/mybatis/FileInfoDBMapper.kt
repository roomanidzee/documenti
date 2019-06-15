package com.romanidze.documenti.mappers.mybatis

import com.romanidze.documenti.domain.postgres.FileInfo

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
interface FileInfoDBMapper {

    @Insert("INSERT INTO file_info(original_name, encoded_name, file_size, file_type, file_url, created_time)" +
            "VALUES(#{originalName}, #{encodedName}, #{fileSize}, #{fileType}, #{fileURL}, #{createdTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    fun save(fileInfo: FileInfo)

    @Select("SELECT * FROM file_info")
    @Results(
            Result(column = "original_name", property = "originalName"),
            Result(column = "encoded_name", property = "encodedName"),
            Result(column = "file_size", property = "fileSize"),
            Result(column = "file_type", property = "fileType"),
            Result(column = "file_url", property = "fileURL"),
            Result(column = "created_time", property = "createdTime")
    )
    fun findAll(): List<FileInfo>

    @Update("UPDATE file_info SET original_name = #{originalName}, encoded_name = #{encodedName}," +
            "file_size = #{fileSize}, file_type = #{fileType}, file_url = #{fileURL} WHERE id = #{id}")
    fun update(fileInfo: FileInfo)

    @Delete("DELETE FROM file_info WHERE id = #{id}")
    fun delete(@Param("id") id: Long)

    @Select("SELECT * FROM file_info WHERE id = #{id}")
    fun findByID(@Param("id") id: Long): FileInfo

}