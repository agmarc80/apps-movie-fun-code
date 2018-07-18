package org.superbiz.moviefun.albums;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionOperations;
import org.springframework.transaction.support.TransactionTemplate;
import org.superbiz.moviefun.DatabaseServiceCredentials;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
public class AlbumsConfig {

    @Bean
    public DataSource albumsDataSource(DatabaseServiceCredentials serviceCredentials) {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setURL(serviceCredentials.jdbcUrl("albums-mysql", "p-mysql"));
        HikariDataSource hikaridatasource =new HikariDataSource();
        hikaridatasource.setDataSource(dataSource);
        return hikaridatasource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean albumsEntitiyManagerFactory(DataSource albumsDataSource, HibernateJpaVendorAdapter hibernateJpaVendorAdapter){
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean= new  LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(albumsDataSource);
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(hibernateJpaVendorAdapter);
        localContainerEntityManagerFactoryBean.setPackagesToScan("org.superbiz.moviefun.albums");
        localContainerEntityManagerFactoryBean.setPersistenceUnitName("albums");
        return localContainerEntityManagerFactoryBean;

    }

    @Bean
    public PlatformTransactionManager albumsPlatformTransactionManager(EntityManagerFactory albumsEntitiyManagerFactory){
        return new JpaTransactionManager(albumsEntitiyManagerFactory);
    }

    @Bean
    public TransactionOperations albumsTransactionOperations(PlatformTransactionManager albumsPlatformTransactionManager){
        return new TransactionTemplate(albumsPlatformTransactionManager);
    }
}
