package cefet.rba.algorithm;

import cefet.rba.selection.Individual;

public interface EvolutionaryAlgorithm<T extends Comparable<T>> {
	public Individual<T> getBestIndividual();
}
