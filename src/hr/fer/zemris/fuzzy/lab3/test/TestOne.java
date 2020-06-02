package hr.fer.zemris.fuzzy.lab3.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

import hr.fer.zemris.fuzzy.lab1.operations.IBinaryFunction;
import hr.fer.zemris.fuzzy.lab1.operations.Operations;
import hr.fer.zemris.fuzzy.lab3.AkcelFuzzySystemMin;
import hr.fer.zemris.fuzzy.lab3.COADefuzzifier;
import hr.fer.zemris.fuzzy.lab3.KormiloFuzzySystemMin;

public class TestOne {

	public static void main(String[] args) throws Exception {
		int L = 0;
		int D = 0;
		int LK = 0;
		int DK = 0;
		int v = 0;
		int s = 0;
		int acceleration;
		int angle;
		String line = null;

		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

		COADefuzzifier defuzzifier = new COADefuzzifier();
		IBinaryFunction tNorm = Operations.product();
		AkcelFuzzySystemMin akcelFuzzySystemMin = new AkcelFuzzySystemMin(defuzzifier, tNorm);
		KormiloFuzzySystemMin kormiloFuzzySystemMin = new KormiloFuzzySystemMin(defuzzifier, tNorm);

		Scanner scanner = null;
		while (true) {
			line = bufferedReader.readLine();
			if (line == "KRAJ") {
				break;
			}
			if (line != "") {
				scanner = new Scanner(line);
				L = scanner.nextInt();
				D = scanner.nextInt();
				LK = scanner.nextInt();
				DK = scanner.nextInt();
				v = scanner.nextInt();
				s = scanner.nextInt();
			}

			acceleration = akcelFuzzySystemMin.conclude(L, D, LK, DK, v, s);
			angle = kormiloFuzzySystemMin.conclude(L, D, LK, DK, v, s);

			System.out.println(acceleration + " " + angle);
			System.out.flush();
		}
		scanner.close();
	}
}