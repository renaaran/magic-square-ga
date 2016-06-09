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
