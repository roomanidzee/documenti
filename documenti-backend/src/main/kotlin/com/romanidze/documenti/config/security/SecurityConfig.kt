package com.romanidze.documenti.config.security

import com.romanidze.documenti.config.security.filters.JWTTokenAuthenticationFilter
import com.romanidze.documenti.config.security.providers.JWTTokenAuthenticationProvider

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter

@EnableWebSecurity
class SecurityConfig(private val authenticationProvider: JWTTokenAuthenticationProvider,
                     private val authenticationFilter: JWTTokenAuthenticationFilter,
                     @Qualifier("customDetailsService") private val userDetailsService: UserDetailsService,
                     private val authenticationEntryPoint: AuthenticationEntryPoint,
                     private val accessDeniedHandler: AccessDeniedHandler): WebSecurityConfigurerAdapter() {

    @Bean
    @Primary
    fun passwordEncoder(): PasswordEncoder{
        return BCryptPasswordEncoder()
    }

    override fun configure(http: HttpSecurity?) {

        http!!.addFilterBefore(this.authenticationFilter, BasicAuthenticationFilter::class.java)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
              .and()
                .exceptionHandling().authenticationEntryPoint(this.authenticationEntryPoint)
                                    .accessDeniedHandler(this.accessDeniedHandler)
              .and()
                .authorizeRequests()
                .antMatchers("/user/**", "/profile/**").hasAuthority("USER")
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .antMatchers("/auth/**").permitAll()
                .and()
                .csrf()
                .disable()


    }

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth!!.authenticationProvider(this.authenticationProvider)
        auth.userDetailsService(this.userDetailsService)
    }

}