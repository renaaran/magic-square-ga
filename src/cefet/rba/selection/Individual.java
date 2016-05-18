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
