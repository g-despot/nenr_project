package hr.fer.zemris.fuzzy.lab1.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Domain implements IDomain {

	public Domain() {
	}

	public static IDomain intRange(int startElement, int endElement) {
		return new SimpleDomain(startElement, endElement);
	}

	public static IDomain combine(IDomain domainOne, IDomain domainTwo) {
		try {
			List<IDomain> list = new ArrayList<>();
			list.addAll(Arrays.asList(domainOne.getComponents()));
			list.addAll(Arrays.asList(domainTwo.getComponents()));
			return new CompositeDomain(list.toArray(new SimpleDomain[list.size()]));
		} catch (Exception e) {
			throw new IllegalArgumentException();
		}
	}
}
