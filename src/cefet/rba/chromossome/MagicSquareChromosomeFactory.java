package cefet.rba.chromossome;


public class MagicSquareChromosomeFactory implements ChromosomeFactory<Integer> {

	private final int squareSide;
	
	public MagicSquareChromosomeFactory(int squareSide) {
		this.squareSide = squareSide;
	}
	
	@Override
	public Chromosome<Integer> makeIt() {
		return new MagicSquareChromosome(squareSide);
	}
}
