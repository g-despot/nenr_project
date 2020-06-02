package hr.fer.zemris.fuzzy.lab1.domain;

import java.util.Iterator;

public class SimpleDomain extends Domain {

	private int startElement;
	private int endElement;
	public final int numberOfComponents;

	public SimpleDomain(int startElement, int endElement) {
		this.startElement = startElement;
		this.endElement = endElement;
		this.numberOfComponents = 1;
	}

	public int getCardinality() {
		return this.endElement - this.startElement;
	}

	public IDomain getComponent(int location) {
		return this;
	}

	public IDomain[] getComponents() {
		return new IDomain[] { this };
	}

	public int getNumberOfComponents() {
		return this.numberOfComponents;
	}

	public Iterator<DomainElement> iterator() {
		return new Iterator<>() {
			private int next = startElement;

			public boolean hasNext() {
				return next < endElement;
			}

			public DomainElement next() {
				DomainElement domainElement = DomainElement.of(next);
				next = next + 1;
				return domainElement;
			}
		};
	}

	public int getFirst() {
		return this.startElement;
	}

	public int getLast() {
		return this.endElement;
	}

	public int indexOfElement(DomainElement domainElement) {
		return domainElement.getComponentValue(0) - startElement;
	}

	public DomainElement elementForIndex(int index) {
		return new DomainElement(new int[] { startElement + index });
	}
}