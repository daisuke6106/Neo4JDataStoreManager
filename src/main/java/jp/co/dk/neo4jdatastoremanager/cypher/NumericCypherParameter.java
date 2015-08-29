package jp.co.dk.neo4jdatastoremanager.cypher;

class NumericCypherParameter extends CypherParameter{
	
	protected int parameter;
	
	NumericCypherParameter(int parameter) {
		this.parameter = parameter;
	}

	@Override
	Object getParameter() {
		return Integer.valueOf(this.parameter);
	}
	
	@Override
	public boolean equals(Object object) {
		if (object == null) return false;
		if (!(object instanceof NumericCypherParameter)) return false;
		NumericCypherParameter thisClassObj = (NumericCypherParameter) object;
		if (thisClassObj.hashCode() == this.hashCode()) return true;
		return false;
	}
	
	@Override
	public int hashCode() {
		return (int) ((int)this.parameter * 17L);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.parameter).append("(int)");
		return sb.toString();
	}

}
