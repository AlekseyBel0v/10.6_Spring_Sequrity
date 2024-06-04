package com.belov.springsequrity;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.UserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecutityConfuguration extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        // пример настройки провайдера данных для jdbcAuthentication()
//        auth.jdbcAuthentication().dataSource(dataSource)  //dataSource для подключения к базе
//        .usersByUsernameQuery("any query for getting User form DB");
//        .authoritiesByUsernameQuery("any query for getting authorities form DB for this user");

        // настройка разрешений (ролей) для пользователей
        auth.inMemoryAuthentication()
                //префикс {noop} указывает на то, что в качестве реализации PasswordEncoder используется NoOpPasswordEncoder (хранит в "сыром" виде)
                //BCryptPasswordEncoder - популярны шифровщик паролей
                .withUser("Ivan").password("{noop}1111").roles("ADMIN")
                .and()
                .withUser("Olga").password("{noop}2222").authorities("raed")
                .and()
                .withUser("Oleg").password("{noop}3333").authorities("write");

    }

    //метод устанавливает связь между эндпоинтами и authority (или ролями, или скоуп)
    //authority (разрешение) может быть одинаковое у нескольких пользователей с разными ролями
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()    //formLogin() - значит, что хотим использовать форму "логин"
                .and()
                // этот ресурс вынесен наружу с помощью permitAll()
                .authorizeRequests().antMatchers("/hi").permitAll() //permitAll() можно прописать только на уровне эндпоинтов при помощи конфигурации
                .and()
                //проверяет право доступа
                .authorizeRequests().antMatchers("/read").hasAuthority("raed")
                .and()
                //проверяет право доступа
                .authorizeRequests().antMatchers("/write").hasAuthority("write")
                .and()
                //проверяет право доступа
                .authorizeRequests().antMatchers("/admin").hasRole("ADMIN")
                .and()
                // все запросы (anyRequest()) должны пройти аутентификацию по умолчанию (кроме запроса с permitAll())
                .authorizeRequests().anyRequest().authenticated();
    }
}
