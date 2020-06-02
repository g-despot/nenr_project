package hr.fer.zemris.fuzzy.lab3;

import java.util.List;

import hr.fer.zemris.fuzzy.lab1.domain.DomainElement;
import hr.fer.zemris.fuzzy.lab1.domain.IDomain;
import hr.fer.zemris.fuzzy.lab1.domain.SimpleDomain;
import hr.fer.zemris.fuzzy.lab1.fuzzySet.IFuzzySet;
import hr.fer.zemris.fuzzy.lab1.fuzzySet.MutableFuzzySet;
import hr.fer.zemris.fuzzy.lab1.operations.Operations;

public class FuzzyLogic {

	public static IFuzzySet conclude(List<IFuzzySet> rules, DomainElement domainElement) {
		IDomain domain = new SimpleDomain(0, 11);
		IFuzzySet globalResult = new MutableFuzzySet(domain);
		DomainElement c;

		for (IFuzzySet rule : rules) {
			MutableFuzzySet localResult = new MutableFuzzySet(domain);
			for (DomainElement domainElementY : domain) {
				c = DomainElement.combine(domainElement, domainElementY);
				localResult.set(domainElementY, rule.getValueAt(c));
			}
			globalResult = Operations.binaryOperation(globalResult, localResult, Operations.zadehOr());
		}
		return globalResult;
	}
}