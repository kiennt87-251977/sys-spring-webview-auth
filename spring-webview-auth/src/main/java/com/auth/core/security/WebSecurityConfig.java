//package com.web.core.security;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@EnableWebSecurity
//public class WebSecurityConfig {
//    @Value("${app.white-list}")
//    private String[] whiteList;
//
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/index", "/home").access("hasRole('USER')")
//                .antMatchers("/admin/**", "/webview_portal").hasRole("ADMIN")
//                .and()
//                // some more method calls
//                .formLogin();
//        return http.build();
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user = User.withUsername("user1").password("user1").roles("USER").build();
//        UserDetails admin = User.withUsername("admin").password("admin").roles("ADMIN").build();
//        return new InMemoryUserDetailsManager(user, admin);
//    }
//
//
////    @Bean
////    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
////        return http.cors()
////                .configurationSource(corsConfigurationSource())
////                .and()
////                .authorizeRequests()
////                .antMatchers(whiteList)
////                .permitAll()
////                .anyRequest()
////                .authenticated()
////                .and().csrf().disable()
////                .build();
////    }
//
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        final CorsConfiguration corsConfiguration = new CorsConfiguration();
//        List<String> allowHeader = new ArrayList<>();
//        allowHeader.add("Authorization");
//        allowHeader.add("Cache-Control");
//        allowHeader.add("Content-Type");
//        List<String> allowOrigins = new ArrayList<>();
//        List<String> allowMethod = new ArrayList<>();
//        allowMethod.add("GET");
//        allowMethod.add("POST");
//        allowMethod.add("PUT");
//        allowMethod.add("DELETE");
//        allowMethod.add("OPTIONS");
//        allowMethod.add("PATCH");
//
//        corsConfiguration.setAllowedHeaders(allowHeader);
//        corsConfiguration.setAllowedOrigins(allowOrigins);
//        corsConfiguration.setAllowedMethods(allowMethod);
//        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", corsConfiguration);
//        return source;
//    }
//}