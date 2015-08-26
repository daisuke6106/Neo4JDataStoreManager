package jp.co.dk.neo4jdatastoremanager.property;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jp.co.dk.neo4jdatastoremanager.Neo4JDataStoreParameter;
import jp.co.dk.neo4jdatastoremanager.exception.Neo4JDataStoreManagerException;
import jp.co.dk.property.PropertiesFile;
import jp.co.dk.property.exception.PropertyException;

/**
 * データストアに関するプロパティを定義するクラスです。
 * 
 * @version 1.0
 * @author D.Kanno
 */
public class Neo4JDataStoreManagerProperty extends PropertiesFile {
	
	/**
	 * コンストラクタ<p>
	 * デフォルトのプロパティファイルを元にプロパティファイルオブジェクトのインスタンスを生成する。<br/>
	 * デフォルトのプロパティファイルは"properties/DataStoreManager.properties"を参照します。<br/>
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
	
	/**
	 * このプロパティファイルからデフォルトのデータストアパラメータオブジェクトを生成し、返却する。<p/>
	 * このプロパティから"datastore.type"の値を取得し、その設定値に紐づくデータストアへの設定パラメータを収集し、データストアパラメータインスタンスを作成、返却します。<br/>
	 * <br/>
	 * たとえば、<br/>
	 * datastore.type=oracleの場合、<br/>
	 * <br/>
	 * ・"datastore.oracle.url"<br/>
	 * ・"datastore.oracle.sid"<br/>
	 * ・"datastore.oracle.user"<br/>
	 * ・"datastore.oracle.password"<br/>
	 * <br/>
	 * の値を収集し、データストアパラメータへ設定し、そのインスタンスを返却します。<br/>
	 * <br/>
	 * プロパティファイルに値が設定されていないなど、プロパティファイルからデータストアパラメータクラスの生成に失敗した場合、例外が送出されます。<br/>
	 * 
	 * @return データストアパラメータ
	 * @throws Neo4JDataStoreManagerException データストアパラメータの生成に失敗した場合
	 */
	public Neo4JDataStoreParameter getDefaultDataStoreParameter() throws Neo4JDataStoreManagerException {
		String neo4jurl      = this.getString("neo4j.server"  );
		String neo4juser     = this.getString("neo4j.user"    );
		String neo4jpassword = this.getString("neo4j.password");
		if (neo4juser != null && !neo4juser.equals("") && neo4jpassword != null && !neo4jpassword.equals("")) {
			return new Neo4JDataStoreParameter(neo4jurl, neo4juser, neo4jpassword);
		} else {
			return new Neo4JDataStoreParameter(neo4jurl);
		}
	}
	
	/**
	 * このプロパティファイルから
	 * 
	 * @return 名称をキーとデータストアパラメータインスタンスが紐づくマップオブジェクト
	 * @throws Neo4JDataStoreManagerException　データストアパラメータの生成に失敗した場合
	 */
	public Map<String, Neo4JDataStoreParameter> getDataStoreParameters() throws Neo4JDataStoreManagerException {
		Map<String, Neo4JDataStoreParameter> configurationMap = new HashMap<String, Neo4JDataStoreParameter>();
		List<String> nameList = this.getNameList("neo4j.server");
		for (String name : nameList) {
			configurationMap.put(name, this.getDataStoreParameter(name));
		}
		return configurationMap;
	}
	

	/**
	 * このプロパティファイルから指定の名称のデータストアパラメータオブジェクトを生成し、返却する。<p/>
	 * 引数に"USERS"が設定されていた場合、このプロパティから"datastore.type.USERS"の値を取得し、その設定値に紐づくデータストアへの設定パラメータを収集し、データストアパラメータインスタンスを作成、返却します。<br/>
	 * <br/>
	 * たとえば、<br/>
	 * datastore.type.USERS=oracleの場合、<br/>
	 * <br/>
	 * ・"datastore.oracle.url.USERS"<br/>
	 * ・"datastore.oracle.sid.USERS"<br/>
	 * ・"datastore.oracle.user.USERS"<br/>
	 * ・"datastore.oracle.password.USERS"<br/>
	 * <br/>
	 * の値を収集し、データストアパラメータへ設定し、そのインスタンスを返却します。<br/>
	 * <br/>
	 * プロパティファイルに値が設定されていないなど、プロパティファイルからデータストアパラメータクラスの生成に失敗した場合、例外が送出されます。<br/>
	 * 
	 * @return データストアパラメータ
	 * @throws Neo4JDataStoreManagerException データストアパラメータの生成に失敗した場合
	 */
	protected Neo4JDataStoreParameter getDataStoreParameter(String name) throws Neo4JDataStoreManagerException {
		String neo4jurl      = this.getString("neo4j.server"  , name);
		String neo4juser     = this.getString("neo4j.user"    , name);
		String neo4jpassword = this.getString("neo4j.password", name);
		if (neo4juser != null && neo4juser.equals("") && neo4jpassword != null && neo4jpassword.equals("")) {
			return new Neo4JDataStoreParameter(neo4jurl, neo4juser, neo4jpassword);
		} else {
			return new Neo4JDataStoreParameter(neo4jurl);
		}
	}
	
	/**
	 * 指定のプロパティキーに紐づく名称を取得します。<p/>
	 * プロパティファイルに以下のような定義がなされていた場合、以下のようなリストを返却します。<br/>
	 * <br/>
	 * 引数に"propertykey"<br/>
	 * <br/>
	 * propertykey=aaa<br/>
	 * propertykey.TEST01=bbb<br/>
	 * propertykey.TEST02=ccc<br/>
	 * <br/>
	 * が定義されていた場合、返却のリストには<br/>
	 * TEST01,TEST02を保持したリストを返却します。<br/>
	 * <br/>
	 * 引数にnullが設定された、存在しないプロパティキーを指定された場合、ドットのあとに続く名称が存在しない場合、空のリストを返却します。
	 * 
	 * @param key プロパティキー
	 * @return プロパティキーに続く名称の一覧
	 */
	protected List<String> getNameList(String key) {
		List<String> list = new ArrayList<String>();
		if (key == null || key.equals("")) return list; 
		Iterator<String> keys = getKeys(key).iterator();
		while(keys.hasNext()) {
			String getKey = keys.next();
			String replaceKey = getKey.replaceAll(key+".", "");
			if (!key.equals(replaceKey) && !replaceKey.equals("")) list.add(replaceKey); 
		}
		return list;
	}
	
	/**
	 * このプロパティキーを指定の名称で補完した値を返却します。<p/>
	 * 例えば、"datastore.type"、nameの値が"USERS"とした場合、<br/>
	 * プロパティファイルから"datastore.type.USERS"のキーに設定されている値を取得して返却します。<br/>
	 * <br/>
	 * 取得出来なかった場合、nullが取得される。
	 * 
	 * @param key プロパティキー
	 * @param name 名称
	 * @return プロパティ設定値
	 */
	protected String getStringWithName(String key, String name) {
		if (name == null) return null;
		String value = this.getString(new StringBuilder(key).append('.').append(name).toString());
		if (value == null ) return null;
		return value;
	}
	
}

