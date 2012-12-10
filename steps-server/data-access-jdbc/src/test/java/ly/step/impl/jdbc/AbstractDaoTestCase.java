package ly.step.impl.jdbc;

import java.io.IOException;
import java.nio.charset.Charset;

import javax.sql.DataSource;

import org.junit.BeforeClass;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.google.common.io.Resources;

public class AbstractDaoTestCase {

    private static DriverManagerDataSource driverManagerDataSource;

    @BeforeClass
    public static void setupDbConnection() throws ClassNotFoundException {
	if (driverManagerDataSource == null) {
	    Class.forName("com.mysql.jdbc.Driver");
	    driverManagerDataSource = new DriverManagerDataSource(
		    "jdbc:mysql://localhost/step-test?useUnicode=true&characterEncoding=utf8",
		    "step-test", "step-test");
	}
    }

    private JdbcTemplate jdbcTemplate;

    public AbstractDaoTestCase() {
	super();
    }

    protected void dbInit(String tableName) throws DataAccessException,
	    IOException {
	getJdbcTemplate().execute("drop table if exists " + tableName);
	getJdbcTemplate().execute(Resources.toString(
	        Resources.getResource("db-script/" + tableName + ".sql"),
	        Charset.forName("utf-8")));
    }

    protected final DataSource getDataSource() {
	return driverManagerDataSource;
    }

    protected final JdbcTemplate getJdbcTemplate() {
	if (this.jdbcTemplate == null) {
	    jdbcTemplate = new JdbcTemplate(driverManagerDataSource);
	}
	return jdbcTemplate;
    }

}