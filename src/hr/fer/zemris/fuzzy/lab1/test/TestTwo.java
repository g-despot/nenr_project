package hr.fer.zemris.fuzzy.lab1.test;

import hr.fer.zemris.fuzzy.lab1.domain.Debug;
import hr.fer.zemris.fuzzy.lab1.domain.Domain;
import hr.fer.zemris.fuzzy.lab1.domain.DomainElement;
import hr.fer.zemris.fuzzy.lab1.domain.IDomain;
import hr.fer.zemris.fuzzy.lab1.fuzzySet.IFuzzySet;
import hr.fer.zemris.fuzzy.lab1.fuzzySet.MutableFuzzySet;
import hr.fer.zemris.fuzzy.lab1.operations.Operations;

public class TestTwo {
	public static void main(String[] args) throws Exception {
		IDomain d = Domain.intRange(0, 11);
		IFuzzySet set1 = new MutableFuzzySet(d)
				.set(DomainElement.of(0), 1.0)
				.set(DomainElement.of(1), 0.8)
				.set(DomainElement.of(2), 0.6)
				.set(DomainElement.of(3), 0.4)
				.set(DomainElement.of(4), 0.2);
		Debug.print(set1, "Set1:");
		IFuzzySet notSet1 = Operations.unaryOperation(set1, Operations.zadehNot());
		Debug.print(notSet1, "notSet1:");
		IFuzzySet union = Operations.binaryOperation(set1, notSet1, Operations.zadehOr());
		Debug.print(union, "Set1 union notSet1:");
		IFuzzySet hinters = Operations.binaryOperation(set1, notSet1, Operations.hamacherTNorm(1.0));
		Debug.print(hinters, "Set1 intersection with notSet1 using parameterised Hamacher T norm with parameter 1.0:");
	}
}
