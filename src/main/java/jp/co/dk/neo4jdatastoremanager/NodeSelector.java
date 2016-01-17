package jp.co.dk.neo4jdatastoremanager;

@FunctionalInterface
public interface NodeSelector {
	boolean isSelect(org.neo4j.graphdb.Node node);
}
