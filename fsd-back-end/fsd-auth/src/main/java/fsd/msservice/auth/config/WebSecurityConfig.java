package fsd.msservice.auth.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import fsd.common.config.AuthPathConfig;
import fsd.common.util.RequestMatcherUtil;
import fsd.msservice.auth.filter.JsonUsernamePasswordAuthenticationFilter;
import fsd.msservice.auth.filter.provider.BuyerAuthenticationProvider;
import fsd.msservice.auth.filter.provider.SellerAuthenticationProvider;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

//	public static final String TOKEN_BASED_ENTRY_POINT = "/api/limit/**";
//	public static final String AUTH_ENTRY_POINT = "/api/auth";
//	public static final String LOGIN_ENTRY_POINT = "/api/login";

	/** auth path config defined in the yml */
	@Autowired
	private AuthPathConfig authPathConfig;

	@Autowired
	private ObjectMapper objectMapper;

	/**
	 * 未登录或认证不成功处理器
	 */
//	@Autowired
//	private EntryPointUnauthorizedHandler unauthorizedHandler;

	/**
	 * 访问无权限处理器
	 */
	@Autowired
	private AccessDeniedHandler accessDeniedHandler;

	/**
	 * Load BCryptPasswordEncoder
	 * 
	 * @return
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	@Qualifier("buyerDetailsService")
	private UserDetailsService buyerDetailsService;

	@Autowired
	@Qualifier("sellerDetailsService")
	private UserDetailsService sellerDetailsService;

	@Autowired
	private AuthenticationSuccessHandler authenticationSuccessHandler;

	@Autowired
	private AuthenticationFailureHandler authenctiationFailureHandler;

//	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
//	@Override
//	public AuthenticationManager authenticationManagerBean() throws Exception {
//		return super.authenticationManagerBean();
//	}
//
//	@Autowired
//	protected AuthenticationManager authenticationManager;

	/**
	 * BuyerAuthenticationProvider
	 * 
	 * @return
	 */
	@Bean("buyerAuthenticationProvider")
	DaoAuthenticationProvider daoBuyerAuthenticationProvider() {
		return new BuyerAuthenticationProvider(passwordEncoder(), buyerDetailsService);
	}

	/**
	 * SellerAuthenticationProviderBuyer
	 * 
	 * @return
	 */
	@Bean("sellerAuthenticationProvider")
	DaoAuthenticationProvider daoSellerAuthenticationProvider() {
		return new SellerAuthenticationProvider(passwordEncoder(), sellerDetailsService);
	}

	/**
	 * Add Provider to AuthenticationManager
	 * 
	 */
	// @Autowired
	// public void configureGlobal(AuthenticationManagerBuilder auth) {
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(daoBuyerAuthenticationProvider());
		auth.authenticationProvider(daoSellerAuthenticationProvider());
	}

//	@Autowired
//	public void configureAuthentication(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(this.buyerDetailsService).passwordEncoder(passwordEncoder());
//	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		JsonUsernamePasswordAuthenticationFilter jsonUsernamePasswordAuthenticationFilter = new JsonUsernamePasswordAuthenticationFilter(
				authPathConfig, objectMapper);
		jsonUsernamePasswordAuthenticationFilter.setAuthenticationManager(super.authenticationManager());
		jsonUsernamePasswordAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
		jsonUsernamePasswordAuthenticationFilter.setAuthenticationFailureHandler(authenctiationFailureHandler);

		// JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter = new
		// JwtAuthenticationTokenFilter();

		// ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry
		// registry =
		http
				// Close cors, allow all request access
				.cors().and()
				// Since use JWT, csrf is disabled
				.csrf().disable()
				// Since use token, don't create session
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()

//				.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//				// allow all static resource access
//				.antMatchers(HttpMethod.GET, "/", "/error", "/*.html", "/favicon.ico", "/**/*.html", "/**/*.css",
//						"/**/*.js")
//				.permitAll()
//				// allow access for rest api who get token
//				.antMatchers(AUTH_ENTRY_POINT, LOGIN_ENTRY_POINT).permitAll()
				// any other requests must authorize
				.requestMatchers(RequestMatcherUtil.requestMatchers(authPathConfig.getLoginPath())
						.toArray(new RequestMatcher[0]))
				.permitAll().anyRequest().authenticated().and()
				//
				// .addFilterBefore(jwtAuthenticationTokenFilter,
				// UsernamePasswordAuthenticationFilter.class)
				// .addFilterAfter(jsonUsernamePasswordAuthenticationFilter,
				// UsernamePasswordAuthenticationFilter.class)
				.addFilterAt(jsonUsernamePasswordAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
				.exceptionHandling()// .authenticationEntryPoint(unauthorizedHandler)
				.accessDeniedHandler(accessDeniedHandler);
		// .formLogin()
		// .successHandler(authenticationSuccessHandler)
		// .failureHandler(authenctiationFailureHandler)
		// .and().authorizeRequests()
		// .antMatchers("/login/form").permitAll()
		// .antMatchers("/login").permitAll().anyRequest().authenticated();

		// disable cache
		http.headers().cacheControl();
	}

	private ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry antMatchers(
			ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry) {

		List<RequestMatcher> requestMatchers = new ArrayList<>();

		// for non-auth-path
		requestMatchers.addAll(RequestMatcherUtil.requestMatchers(authPathConfig.getNonAuthPath()));

		// for login-path
		requestMatchers.addAll(RequestMatcherUtil.requestMatchers(authPathConfig.getLoginPath()));

		return ObjectUtils.isEmpty(requestMatchers) ? registry
				: registry.requestMatchers(requestMatchers.toArray(new RequestMatcher[0])).permitAll();

	}

}