package com.bits.canvas.web.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

import com.bits.canvas.persistence.repo.UserRepository;
import com.bits.canvas.persistence.service.RepositoryUserDetailService;


/**
 * @author vatsritu
 */
@Configuration
@EnableWebSecurity
public class SecurityContext extends WebSecurityConfigurerAdapter {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RepositoryUserDetailService repositoryUserDetailService;

	

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.headers().disable()
		//Configures form login
		.csrf().disable()
		 .formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/home/authenticate")
                    .failureUrl("/login?error=bad_credentials")
                //Configures the logout function
                .and()
		.logout()
		.deleteCookies("JSESSIONID")
		.logoutUrl("/logout")
		.logoutSuccessUrl("/home")
		//Configures url based authorization
		.and()
		.authorizeRequests()
		//Anyone can access the urls
		.antMatchers(
				"/*",
				"/home/*",
				"/auth/**",
				"/login",
				"/signup/**",
				"/user/register/**",
				"/user/register"
				).permitAll();
	}

	@Bean(name="myRepository")
	public SecurityContextRepository securityContextRepository(){
		//repository.setDisableUrlRewriting(true); 
	HttpSessionSecurityContextRepository repository =  new HttpSessionSecurityContextRepository();
		return repository;
	}


	@Bean(name="myAuthenticationManager")
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception{
		return super.authenticationManagerBean();
	}
	/**
	 * Configures the authentication manager bean which processes authentication
	 * requests.
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
		.userDetailsService(repositoryUserDetailService)
		.passwordEncoder(passwordEncoder());
	}

	/**
	 * This is used to hash the password of the user.
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}


}
