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
package cefet.rba.chromossome;

import java.util.Arrays;

public class IntegerArrayChromosome implements Chromosome<Integer> {

	protected Integer[] chromosome;
	
	public IntegerArrayChromosome(Chromosome<Integer> c) {
		chromosome = new Integer[c.size()];
		for (int i = 0, sz = c.size(); i < sz; i++) {
			chromosome[i] = c.getGene(i);
		}
	}
	
	public IntegerArrayChromosome(int size) {
		chromosome = new Integer[size];
	}
	
	@Override
	public Integer getGene(int locus) {
		return chromosome[locus];
	}

	@Override
	public void setGene(int locus, Integer gene) {
		chromosome[locus] = gene;
	}

	@Override
	public int size() {
		return chromosome.length;
	}

	@Override
	public Chromosome<Integer> copy() {
		return new IntegerArrayChromosome(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(chromosome);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IntegerArrayChromosome other = (IntegerArrayChromosome) obj;
		if (!Arrays.equals(chromosome, other.chromosome))
			return false;
		return true;
	}
	
	
}
