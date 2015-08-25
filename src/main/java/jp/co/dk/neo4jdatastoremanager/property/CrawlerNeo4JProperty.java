package jp.co.dk.neo4jdatastoremanager.property;

import java.io.File;

import jp.co.dk.crawler.gdb.neo4j.exception.CrawlerNeo4JException;
import jp.co.dk.property.AbstractProperty;
import jp.co.dk.property.exception.PropertyException;

/**
 * クローラに関するプロパティを定義するクラスです。
 * 
 * @version 1.0
 * @author D.Kanno
 */
public class CrawlerNeo4JProperty extends AbstractProperty {
	
	public static final CrawlerNeo4JProperty CRAWLER_NEO4J_SERVER   = new CrawlerNeo4JProperty("crawler.neo4j.server");
	
	public static final CrawlerNeo4JProperty CRAWLER_NEO4J_USER     = new CrawlerNeo4JProperty("crawler.neo4j.user");
	
	public static final CrawlerNeo4JProperty CRAWLER_NEO4J_PASSWORD = new CrawlerNeo4JProperty("crawler.neo4j.pass");
	
	/**
	 * コンストラクタ<p>
	 * 
	 * 指定されたプロパティキーをもとにプロパティ定数クラスを生成します。
	 * 
	 * @param key プロパティキー
	 */
	protected CrawlerNeo4JProperty (String key) throws PropertyException {
		super(new File("CrawlerNeo4JProperty.properties"), key);
	}
	
	public CrawlerNeo4JParameter getParameter() throws Neo4JDataStoreManagerException {
		String crawlerNeo4jServer = CRAWLER_NEO4J_SERVER.getString();
		String crawlerNeo4jUser   = CRAWLER_NEO4J_USER.getString();
		String crawlerNeo4jPass   = CRAWLER_NEO4J_PASSWORD.getString();
		if ((crawlerNeo4jUser == null || crawlerNeo4jUser.equals("")) && (crawlerNeo4jPass == null || crawlerNeo4jPass.equals("")) ) {
			return new CrawlerNeo4JParameter(crawlerNeo4jServer);
		} else {
			return new CrawlerNeo4JParameter(crawlerNeo4jServer, crawlerNeo4jUser, crawlerNeo4jPass);
		}
	}
}
