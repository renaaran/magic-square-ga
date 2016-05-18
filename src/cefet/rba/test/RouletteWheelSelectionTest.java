package cefet.rba.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import cefet.rba.chromossome.Chromosome;
import cefet.rba.chromossome.MagicSquareChromosomeFactory;
import cefet.rba.objectiveFunction.StandardDeviationObjectiveFunction;
import cefet.rba.selection.Individual;
import cefet.rba.selection.RouletteWheelSelection;

public class RouletteWheelSelectionTest {

	private static final int SQUARE_SIDE = 3;	
	private List<Individual<Integer>> population;
	private MagicSquareChromosomeFactory chromosomeFactory = new MagicSquareChromosomeFactory(SQUARE_SIDE);
	private StandardDeviationObjectiveFunction objectiveFunction = new StandardDeviationObjectiveFunction(SQUARE_SIDE);

	private static final Logger log4j = LogManager.getLogger(RouletteWheelSelectionTest.class.getName());

	private void initializePopulation(int populationSize) {
		population = new ArrayList<>(populationSize);
		for (int i = 0; i < populationSize; i++) {
			Chromosome<Integer> chromosome = chromosomeFactory.makeIt();
			Individual<Integer> individual = new Individual<>(chromosome.copy(), objectiveFunction.evaluate(chromosome));
			population.add(individual);
		}
	}

	@Test
	public void test() {
		initializePopulation(4);
		RouletteWheelSelection<Integer> rws = new RouletteWheelSelection<Integer>();
		rws.select(population, population.get(0));
		for (Individual<Integer> individual: population) {
			log4j.debug(individual);
		}
	}
}
