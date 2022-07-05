package project.graph.model.algorithm.command;

public class Detail extends Step{
	public Detail(String content) {
		super(content);
	}
	@Override
	public String toString() {
		return super.toString();
	}
	@Override
	public void presentConsole() {
		// TODO Auto-generated method stub
		System.out.println(this);
	}
	public String presentUI(int time) {
		return this.getContent();
	}
	public void unPresentUI() {
	}
}
