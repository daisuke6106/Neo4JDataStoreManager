package jp.co.dk.neo4jdatastoremanager.exception;

import jp.co.dk.message.exception.AbstractMessageFatalException;
import jp.co.dk.neo4jdatastoremanager.message.Neo4JDataStoreManagerMessage;

public class Neo4JDataStoreManagerFatalException  extends AbstractMessageFatalException {

	/**
	 * シリアルバージョンID
	 */
	private static final long serialVersionUID = 2453758320619753992L;

	/**
	 * コンストラクタ<p>
	 * 
	 * 指定のメッセージで例外を生成します。
	 * 
	 * @param msg メッセージ定数インスタンス
	 * @since 1.0
	 */
	public Neo4JDataStoreManagerFatalException(Neo4JDataStoreManagerMessage msg){
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
	public Neo4JDataStoreManagerFatalException(Neo4JDataStoreManagerMessage msg, String param){
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
	public Neo4JDataStoreManagerFatalException(Neo4JDataStoreManagerMessage msg, String... param){
		super(msg, param);
	}
}
