package jp.co.dk.neo4jdatastoremanager;

import java.util.ArrayList;
import java.util.List;

import jp.co.dk.logger.Logger;
import jp.co.dk.logger.LoggerFactory;
import jp.co.dk.neo4jdatastoremanager.cypher.Cypher;
import jp.co.dk.neo4jdatastoremanager.exception.Neo4JDataStoreManagerCypherException;
import jp.co.dk.neo4jdatastoremanager.exception.Neo4JDataStoreManagerException;
import static jp.co.dk.neo4jdatastoremanager.message.Neo4JDataStoreManagerMessage.*;

/**
 * DataBaseDataStoreは、単一のデータベースのデータストアを表すクラスです。<p/>
 * 単一の接続先に対するトランザクション管理、Cypherの実行、実行されたCypherの履歴保持を行う。<br/>
 * 
 * @version 1.1
 * @author D.Kanno
 */
public class Neo4JDataStore {
	
	/** データベースアクセスパラメータ */
	protected Neo4JDataStoreParameter dataBaseAccessParameter;
	
	/** トランザクション */
	protected Transaction transaction;
	
	/** Cypherリスト */
	protected List<Cypher> cypherList = new ArrayList<Cypher>();
	
	/** 発生例外一覧 */
	protected List<Neo4JDataStoreManagerException> exceptionList = new ArrayList<>();
	
	/** ロガーインスタンス */
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * コンストラクタ<p/>
	 * 指定のデータベースアクセスパラメータを基にデータベースデータストアを生成します。
	 * 
	 * @param dataBaseAccessParameter データベースアクセスパラメータ
	 */
	public Neo4JDataStore(Neo4JDataStoreParameter dataBaseAccessParameter) {
		this.logger.constractor(this.getClass(), dataBaseAccessParameter);
		this.dataBaseAccessParameter = dataBaseAccessParameter;
	}
	
	public void startTransaction() throws Neo4JDataStoreManagerException {
		this.transaction = new Transaction(this.dataBaseAccessParameter);
	}
	
	public Node createNode() {
		return this.transaction.createNode();
	}
	
	public Node selectNode(Cypher cypher) throws Neo4JDataStoreManagerCypherException {
		return this.transaction.selectNode(cypher);
	}
	
	public List<Node> selectNodes(Cypher cypher) throws Neo4JDataStoreManagerCypherException {
		return this.transaction.selectNodes(cypher);
	}
	
	public void commit() throws Neo4JDataStoreManagerException {
		if (this.transaction == null) throw new Neo4JDataStoreManagerException(TRANSACTION_IS_NOT_START);
		this.transaction.commit();
	}

	public void rollback() throws Neo4JDataStoreManagerException {
		if (this.transaction == null) throw new Neo4JDataStoreManagerException(TRANSACTION_IS_NOT_START);
		this.transaction.rollback();
	}
	
	public void finishTransaction() throws Neo4JDataStoreManagerException {
		if (this.transaction == null) throw new Neo4JDataStoreManagerException(TRANSACTION_IS_NOT_START);
		this.transaction.close();
		this.transaction = null;
	}
	
	public boolean isTransaction() {
		if (this.transaction != null) return true;
		return false;
	}
	
	public boolean hasError() {
		if (this.exceptionList.size() != 0) return true;
		return false;
	}
	
	@Override
	public int hashCode() {
		int hashcode = this.dataBaseAccessParameter.hashCode();
		return hashcode;
	}
	
	@Override
	public boolean equals(Object object) {
		if (object == null) return false;
		if (!(object instanceof Neo4JDataStore)) return false;
		Neo4JDataStore thisClassObj = (Neo4JDataStore) object;
		if (this.transaction == null && thisClassObj.transaction == null) {
			if(thisClassObj.dataBaseAccessParameter.hashCode() == this.dataBaseAccessParameter .hashCode()) return true;
		} else if (this.transaction == null && thisClassObj.transaction != null) {
			return false;
		} else if (this.transaction != null && thisClassObj.transaction == null) {
			return false;
		} else {
			if(thisClassObj.transaction.hashCode() == this.transaction.hashCode()) return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		if (this.transaction == null) {
			StringBuilder sb = new StringBuilder();
			sb.append("CONNECTION_HASH=[Transaction has not been started]").append(',');
			sb.append(this.dataBaseAccessParameter.toString());
			return sb.toString();
		} else {
			return this.transaction.toString();
		}
		
	}
}
