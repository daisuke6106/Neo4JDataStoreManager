package jp.co.dk.neo4jdatastoremanager.cypher;

class BooleanCypherParameter extends CypherParameter{
	
	protected boolean parameter;
	
	BooleanCypherParameter(boolean parameter) {
		this.parameter = parameter;
	}

	@Override
	Object getParameter() {
		return Boolean.valueOf(parameter);
	}
	
	@Override
	public boolean equals(Object object) {
		if (object == null) return false;
		if (!(object instanceof BooleanCypherParameter)) return false;
		BooleanCypherParameter thisClassObj = (BooleanCypherParameter) object;
		if (thisClassObj.hashCode() == this.hashCode()) return true;
		return false;
	}
	
	@Override
	public int hashCode() {
		return Boolean.valueOf(this.parameter).hashCode();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.parameter).append("(boolean)");
		return sb.toString();
	}
	
}
