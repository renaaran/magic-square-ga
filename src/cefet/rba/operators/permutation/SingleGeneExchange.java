package cefet.rba.operators.permutation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cefet.rba.chromossome.Chromosome;
import cefet.rba.operators.Operator;
import cefet.rba.operators.UnaryOperator;

public class SingleGeneExchange<T> extends Operator implements UnaryOperator<T> {

	private static final Logger log4j = LogManager.getLogger(SingleGeneExchange.class.getName());

	@Override
	public Chromosome<T> evoluate(Chromosome<T> chromosome) {
		setRange(chromosome.size());
		if (log4j.isDebugEnabled()) log4j.debug("Before: "+chromosome);
		if (l1 != l2) {
			T g1 = chromosome.getGene(l1);
			chromosome.setGene(l1, chromosome.getGene(l2));
			chromosome.setGene(l2, g1);
		}
		if (log4j.isDebugEnabled()) log4j.debug("After:  "+chromosome);
		return chromosome;
	}
}
