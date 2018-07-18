package org.superbiz.moviefun.movies;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.engine.jdbc.connections.internal.DatasourceConnectionProviderImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionOperations;
import org.springframework.transaction.support.TransactionTemplate;
import org.superbiz.moviefun.DatabaseServiceCredentials;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
public class MoviesConfig {

    @Bean
    public DataSource moviesDataSource(DatabaseServiceCredentials serviceCredentials) {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setURL(serviceCredentials.jdbcUrl("movies-mysql", "p-mysql"));
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setDataSource(dataSource);
        return hikariDataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean moviesEntitiyManagerFactory(DataSource moviesDataSource, HibernateJpaVendorAdapter hibernateJpaVendorAdapter){
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean= new  LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(moviesDataSource);
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(hibernateJpaVendorAdapter);
        localContainerEntityManagerFactoryBean.setPackagesToScan("org.superbiz.moviefun.movies");
        localContainerEntityManagerFactoryBean.setPersistenceUnitName("movies");
        return localContainerEntityManagerFactoryBean;

    }



    @Bean
    public PlatformTransactionManager moviesPlatformTransactionManager(EntityManagerFactory moviesEntitiyManagerFactory){
        return new JpaTransactionManager(moviesEntitiyManagerFactory);
    }

    @Bean
    public TransactionOperations moviesTransactionOperations(PlatformTransactionManager moviesPlatformTransactionManager){
        return new TransactionTemplate(moviesPlatformTransactionManager);
    }

}
