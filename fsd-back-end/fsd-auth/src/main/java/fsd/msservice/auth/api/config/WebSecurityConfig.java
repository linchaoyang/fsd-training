package fsd.msservice.auth.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import fsd.msservice.auth.filter.JsonUsernamePasswordAuthenticationFilter;
import fsd.msservice.auth.filter.JwtAuthenticationTokenFilter;
import fsd.msservice.auth.filter.handler.EntryPointUnauthorizedHandler;
import fsd.msservice.auth.filter.provider.BuyerAuthenticationProvider;
import fsd.msservice.auth.filter.provider.SellerAuthenticationProvider;

public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String TOKEN_BASED_ENTRY_POINT = "/api/limit/**";
    public static final String AUTH_ENTRY_POINT = "/api/auth";
    public static final String LOGIN_ENTRY_POINT = "/api/login";

    /**
     * 未登录或认证不成功处理器
     */
    @Autowired
    private EntryPointUnauthorizedHandler unauthorizedHandler;

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

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    protected AuthenticationManager authenticationManager;

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
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(daoBuyerAuthenticationProvider());
        auth.authenticationProvider(daoSellerAuthenticationProvider());
    }

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.buyerDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        JsonUsernamePasswordAuthenticationFilter jsonUsernamePasswordAuthenticationFilter = new JsonUsernamePasswordAuthenticationFilter();
        jsonUsernamePasswordAuthenticationFilter.setAuthenticationManager(authenticationManager);
        jsonUsernamePasswordAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        jsonUsernamePasswordAuthenticationFilter.setAuthenticationFailureHandler(authenctiationFailureHandler);

        JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter = new JwtAuthenticationTokenFilter();

        http
                // Close cors, allow all request access
                .cors().and()
                // Since use JWT, csrf is disabled
                .csrf().disable()
                // Since use token, don't create session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // allow all static resource access
                .antMatchers(HttpMethod.GET, "/", "/error", "/*.html", "/favicon.ico", "/**/*.html", "/**/*.css",
                        "/**/*.js")
                .permitAll()
                // allow access for rest api who get token
                .antMatchers(AUTH_ENTRY_POINT, LOGIN_ENTRY_POINT).permitAll()
                // any other requests must authorize
                .anyRequest().authenticated().and()
                //
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(jsonUsernamePasswordAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
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
}