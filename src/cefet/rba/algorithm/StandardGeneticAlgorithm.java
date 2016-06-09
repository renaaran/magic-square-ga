/*
 * Copyright (C) 2016 Renato Barros Arantes
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cefet.rba.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cefet.rba.chromossome.Chromosome;
import cefet.rba.chromossome.ChromosomeFactory;
import cefet.rba.criteria.StopCriteria;
import cefet.rba.objectiveFunction.ObjectiveFunction;
import cefet.rba.operators.AfterCrossoverListener;
import cefet.rba.operators.AfterMutateListener;
import cefet.rba.operators.BinaryOperator;
import cefet.rba.operators.UnaryOperator;
import cefet.rba.selection.Individual;
import cefet.rba.selection.Selection;

public class StandardGeneticAlgorithm<T extends Comparable<T>> implements EvolutionaryAlgorithm<T> {
	
	private int populationSize;
	private int maxGenerations;
	private double crossoverProbability;
	private double mutationProbability;
	private ChromosomeFactory<T> chromosomeFactory;
	private ObjectiveFunction objectiveFunction;
	private BinaryOperator<T> crossoverOperator;
	private UnaryOperator<T> mutationOperator;
	private Selection<T> selection;
	private StopCriteria<T> stopCriteria;
	private Individual<T> bestEver;
	private Individual<T> bestGen;
	private AfterCrossoverListener<T> afterCrossoverListener;
	private AfterMutateListener<T> afterMutateListener;
	private LocalSearchAlgorithm<T> localSearchAlgorithm;
	private int localSearchGenerations;
	private int adaptativeMutationGenerations;
	private double adaptativeMutationProbability;
	
	private static final Logger log4j = LogManager.getLogger(StandardGeneticAlgorithm.class.getName());
	
	private ArrayList<Individual<T>> initializePopulation() {
		ArrayList<Individual<T>> population = new ArrayList<>(populationSize);
		for (int i = 0; i < populationSize; i++) {
			Chromosome<T> chromosome = chromosomeFactory.makeIt();
			Individual<T> individual = new Individual<>(chromosome, objectiveFunction.evaluate(chromosome));
			population.add(individual);
			if (bestEver == null || individual.getFitness().compareTo(bestEver.getFitness()) < 0) {
				bestGen = bestEver = individual;
			}
		}
		return population;
	}

	private void evaluateObjectiveFunction(List<Individual<T>> population) throws InterruptedException {
		if (log4j.isDebugEnabled()) log4j.debug("*** Evoluate Objective Function...");
		bestGen = population.get(0);
		ExecutorService exec = Executors.newCachedThreadPool();
		for (final Individual<T> individual: population) {
			exec.execute(new Runnable() {
				@Override
				public void run() {
					individual.setFitness(objectiveFunction.evaluate(individual.getChromosome()));
					synchronized (bestEver) {
						if (individual.getFitness().compareTo(bestEver.getFitness()) < 0) {
							bestEver = individual;
						}
					}
					synchronized (bestGen) {
						if (individual.getFitness().compareTo(bestGen.getFitness()) < 0) {
							bestGen = individual;
						}
					}
				} // public void...				
			}); // executor...
		} // for...
		exec.shutdown();
		exec.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
	}
	
	private void reproduce(List<Individual<T>> selected) throws InterruptedException {
		Random random = new Random();
		int i = 0;
		int sz = selected.size();
		if (log4j.isDebugEnabled()) log4j.debug("*** Reproduction...");
		ExecutorService exec = Executors.newCachedThreadPool();
		while (i < sz) {
			final Individual<T> individual1 = selected.get(i++);
			final Individual<T> individual2 = selected.get(i++);
			if (random.nextDouble() <= crossoverProbability) {
				exec.execute(new Runnable() {
					@Override
					public void run() {
						crossoverOperator.evoluate(individual1.getChromosome(), individual2.getChromosome());
						if (afterCrossoverListener != null) {
							afterCrossoverListener.crossoverListen(individual1.getChromosome());
							afterCrossoverListener.crossoverListen(individual2.getChromosome());
						} // if...
					} // public void...				
				}); // executor...
			} // if...
		} // while...
		exec.shutdown();
		exec.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
	}
	
	private void mutate(List<Individual<T>> selected) throws InterruptedException {
		Random random = new Random();
		if (log4j.isDebugEnabled()) log4j.debug("*** Mutation...");
		ExecutorService exec = Executors.newCachedThreadPool();
		for (final Individual<T> individual: selected) {
			if (random.nextDouble() <= mutationProbability) {
				exec.execute(new Runnable() {
					@Override
					public void run() {
						mutationOperator.evoluate(individual.getChromosome());
						if (afterMutateListener != null) {
							afterMutateListener.mutateListen(individual.getChromosome());
						} // if...
					} // public void...				
				}); // executor...
			} // if...		
		} // for...
		exec.shutdown();
		exec.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
	}

	public StandardGeneticAlgorithm<T> run() throws InterruptedException {
		int generation = 0;
		AdaptMutation adaptMutation = null; 
		if (adaptativeMutationGenerations > 0) {
			adaptMutation = new AdaptMutation();
		}
		LocalSearch localSearch = null;
		if (localSearchGenerations > 0) {
			localSearch = new LocalSearch();
		}
		ArrayList<Individual<T>> population = initializePopulation();
		while (generation < maxGenerations && !stopCriteria.stop(population)) {
			log4j.debug("*** Best = "+bestGen+"/"+bestEver+" - Generation = "+generation+" - Chromosome = "+bestGen.getChromosome());
			selection.select(population, bestEver);
			reproduce(population);
			mutate(population);
			if (adaptativeMutationGenerations > 0) {
				adaptMutation.mutate();
			}
			if (localSearchGenerations > 0) {
				localSearch.search();
			}
			evaluateObjectiveFunction(population);
			++generation;
		}
		return this;
	}
	
	public StandardGeneticAlgorithm<T> withObjectiveFunction(ObjectiveFunction objectiveFunction) {
		this.objectiveFunction = objectiveFunction;
		return this;
	}

	public ObjectiveFunction getObjectiveFunction() {
		return objectiveFunction;
	}	

	public StandardGeneticAlgorithm<T> withPopulationSize(int populationSize) {
		this.populationSize = populationSize;
		return this;
	}
	
	public int getPopulationSize() {
		return populationSize;
	}
	
	public StandardGeneticAlgorithm<T> withMaxGenerations(int maxGenerations) {
		this.maxGenerations = maxGenerations;
		return this;	
	}
	
	public int getMaxGenerations() {
		return maxGenerations;
	}
	
	public StandardGeneticAlgorithm<T> withCrossoverProbability(double crossoverProbability) {
		this.crossoverProbability = crossoverProbability;
		return this;
	}
	
	public double getCrossoverProbability() {
		return crossoverProbability;
	}
	
	public StandardGeneticAlgorithm<T> withMutationProbability(double mutationProbability) {
		this.mutationProbability = mutationProbability;
		return this;
	}
	
	public double getMutationProbability() {
		return mutationProbability;
	}

	public StandardGeneticAlgorithm<T> withChromosomeFactory(ChromosomeFactory<T> chromosomeFactory) {
		this.chromosomeFactory = chromosomeFactory;
		return this;
	}

	public StandardGeneticAlgorithm<T> withSelectionAlgorithm(Selection<T> selection) {
		this.selection = selection;
		return this;
	}
	
	public StandardGeneticAlgorithm<T> withStopCriteria(StopCriteria<T> stopCriteria) {
		this.stopCriteria = stopCriteria;
		return this;
	}

	public StandardGeneticAlgorithm<T> withCrossoverOperator(
			BinaryOperator<T> crossoverOperator) {
		this.crossoverOperator = crossoverOperator;
		return this;
	}

	public StandardGeneticAlgorithm<T> withMutationOperator(
			UnaryOperator<T> mutationOperator) {
		this.mutationOperator = mutationOperator;
		return this;
	}

	public StandardGeneticAlgorithm<T> withAfterCrossoverListener(
			AfterCrossoverListener<T> afterCrossoverListener) {
		this.afterCrossoverListener = afterCrossoverListener;
		return this;
	}

	public StandardGeneticAlgorithm<T> withAfterMutateListener(
			AfterMutateListener<T> afterMutateListener) {
		this.afterMutateListener = afterMutateListener;
		return this;
	}

	public StandardGeneticAlgorithm<T> withAdaptativeMutation(int adaptativeMutationGenerations, double adaptativeMutationProbability) {
		this.adaptativeMutationGenerations = adaptativeMutationGenerations;
		this.adaptativeMutationProbability = adaptativeMutationProbability;
		return this;
	}

	public StandardGeneticAlgorithm<T> withLocalSearchAlgorithm(int localSearchGenerations, LocalSearchAlgorithm<T> localSearchAlgorithm) {
		this.localSearchGenerations = localSearchGenerations;
		this.localSearchAlgorithm = localSearchAlgorithm;
		return this;
	}
	
	public ChromosomeFactory<T> getChromosomeFactory() {
		return chromosomeFactory;
	}

	@Override
	public Individual<T> getBestIndividual() {
		return bestEver;
	}
	
	private static enum StatesEnum { INITIALIZE, TEST_CAGED_AT_LOCAL_MINIMUM, TEST_FREEDOM };
	
	private class AdaptMutation {
		private Individual<T> oldBestEver;
		private double oldMutationProbability;
		private int cagedAtMinGenCnt;
		private StatesEnum state = StatesEnum.INITIALIZE;
		
		public void mutate() {
			switch (state) {
				case INITIALIZE :
					oldBestEver = bestEver;
					cagedAtMinGenCnt  = 0;
					oldMutationProbability = mutationProbability;
					state = StatesEnum.TEST_CAGED_AT_LOCAL_MINIMUM;
					break;
				case TEST_CAGED_AT_LOCAL_MINIMUM :
					if (oldBestEver.getFitness().equals(bestEver.getFitness())) {
						if (++cagedAtMinGenCnt == adaptativeMutationGenerations) {
							mutationProbability = adaptativeMutationProbability;						
							log4j.info(String.format("*************** FORCE MUTATION to %.2f!", mutationProbability));
							state = StatesEnum.TEST_FREEDOM;
						}
					} else {
						oldBestEver = bestEver;
						cagedAtMinGenCnt = 0;
					}
					break;
				case TEST_FREEDOM :
					++cagedAtMinGenCnt;
					if (oldBestEver.getFitness().compareTo(bestEver.getFitness()) > 0) {
						log4j.info(String.format("*************** FREED FROM LOCAL MINIMUM AFTER %d GENERATIONS!", cagedAtMinGenCnt));
						oldBestEver = bestEver;
						cagedAtMinGenCnt = 0;
						mutationProbability = oldMutationProbability;		
						state = StatesEnum.TEST_CAGED_AT_LOCAL_MINIMUM;
					}
					break;
			} // switch...
		} // adapt
	}
	
	private class LocalSearch {
		private Individual<T> oldBestEver;
		private int cagedAtMinGenCnt;		
		private StatesEnum state = StatesEnum.INITIALIZE;
		
		public void search() {
			switch (state) {
				case INITIALIZE :
					oldBestEver = bestEver;
					cagedAtMinGenCnt  = 0;
					state = StatesEnum.TEST_CAGED_AT_LOCAL_MINIMUM;
					break;
				case TEST_CAGED_AT_LOCAL_MINIMUM :
					if (oldBestEver.getFitness().equals(bestEver.getFitness())) {
						if (++cagedAtMinGenCnt == localSearchGenerations) {
							log4j.info("*************** Before improvement: "+bestEver);
							bestEver = localSearchAlgorithm.improve(bestEver);
							log4j.info("*************** After improvement:  "+bestEver);									
							state = StatesEnum.INITIALIZE;
							oldBestEver = bestEver;
							cagedAtMinGenCnt = 0;
						}
					} else {
						oldBestEver = bestEver;
						cagedAtMinGenCnt = 0;
					}
					break;
				default:
					break;
			} // switch...
		} // search...
	}	
	
}
