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
