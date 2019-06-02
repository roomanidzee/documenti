package com.romanidze.documenti.mappers.mybatis

import com.romanidze.documenti.domain.Profile

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
interface ProfileDBMapper {

    @Insert("INSERT INTO profiles(user_id, surname, name, patronymic, email, phone_number) " +
            "VALUES(#{userID}, #{surname}, #{name}, #{patronymic}, #{email}, #{phoneNumber})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    fun save(profile: Profile)

    @Select("SELECT * FROM profiles")
    @Results(
        Result(column = "phone_number", property = "phoneNumber"),
        Result(column = "user_id", property = "userID")
    )
    fun findAll(): List<Profile>

    @Update("UPDATE profiles SET user_id = #{userID}, surname = #{surname}, name = #{name}," +
            "patronymic = #{patronymic}, email = #{email}, phone_number = #{phoneNumber} WHERE id = #{id}")
    fun update(profile: Profile)

    @Delete("DELETE FROM profiles WHERE id = #{id}")
    fun delete(@Param("id") id: Long)

}