package hr.fer.zemris.fuzzy.lab1.operations;

import hr.fer.zemris.fuzzy.lab1.domain.Domain;
import hr.fer.zemris.fuzzy.lab1.domain.DomainElement;
import hr.fer.zemris.fuzzy.lab1.domain.IDomain;
import hr.fer.zemris.fuzzy.lab1.fuzzySet.IFuzzySet;
import hr.fer.zemris.fuzzy.lab1.fuzzySet.MutableFuzzySet;

public class Operations {

	public Operations() {
	}

	public static IFuzzySet unaryOperation(IFuzzySet fuzzySet, IUnaryFunction unaryFunction) {
		IDomain domain = fuzzySet.getDomain();
		MutableFuzzySet mutableFuzzySet = new MutableFuzzySet(domain);

		for (DomainElement domainElement : domain) {
			mutableFuzzySet.set(domainElement, unaryFunction.valueAt(fuzzySet.getValueAt(domainElement)));
		}

		return mutableFuzzySet;

	}

	public static IFuzzySet binaryOperation(IFuzzySet fuzzySetOne, IFuzzySet fuzzySetTwo,
			IBinaryFunction binaryFunction) {
		IDomain domainOne = fuzzySetOne.getDomain();
		MutableFuzzySet mutableFuzzySet = new MutableFuzzySet(domainOne);

		for (DomainElement domainElement : domainOne) {
			mutableFuzzySet.set(domainElement, binaryFunction.valueAt(fuzzySetOne.getValueAt(domainElement),
					fuzzySetTwo.getValueAt(domainElement)));
		}

		return mutableFuzzySet;

	}

	public static IUnaryFunction zadehNot() {
		return (a) -> 1 - a;
	}

	public static IBinaryFunction zadehAnd() {
		return (a, b) -> Math.min(a, b);
	}

	public static IBinaryFunction zadehOr() {
		return (a, b) -> Math.max(a, b);
	}

	public static IBinaryFunction hamacherTNorm(double nu) {
		return (a, b) -> (a * b) / (nu + (1 - nu) * (a + b - a * b));
	}

	public static IBinaryFunction hamacherSNorm(double nu) {
		return (a, b) -> (a + b - (2 - nu) * a * b) / (1 - (1 - nu) * a * b);
	}

	public static IBinaryFunction product() {
		return (a, b) -> a * b;
	}

	public static IFuzzySet cartesianProduct(IFuzzySet fuzzySetOne, IFuzzySet fuzzySetTwo,
			IBinaryFunction binaryFunction) {
		IDomain domainOne = fuzzySetOne.getDomain();
		IDomain domainTwo = fuzzySetTwo.getDomain();
		IDomain domain = Domain.combine(domainOne, domainTwo);
		MutableFuzzySet cartesianProduct = new MutableFuzzySet(domain);

		for (DomainElement domainElementOne : domainOne) {
			for (DomainElement domainElementTwo : domainTwo) {
				DomainElement domainElement = DomainElement.combine(domainElementOne, domainElementTwo);
				double mu = binaryFunction.valueAt(fuzzySetOne.getValueAt(domainElementOne),
						fuzzySetTwo.getValueAt(domainElementTwo));
				cartesianProduct.set(domainElement, mu);
			}
		}
		return cartesianProduct;
	}
}
