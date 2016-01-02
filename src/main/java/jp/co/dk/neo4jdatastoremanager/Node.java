package jp.co.dk.neo4jdatastoremanager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jp.co.dk.neo4jdatastoremanager.exception.Neo4JDataStoreManagerException;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;

import static jp.co.dk.neo4jdatastoremanager.message.Neo4JDataStoreManagerMessage.*;

/**
 * <p>Neo4Jでのノードを表すクラスです。</p>
 * 
 * @version 0.1
 * @author D.Kanno
 */
public class Node {
	
	/** ノードＩＤ */
	protected long id;
	
	/** Neo4Jノードオブジェクト */
	protected org.neo4j.graphdb.Node node;
	
	/**
	 * <p>Neo4Jノードオブジェクトを基にノードを作成します。</p>
	 * @param node Neo4Jノードオブジェクト
	 */
	Node(org.neo4j.graphdb.Node node) {
		this.node = node;
		this.id   = this.node.getId();
	}
	
	/**
	 * <p>このノードから外向きに関連するノードで指定の条件に合致するノードを取得する。</p>
	 * @param selector 指定の条件を定義したオブジェクト
	 * @return この条件に合致したノード一覧
	 */
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
	
	/**
	 * <p>このノードから外向きに関連するノードを取得する。</p>
	 * @return 外向きに関連するノード一覧
	 */
	public List<Node> getOutGoingNodes() {
		List<Node> nodeList = new ArrayList<Node>();
		Iterator<Relationship> relationshipList = this.node.getRelationships(Direction.OUTGOING).iterator();
		while (relationshipList.hasNext()) {
			Relationship relationship = relationshipList.next();
			nodeList.add(new Node(relationship.getEndNode()));
		}
		return nodeList;
	}
	
	/**
	 * このノードにラベルを追加する。
	 * @param label ラベルオブジェクト
	 */
	public void addLabel(org.neo4j.graphdb.Label label) {
		this.node.addLabel(label);
	}
	
	/**
	 * <p>このノードに対して指定のマップの要素すべてをプロパティとして登録します。</p>
	 * マップのキーはプロパティのキー、マップの値はプロパティとして設定されます。
	 * 値には、String,Boolean,Integerの３種類のみ設定可能です。
	 * 値に、それ以外の型、もしくはNULL値が設定されていた場合、例外が送出されます。
	 * 
	 * @param properties プロパティ一覧
	 * @throws Neo4JDataStoreManagerException NULL値が設定されていた場合
	 */
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
	
	/**
	 * <p>このノードに対して指定のプロパティを登録します。</p>
	 * @param key プロパティキー
	 * @param value プロパティ値
	 */
	public void setProperty(String key, String value) {
		this.node.setProperty(key, value);
	}
	
	/**
	 * <p>このノードに対して指定のプロパティを登録します。</p>
	 * @param key プロパティキー
	 * @param value プロパティ値
	 */
	public void setProperty(String key, int value) {
		this.node.setProperty(key, Integer.valueOf(value));
	}
	
	/**
	 * <p>このノードに対して指定のプロパティを登録します。</p>
	 * @param key プロパティキー
	 * @param value プロパティ値
	 */
	public void setProperty(String key, boolean value) {
		this.node.setProperty(key, Boolean.valueOf(value));
	}
	
