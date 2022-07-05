package project.graph.model.algorithm.command;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CompositeStep extends Step{
	List<Step> listSteps = new ArrayList<Step>();
	public CompositeStep(String content) {
		// TODO Auto-generated constructor stub
		super(content);
	}
	public void addStep(Step step) {
		listSteps.add(step);
	}
	public void clear() {
		listSteps.clear();
	}
	@Override
	public String toString() {
		if(this.getContent().equals("")==false) {
		return "\n["+super.toString()+"]";}
		return "";
	}
	@Override
	public void presentConsole() {
		// TODO Auto-generated method stub
		System.out.println(this.toString());
		Iterator<Step> iterator = listSteps.iterator();
		Step n;
		while(iterator.hasNext()) {
			n=iterator.next();
			n.presentConsole();
		}
	}
	@Override
	public void presentList(List<Step> list) {
		// TODO Auto-generated method stub
		if(this.getContent().equals("")==false) list.add(new CompositeStep(this.getContent()));
		for(int i=0;i<listSteps.size();i++) {
			if(listSteps.get(i) instanceof Detail) {
				list.add(listSteps.get(i));
			}
			else listSteps.get(i).presentList(list);
		}
	}
}
