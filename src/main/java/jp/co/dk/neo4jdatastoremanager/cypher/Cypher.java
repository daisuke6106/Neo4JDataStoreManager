package jp.co.dk.neo4jdatastoremanager.cypher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.dk.neo4jdatastoremanager.exception.Neo4JDataStoreManagerCypherException;
import static jp.co.dk.neo4jdatastoremanager.message.Neo4JDataStoreManagerMessage.*;

/**
 * Cypherは、Cypher本文と、そのCypherに対するパラメータを保持し、単一のCypher本文を生成するクラスです。
 * 
 * @version 1.1
 * @author D.Kanno
 */
public class Cypher implements Cloneable {
	
	/** Cypher本文 */
	private StringBuilder cypher = new StringBuilder();
	
	/** パラメータ */
	private List<CypherParameter> cypherParameter = new ArrayList<CypherParameter>();
	
	/**
	 * コンストラクタ<p/>
	 * 指定のCypherを表す文字列を元に、Cypherオブジェクトのインスタンスを生成します。<br/>
	 * Cypher本文がnull、または空文字の場合例外を送出します。
	 * 
	 * @param cypher Cypher本文の文字列
	 * @throws Neo4JDataStoreManagerCypherException Cypherオブジェクトのインスタンス生成に失敗した場合
	 */
	public Cypher(String cypher) throws Neo4JDataStoreManagerCypherException {
		if (cypher == null || cypher.equals("")) throw new Neo4JDataStoreManagerCypherException(CYPHER_IS_NOT_SET); 
		this.cypher.append(cypher);
	}
	
	/**
	 * 指定のCypher言語の文字列を本クラスのCypherの最初に追加します。
	 * @param cypher Cypher文字列
	 * @throws Neo4JDataStoreManagerCypherException 引数に指定されたCypherがnullまたは空文字の場合
	 */
	public Cypher appendTop(String cypher) throws Neo4JDataStoreManagerCypherException {
		if (cypher == null || cypher.equals("")) throw new Neo4JDataStoreManagerCypherException(CYPHER_IS_NOT_SET);
		this.cypher = new StringBuilder(cypher).append(this.cypher);
		return this;
	}
	
	/**
	 * 指定のCypherオブジェクトを本クラスのCypherの最初に追加します。<p/>
	 * 引数に指定されたCypherオブジェクトが持つCypherを表す文字列、パラメータともに最初に追加します。
	 * @param cypher Cypherオブジェクト
	 * @throws Neo4JDataStoreManagerCypherException 引数に指定されたCypherがnullまたは空文字の場合
	 */
	public Cypher appendTop(Cypher cypher) throws Neo4JDataStoreManagerCypherException {
		if (cypher == null) throw new Neo4JDataStoreManagerCypherException(CYPHER_IS_NOT_SET);
		this.cypher = new StringBuilder(cypher.cypher.toString()).append(this.cypher);
		List<CypherParameter> newCypherParameter = new ArrayList<>(cypher.cypherParameter);
		newCypherParameter.addAll(this.cypherParameter);
		this.cypherParameter = newCypherParameter;
		return this;
	}
	
	/**
	 * 指定のCypher言語の文字列を本クラスのCypherの末尾に追加します。
	 * @param cypher Cypher文字列
	 * @throws Neo4JDataStoreManagerCypherException 引数に指定されたCypherがnullまたは空文字の場合
	 */
	public Cypher append(String cypher) throws Neo4JDataStoreManagerCypherException {
		if (cypher == null || cypher.equals("")) throw new Neo4JDataStoreManagerCypherException(CYPHER_IS_NOT_SET); 
		this.cypher.append(cypher);
		return this;
	}
	
