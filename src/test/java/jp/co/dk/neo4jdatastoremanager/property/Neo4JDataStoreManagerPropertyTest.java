package jp.co.dk.neo4jdatastoremanager.property;

import jp.co.dk.test.template.TestCaseTemplate;

import org.junit.Test;

public class Neo4JDataStoreManagerPropertyTest extends TestCaseTemplate{

	@Test
	public void test() {
		assertThat(Neo4JDataStoreManagerProperty.CRAWLER_NEO4J_SERVER.getString()  , is("http://localhost:7474/db/data"));
		assertThat(Neo4JDataStoreManagerProperty.CRAWLER_NEO4J_USER.getString()    , is("neo4j"));
		assertThat(Neo4JDataStoreManagerProperty.CRAWLER_NEO4J_PASSWORD.getString(), is("123456"));
	}

}
