package jp.co.dk.neo4jdatastoremanager.message;

import java.io.Serializable;

import jp.co.dk.message.AbstractMessage;

/**
 * Neo4JDataStoreManagerMessageは、データストアの操作にて使用するメッセージを定義する定数クラスです。
 * 
 * @version 1.0
 * @author D.Kanno
 */
public class Neo4JDataStoreManagerMessage extends AbstractMessage implements Serializable{
	
	/** シリアルバージョンID */
	private static final long serialVersionUID = -9157146750413541129L;
	
	/** Neo4J接続プロパティが設定されていません。 */
	public static final Neo4JDataStoreManagerMessage NEO4JPROPERTY_IS_NOT_SET = new Neo4JDataStoreManagerMessage("E001");
	
	/** Neo4J接続パラメータが設定されていません。 */
	public static final Neo4JDataStoreManagerMessage NEO4JPARAMETER_IS_NOT_SET = new Neo4JDataStoreManagerMessage("E002");
	
	/** Neo4J接続先サーバが設定されていません。 */
	public static final Neo4JDataStoreManagerMessage NEO4JSERVER_IS_NOT_SET = new Neo4JDataStoreManagerMessage("E003");
	
	/** Neo4Jユーザ名が設定されていません。 */
	public static final Neo4JDataStoreManagerMessage NEO4JUSERNAME_IS_NOT_SET = new Neo4JDataStoreManagerMessage("E004");
	
	/** Neo4Jパスワードが設定されていません。 */
	public static final Neo4JDataStoreManagerMessage NEO4JPASSWORD_IS_NOT_SET = new Neo4JDataStoreManagerMessage("E005");
	
	/** Cypherが設定されていません。 */
	public static final Neo4JDataStoreManagerMessage CYPHER_IS_NOT_SET = new Neo4JDataStoreManagerMessage("E006");

	/** トランザクションが開始されていません。 */
	public static final Neo4JDataStoreManagerMessage TRANSACTION_IS_NOT_START = new Neo4JDataStoreManagerMessage("E007");
	
	/** パラメータが不正です。KEY=[{0}],PARAMETER=[{1}] */
	public static final Neo4JDataStoreManagerMessage PARAMETER_IS_FRAUD = new Neo4JDataStoreManagerMessage("E008");
	
	/** パラメータの読込に失敗しました。KEY=[{0}] */
	public static final Neo4JDataStoreManagerMessage PARAMETER_FAILED_TO_READ = new Neo4JDataStoreManagerMessage("E008");
	
	protected Neo4JDataStoreManagerMessage(String messageId) {
		super(messageId);
	}

}
