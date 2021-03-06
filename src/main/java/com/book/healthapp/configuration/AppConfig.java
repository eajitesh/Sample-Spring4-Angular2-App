package com.book.healthapp.configuration;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.book.healthapp.domain.User;
import com.book.healthapp.interceptors.SignupInterceptor;

@Configuration
@EntityScan("com.book.healthapp.domain")
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
public class AppConfig  { 
	
	@Value("${spring.datasource.driverClassName}") String driverClassName;
	@Value("${spring.datasource.url}") String url;
	@Value("${spring.datasource.username}") String username;
	@Value("${spring.datasource.password}") String password;
	
	@Bean(name = "dataSource")
	public DataSource getDataSource() {
	    DataSource dataSource = DataSourceBuilder
	            .create()
	            .username(username)
	            .password(password)
	            .url(url)
	            .driverClassName(driverClassName)
	            .build();
	    return dataSource;
	}
	
	@Bean(name = "sessionFactory")
	public SessionFactory getSessionFactory(DataSource dataSource) {
	    LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);
	    sessionBuilder.scanPackages("com.book.healthapp.domain");
	    return sessionBuilder.buildSessionFactory();
	}
	
	@Bean(name = "transactionManager")
	public HibernateTransactionManager getTransactionManager(
	        SessionFactory sessionFactory) {
	    HibernateTransactionManager transactionManager = new HibernateTransactionManager(
	            sessionFactory);
	    return transactionManager;
	}
	
//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//	    registry.addInterceptor(new SignupInterceptor()).addPathPatterns("/account/signup/process");
//	}
} 