package hr.fer.zemris.fuzzy.lab3.test;

import java.util.List;
import java.util.ArrayList;

import hr.fer.zemris.fuzzy.lab1.domain.Debug;
import hr.fer.zemris.fuzzy.lab1.domain.DomainElement;
import hr.fer.zemris.fuzzy.lab1.fuzzySet.IFuzzySet;
import hr.fer.zemris.fuzzy.lab1.operations.IBinaryFunction;
import hr.fer.zemris.fuzzy.lab1.operations.Operations;
import hr.fer.zemris.fuzzy.lab3.AkcelFuzzySystemMin;
import hr.fer.zemris.fuzzy.lab3.COADefuzzifier;
import hr.fer.zemris.fuzzy.lab3.FuzzyLogic;
import hr.fer.zemris.fuzzy.lab3.KormiloFuzzySystemMin;

public class TestTwo {

	public static void main(String[] args) throws Exception {
		int L = 1300;
		int D = 0;
		int LK = 1300;
		int DK = 0;
		int v = 10;
		int akcelRuleNumber = 26;
		int kormiloRuleNumber = 24;

		COADefuzzifier defuzzifier = new COADefuzzifier();
		IBinaryFunction tNorm = Operations.zadehAnd();

		AkcelFuzzySystemMin akcelFuzzySystemMin = new AkcelFuzzySystemMin(defuzzifier, tNorm);
		KormiloFuzzySystemMin kormiloFuzzySystemMin = new KormiloFuzzySystemMin(defuzzifier, tNorm);

		List<IFuzzySet> akcelRule = new ArrayList<>();
		akcelRule.add(akcelFuzzySystemMin.getRule(akcelRuleNumber));

		int location = (int) ((double) (L) / (L + D) * 10);
		int angleLocation = (int) ((double) (LK) / (LK + DK) * 10);

		System.out.println("location: " + location);
		System.out.println("angleLocation: " + angleLocation);

		DomainElement accelerationInput = DomainElement.of(new int[] { location, angleLocation, v });
		IFuzzySet accelerationConclusion = FuzzyLogic.conclude(akcelRule, accelerationInput);
		Debug.print(accelerationConclusion, "Akceleracija: ");

		List<IFuzzySet> kormiloRule = new ArrayList<>();
		kormiloRule.add(kormiloFuzzySystemMin.getRule(kormiloRuleNumber));

		DomainElement angleInput = DomainElement.of(location, angleLocation);
		IFuzzySet angleConclusion = FuzzyLogic.conclude(kormiloRule, angleInput);
		Debug.print(angleConclusion, "Kut: ");
	}
}