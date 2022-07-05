package project.graph.model.algorithm.command;

import java.util.List;

public class Step { 
	private String content;
	public Step(String content) {
		this.content = content;
	}
	public String getContent() {
		return content;
	}
	@Override
	public String toString() {
		return content;
	}
	public void presentConsole() {};
	public void presentList(List<Step> listSteps) {};
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(obj instanceof Step) {
			if(((Step) obj).getContent().equals(content)) return true;
		}
		return false;
	}
	
}
