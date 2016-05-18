package cefet.rba.objectiveFunction;

import cefet.rba.chromossome.Chromosome;

public interface ObjectiveFunction {
	double evaluate(Chromosome<?> chromosome);
}
