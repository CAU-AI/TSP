package ga;

public interface Selection {
	public int getFirstParent();
	public int getSecondParent();
	public void setParentList();
	public void setInputList(GAElement[] inputList);
}
