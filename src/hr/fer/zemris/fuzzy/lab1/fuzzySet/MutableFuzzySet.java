package hr.fer.zemris.fuzzy.lab1.fuzzySet;

import hr.fer.zemris.fuzzy.lab1.domain.DomainElement;
import hr.fer.zemris.fuzzy.lab1.domain.IDomain;

public class MutableFuzzySet implements IFuzzySet {
	private IDomain domain;
	private double[] values;

	public MutableFuzzySet(IDomain domain) {
		this.domain = domain;
		this.values = new double[domain.getCardinality()];
		for (DomainElement domainElement: this.domain) {
			values[domain.indexOfElement(domainElement)] = this.getValueAt(domainElement);
		}
	}

	public IDomain getDomain() {
		return this.domain;
	}

	public double getValueAt(DomainElement domainElement) {
		return this.values[this.domain.indexOfElement(domainElement)];
	}

	public MutableFuzzySet set(DomainElement domainElement, double value) {
        this.values[domain.indexOfElement(domainElement)] = value;
        return this;
	}
}
