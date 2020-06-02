package hr.fer.zemris.fuzzy.lab1.test;

import hr.fer.zemris.fuzzy.lab1.domain.Debug;
import hr.fer.zemris.fuzzy.lab1.domain.Domain;
import hr.fer.zemris.fuzzy.lab1.domain.DomainElement;
import hr.fer.zemris.fuzzy.lab1.domain.IDomain;
import hr.fer.zemris.fuzzy.lab1.fuzzySet.CalculatedFuzzySet;
import hr.fer.zemris.fuzzy.lab1.fuzzySet.IFuzzySet;
import hr.fer.zemris.fuzzy.lab1.fuzzySet.MutableFuzzySet;
import hr.fer.zemris.fuzzy.lab1.fuzzySet.StandardFuzzySets;

public class TestOne {
	public static void main(String[] args) throws Exception {
		IDomain d = Domain.intRange(0, 11); // {0,1,...,10}
		IFuzzySet set1 = new MutableFuzzySet(d)
				.set(DomainElement.of(0), 1.0)
				.set(DomainElement.of(1), 0.8)
				.set(DomainElement.of(2), 0.6)
				.set(DomainElement.of(3), 0.4)
				.set(DomainElement.of(4), 0.2);
		Debug.print(set1, "Set1:");
		IDomain d2 = Domain.intRange(-5, 6); // {-5,-4,...,4,5}
		IFuzzySet set2 = new CalculatedFuzzySet(
				d2,
				StandardFuzzySets.lambdaFunction(
						d2.indexOfElement(DomainElement.of(-4)),
						d2.indexOfElement(DomainElement.of(0)), 
						d2.indexOfElement(DomainElement.of(4))));
		Debug.print(set2, "Set2:");
	}
}
