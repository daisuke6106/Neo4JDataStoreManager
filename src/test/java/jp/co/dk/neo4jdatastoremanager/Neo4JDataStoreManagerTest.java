package jp.co.dk.neo4jdatastoremanager;

import static org.junit.Assert.*;
import jp.co.dk.neo4jdatastoremanager.exception.Neo4JDataStoreManagerException;
import jp.co.dk.neo4jdatastoremanager.property.Neo4JDataStoreManagerProperty;
import jp.co.dk.property.exception.PropertyException;

import org.junit.Test;

public class Neo4JDataStoreManagerTest {

	@Test
	public void test() {
		try {
			Neo4JDataStoreManager sut = new Neo4JDataStoreManager(new Neo4JDataStoreManagerProperty());
			sut.startTrunsaction();
			Neo4JDataStore neo4JDataStore = sut.getDataAccessObject("TEST");
			Node node = neo4JDataStore.createNode();
			node.setProperty("test", "test");
			sut.finishTrunsaction();
		} catch (Neo4JDataStoreManagerException | PropertyException e) {
			e.printStackTrace();
			fail();
		}
	}

}
