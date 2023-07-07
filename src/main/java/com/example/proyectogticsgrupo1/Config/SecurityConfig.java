package com.example.proyectogticsgrupo1.Config;

import com.example.proyectogticsgrupo1.Entity.Usuario;
import com.example.proyectogticsgrupo1.Entity.UxUiEntity;
import com.example.proyectogticsgrupo1.Repository.PacienteRepository;
import com.example.proyectogticsgrupo1.Repository.UsuarioRepository;
import com.example.proyectogticsgrupo1.Repository.UxUiRepository;
import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.*;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;

import javax.sql.DataSource;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    final DataSource dataSource;
    final UsuarioRepository usuarioRepository;
    final PacienteRepository pacienteRepository;
    final UxUiRepository uxUiRepository;

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();


    public SecurityConfig(DataSource dataSource, UsuarioRepository usuarioRepository, PacienteRepository pacienteRepository, UxUiRepository uxUiRepository) {
        this.dataSource = dataSource;
        this.usuarioRepository = usuarioRepository;
        this.pacienteRepository = pacienteRepository;
        this.uxUiRepository = uxUiRepository;
    }




    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf().ignoringRequestMatchers("/superadmin/crearPlantillaInforme");
        http.csrf().ignoringRequestMatchers("/superadmin/listarTitulos");
        http.csrf().ignoringRequestMatchers("/superadmin/BorrarPlantilla");

        http.csrf().ignoringRequestMatchers("/superadmin/modificarPlantilla");

        http.csrf().ignoringRequestMatchers("/doctor/pacientesatendidos/verhistorial/vercita/verinformesmedico/llenarinforme/guardar");
        http.csrf().ignoringRequestMatchers("/doctor/dashboard/info/llenarinforme/guardar");
        http.csrf().ignoringRequestMatchers("/doctor/pacientesatendidos/verhistorial/vercita");
        http.csrf().ignoringRequestMatchers("/doctor/cuestionario/enviarcuestionario");
        http.csrf().ignoringRequestMatchers("/doctor/autenticacionzoom");

        http.csrf().ignoringRequestMatchers("/administrativo/consultaReniec");

        http.csrf().ignoringRequestMatchers("/paciente/llenarCuestionario");














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

                    HttpSession session = request.getSession();
                    Usuario usuario = usuarioRepository.findByCorreo(authentication.getName());
                    UxUiEntity uxUiEntity = uxUiRepository.findByTipodeusuarioIdtipodeusuario(usuario.getTipodeusuario().getIdtipodeusuario());
                    session.setAttribute("usuario", usuario);
                    session.setAttribute("configuiux", uxUiEntity.getCodigocolor());
                    System.out.println("codigo color"+uxUiEntity.getCodigocolor());
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
                            response.sendRedirect("/paciente/");
                        }else if (rol.equals("superadmin")) {
                            response.sendRedirect("/superadmin/index");
                        }else if (rol.equals("administrativo")) {
                            response.sendRedirect("/administrativo/dashboard");
                        }else {
                            response.sendRedirect("/");
                        }
                    }

                });

        //para cerrar sesion
        http.logout().logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true);


        http.authorizeHttpRequests()
//                .requestMatchers("/superadmin/crearPlantillaInforme").permitAll()
                .requestMatchers(HttpMethod.POST, "/superadmin/crearPlantillaInforme").permitAll()

                .requestMatchers(HttpMethod.GET, "/superadmin/listarTitulos").permitAll()

                .requestMatchers(HttpMethod.GET, "/superadmin/BorrarPlantilla").permitAll()

                .requestMatchers(HttpMethod.GET, "/superadmin/modificarPlantilla").permitAll()

                .requestMatchers(HttpMethod.POST, "/doctor/pacientesatendidos/verhistorial/vercita/verinformesmedico/llenarinforme/guardar").permitAll()
                .requestMatchers(HttpMethod.POST,"/doctor/dashboard/info/llenarinforme/guardar").permitAll()

                .requestMatchers(HttpMethod.GET,"/doctor/pacientesatendidos/verhistorial/vercita").permitAll()
                .requestMatchers(HttpMethod.GET,"/doctor/autenticacionzoom").permitAll()
                .requestMatchers(HttpMethod.POST, "/doctor/cuestionario/enviarcuestionario").permitAll()
                .requestMatchers(HttpMethod.GET,"/doctor/pacientesatendidos").permitAll()

                .requestMatchers(HttpMethod.POST,"/administrativo/consultaReniec").permitAll()



                .requestMatchers(HttpMethod.POST,"/paciente/llenarCuestionario").permitAll()



                .requestMatchers("/doctor", "/doctor/**").hasAnyAuthority("doctor")
                .requestMatchers("/administrador", "/administrador/**").hasAnyAuthority("administrador")
                .requestMatchers("/paciente", "/paciente/**").hasAnyAuthority("paciente")
                .requestMatchers("/superadmin", "/superadmin/**").hasAnyAuthority("superadmin")
                .requestMatchers("/administrativo", "/administrativo/**").hasAnyAuthority("administrativo")
                //.requestMatchers("/shipper", "/shipper/**").hasAuthority("admin")
                //Dejar accesible a todos los usuarios cualquier otra ruta con anyRequest()



                .anyRequest().permitAll();

                //.anyRequest().authenticated();



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