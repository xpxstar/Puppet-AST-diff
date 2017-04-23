package cn.ac.iscas.cloudeploy.v2.puppet.transform.ast;

public abstract class ASTBase {
	private int line;
	private ASTBase file;
	public int getLine() {
		return line;
	}
	public void setLine(int line) {
		this.line = line;
	}
	public ASTBase getFile() {
		return file;
	}
	public void setFile(ASTBase file) {
		this.file = file;
	} 
    @Override
    public int hashCode() {
    	if (file == null) {
			return 0;
		}
    	return file.hashCode();
    }
    @Override
    public boolean equals(Object obj) {
    	if (obj == null) {
			return false;
		}
    	ASTBase tObj = (ASTBase)obj;
    	if (tObj.getFile() == null) {
			return false;
		}
    	return file.equals(tObj.getFile());
    }
    
    /**将部分值转换为string
	 * @param value
	 * @return
	 */
	public String changeString() {
		return "base";
	}
	/**提取本元素的名称
	 * @param value
	 * @return
	 */
	public String elementName() {
		return "base";
	}
}
