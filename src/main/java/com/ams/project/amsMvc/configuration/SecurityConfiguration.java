package com.ams.project.amsMvc.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private DataSource dataSource;
    @Value("${spring.queries.users-query}")
    private String usersQuery;
    @Value("${spring.queries.roles-query}") //charger les roles de user connecter
    private String rolesQuery;

    @Override
    //bch t7el connexion 
    protected void configure(AuthenticationManagerBuilder auth) // objet qui permet la connexion ,la chargement de user et son role de data source
            throws Exception {
        auth.
                jdbcAuthentication()
                .usersByUsernameQuery(usersQuery) //nchoufo mawjoud wala
                .authoritiesByUsernameQuery(rolesQuery) //chnoma les roles mta3ou user
                .dataSource(dataSource)
                .passwordEncoder(bCryptPasswordEncoder);
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception { //bch tnadhem l'autorisation

        http.
                authorizeRequests()
                .antMatchers("/").permitAll() // accès pour tous users
                .antMatchers("/login").permitAll() // accès pour tous users
                .antMatchers("/registration").permitAll() // accès pour tous users
                .antMatchers("/role/**").permitAll()
                .antMatchers("/index/**").permitAll()
                .antMatchers("/contact/**").permitAll()
                .antMatchers("/admin/**").permitAll()
                .antMatchers("/produits/**").permitAll()
                .antMatchers("/partenaires/**").permitAll()
                .antMatchers("/accounts/**").hasAuthority("SUPERADMIN")
                .antMatchers("/provider/**").hasAuthority("ADMIN")
                //.antMatchers("/contact/**").hasAuthority("ADMIN")
                .antMatchers("/actualite/**").permitAll()
                .antMatchers("/fournisseur/**").permitAll()
                .antMatchers("/article/**").permitAll()
                .antMatchers("/affectation/**").hasAnyAuthority("ADMIN", "SUPERADMIN").anyRequest()
                
               //// .antMatchers("/provider/**").hasAnyAuthority("ADMIN", "SUPERADMIN")
               // .antMatchers("/article/**").hasAnyAuthority("AGENT", "CLIENT").anyRequest()

                .authenticated().and().csrf().disable().formLogin() // l'accès de fait via un formulaire //disable() puisk ne5dem en mode dev on mode prod na7ouha
                // stateless nahouha formLogin() ki ne5dem full stack
                .loginPage("/login").failureUrl("/login?error=true") // fixer la page login
                
                .defaultSuccessUrl("/dashboard") // page d'accueil après login avec succès
                .usernameParameter("email") // paramètres d'authentifications login et password
                .passwordParameter("password")
                .and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // route de deconnexion ici /logut
                .logoutSuccessUrl("/login").and().exceptionHandling() // une fois deconnecté redirection vers login
                
                .accessDeniedPage("/403"); //ma3andouch l7a9 bch yaksedi 
    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/back/**", "/front/**","/images/**","/css/**","/aploadsActualite/**", "/aploads/**" ,"/aploadsProvider/**");
    }

 
}
