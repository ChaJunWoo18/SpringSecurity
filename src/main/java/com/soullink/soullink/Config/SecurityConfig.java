package com.soullink.soullink.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록됨
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();

        roleHierarchy.setHierarchy("ROLE_C > ROLE_B\n" +
                "ROLE_B > ROLE_A");
        return roleHierarchy;
    }
    /** 
     * http
     *                 .authorizeHttpRequests((auth) -> auth
     *                         .requestMatchers("/login").permitAll()
     *                         .requestMatchers("/").hasAnyRole("A")
     *                         .requestMatchers("/manager").hasAnyRole("B")
     *                         .requestMatchers("/admin").hasAnyRole("C")
     *                         .anyRequest().authenticated();
     * **/

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "/login", "/loginProc", "/join", "/joinProc").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/my/**").hasAnyRole("ADMIN", "USER")
                        .anyRequest().authenticated()
                );

        http
                .formLogin((auth) -> auth
                        .loginPage("/login")
                        .loginProcessingUrl("/loginProc")
                        .permitAll()
                );
//        http
//                .httpBasic(Customizer.withDefaults());

        http
                .logout((auth) -> auth.logoutUrl("/logout")
                        .logoutSuccessUrl("/"));
//        http
//                .csrf(auth-> auth.disable());
        http
                .sessionManagement((auth) -> auth
                        .maximumSessions(1)  //다중로그인 기기 개수
                        .maxSessionsPreventsLogin(true)); //true : 초과 시 시도 차단, false:기존 거 해제 후 연결
        http
                .sessionManagement(auth -> auth
                        .sessionFixation().changeSessionId()); //none: 로그인시세션정보변겨 안함, newSession : 로그인 시 세션 새로 생성 , changeSessionId:로그인시 동일한 세션에 대한 id변경
        return http.build();
    }
    //InMemory(소수 유저인 경우 좋음. 토이프로젝트같은)
//    @Bean
//    public UserDetailsService userDetailsService() {
//
//        UserDetails user1 = User.builder()
//                .username("user1")
//                .password(bCryptPasswordEncoder().encode("1234"))
//                .roles("ADMIN")
//                .build();
//
//        UserDetails user2 = User.builder()
//                .username("user2")
//                .password(bCryptPasswordEncoder().encode("1234"))
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(user1, user2);
//    }

}
