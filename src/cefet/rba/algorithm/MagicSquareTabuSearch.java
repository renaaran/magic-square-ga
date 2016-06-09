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

import java.util.ArrayDeque;
import java.util.Deque;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cefet.rba.chromossome.Chromosome;
import cefet.rba.chromossome.MagicSquareChromosome;
import cefet.rba.objectiveFunction.StandardDeviationObjectiveFunction;
import cefet.rba.selection.Individual;

public class MagicSquareTabuSearch<T> implements LocalSearchAlgorithm<T> {

	private static final Logger log4j = LogManager.getLogger(MagicSquareTabuSearch.class.getName());

	private int tabuListSize;	
	private Deque<Chromosome<T>> tabuList;
	private Individual<T> best, oldBest;
	private StandardDeviationObjectiveFunction standardDeviationObjectiveFunction;
	
	public MagicSquareTabuSearch(StandardDeviationObjectiveFunction standardDeviationObjectiveFunction) {
		this.tabuList = new ArrayDeque<Chromosome<T>>();
		this.standardDeviationObjectiveFunction = standardDeviationObjectiveFunction;
	}
	
	private void addToTabuSearch(final Chromosome<T> chromosome) {
		if (tabuList.size() == tabuListSize) {
			tabuList.removeLast();
		}
		tabuList.push(chromosome);
	}
	
	private boolean isTabuMovement(final Chromosome<T> chromosome) {
		return tabuList.contains(chromosome);
	}
	
	private Chromosome<T> swap(final Chromosome<T> chromosome, final int l1, final int l2) {
		Chromosome<T> clone = chromosome.copy();
		T gene = clone.getGene(l1);
		clone.setGene(l1, chromosome.getGene(l2));
		clone.setGene(l2, gene);
		return clone;
	}
	
	@Override
	public Individual<T> improve(final Individual<T> individual) {
		double bestFitness = Double.MAX_VALUE;
		int sz = individual.getChromosome().size();
		tabuListSize = ((sz-1)*sz)/2;
		addToTabuSearch(individual.getChromosome());
		Individual<T> current = individual;
		int generationsWithNoChange = 0;		
		while (generationsWithNoChange < 2000 && bestFitness != 0.0) {
			for (int i = 0; i < sz; i++) {
				for (int j = i+1; j < sz; j++) {
					Chromosome<T> c = swap(current.getChromosome(), i, j);
					((MagicSquareChromosome)c).calculateSummation();
					Individual<T> tmp = new Individual<T>(c, standardDeviationObjectiveFunction.evaluate(c));
					if (tmp.getFitness().compareTo(bestFitness) < 0 && !isTabuMovement(tmp.getChromosome())) {
						log4j.info("*************** TabuSearch improvement: "+tmp);
						addToTabuSearch(tmp.getChromosome());
						bestFitness = tmp.getFitness();
						oldBest = best;
						best = tmp;
					} // if...
				} // for...
			} // for..
			current = best;
			if (oldBest == best) {
				++generationsWithNoChange;
			} else {
				oldBest = best;
			}
		} // while...
		if (best.getFitness().compareTo(individual.getFitness()) < 0) {
			return best;
		}
		return individual;
	}
}
