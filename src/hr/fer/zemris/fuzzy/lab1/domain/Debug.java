package hr.fer.zemris.fuzzy.lab1.domain;

import hr.fer.zemris.fuzzy.lab1.fuzzySet.IFuzzySet;

public class Debug {
	public static void print(IDomain domain, String headingText) {
		System.out.println(headingText);
		for (var e : domain) {
			System.out.println("Element domene je: " + e);
		}
		System.out.println("Kardinalitit domene je: " + domain.getCardinality());
	}

	public static void print(IFuzzySet set, String string) throws Exception {
		System.out.println(string);
		for (var e : set.getDomain()) {
			System.out.printf("d(%d)", e.getComponentValue(0));
			System.out.printf("=%6f", set.getValueAt(e));
			System.out.println();
		}
	}
}
