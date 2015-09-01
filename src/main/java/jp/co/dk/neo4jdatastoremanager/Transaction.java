package jp.co.dk.neo4jdatastoremanager;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import jp.co.dk.logger.Logger;
import jp.co.dk.logger.LoggerFactory;
import jp.co.dk.neo4jdatastoremanager.cypher.Cypher;
import jp.co.dk.neo4jdatastoremanager.exception.Neo4JDataStoreManagerCypherException;
import jp.co.dk.neo4jdatastoremanager.exception.Neo4JDataStoreManagerException;

import org.neo4j.rest.graphdb.RestAPIFacade;
import org.neo4j.rest.graphdb.RestGraphDatabase;
import org.neo4j.rest.graphdb.query.RestCypherQueryEngine;

import static jp.co.dk.neo4jdatastoremanager.message.Neo4JDataStoreManagerMessage.*;

/**
 * <p>Transactionは、単一のデータベースへの単一のトランザクションを管理するクラスです。</p>
 * 
 * @version 0.1
 * @author D.Kanno
 */
class Transaction implements Closeable {
	
	/** データストアパラメータ */
	protected Neo4JDataStoreParameter parameter;
	
	/** グラフデータベースサービス */
	protected RestAPIFacade restApiFacade;
	
	/** グラフデータベースサービス */
	protected RestGraphDatabase graphDatabaseService;
	
	/** トランザクション */
	protected org.neo4j.graphdb.Transaction transaction;
	
	/** ロガーインスタンス */
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * <p>指定のNeo4Jデータストア接続パラメータを基にNeo4Jに接続し、トランザクションを開始します。</p>
	 * @param parameter Neo4Jデータストア接続パラメータ
	 * @throws Neo4JDataStoreManagerException Neo4Jデータストア接続パラメータが設定されていなかった場合
	 */
	Transaction(Neo4JDataStoreParameter parameter) throws Neo4JDataStoreManagerException {
		if (parameter == null) throw new Neo4JDataStoreManagerException(NEO4JPARAMETER_IS_NOT_SET);
		this.parameter = parameter;
		if (parameter.isAuthSet()) {
			this.restApiFacade = new RestAPIFacade(parameter.getNeo4jServer(), parameter.getNeo4jUser() ,parameter.getNeo4jPassword());
		} else {
			this.restApiFacade = new RestAPIFacade(parameter.getNeo4jServer());
		}
		this.graphDatabaseService = new RestGraphDatabase(this.restApiFacade);
		this.transaction          = this.graphDatabaseService.beginTx();
		this.logger.info("transaction start param=[" + this.parameter + "]");
	}
	
	/**
	 * <p>コミットを実施する。</p>
	 * このトランザクションに対して、コミット処理を実施します。
	 */
	public void commit() {
		this.transaction.success();
	}
	
	/**
	 * <p>ロールバックを実施する。</p>
	 * このトランザクションに対して、ロールバック処理を実施します。
	 */
	public void rollback() {
		this.transaction.failure();
	}
	
	/**
	 * <p>このトランザクションにてノードを作成し返却します。</p>
	 * @return ノードオブジェクト
	 */
	public Node createNode() {
		return new Node(this.graphDatabaseService.createNode());
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
		if (cypher == null) throw new Neo4JDataStoreManagerCypherException(CYPHER_IS_NOT_SET);
		RestCypherQueryEngine queryEngine = new RestCypherQueryEngine(this.restApiFacade);
		try {
			return new Node(queryEngine.query(cypher.getCypher(), cypher.getParameter()).to(org.neo4j.graphdb.Node.class).single());
		} catch (NoSuchElementException e) {
			return null;
		}
	}
	
	/**
	 * <p>検索結果を取得する。（複数）</p>
	 * 指定のCypherを実行し、複数のノードを取得します。
	 * 
	 * @param cypher 実行対象のCypher
	 * @return 取得したノード
	 * @throws Neo4JDataStoreManagerCypherException Cypherの実行に失敗した場合
	 */
	public List<Node> selectNodes(Cypher cypher) throws Neo4JDataStoreManagerCypherException {
		if (cypher == null) throw new Neo4JDataStoreManagerCypherException(CYPHER_IS_NOT_SET);
		List<Node> nodeList = new ArrayList<>();
		RestCypherQueryEngine queryEngine = new RestCypherQueryEngine(this.restApiFacade);
		Iterator<org.neo4j.graphdb.Node> resultIterator = queryEngine.query(cypher.getCypher(), cypher.getParameter()).to(org.neo4j.graphdb.Node.class).iterator();
		while(resultIterator.hasNext()) nodeList.add(new Node(resultIterator.next()));
		return nodeList;
	}
	
	@Override
	public void close() {
		this.transaction.close();
		this.graphDatabaseService.shutdown();
	}
	
	@Override
	protected void finalize() { 
		this.close();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((parameter == null) ? 0 : parameter.hashCode());
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
		Transaction other = (Transaction) obj;
		if (parameter == null) {
			if (other.parameter != null)
				return false;
		} else if (!parameter.equals(other.parameter))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CONNECTION_HASH=[").append(this.transaction.hashCode()).append("] PARAMETER=").append(this.parameter.toString()).append(']');
		return builder.toString();
	}
}
