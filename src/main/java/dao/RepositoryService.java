package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import model.Product;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

/**
 * Repository service
 */
@Slf4j
public class RepositoryService {

    private static final DataSource dataSource = createDataSourceWithPoolConnect();

    public static Optional<List<Product>> findProducts() {
        log.info("в коннекшене");
        Connection con = null;
        List<Product> products = new ArrayList<>();
        try {
            con = dataSource.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from PRODUCTS");
            while (rs.next()) {
                String id = rs.getString("ID");
                String name = rs.getString("NAME");
                String desc = rs.getString("DESC");
                products.add(new Product(id, name, desc));
            }
            rs.close();
            st.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (con != null) try {
                con.close();
            } catch (Exception ignore) {
            }
        }

        if (products.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(products);
        }
    }

    private static DataSource createDataSourceWithPoolConnect() {
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
