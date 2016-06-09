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

import cefet.rba.chromossome.Chromosome;

public class Individual<T> {
	
	@Override
	public String toString() {
		return "Individual [fitness=" + fitness + "]";
	}

	private Chromosome<T> chromosome;
	private Double fitness;
	
	public Individual(Chromosome<T> chromosome, Double fitness) {
		this.chromosome = chromosome;
		this.fitness = fitness;
	}

	public Double getFitness() {
		return fitness;
	}

	public void setFitness(Double fitness) {
		this.fitness = fitness;
	}
	
	public Chromosome<T> getChromosome() {
		return chromosome;
	}
}
