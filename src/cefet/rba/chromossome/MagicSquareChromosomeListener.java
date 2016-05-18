package cefet.rba.chromossome;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cefet.rba.operators.AfterCrossoverListener;
import cefet.rba.operators.AfterMutateListener;

public class MagicSquareChromosomeListener implements AfterCrossoverListener<Integer>, AfterMutateListener<Integer> {

	private static final Logger log4j = LogManager.getLogger(MagicSquareChromosomeListener.class.getName());

	@Override
	public void mutateListen(Chromosome<Integer> chromosome) {
		log4j.debug("mutateListen.Before: "+chromosome+" sumation = "+Arrays.toString(((MagicSquareChromosome)chromosome).getSummation()));
		((MagicSquareChromosome)chromosome).calculateSummation();
		log4j.debug("mutateListen.After:  "+chromosome+" sumation = "+Arrays.toString(((MagicSquareChromosome)chromosome).getSummation()));
	}

	@Override
	public void crossoverListen(Chromosome<Integer> chromosome) {
		log4j.debug("crossoverListen.Before: "+chromosome+" sumation = "+Arrays.toString(((MagicSquareChromosome)chromosome).getSummation()));
		((MagicSquareChromosome)chromosome).calculateSummation();
		log4j.debug("crossoverListen.After:  "+chromosome+" sumation = "+Arrays.toString(((MagicSquareChromosome)chromosome).getSummation()));
	}
}
