package jp.co.dk.neo4jdatastoremanager.property;

import java.io.File;

import jp.co.dk.property.AbstractProperty;
import jp.co.dk.property.exception.PropertyException;

/**
 * Neo4Jデータストアに関するプロパティを定義するクラスです。
 * 
 * @version 0.1.0
 * @author D.Kanno
 */
public class Neo4JDataStoreManagerProperty extends AbstractProperty {
	
	/** NEO4Jサーバアドレス */
	public static final Neo4JDataStoreManagerProperty CRAWLER_NEO4J_SERVER   = new Neo4JDataStoreManagerProperty("crawler.neo4j.server");
	/** NEO4Jサーバユーザ名 */
	public static final Neo4JDataStoreManagerProperty CRAWLER_NEO4J_USER     = new Neo4JDataStoreManagerProperty("crawler.neo4j.user");
	/** NEO4Jサーバパスワード */
	public static final Neo4JDataStoreManagerProperty CRAWLER_NEO4J_PASSWORD = new Neo4JDataStoreManagerProperty("crawler.neo4j.pass");
	
	/**
	 * コンストラクタ<p>
	 * 指定されたプロパティキーをもとにプロパティ定数クラスを生成します。
	 * 
	 * @param key プロパティキー
	 */
	public Neo4JDataStoreManagerProperty (String key) throws PropertyException {
		super(new File("Neo4JDataStoreManager.properties"), key);
	}
}
