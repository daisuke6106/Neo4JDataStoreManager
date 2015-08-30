package jp.co.dk.neo4jdatastoremanager;

import java.util.HashMap;
import java.util.Map;

import jp.co.dk.logger.Logger;
import jp.co.dk.logger.LoggerFactory;
import jp.co.dk.neo4jdatastoremanager.exception.Neo4JDataStoreManagerException;
import jp.co.dk.neo4jdatastoremanager.property.Neo4JDataStoreManagerProperty;
import static jp.co.dk.neo4jdatastoremanager.message.Neo4JDataStoreManagerMessage.*;

/**
 * <p>Neo4JDataStoreManagerは、単一、もしくは複数のNeo4Jデータベースへの接続、トランザクションの管理を行うクラスです。</p>
 * 
 * @version 0.1
 * @author D.Kanno
 */
public class Neo4JDataStoreManager {
	
	/** デフォルトのデータストア */
	protected Neo4JDataStore defaultDataStore;
	
	/** 各種データストア */
	protected Map<String, Neo4JDataStore> dataStores = new HashMap<String, Neo4JDataStore>();
	
	/** データストアプロパティ */
	protected Neo4JDataStoreManagerProperty dataStoreManagerProperty;
	
	/** ロガーインスタンス */
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * <p>コンストラクタ</p>
	 * 
	 * 
	 * @param dataStoreManagerProperty データストアマネージャプロパティ
	 * @throws Neo4JDataStoreManagerException インスタンスの生成に失敗した場合
	 */
	public Neo4JDataStoreManager(Neo4JDataStoreManagerProperty dataStoreManagerProperty) throws Neo4JDataStoreManagerException{
		this.logger.constractor(this.getClass(), dataStoreManagerProperty);
		if (dataStoreManagerProperty == null) throw new Neo4JDataStoreManagerException(NEO4JPROPERTY_IS_NOT_SET);
		this.dataStoreManagerProperty                = dataStoreManagerProperty;
		this.defaultDataStore                        = dataStoreManagerProperty.getDefaultDataStoreParameter().createDataStore();
		Map<String, Neo4JDataStoreParameter> parameterMap = dataStoreManagerProperty.getDataStoreParameters();
		for (Map.Entry<String, Neo4JDataStoreParameter> parameter : parameterMap.entrySet()) this.dataStores.put(parameter.getKey(), parameter.getValue().createDataStore());
	}
	
	/**
	 * このデータストア管理クラスが管理しているすべてのデータストアに対してトランザクションを開始します。<p/>
	 * トランザクション開始処理に失敗した場合、例外を送出します。
	 * 
	 * @throws Neo4JDataStoreManagerException トランザクション開始に失敗した場合
	 */
	public void startTrunsaction() throws Neo4JDataStoreManagerException {
		this.defaultDataStore.startTransaction();
		for (Neo4JDataStore dataStore : this.dataStores.values()) {
			dataStore.startTransaction();
		}
	}
	
	public Neo4JDataStore getDataAccessObject(String name) throws Neo4JDataStoreManagerException {
		Neo4JDataStore dataStore = this.dataStores.get(name);
		if (dataStore != null) return dataStore;
		return this.defaultDataStore;
	}
	
	/**
	 * このトランザクションに対してコミットを実行します。
	 * 
	 * @throws Neo4JDataStoreManagerException コミットに失敗した場合
	 */
	public void commit() throws Neo4JDataStoreManagerException {
		this.defaultDataStore.commit();
		for (Map.Entry<String, Neo4JDataStore> dataStore : dataStores.entrySet()) dataStore.getValue().commit();
	}
	
	/**
	 * このトランザクションに対してロールバックを実行します。
	 * 
	 * @throws Neo4JDataStoreManagerException ロールバックに失敗した場合
	 */
	public void rollback() throws Neo4JDataStoreManagerException {
		this.defaultDataStore.rollback();
		for (Map.Entry<String, Neo4JDataStore> dataStore : dataStores.entrySet()) dataStore.getValue().rollback();
	}
	
	/**
	 * このデータストアにてエラーが発生したかを判定します。
	 * 
	 * @return 判定結果（true=エラーが発生した、false=エラーは発生していない）
	 */
	public boolean hasError() {
		if (this.defaultDataStore.hasError()) return true;
		for (Map.Entry<String, Neo4JDataStore> dataStore : dataStores.entrySet()) {
			if (dataStore.getValue().hasError()) return true;
		}
		return false;
	}
	
	/**
	 * このデータストア管理クラスが管理しているすべてのデータストアに対してトランザクションを終了します。<p/>
	 * トランザクション終了処理に失敗した場合、例外を送出します。<br/>
	 * 
	 * @throws Neo4JDataStoreManagerException トランザクション終了に失敗した場合
	 */
	public void finishTrunsaction() throws Neo4JDataStoreManagerException {
		if (this.hasError()) {
			this.defaultDataStore.rollback();
			for (Map.Entry<String, Neo4JDataStore> dataStore : dataStores.entrySet()) dataStore.getValue().rollback();
		} else {
			this.defaultDataStore.commit();
			for (Map.Entry<String, Neo4JDataStore> dataStore : dataStores.entrySet()) dataStore.getValue().commit();
		}
		this.defaultDataStore.finishTransaction();
		for (Map.Entry<String, Neo4JDataStore> dataStore : dataStores.entrySet()) dataStore.getValue().finishTransaction();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dataStores == null) ? 0 : dataStores.hashCode());
		result = prime
				* result
				+ ((defaultDataStore == null) ? 0 : defaultDataStore.hashCode());
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
		Neo4JDataStoreManager other = (Neo4JDataStoreManager) obj;
		if (dataStores == null) {
			if (other.dataStores != null)
				return false;
		} else if (!dataStores.equals(other.dataStores))
			return false;
		if (defaultDataStore == null) {
			if (other.defaultDataStore != null)
				return false;
		} else if (!defaultDataStore.equals(other.defaultDataStore))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NEO4JDATASTOREMANAGER [DEFAULTDATASTORE=");
		builder.append(defaultDataStore);
		builder.append(", DATASTORES=");
		builder.append(dataStores);
		builder.append("]");
		return builder.toString();
	}
	
	
}
