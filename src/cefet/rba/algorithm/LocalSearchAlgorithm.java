package cefet.rba.algorithm;

import cefet.rba.selection.Individual;

public interface LocalSearchAlgorithm<T> {
	public Individual<T> improve(Individual<T> best);
}
