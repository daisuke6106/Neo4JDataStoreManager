package jp.co.dk.neo4jdatastoremanager.exception;

import jp.co.dk.message.exception.AbstractMessageException;
import jp.co.dk.neo4jdatastoremanager.message.Neo4JDataStoreManagerMessage;

public class Neo4JDataStoreManagerCypherException  extends AbstractMessageException {

	/**
	 * シリアルバージョンID
	 */
	private static final long serialVersionUID = -1244653001584143393L;

	/**
	 * コンストラクタ<p>
	 * 
	 * 指定のメッセージで例外を生成します。
	 * 
	 * @param msg メッセージ定数インスタンス
	 * @since 1.0
	 */
	public Neo4JDataStoreManagerCypherException(Neo4JDataStoreManagerMessage msg){
		super(msg);
	}
}
