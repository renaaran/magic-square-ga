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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cefet.rba.operators.AfterCrossoverListener;
import cefet.rba.operators.AfterMutateListener;

public class MagicSquareChromosomeListener implements AfterCrossoverListener<Integer>, AfterMutateListener<Integer> {

	private static final Logger log4j = LogManager.getLogger(MagicSquareChromosomeListener.class.getName());

	@Override
	public void mutateListen(Chromosome<Integer> chromosome) {
		log4j.debug("mutateListen.Before: "+chromosome+" sumation = "+Arrays.toString(((MagicSquareChromosome)chromosome).getSummation()));
		((MagicSquareChromosome)chromosome).calculateSummation();
		log4j.debug("mutateListen.After:  "+chromosome+" sumation = "+Arrays.toString(((MagicSquareChromosome)chromosome).getSummation()));
	}

	@Override
	public void crossoverListen(Chromosome<Integer> chromosome) {
		log4j.debug("crossoverListen.Before: "+chromosome+" sumation = "+Arrays.toString(((MagicSquareChromosome)chromosome).getSummation()));
		((MagicSquareChromosome)chromosome).calculateSummation();
		log4j.debug("crossoverListen.After:  "+chromosome+" sumation = "+Arrays.toString(((MagicSquareChromosome)chromosome).getSummation()));
	}
}
