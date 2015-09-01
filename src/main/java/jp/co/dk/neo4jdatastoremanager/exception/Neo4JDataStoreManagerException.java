package jp.co.dk.neo4jdatastoremanager.exception;

import jp.co.dk.message.exception.AbstractMessageException;
import jp.co.dk.neo4jdatastoremanager.message.Neo4JDataStoreManagerMessage;

public class Neo4JDataStoreManagerException  extends AbstractMessageException {

	/**
	 * シリアルバージョンID
	 */
	private static final long serialVersionUID = 6187528686419084458L;
	
	/**
	 * コンストラクタ<p>
	 * 
	 * 指定のメッセージで例外を生成します。
	 * 
	 * @param msg メッセージ定数インスタンス
	 * @since 1.0
	 */
	public Neo4JDataStoreManagerException(Neo4JDataStoreManagerMessage msg){
		super(msg);
	}
	
	/**
	 * コンストラクタ<p>
	 * 
	 * 指定のメッセージで例外を生成します。
	 * 
	 * @param msg メッセージ定数インスタンス
	 * @param param 埋め込み文字
	 * @since 1.0
	 */
	public Neo4JDataStoreManagerException(Neo4JDataStoreManagerMessage msg, String param){
		super(msg, param);
	}
	
	/**
	 * コンストラクタ<p>
	 * 
	 * 指定のメッセージで例外を生成します。
	 * 
	 * @param msg メッセージ定数インスタンス
	 * @param param 埋め込み文字
	 * @since 1.0
	 */
	public Neo4JDataStoreManagerException(Neo4JDataStoreManagerMessage msg, String... param){
		super(msg, param);
	}
}
