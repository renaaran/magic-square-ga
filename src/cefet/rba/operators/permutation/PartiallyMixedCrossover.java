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
package cefet.rba.operators.permutation;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cefet.rba.chromossome.Chromosome;
import cefet.rba.operators.BinaryOperator;
import cefet.rba.operators.Operator;

public class PartiallyMixedCrossover<T extends Comparable<T>> extends Operator implements BinaryOperator<T> {

	private static final Logger log4j = LogManager.getLogger(PartiallyMixedCrossover.class.getName());

	private String printRangeMark(int size, int l1, int l2) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(os);
		ps.print("[ ");
		for (int i = 0; i < size; i++) {
			if (i >= l1 && i <= l2) {
				ps.format("%3c ", '+');
			} else {
				ps.format("%3c ", ' ');
			}
		}
		ps.print(" ]");
		return os.toString();
	}

	private void exchange(Chromosome<T> chromosome, int locus, T gene) {
		for (int l = 0, sz = chromosome.size(); l < sz; l++) {
			if (chromosome.getGene(l).compareTo(gene) == 0) {
				chromosome.setGene(l, chromosome.getGene(locus));
				chromosome.setGene(locus, gene);
			}
		}
	}
	
	@Override
	public void evoluate(Chromosome<T> chromosome1, Chromosome<T> chromosome2) {
		setRange(chromosome1.size());
		if (log4j.isDebugEnabled()) {
			log4j.debug("Before: ");
			log4j.debug("chromosome1: "+chromosome1);
			log4j.debug("chromosome2: "+chromosome2);
			log4j.debug("             "+printRangeMark(chromosome1.size(), l1, l2));
		}
		for (int i = l1; i <= l2; i++) {
			T g1 = chromosome1.getGene(i);
			T g2 = chromosome2.getGene(i);
			exchange(chromosome1, i, g2);
			exchange(chromosome2, i, g1);
		}
		if (log4j.isDebugEnabled()) {
			log4j.debug(String.format("l1=%d - l2=%d", l1, l2));				
			log4j.debug("After: ");
			log4j.debug("chromosome1: "+chromosome1);
			log4j.debug("chromosome2: "+chromosome2);
			log4j.debug("             "+printRangeMark(chromosome1.size(), l1, l2));
		}
	}
}
