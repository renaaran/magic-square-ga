package cefet.rba.operators;

import cefet.rba.chromossome.Chromosome;

public interface AfterMutateListener<T> {
	void mutateListen(Chromosome<T> chromosome);
}
