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

public class AkcelFuzzySystemMin {
	private COADefuzzifier defuzzifier;
	private ArrayList<IFuzzySet> rules;

	public AkcelFuzzySystemMin(COADefuzzifier defuzzifier, IBinaryFunction tNorm) {
		this.defuzzifier = defuzzifier;
		this.rules = new ArrayList<>();
		initRulesDatabase(tNorm);
	}

	private void initRulesDatabase(IBinaryFunction tNorm) {
		IDomain locationDomain = new SimpleDomain(0, 11);
		IDomain speedDomain = new SimpleDomain(0, 11);
		IDomain akcelDomain = new SimpleDomain(0, 11);

		IFuzzySet locationN = new CalculatedFuzzySet(locationDomain, StandardFuzzySets.lFunction(0, 4));
		IFuzzySet locationZ = new CalculatedFuzzySet(locationDomain, StandardFuzzySets.lambdaFunction(3, 5, 7));
		IFuzzySet locationP = new CalculatedFuzzySet(locationDomain, StandardFuzzySets.gammaFunction(6, 10));

		IFuzzySet speedN = new CalculatedFuzzySet(speedDomain, StandardFuzzySets.lFunction(0, 4));
		IFuzzySet speedZ = new CalculatedFuzzySet(speedDomain, StandardFuzzySets.lambdaFunction(3, 5, 7));
		IFuzzySet speedP = new CalculatedFuzzySet(speedDomain, StandardFuzzySets.gammaFunction(6, 10));

		IFuzzySet accelerationN = new CalculatedFuzzySet(akcelDomain, StandardFuzzySets.lFunction(0, 4));
		IFuzzySet accelerationZ = new CalculatedFuzzySet(akcelDomain, StandardFuzzySets.lambdaFunction(3, 5, 9));
		IFuzzySet accelerationP = new CalculatedFuzzySet(akcelDomain, StandardFuzzySets.gammaFunction(8, 10));

		rules.add(imply(locationN, locationN, speedN, accelerationP, tNorm));
		rules.add(imply(locationN, locationZ, speedN, accelerationP, tNorm));
		rules.add(imply(locationN, locationP, speedN, accelerationN, tNorm));
		rules.add(imply(locationZ, locationN, speedN, accelerationN, tNorm));
		rules.add(imply(locationZ, locationZ, speedN, accelerationP, tNorm));
		rules.add(imply(locationZ, locationP, speedN, accelerationN, tNorm));
		rules.add(imply(locationP, locationN, speedN, accelerationN, tNorm));
		rules.add(imply(locationP, locationZ, speedN, accelerationP, tNorm));
		rules.add(imply(locationP, locationP, speedN, accelerationN, tNorm));

		rules.add(imply(locationN, locationN, speedZ, accelerationN, tNorm));
		rules.add(imply(locationN, locationZ, speedZ, accelerationP, tNorm));
		rules.add(imply(locationN, locationP, speedZ, accelerationN, tNorm));
		rules.add(imply(locationZ, locationN, speedZ, accelerationN, tNorm));
		rules.add(imply(locationZ, locationZ, speedZ, accelerationZ, tNorm));
		rules.add(imply(locationZ, locationP, speedZ, accelerationN, tNorm));
		rules.add(imply(locationP, locationN, speedZ, accelerationN, tNorm));
		rules.add(imply(locationP, locationZ, speedZ, accelerationP, tNorm));
		rules.add(imply(locationP, locationP, speedZ, accelerationN, tNorm));

		rules.add(imply(locationN, locationN, speedP, accelerationP, tNorm));
		rules.add(imply(locationN, locationZ, speedP, accelerationP, tNorm));
		rules.add(imply(locationN, locationP, speedP, accelerationP, tNorm));
		rules.add(imply(locationZ, locationN, speedP, accelerationP, tNorm));
		rules.add(imply(locationZ, locationZ, speedP, accelerationP, tNorm));
		rules.add(imply(locationZ, locationP, speedP, accelerationP, tNorm));
		rules.add(imply(locationP, locationN, speedP, accelerationP, tNorm));
		rules.add(imply(locationP, locationZ, speedP, accelerationP, tNorm));
		rules.add(imply(locationP, locationP, speedP, accelerationP, tNorm));
	}

	public IFuzzySet imply(IFuzzySet fuzzySetOne, IFuzzySet fuzzySetTwo, IFuzzySet fuzzySetThree,
			IFuzzySet fuzzySetFour, IBinaryFunction binaryFunction) {
		IFuzzySet fuzzySetCombined = Operations.cartesianProduct(fuzzySetOne, fuzzySetTwo, binaryFunction);
		return Operations.cartesianProduct(Operations.cartesianProduct(fuzzySetCombined, fuzzySetThree, binaryFunction),
				fuzzySetFour, Operations.zadehAnd());
	}

	public int conclude(int L, int D, int LK, int DK, int v, int s) {
		int location = (int) ((double) (L) / (L + D) * 10);
		int angleLocation = (int) ((double) (LK) / (LK + DK) * 10);
		int speed = (int) (v - 10) / 3;

		DomainElement akcelInput = DomainElement.of(new int[] { location, angleLocation, speed });
		IFuzzySet conclusion = FuzzyLogic.conclude(rules, akcelInput);
		int result = defuzzifier.defuzzify(conclusion);
		result = result - 1;
		return result;
	}

	public COADefuzzifier getDefuzzifier() {
		return defuzzifier;
	}

	public void setDefuzzifier(COADefuzzifier defuzzifier) {
		this.defuzzifier = defuzzifier;
	}

	public ArrayList<IFuzzySet> getRules() {
		return rules;
	}

	public IFuzzySet getRule(int i) {
		return rules.get(i);
	}

	public void setRules(ArrayList<IFuzzySet> rules) {
		this.rules = rules;
	}
}