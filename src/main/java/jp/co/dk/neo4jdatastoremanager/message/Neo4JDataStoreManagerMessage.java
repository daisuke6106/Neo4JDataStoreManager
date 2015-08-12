package jp.co.dk.neo4jdatastoremanager.message;

import jp.co.dk.message.AbstractMessage;

/**
 * Neo4JDataStoreManagerMessageは、データストアの操作にて使用するメッセージを定義する定数クラスです。
 * 
 * @version 0.1.0
 * @author D.Kanno
 */
public class Neo4JDataStoreManagerMessage extends AbstractMessage{
	
	/** データストアが生成できませんでした。プロパティが設定されていません。 */
	public static final Neo4JDataStoreManagerMessage PROPERTY_IS_NOT_SET = new Neo4JDataStoreManagerMessage("E001");
	
	protected Neo4JDataStoreManagerMessage(String messageId) {
		super(messageId);
	}

}
