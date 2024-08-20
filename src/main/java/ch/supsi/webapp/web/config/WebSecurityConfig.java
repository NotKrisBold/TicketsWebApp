package ch.supsi.webapp.web.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		return http.csrf(AbstractHttpConfigurer::disable)
				.formLogin(form -> form
						.loginPage("/login")
						.failureUrl("/login?error")
						.permitAll())
				.logout(logout -> logout
						.logoutUrl("/logout")
						.logoutSuccessUrl("/")
						.permitAll())
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers("/", "/home-table").permitAll()
						.requestMatchers("/login", "/register").permitAll()
						.requestMatchers("/ticket/search").permitAll()
						.requestMatchers("/ticket/new").authenticated()
						.requestMatchers("/ticket/*/edit").hasRole("ADMIN")
						.requestMatchers("/ticket/*/delete").hasRole("ADMIN")
						.requestMatchers("/ticket/*/fastedit").hasRole("ADMIN")
						.requestMatchers("/ticket/**").permitAll()
						.requestMatchers("/tickets/**").permitAll()
						.requestMatchers("/css/**").permitAll()
						.requestMatchers("/js/**").permitAll()
						.requestMatchers("/webjars/**").permitAll()
						.requestMatchers("/fonts/**").permitAll()
						.anyRequest().authenticated()
				)
				.build();
	}

	@Bean
	PasswordEncoder BCPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}
}
