package jp.co.dk.neo4jdatastoremanager.property;

import static jp.co.dk.crawler.gdb.neo4j.message.CrawlerNeo4JMessage.*;
import jp.co.dk.crawler.gdb.neo4j.exception.CrawlerNeo4JException;

public class CrawlerNeo4JParameter {
	
	/** NEO4Jサーバアドレス */
	protected String crawlerNeo4jServer;
	
	/** NEO4Jサーバユーザ名 */
	protected String crawlerNeo4jUser;
	
	/** NEO4Jサーバパスワード */
	protected String crawlerNeo4jPass;
	
	/** 認証情報が設定有無 */
	protected boolean isAuthSet = false;
	
	public CrawlerNeo4JParameter(String crawlerNeo4jServer) throws Neo4JDataStoreManagerException {
		if (crawlerNeo4jServer == null || crawlerNeo4jServer.equals("")) throw new Neo4JDataStoreManagerException(NEO4JSERVER_IS_NOT_SET);
		this.crawlerNeo4jServer = crawlerNeo4jServer;
		this.isAuthSet = false;
	}
	
	public CrawlerNeo4JParameter(String crawlerNeo4jServer, String crawlerNeo4jUser, String crawlerNeo4jPass) throws Neo4JDataStoreManagerException {
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
	
}
