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
package cefet.rba.criteria;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cefet.rba.selection.Individual;

public class MagicSquareStopCriteria<T> implements StopCriteria<T> {

	private static Double ZERO = new Double(0.0);
	private static final Logger log4j = LogManager.getLogger(MagicSquareStopCriteria.class.getName());
	
	@Override
	public boolean stop(List<Individual<T>> population) {
		for (Individual<T> individual: population) {
			if (individual.getFitness().equals(ZERO)) {
				log4j.info("BEST = "+individual);
				return true;
			} 
		}
		return false;
	}
}
