package cefet.rba.criteria;

import java.util.List;

import cefet.rba.selection.Individual;

public interface StopCriteria<T> {
	public boolean stop(List<Individual<T>> population);
}
