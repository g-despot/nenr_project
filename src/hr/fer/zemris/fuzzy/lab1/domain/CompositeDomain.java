package hr.fer.zemris.fuzzy.lab1.domain;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class CompositeDomain extends Domain {

	private SimpleDomain[] compositeDomain;

	public CompositeDomain(SimpleDomain... simpleDomains) {
		this.compositeDomain = simpleDomains;
	}

	public Iterator<DomainElement> iterator() {
		return new CompositeDomainIterator();
	}

	public int getCardinality() {
		int cardinality = 1;
		for (int i = 0; i < compositeDomain.length; i = i + 1) {
			cardinality = cardinality * compositeDomain[i].getCardinality();
		}
		return cardinality;
	}

	public IDomain getComponent(int location) {
		return compositeDomain[location];
	}

	public SimpleDomain[] getComponents() {
		return compositeDomain;
	}

	public int getNumberOfComponents() {
		return compositeDomain.length;
	}

	public int indexOfElement(DomainElement domainElement) {
		int sum = 0;
		for (int i = 0; i < this.getNumberOfComponents(); i++) {
			sum = sum + compositeDomain[i].indexOfElement(DomainElement.of(domainElement.getComponentValue(i)))
					* nextDomain(i);
		}
		return sum;
	}

	public DomainElement elementForIndex(int index) {
		int[] values = new int[getNumberOfComponents()];

		for (int i = 0; i < getNumberOfComponents(); i++) {
			int element = (index / nextDomain(i)) % compositeDomain[i].getCardinality();
			values[i] = compositeDomain[i].elementForIndex(element).getComponentValue(0);
		}
		return new DomainElement(values);
	}

	private int nextDomain(int i) {
		i++;
		if (i >= compositeDomain.length) {
			return 1;
		}

		return compositeDomain[i].getCardinality() * nextDomain(i);
	}

	private class CompositeDomainIterator implements Iterator<DomainElement> {

		private List<Iterator<DomainElement>> domainElementIterators = new ArrayList<>();
		private List<Integer> componentValue = new ArrayList<>();
		private int counter = 0;
		private boolean finished = false;
		private DomainElement domainElement;

		public CompositeDomainIterator() {
			for (SimpleDomain domain : compositeDomain) {
				domainElementIterators.add(domain.iterator());
			}
			for (Iterator<DomainElement> domainElementIterator : domainElementIterators) {
				Integer temp = domainElementIterator.next().getComponentValue(0);
				componentValue.add(temp);
			}
		}

		public boolean hasNext() {
			return counter < getCardinality();
		}

		public DomainElement next() {

			if (!finished) {
				counter++;
				finished = true;
				domainElement = new DomainElement(componentValue.stream().mapToInt(i -> i).toArray());
				return domainElement;
			}

			for (int i = domainElementIterators.size() - 1; i >= 0; i--) {
				if (!domainElementIterators.get(i).hasNext()) {
					domainElementIterators.set(i, compositeDomain[i].iterator());
					Integer temp1 = domainElementIterators.get(i).next().getComponentValue(0);
					componentValue.set(i, temp1);
				} else {
					Integer temp2 = domainElementIterators.get(i).next().getComponentValue(0);
					componentValue.set(i, temp2);
					break;
				}
			}

			domainElement = new DomainElement(componentValue.stream().mapToInt(i -> i).toArray());
			counter++;
			return domainElement;
		}
	}
}