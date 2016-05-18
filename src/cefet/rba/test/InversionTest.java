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
