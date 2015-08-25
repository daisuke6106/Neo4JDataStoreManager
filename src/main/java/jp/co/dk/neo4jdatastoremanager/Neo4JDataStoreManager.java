package jp.co.dk.neo4jdatastoremanager;

import java.util.HashMap;
import java.util.Map;

import jp.co.dk.logger.Logger;
import jp.co.dk.logger.LoggerFactory;
import jp.co.dk.neo4jdatastoremanager.exception.Neo4JDataStoreManagerException;
import jp.co.dk.neo4jdatastoremanager.property.Neo4JDataStoreManagerProperty;

import static jp.co.dk.neo4jdatastoremanager.message.Neo4JDataStoreManagerMessage.*;

/**
 * DataStoreManagerは、Oracle、Mysql、PostgreSqlなどのデータストアに対する管理を行うクラスです。<p/>
 * <br/>
 * このクラスは指定のデータストアへのトランザクション制御、各種テーブルへのDAOの取得などデータストアへの操作を管理する制御クラスとなります。<br/>
 * <br/>
 * 対応しているデータストアは以下の 通りとなります。<br/>
 * 
 * ===RDB===<br/>
 * ・Oracle<br/>
 * ・MySql<br/>
 * ・PostgreSql<br/>
 * <br/>
 * ===ファイル===<br/>
 * ・csvファイル<br/>
 * 
 * @version 1.0
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
	 * コンストラクタ<p/>
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
		for (String name : parameterMap.keySet()) this.dataStores.put(name, parameterMap.get(name).createDataStore());
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
		for (String name : dataStores.keySet()) {
			this.dataStores.get(name).commit();
		}
	}
	
	/**
	 * このトランザクションに対してロールバックを実行します。
	 * 
	 * @throws Neo4JDataStoreManagerException ロールバックに失敗した場合
	 */
	public void rollback() throws Neo4JDataStoreManagerException {
		this.defaultDataStore.rollback();
		for (String name : dataStores.keySet()) {
			this.dataStores.get(name).rollback();
		}
	}
	
	/**
	 * このデータストアにてエラーが発生したかを判定します。
	 * 
	 * @return 判定結果（true=エラーが発生した、false=エラーは発生していない）
	 */
	public boolean hasError() {
		if (this.defaultDataStore.hasError()) return true;
		for (String name : dataStores.keySet()) {
			if (this.dataStores.get(name).hasError()) return true;
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
			for (String name : dataStores.keySet()) {
				this.dataStores.get(name).rollback();
			}
		} else {
			this.defaultDataStore.commit();
			for (String name : dataStores.keySet()) {
				this.dataStores.get(name).commit();
			}
		}
		this.defaultDataStore.finishTransaction();
		for (Neo4JDataStore dataStore : dataStores.values()) {
			dataStore.finishTransaction();
		}
	}
}
