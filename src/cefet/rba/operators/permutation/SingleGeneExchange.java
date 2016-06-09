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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cefet.rba.chromossome.Chromosome;
import cefet.rba.operators.Operator;
import cefet.rba.operators.UnaryOperator;

public class SingleGeneExchange<T> extends Operator implements UnaryOperator<T> {

	private static final Logger log4j = LogManager.getLogger(SingleGeneExchange.class.getName());

	@Override
	public Chromosome<T> evoluate(Chromosome<T> chromosome) {
		setRange(chromosome.size());
		if (log4j.isDebugEnabled()) log4j.debug("Before: "+chromosome);
		if (l1 != l2) {
			T g1 = chromosome.getGene(l1);
			chromosome.setGene(l1, chromosome.getGene(l2));
			chromosome.setGene(l2, g1);
		}
		if (log4j.isDebugEnabled()) log4j.debug("After:  "+chromosome);
		return chromosome;
	}
}
