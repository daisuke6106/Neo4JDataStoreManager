package jp.co.dk.neo4jdatastoremanager.property;

import jp.co.dk.property.PropertiesFile;
import jp.co.dk.property.exception.PropertyException;

/**
 * データストアに関するプロパティを定義するクラスです。
 * 
 * @version 0.1.0
 * @author D.Kanno
 */
public class Neo4JDataStoreManagerProperty extends PropertiesFile {
	
	/**
	 * コンストラクタ<p>
	 * デフォルトのプロパティファイルを元にプロパティファイルオブジェクトのインスタンスを生成する。<br/>
	 * デフォルトのプロパティファイルは"Neo4JDataStoreManager.properties"を参照します。<br/>
	 * <br/>
	 * プロパティファイルが存在しない場合、例外が発生します。
	 * 
	 * @throws PropertyException プロパティファイルオブジェクトのインスタンス作成に失敗した場合
	 */
	public Neo4JDataStoreManagerProperty () throws PropertyException {
		this("Neo4JDataStoreManager.properties");
	}
	
	/**
	 * コンストラクタ<p>
	 * 指定のプロパティファイルを元にプロパティファイルオブジェクトのインスタンスを生成する。<br/>
	 * <br/>
	 * プロパティファイルが存在しない場合、例外が発生します。
	 * 
	 * @param file プロパティファイル
	 * @throws PropertyException プロパティファイルオブジェクトのインスタンス作成に失敗した場合
	 */
	public Neo4JDataStoreManagerProperty (String file) throws PropertyException {
		super(file);
	}
		
}
