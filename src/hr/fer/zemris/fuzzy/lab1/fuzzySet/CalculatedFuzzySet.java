package hr.fer.zemris.fuzzy.lab1.fuzzySet;

import hr.fer.zemris.fuzzy.lab1.domain.DomainElement;
import hr.fer.zemris.fuzzy.lab1.domain.IDomain;

public class CalculatedFuzzySet implements IFuzzySet {
	private IDomain domain;
	private IIntUnaryFunction intUnaryFunction;

	public CalculatedFuzzySet(IDomain domain, IIntUnaryFunction intUnaryFunction) {
		this.domain = domain;
		this.intUnaryFunction = intUnaryFunction;
	}

	public IDomain getDomain() {
		return this.domain;
	}

	public double getValueAt(DomainElement domainElement) {
		int index = domain.indexOfElement(domainElement);
		return this.intUnaryFunction.valueAt(index);
	}
}
