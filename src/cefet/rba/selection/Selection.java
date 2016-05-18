package cefet.rba.selection;

import java.util.List;

public interface Selection<T> {
	public void select(List<Individual<T>> population, Individual<T> best);
}
