package hr.fer.zemris.fuzzy.lab3;

import java.util.ArrayList;

import hr.fer.zemris.fuzzy.lab1.domain.DomainElement;
import hr.fer.zemris.fuzzy.lab1.domain.IDomain;
import hr.fer.zemris.fuzzy.lab1.domain.SimpleDomain;
import hr.fer.zemris.fuzzy.lab1.fuzzySet.CalculatedFuzzySet;
import hr.fer.zemris.fuzzy.lab1.fuzzySet.IFuzzySet;
import hr.fer.zemris.fuzzy.lab1.fuzzySet.StandardFuzzySets;
import hr.fer.zemris.fuzzy.lab1.operations.IBinaryFunction;
import hr.fer.zemris.fuzzy.lab1.operations.Operations;

public class KormiloFuzzySystemMin {
	private ArrayList<IFuzzySet> rules = new ArrayList<>();
	private COADefuzzifier defuzzifier;
	private IDomain locationDomain = new SimpleDomain(0, 11);
	private IDomain angleDomain = new SimpleDomain(0, 11);

	public KormiloFuzzySystemMin(COADefuzzifier def, IBinaryFunction tNorm) {
		this.defuzzifier = def;
		initRulesDatabase(tNorm);
	}

	private void initRulesDatabase(IBinaryFunction tNorm) {
		IFuzzySet locationNB = new CalculatedFuzzySet(locationDomain, StandardFuzzySets.lFunction(0, 3));
		IFuzzySet locationNS = new CalculatedFuzzySet(locationDomain, StandardFuzzySets.lambdaFunction(2, 3, 5));
		IFuzzySet locationZ = new CalculatedFuzzySet(locationDomain, StandardFuzzySets.lambdaFunction(4, 5, 6));
		IFuzzySet locationPS = new CalculatedFuzzySet(locationDomain, StandardFuzzySets.lambdaFunction(5, 7, 8));
		IFuzzySet locationPB = new CalculatedFuzzySet(locationDomain, StandardFuzzySets.gammaFunction(7, 10));

		IFuzzySet angleNB = new CalculatedFuzzySet(angleDomain, StandardFuzzySets.lFunction(0, 3));
		IFuzzySet angleNS = new CalculatedFuzzySet(angleDomain, StandardFuzzySets.lambdaFunction(2, 3, 5));
		IFuzzySet angleZ = new CalculatedFuzzySet(angleDomain, StandardFuzzySets.lambdaFunction(4, 5, 6));
		IFuzzySet anglePS = new CalculatedFuzzySet(angleDomain, StandardFuzzySets.lambdaFunction(5, 7, 8));
		IFuzzySet anglePB = new CalculatedFuzzySet(angleDomain, StandardFuzzySets.gammaFunction(7, 10));

		rules.add(imply(locationNB, locationNB, angleNB, tNorm));
		rules.add(imply(locationNB, locationNS, angleNB, tNorm));
		rules.add(imply(locationNB, locationZ, angleNS, tNorm));
		rules.add(imply(locationNB, locationPS, angleNS, tNorm));
		rules.add(imply(locationNB, locationPB, angleZ, tNorm));

		rules.add(imply(locationNS, locationNB, angleNB, tNorm));
		rules.add(imply(locationNS, locationNS, angleNS, tNorm));
		rules.add(imply(locationNS, locationZ, angleNS, tNorm));
		rules.add(imply(locationNS, locationPS, angleNS, tNorm));
		rules.add(imply(locationNS, locationPB, angleZ, tNorm));

		rules.add(imply(locationZ, locationNB, angleNB, tNorm));
		rules.add(imply(locationZ, locationNS, angleNS, tNorm));
		rules.add(imply(locationZ, locationZ, angleZ, tNorm));
		rules.add(imply(locationZ, locationPS, anglePS, tNorm));
		rules.add(imply(locationZ, locationPB, anglePB, tNorm));

		rules.add(imply(locationPS, locationNB, angleZ, tNorm));
		rules.add(imply(locationPS, locationNS, anglePS, tNorm));
		rules.add(imply(locationPS, locationZ, anglePS, tNorm));
		rules.add(imply(locationPS, locationPS, anglePB, tNorm));
		rules.add(imply(locationPS, locationPB, anglePB, tNorm));

		rules.add(imply(locationPB, locationNB, angleZ, tNorm));
		rules.add(imply(locationPB, locationNS, anglePS, tNorm));
		rules.add(imply(locationPB, locationZ, anglePS, tNorm));
		rules.add(imply(locationPB, locationPS, anglePS, tNorm));
		rules.add(imply(locationPB, locationPB, anglePB, tNorm));
	}

	public IFuzzySet imply(IFuzzySet fuzzySetOne, IFuzzySet fuzzySetTwo, IFuzzySet fuzzySetThree,
			IBinaryFunction binaryFunction) {
		return Operations.cartesianProduct(Operations.cartesianProduct(fuzzySetOne, fuzzySetTwo, binaryFunction),
				fuzzySetThree, Operations.zadehAnd());
	}

	public int conclude(int L, int D, int LK, int DK, int v, int s) {
		int location = (int) ((double) (L) / (L + D) * 10);
		int angleLocation = (int) ((double) (LK) / (LK + DK) * 10);

		DomainElement kormiloInput = DomainElement.of(new int[] { location, angleLocation });
		IFuzzySet conclusion = FuzzyLogic.conclude(rules, kormiloInput);
		int result = defuzzifier.defuzzify(conclusion);
		result = result * 18 - 90;
		return result;
	}

	public ArrayList<IFuzzySet> getRules() {
		return rules;
	}

	public void setRules(ArrayList<IFuzzySet> rules) {
		this.rules = rules;
	}

	public IFuzzySet getRule(int i) {
		return rules.get(i);
	}

	public COADefuzzifier getDefuzzifier() {
		return defuzzifier;
	}

	public void setDefuzzifier(COADefuzzifier defuzzifier) {
		this.defuzzifier = defuzzifier;
	}
}