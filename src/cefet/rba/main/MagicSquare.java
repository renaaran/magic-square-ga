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
package cefet.rba.main;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cefet.rba.algorithm.MagicSquareTabuSearch;
import cefet.rba.algorithm.StandardGeneticAlgorithm;
import cefet.rba.chromossome.MagicSquareChromosome;
import cefet.rba.chromossome.MagicSquareChromosomeFactory;
import cefet.rba.chromossome.MagicSquareChromosomeListener;
import cefet.rba.criteria.MagicSquareStopCriteria;
import cefet.rba.objectiveFunction.StandardDeviationObjectiveFunction;
import cefet.rba.operators.permutation.PartiallyMixedCrossover;
import cefet.rba.operators.permutation.SingleGeneExchange;
import cefet.rba.selection.RouletteWheelSelection;

public class MagicSquare {
		
	private static final int SQUARE_SIDE = 5;	
	private static final Logger log4j = LogManager.getLogger(MagicSquare.class.getName());
	
	public static void main(String[] args) throws InterruptedException {
		Date inicio = new Date();
		log4j.info("Início...");
		int numAcertos = 0;
		for (int i = 0; i < 60; i++) {
			MagicSquareChromosomeListener magicSquareChromosomeListener = new MagicSquareChromosomeListener();
			StandardDeviationObjectiveFunction standardDeviationObjectiveFunction = new StandardDeviationObjectiveFunction(SQUARE_SIDE);			
			StandardGeneticAlgorithm<Integer> sga = new StandardGeneticAlgorithm<Integer>()
				.withPopulationSize(500)
				.withMaxGenerations(1000)
				.withCrossoverProbability(0.8)
				.withMutationProbability(0.2)
				.withObjectiveFunction(standardDeviationObjectiveFunction)
				.withChromosomeFactory(new MagicSquareChromosomeFactory(SQUARE_SIDE))
				.withSelectionAlgorithm(new RouletteWheelSelection<Integer>())
				.withStopCriteria(new MagicSquareStopCriteria<Integer>())
				.withCrossoverOperator(new PartiallyMixedCrossover<Integer>())
				.withMutationOperator(new SingleGeneExchange<Integer>())
				.withAfterCrossoverListener(magicSquareChromosomeListener)
				.withAfterMutateListener(magicSquareChromosomeListener)
				.withAdaptativeMutation(20, 0.9)
				//.withLocalSearchAlgorithm(100, new MagicSquareTabuSearch<Integer>(standardDeviationObjectiveFunction))
				.run();
			log4j.info((i+1)+") MAGIC_CONSTANT = "+((StandardDeviationObjectiveFunction)sga.getObjectiveFunction()).getMagicConstant());
			log4j.info("BEST :"+sga.getBestIndividual());
			log4j.info("\n"+((MagicSquareChromosome)sga.getBestIndividual().getChromosome()).toStringAsMatrix());
			assert ((MagicSquareChromosome)sga.getBestIndividual().getChromosome()).integrityTest() == -1 : "Integrit Test Fail";
			if (sga.getBestIndividual().getFitness().equals(0.0)) {
				++numAcertos;
				break;
			}
		}
		Date fim = new Date();
		log4j.info("NumAcertos: "+numAcertos);
		log4j.info(String.format("Tempo de processamento: %ds",(fim.getTime()-inicio.getTime())/1000));
	}

}
