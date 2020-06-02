package hr.fer.zemris.fuzzy.lab1.domain;

public interface IDomain extends Iterable<DomainElement> {
	int getCardinality();

	IDomain getComponent(int location);

	IDomain[] getComponents();

	int getNumberOfComponents();

	int indexOfElement(DomainElement domainElement);

	DomainElement elementForIndex(int index);
}
