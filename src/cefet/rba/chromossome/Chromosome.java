package cefet.rba.chromossome;

public interface Chromosome<T> {
	T getGene(int locus);
	void setGene(int locus, T gene);
	int size();
	Chromosome<T> copy();
}
