package cefet.rba.operators;

import java.util.Random;

public class Operator {
	
	protected final Random random = new Random();
	protected int l1, l2;
	
	public int getL1() {
		return l1;
	}

	public void setL1(int l1) {
		this.l1 = l1;
	}


	public int getL2() {
		return l2;
	}

	public void setL2(int l2) {
		this.l2 = l2;
	}

	public void setRange(int size) {
		do {
			l1 = random.nextInt(size);
			l2 = random.nextInt(size);
		} while (l1 == l2);
		if (l2 < l1) {
			int t = l1;
			l1 = l2;
			l2 = t;
		}		
	}
}
