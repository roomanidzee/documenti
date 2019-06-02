package com.romanidze.documenti.mappers.mybatis

import com.romanidze.documenti.domain.User

import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.Update
import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Options
import org.apache.ibatis.annotations.Param

@Mapper
interface UserDBMapper {

    @Insert("INSERT INTO users(username, password) VALUES(#{username}, #{password})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    fun save(user: User)

    @Select("SELECT * FROM users")
    fun findAll(): List<User>

    @Update("UPDATE users SET username = #{username}, password = #{password} WHERE id = #{id}")
    fun update(user: User)

    @Delete("DELETE FROM users WHERE id = #{id}")
    fun delete(@Param("id") id: Long)


}