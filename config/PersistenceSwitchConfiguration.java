//package za.co.wirecard.channel.backoffice.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//
//import javax.sql.DataSource;
//
//@Configuration
//@PropertySource({"classpath:application.yml"})
//public class PersistenceSwitchConfiguration {
//
//    @Value("${spring.datasource2.url}")
//    private String datasource2Url;
//
//    @Value("${spring.datasource2.username}")
//    private String datasource2Username;
//
//    @Value("${spring.datasource2.driver-class-name}")
//    private String datasource2DriverClassName;
//
//    @Bean
//    @ConfigurationProperties(prefix="spring.datasource2")
//    public DataSource switchDataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName(datasource2DriverClassName);
//        dataSource.setUrl(datasource2Url);
//        dataSource.setUsername(datasource2Username);
//        return dataSource;
//        // return DataSourceBuilder.create().build();
//    }
//
//}
