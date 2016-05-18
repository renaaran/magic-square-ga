package cefet.rba.operators;

import cefet.rba.chromossome.Chromosome;

public interface BinaryOperator<T> {
	void evoluate(Chromosome<T> chromosome1, Chromosome<T> chromosome2);
}
