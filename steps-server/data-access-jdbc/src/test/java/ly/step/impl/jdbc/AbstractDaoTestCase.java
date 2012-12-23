package ly.step.impl.jdbc;

import java.io.IOException;
import java.nio.charset.Charset;

import javax.sql.DataSource;

import org.junit.BeforeClass;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.support.TransactionTemplate;

import com.google.common.io.Resources;

public class AbstractDaoTestCase {

    private static DriverManagerDataSource driverManagerDataSource;
    private static DataSourceTransactionManager dataSourceTransactionManager;

    private static TransactionTemplate transactionTemplate;

    protected static final DataSource getDataSource() {
	return driverManagerDataSource;
    }

    protected static final TransactionTemplate getTransactionTemplate() {
	return transactionTemplate;
    }

    @BeforeClass
    public static void setupDbConnection() throws ClassNotFoundException {
	if (driverManagerDataSource == null) {
	    Class.forName("com.mysql.jdbc.Driver");
	    driverManagerDataSource = new DriverManagerDataSource(
		    "jdbc:mysql://localhost/step-test?useUnicode=true&characterEncoding=utf8",
		    "step-test", "step-test");
	}

	dataSourceTransactionManager = new DataSourceTransactionManager(
	        driverManagerDataSource);

	transactionTemplate = new TransactionTemplate(
	        dataSourceTransactionManager);
    }

    private JdbcTemplate jdbcTemplate;

    public AbstractDaoTestCase() {
	super();
    }

    protected void dbInit(String tableName) throws DataAccessException,
	    IOException {
	getJdbcTemplate().execute("drop table if exists " + tableName);
	getJdbcTemplate().execute(
	        Resources.toString(
	                Resources.getResource("db-script/"
	                        + tableName.toLowerCase() + ".sql"),
	                Charset.forName("utf-8")));
    }

    protected final JdbcTemplate getJdbcTemplate() {
	if (this.jdbcTemplate == null) {
	    jdbcTemplate = new JdbcTemplate(driverManagerDataSource);
	}
	return jdbcTemplate;
    }

}