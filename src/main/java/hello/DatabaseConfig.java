package hello;

import com.zaxxer.hikari.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {
	@Value("${spring.datasource.hikari.connectionTimeout}")
	private String timeout;
	
	@Value("${spring.datasource.username}")
	private String username;
	
	@Value("${spring.datasource.password}")
	private String password;

  @Bean
  public DataSource dataSource() throws URISyntaxException {
	  URI dbUri = new URI("postgres://ovdlcgumsqbven:55e4778bdfdeca7e78a7aa228006e08a9567e9a7af8ef3845016bc7c0371daa0@ec2-174-129-229-106.compute-1.amazonaws.com:5432/dbh0blq61mh5m2");
      HikariConfig config = new HikariConfig();
      String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() + "?sslmode=require";
      config.setJdbcUrl(dbUrl);
      config.setDriverClassName("org.postgresql.Driver");
      config.setConnectionTimeout(Long.parseLong(timeout));
      config.setUsername(username);
      config.setPassword(password);
      return new HikariDataSource(config);
  }
}