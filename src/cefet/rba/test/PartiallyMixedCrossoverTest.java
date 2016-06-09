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
package cefet.rba.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import cefet.rba.chromossome.MagicSquareChromosome;
import cefet.rba.chromossome.MagicSquareChromosomeFactory;
import cefet.rba.operators.permutation.PartiallyMixedCrossover;

public class PartiallyMixedCrossoverTest {

	private static final Logger log4j = LogManager.getLogger(PartiallyMixedCrossoverTest.class.getName());

	@Test
	public void testEvoluate() {
		PartiallyMixedCrossover<Integer> pmc = new PartiallyMixedCrossover<>();
		MagicSquareChromosomeFactory chromosomeFactory = new MagicSquareChromosomeFactory(4);
		MagicSquareChromosome chromosome1 = (MagicSquareChromosome) chromosomeFactory.makeIt();
		MagicSquareChromosome chromosome2 = (MagicSquareChromosome) chromosomeFactory.makeIt();
		assert chromosome1 != null && chromosome2 != null;
		assert chromosome1.size() == 16 && chromosome1.size() == 16;
		pmc.evoluate(chromosome1, chromosome2);
		assert chromosome1.integrityTest() == -1;
		assert chromosome2.integrityTest() == -1;
		log4j.debug("Fim!");
	}

}
