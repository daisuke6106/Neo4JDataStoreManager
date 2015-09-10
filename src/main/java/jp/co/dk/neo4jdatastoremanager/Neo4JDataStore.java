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
 * <p>DataBaseDataStoreは、単一のデータベースのデータストアを表すクラスです。</p>
 * 単一の接続先に対するトランザクション管理、Cypherの実行、実行されたCypherの履歴保持を行う。
 * 
 * @version 0.1
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
	 * <p>指定のデータベースアクセスパラメータを基にデータベースデータストアを生成します。</p>
	 * 
	 * @param dataBaseAccessParameter データベースアクセスパラメータ
	 */
	public Neo4JDataStore(Neo4JDataStoreParameter dataBaseAccessParameter) {
		this.logger.constractor(this.getClass(), dataBaseAccessParameter);
		this.dataBaseAccessParameter = dataBaseAccessParameter;
	}
	
	/**
	 * <p>トランザクションを開始する。</p>
	 * 本オブジェクトに設定されたパラメータを基にデータベースに接続してトランザクションを開始します。
	 * @throws Neo4JDataStoreManagerException トランザクション開始に失敗した場合
	 */
	public void startTransaction() throws Neo4JDataStoreManagerException {
		this.transaction = new Transaction(this.dataBaseAccessParameter);
	}
	
	/**
	 * <p>ノードを作成する。</p>
	 * このデータストアに対してノードを作成し、返却します。
	 * @return 作成したノード
	 */
	public Node createNode() {
		return this.transaction.createNode();
	}
	
	public String selectString(Cypher cypher) throws Neo4JDataStoreManagerCypherException {
		return this.transaction.selectString(cypher);
	}
	
	public List<String> selectStringList(Cypher cypher) throws Neo4JDataStoreManagerCypherException {
		return this.transaction.selectStringList(cypher);
	}
	
	public Integer selectInt(Cypher cypher) throws Neo4JDataStoreManagerCypherException {
		return this.transaction.selectInt(cypher);
	}
	
	public List<Integer> selectIntList(Cypher cypher) throws Neo4JDataStoreManagerCypherException {
		return this.transaction.selectIntList(cypher);
	}
	
	public Boolean selectBoolean(Cypher cypher) throws Neo4JDataStoreManagerCypherException {
		return this.transaction.selectBoolean(cypher);
	}
	
	public List<Boolean> selectBooleanList(Cypher cypher) throws Neo4JDataStoreManagerCypherException {
		return this.transaction.selectBooleanList(cypher);
	}
	
	/**
	 * <p>検索結果を取得する。（単一）</p>
	 * 指定のCypherを実行し、単一のノードを取得します。
	 * 
	 * @param cypher 実行対象のCypher
	 * @return 取得したノード
	 * @throws Neo4JDataStoreManagerCypherException Cypherの実行に失敗した場合
	 */
	public Node selectNode(Cypher cypher) throws Neo4JDataStoreManagerCypherException {
		return this.transaction.selectNode(cypher);
	}
	
	/**
	 * <p>検索結果を取得する。（複数）</p>
	 * 指定のCypherを実行し、複数のノードを取得します。
	 * 
	 * @param cypher 実行対象のCypher
	 * @return 取得したノード
	 * @throws Neo4JDataStoreManagerCypherException Cypherの実行に失敗した場合
	 */
	public List<Node> selectNodeList(Cypher cypher) throws Neo4JDataStoreManagerCypherException {
		return this.transaction.selectNodeList(cypher);
	}
	
	/**
	 * <p>コミットを実施する。</p>
	 * 現在開始済みのトランザクションに対して、コミット処理を実施します。
	 * トランザクションが開始されていない状態で実行された場合、例外を送出します。
	 * @throws Neo4JDataStoreManagerException トランザクションが開始されていない状態で実行された場合
	 */
	public void commit() throws Neo4JDataStoreManagerException {
		if (this.transaction == null) throw new Neo4JDataStoreManagerException(TRANSACTION_IS_NOT_START);
		this.transaction.commit();
	}
	
	/**
	 * <p>ロールバックを実施する。</p>
	 * 現在開始済みのトランザクションに対して、ロールバック処理を実施します。
	 * トランザクションが開始されていない状態で実行された場合、例外を送出します。
	 * @throws Neo4JDataStoreManagerException トランザクションが開始されていない状態で実行された場合
	 */
	public void rollback() throws Neo4JDataStoreManagerException {
		if (this.transaction == null) throw new Neo4JDataStoreManagerException(TRANSACTION_IS_NOT_START);
		this.transaction.rollback();
	}
	
	/**
	 * <p>トランザクションのクローズ処理を実施する。</p>
	 * 現在開始済みのトランザクションに対して、クローズ処理を実施します。
	 * トランザクションが開始されていない状態で実行された場合、例外を送出します。
	 * @throws Neo4JDataStoreManagerException トランザクションが開始されていない状態で実行された場合
	 */
	public void finishTransaction() throws Neo4JDataStoreManagerException {
		if (this.transaction == null) throw new Neo4JDataStoreManagerException(TRANSACTION_IS_NOT_START);
		this.transaction.close();
		this.transaction = null;
	}
	
	/**
	 * <p>トランザクションが開始されているかを判定します。</p>
	 * トランザクションが開始済みである場合、true、開始されていない場合はfalseを返却します
	 * @return トランザクションが開始済みである場合、true、開始されていない場合はfalse
	 */
	public boolean isTransaction() {
		if (this.transaction != null) return true;
		return false;
	}
	
	/**
	 * <p>データベース操作時に、異常が発生しているか否かを判定。</p>
	 * データベース操作時に例外が発生していた場合はtrue、発生していなかった場合は、falseを返却します。
	 * @return データベース操作時に例外が発生していた場合はtrue、発生していなかった場合は、false
	 */
	public boolean hasError() {
		if (this.exceptionList.size() != 0) return true;
		return false;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((dataBaseAccessParameter == null) ? 0
						: dataBaseAccessParameter.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Neo4JDataStore other = (Neo4JDataStore) obj;
		if (dataBaseAccessParameter == null) {
			if (other.dataBaseAccessParameter != null)
				return false;
		} else if (!dataBaseAccessParameter
				.equals(other.dataBaseAccessParameter))
			return false;
		return true;
	}

	@Override
	public String toString() {
		if (this.transaction == null) {
			StringBuilder sb = new StringBuilder();
			sb.append("CONNECTION_HASH=[Transaction has not been started.] PARAMETER=").append(this.dataBaseAccessParameter.toString()).append(']');
			return sb.toString();
		} else {
			return this.transaction.toString();
		}
		
	}
}
