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
package cefet.rba.selection;

import java.util.List;
import java.util.Random;
import java.util.TreeSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RouletteWheelSelection<T> implements Selection<T> {

	private static final Logger log4j = LogManager.getLogger(RouletteWheelSelection.class.getName());
	
	private static class Range implements Comparable<Range> {
		@Override
		public String toString() {
			return "Range [" + individual + ", low=" + low
					+ ", high=" + high + " high-low=" + (high-low) + "]";
		}

		private final Individual<?> individual;
		private final double low;
		private final double high;
		
		public Range(Individual<?> individual, double low, double high) {
			this.individual = individual;
			this.low = low;
			this.high = high;
		}

		@Override
		public int compareTo(Range r) {
			double result = this.low-r.low;
			if (result < 0) {
				return -1;
			}
			if (result > 0) {
				return 1;
			}
			return 0;
		}
	};
	
	private Random random = new Random();
	private TreeSet<Range> roulette;
	
	@SuppressWarnings("unchecked")
	private Individual<T> getIndividual() {
		Individual<T>[] tournament = new Individual[2]; 
		for (int j = 0; j < tournament.length; j++) {
			double randomNumber = random.nextDouble();
			Range result = roulette.floor(new Range(null, randomNumber, 0.0));
			assert result != null : "roulette.floor == null";
			if (log4j.isDebugEnabled()) log4j.debug("Select: rand = "+ randomNumber+" - "+result);
			tournament[j] = (Individual<T>) result.individual;
		}
		/// select the best individual between both selected
		if (tournament[0].getFitness().compareTo(tournament[1].getFitness()) < 0) {
			if (log4j.isDebugEnabled()) log4j.debug("Tournament winner: "+tournament[0]);
			return new Individual<T>(tournament[0].getChromosome().copy(), tournament[0].getFitness());
		} else {
			if (log4j.isDebugEnabled()) log4j.debug("Tournament winner: "+tournament[1]);
			return new Individual<T>(tournament[1].getChromosome().copy(), tournament[1].getFitness());
		}
	}
	
	@Override
	public void select(List<Individual<T>> population, Individual<T> best) {
		double fitnessSum = 0.0;
		// sum up the fitness of all individuals.
		for (Individual<T> individual: population) {
			fitnessSum += 1/individual.getFitness();
		}
		// divide the individual fitness by the total fitness, 
		// determining the portion of the wheel to be assign to each individual.
		double portion = 0.0;
		roulette = new TreeSet<Range>();
		for (Individual<T> individual: population) {
			Range range = new Range(individual, portion, portion += (1/individual.getFitness())/fitnessSum);
			roulette.add(range);
			if (log4j.isDebugEnabled()) log4j.debug("Add: "+range);
		}
		// sort a random number in the interval (0, 1] and compare it to the
		// interval belonging to each individual. Select the individual
		// whose interval contains the sorted number and place it in the
		// new population P.
		population.clear();
		population.add(new Individual<T>(best.getChromosome().copy(), best.getFitness()));
		for (int i = 1, sz = roulette.size(); i < sz; i++) {
			Individual<T> selectedIndividual = null;
			// this do-while check if a individual will be mate of himself
			// and avoid it...
			do { selectedIndividual = getIndividual();
			} while ((i+1) % 2 == 0 && population.get(i-1).getFitness().equals(selectedIndividual.getFitness()));
			population.add(selectedIndividual);
		}
	}
}
