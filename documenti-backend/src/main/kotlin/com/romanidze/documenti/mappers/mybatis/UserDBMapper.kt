package com.romanidze.documenti.mappers.mybatis

import com.romanidze.documenti.domain.postgres.User

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
interface UserDBMapper {

    @Insert("INSERT INTO users(username, password, user_role, user_state) " +
            "VALUES(#{username}, #{password}, #{role}, #{state})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    fun save(user: User)

    @Select("SELECT * FROM users")
    @Results(
            Result(column = "user_role", property = "role"),
            Result(column = "user_state", property = "state")
    )
    fun findAll(): List<User>

    @Update("UPDATE users SET username = #{username}, password = #{password}, " +
            "user_role = #{role}, user_state = #{state} WHERE id = #{id}")
    fun update(user: User)

    @Delete("DELETE FROM users WHERE id = #{id}")
    fun delete(@Param("id") id: Long)

    @Select("SELECT * FROM users WHERE username = #{username}")
    @Results(
        Result(column = "user_role", property = "role"),
        Result(column = "user_state", property = "state")
    )
    fun findByUsername(@Param("username") username: String): User?

}