package jp.co.dk.neo4jdatastoremanager;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import jp.co.dk.neo4jdatastoremanager.cypher.Cypher;
import jp.co.dk.neo4jdatastoremanager.exception.Neo4JDataStoreManagerCypherException;
import jp.co.dk.neo4jdatastoremanager.exception.Neo4JDataStoreManagerException;

import org.neo4j.rest.graphdb.RestAPIFacade;
import org.neo4j.rest.graphdb.RestGraphDatabase;
import org.neo4j.rest.graphdb.query.RestCypherQueryEngine;

import static jp.co.dk.neo4jdatastoremanager.message.Neo4JDataStoreManagerMessage.*;

public class Transaction implements Closeable {
	
	/** グラフデータベースサービス */
	protected RestAPIFacade restApiFacade;
	
	/** グラフデータベースサービス */
	protected RestGraphDatabase graphDatabaseService;
	
	/** トランザクション */
	protected org.neo4j.graphdb.Transaction transaction;
	
	Transaction(Neo4JDataStoreParameter parameter) throws Neo4JDataStoreManagerException {
		if (parameter == null) throw new Neo4JDataStoreManagerException(NEO4JPARAMETER_IS_NOT_SET);
		if (parameter.isAuthSet()) {
			this.restApiFacade = new RestAPIFacade(parameter.getCrawlerNeo4jServer(), parameter.getCrawlerNeo4jUser() ,parameter.getCrawlerNeo4jPass());
		} else {
			this.restApiFacade = new RestAPIFacade(parameter.getCrawlerNeo4jServer());
		}
		this.graphDatabaseService = new RestGraphDatabase(this.restApiFacade);
		this.transaction          = this.graphDatabaseService.beginTx();
	}
	
	public void commit() {
		this.transaction.success();
	}
	
	public void rollback() {
		this.transaction.failure();
	}
	
	public Node createNode() {
		return new Node(this.graphDatabaseService.createNode());
	}
	
	public Node selectNode(Cypher cypher) throws Neo4JDataStoreManagerCypherException {
		if (cypher == null) throw new Neo4JDataStoreManagerCypherException(CYPHER_IS_NOT_SET);
		RestCypherQueryEngine queryEngine = new RestCypherQueryEngine(this.restApiFacade);
		try {
			return new Node(queryEngine.query(cypher.getCypher(), cypher.getParameter()).to(org.neo4j.graphdb.Node.class).single());
		} catch (NoSuchElementException e) {
			return null;
		}
	}
	
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
}
