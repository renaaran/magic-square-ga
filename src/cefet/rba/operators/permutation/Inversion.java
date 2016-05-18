package cefet.rba.operators.permutation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cefet.rba.chromossome.Chromosome;
import cefet.rba.operators.Operator;
import cefet.rba.operators.UnaryOperator;

public class Inversion<T> extends Operator implements UnaryOperator<T> {

	private static final Logger log4j = LogManager.getLogger(Inversion.class.getName());
	
	@Override
	public Chromosome<T> evoluate(Chromosome<T> chromosome) {
		setRange(chromosome.size());
		int range = (l1+l2)/2;
		if (log4j.isDebugEnabled()) log4j.debug(String.format("l1=%d - l2=%d - range=%d", l1, l2, range));
		for (; l1 <= range; l1++, l2--) {
			T gene = chromosome.getGene(l1);
			chromosome.setGene(l1, chromosome.getGene(l2));
			chromosome.setGene(l2, gene);
		}
		return chromosome;
	}

}
