package hr.fer.zemris.fuzzy.lab1.fuzzySet;

import hr.fer.zemris.fuzzy.lab1.domain.DomainElement;
import hr.fer.zemris.fuzzy.lab1.domain.IDomain;

public interface IFuzzySet {
	IDomain getDomain();

	double getValueAt(DomainElement domainElement);
}
