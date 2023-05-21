package com.example.proyectogticsgrupo1.Config;

import com.example.proyectogticsgrupo1.Repository.UsuarioRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
public class SecurityConfig {

    final DataSource dataSource;
    final UsuarioRepository usuarioRepository;

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();


    public SecurityConfig(DataSource dataSource, UsuarioRepository usuarioRepository) {
        this.dataSource = dataSource;
        this.usuarioRepository = usuarioRepository;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        //Usar el formulario por defecto de spring security
        http.formLogin()
                .loginPage("/")
                .loginProcessingUrl("/validacionusuario")
                .usernameParameter("correo")
                .passwordParameter("contrasena")
                .successHandler((request, response, authentication) -> {

                    //HttpSession session = request.getSession();
                    //session.setAttribute("usuario",usuarioRepository.findByEmail(authentication.getName()));

                    DefaultSavedRequest defaultSavedRequest = (DefaultSavedRequest) request.getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST");

                    if (defaultSavedRequest != null) {
                        String targetURL = defaultSavedRequest.getRedirectUrl();
                        redirectStrategy.sendRedirect(request, response, targetURL);
                    } else {
                        String rol = "";
                        for (GrantedAuthority role : authentication.getAuthorities()) {
                            rol = role.getAuthority();
                            break;
                        }

                        if (rol.equals("doctor")) {
                            response.sendRedirect("/doctor/dashboard");
                        } else if (rol.equals("administrador")) {
                            response.sendRedirect("/administrador");
                        } else if (rol.equals("paciente")) {
                            response.sendRedirect("/paciente");
                        }else if (rol.equals("superadmin")) {
                            response.sendRedirect("/superadmin/index");
                        }else {
                            response.sendRedirect("/");
                        }
                    }

                });
        //para cerrar sesion
        http.logout();

        http.authorizeHttpRequests()
                .requestMatchers("/doctor", "/doctor/**").hasAnyAuthority("doctor")
                .requestMatchers("/administrador", "/administrador/**").hasAnyAuthority("administrador")
                .requestMatchers("/paciente", "/paciente/**").hasAnyAuthority("paciente")
                .requestMatchers("/superadmin", "/superadmin/**").hasAnyAuthority("superadmin")
                //.requestMatchers("/shipper", "/shipper/**").hasAuthority("admin")
                //Dejar accesible a todos los usuarios cualquier otra ruta con anyRequest()
                .anyRequest().permitAll();


        /*http.logout().logoutSuccessUrl("/").deleteCookies("JSESSIONID")
                .invalidateHttpSession(true);*/

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsManager users(DataSource dataSource) {
        JdbcUserDetailsManager jdbc = new JdbcUserDetailsManager(dataSource);

        String sql1 = "SELECT correo,contrasena,estado_habilitado FROM usuario where correo = ? ";
        String sql2 = "SELECT u.correo, t.nombre FROM usuario u INNER JOIN tipodeusuario t ON (u.idtipodeusuario = t.idtipodeusuario) " +
                      "WHERE u.correo = ? ";

        jdbc.setUsersByUsernameQuery(sql1);
        jdbc.setAuthoritiesByUsernameQuery(sql2);
        return jdbc;
    }

}
