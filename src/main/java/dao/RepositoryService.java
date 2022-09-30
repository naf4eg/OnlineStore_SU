package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import model.Product;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

@Slf4j
public class RepositoryService {

    private static final DataSource dataSource = createDataSource();

    public static Optional<List<Product>> findProducts() {
        log.info("===>> find products from db");
        List<Product> products = new ArrayList<>();
        try (var connection = dataSource.getConnection()) {
            try (var statement = connection.createStatement()) {
                try (var resultSet = statement.executeQuery("SELECT ID, NAME, DESC FROM PRODUCTS")) {
                    while (resultSet.next()) {
                        products.add(createProductByResultSet(resultSet));
                    }
                }
            }
        } catch (SQLException exc) {
            exc.printStackTrace();
        }

        return products.isEmpty() ? Optional.empty() : Optional.of(products);
    }

    private static Product createProductByResultSet(ResultSet resultSet) throws SQLException {
        var id = resultSet.getString("ID");
        var name = resultSet.getString("NAME");
        var desc = resultSet.getString("DESC");
        return new Product(id, name, desc);
    }

    private static DataSource createDataSource() {
        PoolProperties p = new PoolProperties();
        p.setUrl("jdbc:h2:~/test");
        p.setDriverClassName("org.h2.Driver");
        p.setUsername("sa");
        p.setPassword("");
        p.setValidationQuery("SELECT 1");
        p.setTestOnReturn(false);
        p.setValidationInterval(30000);
        p.setTimeBetweenEvictionRunsMillis(30000);
        p.setMaxActive(100);
        p.setInitialSize(10);
        p.setMaxWait(10000);
        p.setRemoveAbandonedTimeout(60);
        p.setMinEvictableIdleTimeMillis(30000);
        p.setMinIdle(10);
        p.setLogAbandoned(true);
        p.setRemoveAbandoned(true);
        p.setJdbcInterceptors(
                "org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;" +
                        "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");

        DataSource datasource = new DataSource();
        datasource.setPoolProperties(p);
        return datasource;
    }
}
