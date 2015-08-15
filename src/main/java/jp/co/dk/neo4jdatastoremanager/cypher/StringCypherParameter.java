package jp.co.dk.neo4jdatastoremanager.cypher;

class StringCypherParameter extends CypherParameter{
	
	protected String parameter;
	
	StringCypherParameter(String parameter) {
		this.parameter = parameter;
	} 

	@Override
	Object getParameter() {
		return new String(this.parameter);
	}
	
	@Override
	public boolean equals(Object object) {
		if (object == null) return false;
		if (!(object instanceof StringCypherParameter)) return false;
		StringCypherParameter thisClassObj = (StringCypherParameter) object;
		if (thisClassObj.hashCode() == this.hashCode()) return true;
		return false;
	}
	
	@Override
	public int hashCode() {
		if (this.parameter != null) {
			return this.parameter.hashCode() * 17;
		} else {
			return -1;
		}
	}
	
	@Override
	public String toString() {
		if (this.parameter != null) {
			StringBuilder sb = new StringBuilder();
			if (this.parameter.length() < 1000) {
				sb.append(this.parameter).append("(string)");
			} else {
				sb.append("size:").append(this.parameter.length()).append("(string)");
			}
			return sb.toString();
		} else {
			return "null(string)";
		}
	}

}