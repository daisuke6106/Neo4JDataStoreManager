package jp.co.dk.neo4jdatastoremanager;

public interface NodeSelector {
	boolean isSelect(org.neo4j.graphdb.Node node);
}