	/**
	 * <p>このノードに対して指定のプロパティを登録します。</p>
	 * @param key プロパティキー
	 * @param value プロパティ値
	 * @throws Neo4JDataStoreManagerException 
	 */
	public void setProperty(String key, Serializable value) throws Neo4JDataStoreManagerException {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream    oos  = new ObjectOutputStream(baos);
			oos.writeObject(value);
			oos.close();
			baos.close();
			this.node.setProperty(key, new String(baos.toByteArray()));
		} catch (IOException e) {
			throw new Neo4JDataStoreManagerException(PARAMETER_IS_FRAUD, key, value.toString());
		}
	}
	
	/**
	 * <p>このノードから指定のプロパティキーに紐づくString値を取得します。</p>
	 * @param key プロパティキー
	 * @return 値
	 */
	public String getPropertyString(String key) {
		return (String)this.node.getProperty(key);
	}
	
	/**
	 * <p>このノードから指定のプロパティキーに紐づくInteger値を取得します。</p>
	 * @param key プロパティキー
	 * @return 値
	 */
	public Integer getPropertyInteger(String key) {
		return (Integer)this.node.getProperty(key);
	}
	
	/**
	 * <p>このノードから指定のプロパティキーに紐づくBoolean値を取得します。</p>
	 * @param key プロパティキー
	 * @return 値
	 */
	public Boolean getPropertyBoolean(String key) {
		return (Boolean)this.node.getProperty(key);
	}
	
	public Object getPropertyObject(String key) throws Neo4JDataStoreManagerException {
		try {
			String value = (String) this.node.getProperty(key);
			byte[] data = value.getBytes();
			ByteArrayInputStream bis = new ByteArrayInputStream(data);
			ObjectInputStream ois = new ObjectInputStream(bis);
			return ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			throw new Neo4JDataStoreManagerException(PARAMETER_FAILED_TO_READ, key, e);
		}
	}
	
	/**
	 * <p>このノードから指定のプロパティキーの一覧を取得します。</p>
	 * @return プロパティキー一覧
	 */
	public List<String> getPropertyKeys() {
		List<String> keyList = new ArrayList<String>();
		for (String key : this.node.getPropertyKeys()) keyList.add(key);
		return keyList;
	}
	
	/**
	 * <p>このノードから外向きに関連するノードを追加します。</p>
	 * 引数に指定されたリレーションラベルと、ノードをこのノードから外向きの関連として登録します。
	 * @param label リレーションラベル
	 * @param node  ノード
	 */
	public void addOutGoingRelation(RelationshipType label, Node node) {
		this.node.createRelationshipTo(node.node, label);
	}
	
	/**
	 * <p>このノードから外向きに関連するノードを追加します。</p>
	 * 引数に指定されたリレーションラベルと、ノードをこのノードから外向きの関連として登録します。<br/>
	 * 尚、リレーションには指定のリレーションプロパティが設定されます。
	 * 
	 * @param label リレーションラベル
	 * @param node  ノード
	 * @param key   プロパティキー
	 * @param value プロパティ値
	 */
	public void addOutGoingRelation(RelationshipType label, Node node, String key, String value) {
		Relationship relationship = this.node.createRelationshipTo(node.node, label);
		relationship.setProperty(key, value);
	}
	
	/**
	 * <p>このノードから外向きに関連するノードを追加します。</p>
	 * 引数に指定されたリレーションラベルと、ノードをこのノードから外向きの関連として登録します。<br/>
	 * 尚、リレーションには指定のリレーションプロパティが設定されます。<br/>
	 * プロパティに設定できるのはString,Integer,Booleanのみであり、nullまたはそれ以外のクラスのインスタンスが設定されていた場合、例外を送出します。
	 * 
	 * @param label リレーションラベル
	 * @param node  ノード
	 * @param key   プロパティ一覧
	 */
	public void addOutGoingRelation(RelationshipType label, Node node, Map<String, Object> relationshipProperties) throws Neo4JDataStoreManagerException {
		Relationship relationship = this.node.createRelationshipTo(node.node, label);
		for ( Map.Entry<String, Object> relationshipProperty : relationshipProperties.entrySet() ) {
			String key = relationshipProperty.getKey();
			Object val = relationshipProperty.getValue();
			if (val == null) throw new Neo4JDataStoreManagerException(PARAMETER_IS_FRAUD, key, "null");
			if (val instanceof String) {
				relationship.setProperty(key, (String)val);
			} else if (val instanceof Integer){
				relationship.setProperty(key, (Integer)val);
			} else if (val instanceof Boolean) {
				relationship.setProperty(key, (Boolean)val);
			} else {
				throw new Neo4JDataStoreManagerException(PARAMETER_IS_FRAUD, key, val.toString());
			}
			
		}
		
	}
	
	/**
	 * このノードのIDを取得する。
	 * @return このノードのＩＤ
	 */
	public long getID() {
		return this.id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
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
		Node other = (Node) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NODE [ID=");
		builder.append(id);
		builder.append("]");
		return builder.toString();
	}
	
}
