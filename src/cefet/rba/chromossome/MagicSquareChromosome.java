package cefet.rba.chromossome;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Random;

import cefet.rba.config.Config;

public class MagicSquareChromosome extends IntegerArrayChromosome {

	private final static boolean mscPrintAsMatrix = Config.getInstance().getPropertyAsBoolean("mscPrintAsMatrix");
	private final Random random = new Random();
	private final int summation[];
	private final int size;
		
	public MagicSquareChromosome(int size, int p[]) {
		super(size*size);
		this.size = size;
		for (int i = 0; i < p.length; i++) chromosome[i] = p[i];
		// sum column, rows and diagonals.
		summation = new int[size*2+2];
		calculateSummation();		
	}
	
	public MagicSquareChromosome(int size) {
		super(size*size);
		this.size = size;
		// generate the magic square taking care that
		// there are no repetition on it.
		boolean exist[] = new boolean[chromosome.length];
		for (int i = 0, n = 0; i < chromosome.length; i++) {
			do n = random.nextInt(chromosome.length);
			while (exist[n]);
			exist[n] = true;			
			chromosome[i] = n+1;
		}
		// sum column, rows and diagonals.
		summation = new int[size*2+2];
		calculateSummation();
	}

	// copy constructor
	public MagicSquareChromosome(MagicSquareChromosome msc) {
		super(msc.chromosome.length);
		size = msc.size;
		chromosome = Arrays.copyOf(msc.chromosome,  msc.chromosome.length);
		summation = Arrays.copyOf(msc.summation, msc.summation.length);
	}

	public void calculateSummation() {
		// sum up columns and rows and diagonals...
		summation[size*2] = 0;
		summation[size*2+1] = 0;
		for (int i = 0, d1=0, d2=size-1; i < size; i++, d1+=size+1, d2+=size-1) {
			summation[i] = 0;
			summation[size+i] = 0;
			summation[size*2]+= chromosome[d1];
			summation[size*2+1]+= chromosome[d2];
			for (int j = 0, p = i; j < size; j++, p+=size) {
				summation[i]+= chromosome[(i*size)+j];
				summation[size+i]+= chromosome[p];
			}
		}
	}
	
	public final int[] getSummation() {
		return summation;
	}

	// verify if there is a repeated gene at the chromosome
	public int integrityTest() {
		boolean exist[] = new boolean[chromosome.length];
		for (int i = 0; i < chromosome.length; i++) {
			if (exist[i]) {
				return i;
			}
			exist[i] = true;
		}
		return -1;
	}
	
	private void printChromosomeAsArray(PrintStream ps) {
		ps.print("[ ");
		for (int i = 0; i < chromosome.length; i++) {
			ps.format("%3d ", this.getGene(i));
		}
		ps.print(" ]");
	}
	
	private void printChromosomeAsMatrix(PrintStream ps) {
		for (int i = 0; i < size; i++) {
			ps.print("-----");
		}
		ps.println();
		for (int i = 0; i < size; i++) {
			ps.print("| ");
			for (int j = 0; j < size; j++) {
				ps.format("%3d ", this.getGene((i*size)+j));
			}
			ps.println();
		}
		for (int i = 0; i < size; i++) {
			ps.print("-----");
		}
		ps.println();
		ps.print("Summation: ");
		for (int i = 0, sz = this.getSummation().length; i < sz; i++) {
			ps.format("%d ", this.getSummation()[i]);
		}
	}

	public String toStringAsMatrix() {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(os);
		printChromosomeAsMatrix(ps);
		return os.toString();		
	}

	public String toStringAsArray() {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(os);
		printChromosomeAsArray(ps);
		return os.toString();		
	}
	
	@Override
	public String toString() {
		if (mscPrintAsMatrix) {
			return toStringAsMatrix();
		} else {
			return toStringAsArray();
		}
	}
	
	@Override
	public Chromosome<Integer> copy() {
		return new MagicSquareChromosome(this);
	}
}
