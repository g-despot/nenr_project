package hr.fer.zemris.fuzzy.lab3.test;

import hr.fer.zemris.fuzzy.lab1.operations.IBinaryFunction;
import hr.fer.zemris.fuzzy.lab1.operations.Operations;
import hr.fer.zemris.fuzzy.lab3.AkcelFuzzySystemMin;
import hr.fer.zemris.fuzzy.lab3.COADefuzzifier;
import hr.fer.zemris.fuzzy.lab3.KormiloFuzzySystemMin;

public class TestThree {

	public static void main(String[] args) {
		int L = 600;
		int D = 200;
		int LK = 600;
		int DK = 200;
		int v = 10;
		int s = 1;
		
		COADefuzzifier defuzzifier = new COADefuzzifier();
		IBinaryFunction tNorm = Operations.zadehAnd();

		KormiloFuzzySystemMin kormiloFuzzySystemMin = new KormiloFuzzySystemMin(defuzzifier, tNorm);
		AkcelFuzzySystemMin akcelFuzzySystemMin = new AkcelFuzzySystemMin(defuzzifier, tNorm);
		
		System.out.println("Kut: " + kormiloFuzzySystemMin.conclude(L, D, LK, DK, v, s));
		System.out.println("Akceleracija: " + akcelFuzzySystemMin.conclude(L, D, LK, DK, v, s));
	}
}