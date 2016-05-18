package cefet.rba.operators;

import cefet.rba.chromossome.Chromosome;

public interface AfterCrossoverListener<T> {
	void crossoverListen(Chromosome<T> chromosome);
}
