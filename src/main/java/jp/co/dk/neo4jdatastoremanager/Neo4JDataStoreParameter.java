package jp.co.dk.neo4jdatastoremanager;

import jp.co.dk.neo4jdatastoremanager.exception.Neo4JDataStoreManagerException;
import static jp.co.dk.neo4jdatastoremanager.message.Neo4JDataStoreManagerMessage.*;

public class Neo4JDataStoreParameter {
	
	/** NEO4Jサーバアドレス */
	protected String neo4jServer;
	
	/** NEO4Jサーバユーザ名 */
	protected String neo4jUser;
	
	/** NEO4Jサーバパスワード */
	protected String neo4jPassword;
	
	/** 認証情報が設定有無 */
	protected boolean isAuthSet = false;
	
	public Neo4JDataStoreParameter(String neo4jServer) throws Neo4JDataStoreManagerException {
		if (neo4jServer == null || neo4jServer.equals("")) throw new Neo4JDataStoreManagerException(NEO4JSERVER_IS_NOT_SET);
		this.neo4jServer = neo4jServer;
		this.isAuthSet = false;
	}
	
	public Neo4JDataStoreParameter(String neo4jServer, String neo4jUser, String neo4jPass) throws Neo4JDataStoreManagerException {
		if (neo4jServer == null || neo4jServer.equals("")) throw new Neo4JDataStoreManagerException(NEO4JSERVER_IS_NOT_SET);
		if (neo4jUser   == null || neo4jUser.equals(""))   throw new Neo4JDataStoreManagerException(NEO4JUSERNAME_IS_NOT_SET);
		if (neo4jPass   == null || neo4jPass.equals(""))   throw new Neo4JDataStoreManagerException(NEO4JPASSWORD_IS_NOT_SET);
		this.neo4jServer = neo4jServer;
		this.neo4jUser   = neo4jUser;
		this.neo4jPassword   = neo4jPass;
		this.isAuthSet = true;
	}

	public String getNeo4jServer() {
		return neo4jServer;
	}

	public String getNeo4jUser() {
		return neo4jUser;
	}

	public String getNeo4jPassword() {
		return neo4jPassword;
	}

	public boolean isAuthSet() {
		return isAuthSet;
	}
	
	public Neo4JDataStore createDataStore() {
		return new Neo4JDataStore(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (isAuthSet ? 1231 : 1237);
		result = prime * result
				+ ((neo4jPassword == null) ? 0 : neo4jPassword.hashCode());
		result = prime * result
				+ ((neo4jServer == null) ? 0 : neo4jServer.hashCode());
		result = prime * result
				+ ((neo4jUser == null) ? 0 : neo4jUser.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Neo4JDataStoreParameter other = (Neo4JDataStoreParameter) obj;
		if (isAuthSet != other.isAuthSet)
			return false;
		if (neo4jPassword == null) {
			if (other.neo4jPassword != null)
				return false;
		} else if (!neo4jPassword.equals(other.neo4jPassword))
			return false;
		if (neo4jServer == null) {
			if (other.neo4jServer != null)
				return false;
		} else if (!neo4jServer.equals(other.neo4jServer))
			return false;
		if (neo4jUser == null) {
			if (other.neo4jUser != null)
				return false;
		} else if (!neo4jUser.equals(other.neo4jUser))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Neo4JDataStoreParameter [neo4jServer=" + neo4jServer
				+ ", neo4jUser=" + neo4jUser + ", neo4jPassword="
				+ neo4jPassword + ", isAuthSet=" + isAuthSet + "]";
	}
	
}
