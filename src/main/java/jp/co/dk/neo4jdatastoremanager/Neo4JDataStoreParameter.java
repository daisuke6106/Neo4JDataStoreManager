package jp.co.dk.neo4jdatastoremanager;

import jp.co.dk.neo4jdatastoremanager.exception.Neo4JDataStoreManagerException;
import static jp.co.dk.neo4jdatastoremanager.message.Neo4JDataStoreManagerMessage.*;

public class Neo4JDataStoreParameter {
	
	/** NEO4Jサーバアドレス */
	protected String crawlerNeo4jServer;
	
	/** NEO4Jサーバユーザ名 */
	protected String crawlerNeo4jUser;
	
	/** NEO4Jサーバパスワード */
	protected String crawlerNeo4jPass;
	
	/** 認証情報が設定有無 */
	protected boolean isAuthSet = false;
	
	public Neo4JDataStoreParameter(String crawlerNeo4jServer) throws Neo4JDataStoreManagerException {
		if (crawlerNeo4jServer == null || crawlerNeo4jServer.equals("")) throw new Neo4JDataStoreManagerException(NEO4JSERVER_IS_NOT_SET);
		this.crawlerNeo4jServer = crawlerNeo4jServer;
		this.isAuthSet = false;
	}
	
	public Neo4JDataStoreParameter(String crawlerNeo4jServer, String crawlerNeo4jUser, String crawlerNeo4jPass) throws Neo4JDataStoreManagerException {
		if (crawlerNeo4jServer == null || crawlerNeo4jServer.equals("")) throw new Neo4JDataStoreManagerException(NEO4JSERVER_IS_NOT_SET);
		if (crawlerNeo4jUser   == null || crawlerNeo4jUser.equals(""))   throw new Neo4JDataStoreManagerException(NEO4JUSERNAME_IS_NOT_SET);
		if (crawlerNeo4jPass   == null || crawlerNeo4jPass.equals(""))   throw new Neo4JDataStoreManagerException(NEO4JPASSWORD_IS_NOT_SET);
		this.crawlerNeo4jServer = crawlerNeo4jServer;
		this.crawlerNeo4jUser   = crawlerNeo4jUser;
		this.crawlerNeo4jPass   = crawlerNeo4jPass;
		this.isAuthSet = true;
	}

	public String getCrawlerNeo4jServer() {
		return crawlerNeo4jServer;
	}

	public String getCrawlerNeo4jUser() {
		return crawlerNeo4jUser;
	}

	public String getCrawlerNeo4jPass() {
		return crawlerNeo4jPass;
	}

	public boolean isAuthSet() {
		return isAuthSet;
	}
	
	public Neo4JDataStore createDataStore() {
		return new Neo4JDataStore(this);
	}
}
