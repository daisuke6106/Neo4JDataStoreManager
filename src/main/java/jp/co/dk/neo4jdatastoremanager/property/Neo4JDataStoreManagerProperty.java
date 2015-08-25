package jp.co.dk.neo4jdatastoremanager.property;

import java.io.File;

import jp.co.dk.neo4jdatastoremanager.exception.Neo4JDataStoreManagerException;
import jp.co.dk.property.AbstractProperty;
import jp.co.dk.property.exception.PropertyException;

/**
 * クローラに関するプロパティを定義するクラスです。
 * 
 * @version 1.0
 * @author D.Kanno
 */
public class Neo4JDataStoreManagerProperty extends AbstractProperty {
	
	public static final Neo4JDataStoreManagerProperty NEO4J_SERVER   = new Neo4JDataStoreManagerProperty("neo4j.server");
	
	public static final Neo4JDataStoreManagerProperty NEO4J_USER     = new Neo4JDataStoreManagerProperty("neo4j.user");
	
	public static final Neo4JDataStoreManagerProperty NEO4J_PASSWORD = new Neo4JDataStoreManagerProperty("neo4j.pass");
	
	/**
	 * コンストラクタ<p>
	 * 
	 * 指定されたプロパティキーをもとにプロパティ定数クラスを生成します。
	 * 
	 * @param key プロパティキー
	 */
	protected Neo4JDataStoreManagerProperty (String key) throws PropertyException {
		super(new File("Neo4JDataStoreManagerProperties.properties"), key);
	}
	
	public Neo4JDataStoreManagerParameter getParameter() throws Neo4JDataStoreManagerException {
		String crawlerNeo4jServer = NEO4J_SERVER.getString();
		String crawlerNeo4jUser   = NEO4J_USER.getString();
		String crawlerNeo4jPass   = NEO4J_PASSWORD.getString();
		if ((crawlerNeo4jUser == null || crawlerNeo4jUser.equals("")) && (crawlerNeo4jPass == null || crawlerNeo4jPass.equals("")) ) {
			return new Neo4JDataStoreManagerParameter(crawlerNeo4jServer);
		} else {
			return new Neo4JDataStoreManagerParameter(crawlerNeo4jServer, crawlerNeo4jUser, crawlerNeo4jPass);
		}
	}
}
