package com.romanidze.documenti.config.mybatis

import org.apache.ibatis.mapping.SqlSource
import org.apache.ibatis.scripting.LanguageDriver
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver
import org.apache.ibatis.session.Configuration

import java.util.regex.Pattern

class ExtendedLanguageDriver: XMLLanguageDriver(), LanguageDriver {

    private val pattern = Pattern.compile("\\(#\\{(\\w+)\\}\\)")

    override fun createSqlSource(configuration: Configuration?, script: String?, parameterType: Class<*>?): SqlSource {

        var changeScript = script
        val matcher = this.pattern.matcher(changeScript!!)

        if(matcher.find()){
            changeScript =
                    matcher.replaceAll("(<foreach collection=\"$1\" " +
                                       "item=\"__item\" separator=\",\" >#{__item}</foreach>)")
        }

        changeScript = "<script>$changeScript</script>"

        return super.createSqlSource(configuration, changeScript, parameterType)

    }

}