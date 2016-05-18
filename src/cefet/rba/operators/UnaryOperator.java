package cefet.rba.operators;

import cefet.rba.chromossome.Chromosome;

public interface UnaryOperator<T> {
	Chromosome<T> evoluate(Chromosome<T> chromosome);
}
