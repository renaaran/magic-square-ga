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

import cefet.rba.chromossome.Chromosome;
import cefet.rba.chromossome.MagicSquareChromosomeFactory;
import cefet.rba.operators.permutation.Inversion;

public class InversionTest {

	private static final Logger log4j = LogManager.getLogger(InversionTest.class.getName());
	
	@Test
	public void testEvoluate() {
		Inversion<Integer> inv = new Inversion<>();
		MagicSquareChromosomeFactory chromosomeFactory = new MagicSquareChromosomeFactory(4);
		Chromosome<Integer> chromosome = chromosomeFactory.makeIt();
		assert chromosome != null;
		assert chromosome.size() == 16;
		log4j.debug("Before: ");
		log4j.debug("\n"+chromosome);
		inv.evoluate(chromosome);
		log4j.debug("After: ");
		log4j.debug("\n"+chromosome);
		log4j.debug("Fim!");
	}

}
