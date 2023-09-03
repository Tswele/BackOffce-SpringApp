//package za.co.wirecard.channel.backoffice.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//
//import javax.sql.DataSource;
//
//@Configuration
//@PropertySource({"classpath:application.yml"})
//public class PersistenceTransactionConfiguration {
//
//    @Value("${spring.datasource.url}")
//    private String datasourceUrl;
//
//    @Value("${spring.datasource.username}")
//    private String datasourceUsername;
//
//    @Value("${spring.datasource.driver-class-name}")
//    private String datasourceDriverClassName;
//
//    @Primary
//    @Bean
//    @ConfigurationProperties(prefix="spring.datasource")
//    public DataSource transactionDataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName(datasourceDriverClassName);
//        dataSource.setUrl(datasourceUrl);
//        dataSource.setUsername(datasourceUsername);
//        return dataSource;
//        // return DataSourceBuilder.create().build();
//    }
//
//}