	/**
	 * 指定のCypherオブジェクトを本クラスのCypherの末尾に追加します。<p/>
	 * 引数に指定されたCypherオブジェクトが持つCypherを表す文字列、パラメータともに末尾に追加します。
	 * @param cypher Cypherオブジェクト
	 * @throws Neo4JDataStoreManagerCypherException 引数に指定されたCypherがnullまたは空文字の場合
	 */
	public Cypher append(Cypher cypher) throws Neo4JDataStoreManagerCypherException {
		if (cypher == null) throw new Neo4JDataStoreManagerCypherException(CYPHER_IS_NOT_SET); 
		this.cypher.append(cypher.cypher.toString());
		this.cypherParameter.addAll(cypher.cypherParameter);
		return this;
	}
	
	/**
	 * 指定の文字列を元に、Cypherの{x}部分にあたる文字列を設定します。
	 * @param parameter Cypherの{x}部分にあたる文字列
	 * @return 本オブジェクトインスタンス
	 */
	public Cypher setParameter(String parameter){
		this.cypherParameter.add(new StringCypherParameter(parameter));
		return this;
	}
	
	/**
	 * 指定の数値を元に、Cypherの{x}部分にあたる数値を設定します。
	 * @param parameter Cypherの{x}部分にあたる数値(int)
	 * @return 本オブジェクトインスタンス
	 */
	public Cypher setParameter(int parameter) {
		this.cypherParameter.add(new NumericCypherParameter(parameter));
		return this;
	}
	
	/**
	 * 指定の数値を元に、Cypherの{x}部分にあたる数値を設定します。
	 * @param parameter Cypherの{x}部分にあたる数値(boolean)
	 * @return 本オブジェクトインスタンス
	 */
	public Cypher setParameter(boolean parameter) {
		this.cypherParameter.add(new BooleanCypherParameter(parameter));
		return this;
	}
	
	public String getCypher() {
		StringBuilder replacedCypher = new StringBuilder(this.cypher.toString());
		for (int index=1; ; index++) {
			int findIndex = replacedCypher.indexOf("?");
			if (findIndex < 0) break;
			replacedCypher.delete(findIndex, findIndex + 1);
			replacedCypher.insert(findIndex, "{" + index + "}");
		}
		return replacedCypher.toString();
	}
	
	/**
	 * このCypherに設定されたCypherに対するパラメータの一覧を取得します。
	 * @return パラメータの一覧
	 */
	public Map<String, Object> getParameter() {
		Map<String, Object> parameter = new HashMap<String, Object>();
		for (int i=1; i<=this.cypherParameter.size(); i++) parameter.put(Integer.toString(i), this.cypherParameter.get(i-1).getParameter());
		return parameter;
	}
	
	@Override
	public int hashCode() { 
		int hashcode = this.cypher.toString().hashCode();
		for (CypherParameter param : this.cypherParameter) hashcode *= param.hashCode();
		return hashcode *17;
	}
	
	@Override
	public boolean equals(Object object) {
		if (object == null) return false;
		if (!(object instanceof Cypher)) return false;
		Cypher thisClassObj = (Cypher) object;
		if (thisClassObj.hashCode() == this.hashCode()) return true;
		return false;
	}
	
	@Override
	public String toString() {
		StringBuilder sqlstr = new StringBuilder("CYPHER=[").append(this.cypher).append(']');
		if (cypherParameter.size() == 0) {
			sqlstr.append(" PARAMETER=[NOTHING]");
			return sqlstr.toString();
		} else {
			sqlstr.append(" PARAMETER=[");
			for (CypherParameter param : this.cypherParameter) {
				sqlstr.append(param.toString()).append(", ");
			}
			int index = sqlstr.length();
			sqlstr.delete(index-2, index);
			sqlstr.append(']');
			return sqlstr.toString();
		}
	}
	
	@Override
	public Cypher clone() {
		try {
			Cypher cloneInstance          = (Cypher)super.clone();
			cloneInstance.cypher          = new StringBuilder(this.cypher.toString());
			cloneInstance.cypherParameter = new ArrayList<CypherParameter>(this.cypherParameter);
			return cloneInstance;
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e.toString());
		}
	}
}
