package pl.strefaserca.portal.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /** WAŻNE KOLEJNOŚĆ ANT MATCHERS MA ZNACZENIE, JEŻELI WCZEŚNIEJ ZOSTANIE ZABLOKOWANY TO DALEJ NIE PÓJDZIE **/

//        http
//                .requiresChannel()
//                .anyRequest()
//                .requiresSecure();
//    }

        http
//                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) //Cookie nie będzie możliwe do modyfikacji przez javascript
//                .and()//chroni api , cross site request forgery
                /** Jeżeli coś nie działa, być może dlatego, że włączyłem csrf. Na przykład po włączeniu csrf nie można wysłać wiadomości */
                .csrf().disable()

                /** JWT **/
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)//teraz sesja nie będzie przechowywana w bazie
//                .and()
//                .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(),jwtConfig,secretKey)) //pierwszy filtr JWT rejestrujemy
//                .addFilterAfter(new JwtTokenVerifier(secretKey,jwtConfig),JwtUsernameAndPasswordAuthenticationFilter.class)  //drugi filtr JWT rejestrujemy

//        "register"
//        "/", "index" ,"form" ,
//        "/home/*",
                /**   */
//        "/registrationConfirm",
//        "/registrationMessage" ,
//                .requiresChannel()
//                .anyRequest()
//                .requiresSecure()
//                .and()
//                RÓŻNICA między * a ** !  * oznacza katalog(/fonts/*) lub plik(/fonts* do następnego slasha a ** wszystkie podkatalogi lub również
                .authorizeRequests()
                .antMatchers(
                        "/home/**",
                        "/article/**",
                        "/service/**",
                        "/newsletter/**",
                        "/cert/**",
                        "/css/**", "/js/**", "/images/**", "/fonts/**")
                .permitAll() //wszystkie wymienione będą dopuszczone
//                .antMatchers("/login").permitAll()
//                .antMatchers("/api/**").hasRole("USER") // wszystko z takim URL ** musi mieć rolę student = ROLE BASED AUTHENTICATION
//                .antMatchers("/courses").hasRole("USER")
//                .antMatchers("/courses").hasAuthority("USER")
                /** Działa */
//                .antMatchers("/user/**").hasAuthority("READ_PRIVILEGE")
//                .antMatchers("/admin/**").hasAuthority("WRITE_PRIVILEGE")
//                .antMatchers("/user/**").hasRole("USER")
//                .antMatchers("/admin/**").hasRole("ADMIN")
//                .antMatchers("/user/**").hasAuthority("ROLE_USER")
                /** */
//
//                .antMatchers(HttpMethod.DELETE,"/management/api/**").hasAuthority(COURSE_WRITE.getPermission()) //aby skasować musi mieć permission Course_WRITE czyli tylko ADMIN taką ma
////                .antMatchers(HttpMethod.DELETE,"/management/api/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.name())
//                .antMatchers(HttpMethod.POST,"/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
//                .antMatchers(HttpMethod.PUT,"/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
//                .antMatchers("/management/api/**").hasAnyRole(ADMIN.name(), ADMINTRAINEE.name()) //Do get musi być rola ADMIN albo ta druga
////                .antMatchers(HttpMethod.GET,"/management/api/**").hasAnyRole(ADMIN.name(), ADMINTRAINEE.name()) //Do get musi być rola ADMIN albo ta druga


                .anyRequest()//każdy request musi być authenticated
                .authenticated()


//                .httpBasic(); //sposób autoryzacji podstawowy, nie można się wylogować

//                .failureUrl("/login?error=true")
                //OD TEGO MOMENTU WPROWADZIŁ JWT
                .and()
                .formLogin() //form based authentication
                .loginPage("/home/main").permitAll()//każdy może wejść na ten adres
//                .failureHandler(myAuthenticationFailureHandler())
//                    .defaultSuccessUrl("/courses", true)

//                .successHandler(myAuthenticationSuccessHandler())
                //po wejściu zostanie przekierowany na /courses
                .passwordParameter("password")// password i username = parametry w HTML  name="password" i  name="username"
                .usernameParameter("username")
//                .and()
//                .rememberMe().tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
////                    .userDetailsService(applicationUserService)  //u amigosa nie musi podawać tej linijki tak jakby UserDetailService się auto rejestrował, u mnie musiałem podać ręcznie implementację, ok naprawion, userdetailservice musi się też tak nazywać żeby nadpisać
//                .key("uniqueAndSecret")// defaults to 2 weeks(be    z parametrów) z parametrem tokenValidity rozszerzone do 21 dni oraz z kluczem do hashowania (username oraz expiration time)
//                .rememberMeParameter("remember-me") //parametr w HTMLgit
                .and()
                .logout()
//                .logoutUrl("/logout")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET")) //Gdy CSRF wyłączony możemy użyć GET, gdy włączony możemy ją skasowaś i defaultowo będzie używany POST
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID", "remember-me")
                .logoutSuccessUrl("/login");
    }
}
