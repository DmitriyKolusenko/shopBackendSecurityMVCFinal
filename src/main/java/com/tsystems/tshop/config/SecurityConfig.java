package com.tsystems.tshop.config;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity(debug = true)
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailServiceImp userDetailsService;

 	@Override
 	public void configure(WebSecurity web) throws Exception {
 		web.ignoring()
 		// Spring Security should completely ignore URLs starting with /resources/
 				.antMatchers("/resources/**")

		;
 	}

 	@Override
 	protected void configure(HttpSecurity http) throws Exception {
 		http

			//	.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
				.cors().configurationSource(corsConfigurationSource())
				.and().authorizeRequests().antMatchers("/api/products/**").permitAll()
				.and().authorizeRequests()
				.anyRequest().hasAnyRole("USER","MANAGER").anyRequest().authenticated()
				.and().authorizeRequests().antMatchers("api/products/writenew").hasRole("MANAGER")
                .and().formLogin()/*.loginPage("/index.jsp")*/.loginProcessingUrl("/loginAction").successForwardUrl("/api/token").permitAll()
				.and().logout().logoutUrl("/logout").logoutSuccessHandler((new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK)))
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID")
                .and().httpBasic()

			//	.and().logout().logoutUrl("/logout").logoutSuccessUrl("/logout1").permitAll();
				.and().csrf().disable();
 	}

 	@Override
 	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
 		auth.userDetailsService(userDetailsService)
				.passwordEncoder(NoOpPasswordEncoder.getInstance());
 	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
		configuration.addAllowedOrigin("*");
		configuration.setMaxAge((long)3600);
		configuration.addAllowedMethod(HttpMethod.POST);
		configuration.addAllowedMethod(HttpMethod.GET);
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
