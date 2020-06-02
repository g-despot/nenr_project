package hr.fer.zemris.fuzzy.lab2.test;

import hr.fer.zemris.fuzzy.lab1.domain.Domain;
import hr.fer.zemris.fuzzy.lab1.domain.DomainElement;
import hr.fer.zemris.fuzzy.lab1.domain.IDomain;
import hr.fer.zemris.fuzzy.lab1.fuzzySet.IFuzzySet;
import hr.fer.zemris.fuzzy.lab1.fuzzySet.MutableFuzzySet;
import hr.fer.zemris.fuzzy.lab2.Relations;

public class TestThree {
	public static void main(String[] args) {
		IDomain u = Domain.intRange(1, 5); // {1,2,3,4}
		IFuzzySet r = new MutableFuzzySet(Domain.combine(u, u)).set(DomainElement.of(1, 1), 1)
				.set(DomainElement.of(2, 2), 1).set(DomainElement.of(3, 3), 1).set(DomainElement.of(4, 4), 1)
				.set(DomainElement.of(1, 2), 0.3).set(DomainElement.of(2, 1), 0.3).set(DomainElement.of(2, 3), 0.5)
				.set(DomainElement.of(3, 2), 0.5).set(DomainElement.of(3, 4), 0.2).set(DomainElement.of(4, 3), 0.2);
		IFuzzySet r2 = r;
		System.out
				.println("PoÄ?etna relacija je neizrazita relacija ekvivalencije? " + Relations.isFuzzyEquivalence(r2));
		System.out.println();
		for (int i = 1; i <= 3; i++) {
			r2 = Relations.compositionOfBinaryRelations(r2, r);
			System.out.println("Broj odraÄ‘enih kompozicija: " + i + ". Relacija je:");
			for (DomainElement e : r2.getDomain()) {
				System.out.println("mu(" + e + ")=" + r2.getValueAt(e));
			}
			System.out
					.println("Ova relacija je neizrazita relacija ekvivalencije? " + Relations.isFuzzyEquivalence(r2));
			System.out.println();
		}
	}
}