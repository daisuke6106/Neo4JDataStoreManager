package jp.co.dk.neo4jdatastoremanager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jp.co.dk.neo4jdatastoremanager.exception.Neo4JDataStoreManagerException;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;

import static jp.co.dk.neo4jdatastoremanager.message.Neo4JDataStoreManagerMessage.*;

public class Node {
	
	protected org.neo4j.graphdb.Node node;
	
	Node(org.neo4j.graphdb.Node node) {
		this.node = node;
	}
	
	public List<Node> getOutGoingNodes(NodeSelector selector) {
		List<Node> nodeList = new ArrayList<Node>();
		Iterator<Relationship> relationshipList = this.node.getRelationships(Direction.OUTGOING).iterator();
		while (relationshipList.hasNext()) {
			Relationship relationship = relationshipList.next();
			org.neo4j.graphdb.Node node = relationship.getEndNode();
			if (selector.isSelect(node)) {
				nodeList.add(new Node(node));
			}
		}
		return nodeList;
	}
	
	public List<Node> getOutGoingNodes() {
		List<Node> nodeList = new ArrayList<Node>();
		Iterator<Relationship> relationshipList = this.node.getRelationships(Direction.OUTGOING).iterator();
		while (relationshipList.hasNext()) {
			Relationship relationship = relationshipList.next();
			nodeList.add(new Node(relationship.getEndNode()));
		}
		return nodeList;
	}
	
	public void addLabel(org.neo4j.graphdb.Label label) {
		this.node.addLabel(label);
	}
	
	public void setProperty(Map<String, Object> properties) throws Neo4JDataStoreManagerException {
		for (Map.Entry<String, Object> property : properties.entrySet()) {
			String key = property.getKey();
			Object val = property.getValue();
			if (val == null) throw new Neo4JDataStoreManagerException(PARAMETER_IS_FRAUD, key, "null");
			if (val instanceof String) {
				this.setProperty(key, (String)val);
			} else if (val instanceof Integer){
				this.setProperty(key, ((Integer)val).intValue());
			} else if (val instanceof Boolean) {
				this.setProperty(key, ((Boolean)val).booleanValue());
			} else {
				throw new Neo4JDataStoreManagerException(PARAMETER_IS_FRAUD, key, val.toString());
			}
		}
	}
	
	public void setProperty(String key, String value) {
		this.node.setProperty(key, value);
	}
	
	public void setProperty(String key, int value) {
		this.node.setProperty(key, new Integer(value));
	}
	
	public void setProperty(String key, boolean value) {
		this.node.setProperty(key, new Boolean(value));
	}
	
	public String getProperty(String key) {
		return (String)this.node.getProperty(key);
	}
	
	public void addOutGoingRelation(RelationshipType label, Node node) {
		this.node.createRelationshipTo(node.node, label);
	}
}
