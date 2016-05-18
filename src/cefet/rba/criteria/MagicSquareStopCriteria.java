package cefet.rba.criteria;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cefet.rba.selection.Individual;

public class MagicSquareStopCriteria<T> implements StopCriteria<T> {

	private static Double ZERO = new Double(0.0);
	private static final Logger log4j = LogManager.getLogger(MagicSquareStopCriteria.class.getName());
	
	@Override
	public boolean stop(List<Individual<T>> population) {
		for (Individual<T> individual: population) {
			if (individual.getFitness().equals(ZERO)) {
				log4j.info("BEST = "+individual);
				return true;
			} 
		}
		return false;
	}
}
