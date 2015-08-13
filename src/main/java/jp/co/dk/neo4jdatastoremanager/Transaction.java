package jp.co.dk.neo4jdatastoremanager;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.rest.graphdb.RestAPIFacade;
import org.neo4j.rest.graphdb.RestGraphDatabase;

/**
 * Transactionは、Neo4Jのトランザクションを管理するクラスです。
 * 
 * @version 0.1.0
 * @author D.Kanno
 */
class Transaction {
	
	/** グラフデータベースサービス */
	protected RestAPIFacade restApiFacade;
	
	/** グラフデータベースサービス */
	protected GraphDatabaseService graphDatabaseService;
	
	/** トランザクション */
	protected org.neo4j.graphdb.Transaction transaction;
	
	Transaction(String neo4jserverUrl) {
		this.restApiFacade        = new RestAPIFacade(neo4jserverUrl);
		this.graphDatabaseService = new RestGraphDatabase(restApiFacade);
		this.transaction          = this.graphDatabaseService.beginTx();
	}
	
	Transaction(String neo4jserverUrl, String neo4juser, String neo4jpass) {
		this.restApiFacade        = new RestAPIFacade(neo4jserverUrl, neo4juser, neo4jpass);
		this.graphDatabaseService = new RestGraphDatabase(restApiFacade);
		this.transaction          = this.graphDatabaseService.beginTx();
	}
	
}
