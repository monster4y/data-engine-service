package cn.com.data.engine.connection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

public class DBConnectionManager {

	private static DBConnectionManager instance = new DBConnectionManager();

	private HashMap<String, ConnectionProvider> providers = new HashMap<String, ConnectionProvider>();

	private DBConnectionManager() {
	}

	public void addConnectionProvider(String dataSourceName, ConnectionProvider provider) {
		this.providers.put(dataSourceName, provider);
	}

	public Connection getConnection(String dsName) throws SQLException {
		ConnectionProvider provider = providers.get(dsName);
		if (provider == null) {
			throw new SQLException("There is no DataSource named '" + dsName + "'");
		}

		return provider.getConnection();
	}

	public static DBConnectionManager getInstance() {
		return instance;
	}

	public void shutdown(String dsName) throws SQLException {

		ConnectionProvider provider = (ConnectionProvider) providers.get(dsName);
		if (provider == null) {
			throw new SQLException("There is no DataSource named '" + dsName + "'");
		}

		provider.shutdown();

	}

}
