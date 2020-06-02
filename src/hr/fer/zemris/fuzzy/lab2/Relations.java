package hr.fer.zemris.fuzzy.lab2;

import hr.fer.zemris.fuzzy.lab1.domain.CompositeDomain;
import hr.fer.zemris.fuzzy.lab1.domain.DomainElement;
import hr.fer.zemris.fuzzy.lab1.domain.IDomain;
import hr.fer.zemris.fuzzy.lab1.domain.SimpleDomain;
import hr.fer.zemris.fuzzy.lab1.fuzzySet.IFuzzySet;
import hr.fer.zemris.fuzzy.lab1.fuzzySet.MutableFuzzySet;

public class Relations {

	public Relations() {
	}

	public static boolean isSymmetric(IFuzzySet relation) {

		IDomain domain = relation.getDomain();

		if (!isUtimesURelation(relation)) {
			return false;
		}

		for (DomainElement domainElement : domain) {
			DomainElement symmetricDomain = DomainElement.of(domainElement.getComponentValue(1),
					domainElement.getComponentValue(0));
			if (relation.getValueAt(domainElement) != relation.getValueAt(symmetricDomain)) {
				return false;
			}
		}
		return true;
	}

	public static boolean isReflexive(IFuzzySet relation) {

		IDomain domain = relation.getDomain();

		if (!isUtimesURelation(relation)) {
			return false;
		}

		for (DomainElement domainElement : domain) {
			if (domainElement.getComponentValue(0) == domainElement.getComponentValue(1)
					&& relation.getValueAt(domainElement) != 1)
				return false;
		}
		return true;
	}

	public static boolean isMaxMinTransitive(IFuzzySet relation) {

		IDomain domain = relation.getDomain();

		if (!isUtimesURelation(relation)) {
			return false;
		}

		for (DomainElement domainElementOne : domain) {
			int valueAtZero1 = domainElementOne.getComponentValue(0);
			int valueAtOne1 = domainElementOne.getComponentValue(1);

			for (DomainElement domainElementTwo : domain) {
				int valueAtZero2 = domainElementTwo.getComponentValue(0);
				int valueAtOne2 = domainElementTwo.getComponentValue(1);

				if (valueAtOne1 != valueAtZero2)
					continue;
				if (relation.getValueAt(DomainElement.of(valueAtZero1, valueAtOne2)) < Math
						.min(relation.getValueAt(domainElementOne), relation.getValueAt(domainElementTwo)))
					return false;
			}
		}
		return true;
	}

	public static boolean isFuzzyEquivalence(IFuzzySet relation) {
		return Relations.isReflexive(relation) && Relations.isSymmetric(relation)
				&& Relations.isMaxMinTransitive(relation);
	}

	public static IFuzzySet compositionOfBinaryRelations(IFuzzySet relationOne, IFuzzySet relationTwo) {

		IDomain U = relationOne.getDomain().getComponent(0);
		IDomain V = relationOne.getDomain().getComponent(1);
		IDomain V2 = relationTwo.getDomain().getComponent(0);
		IDomain W = relationTwo.getDomain().getComponent(1);
		if (!V.equals(V2)) {
			System.out.println("The dimension is off.");
		}
		MutableFuzzySet mutableFuzzySet = new MutableFuzzySet(new CompositeDomain((SimpleDomain) U, (SimpleDomain) W));

		int deOne, deTwo, deThree;
		double value = 0;
		for (DomainElement domainElementOne : U) {
			for (DomainElement domainElementTwo : W) {
				value = 0;
				for (DomainElement domainElementThree : V) {
					deOne = domainElementOne.getComponentValue(0);
					deTwo = domainElementTwo.getComponentValue(0);
					deThree = domainElementThree.getComponentValue(0);

					value = Math.max(value, Math.min(relationOne.getValueAt(DomainElement.of(deOne, deThree)),
							relationTwo.getValueAt(DomainElement.of(deThree, deTwo))));
					mutableFuzzySet.set(DomainElement.of(deOne, deTwo), value);
				}
			}
		}
		return mutableFuzzySet;
	}

	public static boolean isUtimesURelation(IFuzzySet relation) {
		IDomain domain = relation.getDomain();
		boolean tmp = domain.getNumberOfComponents() == 2;

		if (!tmp) {
			return false;
		}

		IDomain componentOne = domain.getComponent(0);
		IDomain componentTwo = domain.getComponent(1);

		return componentOne.equals(componentTwo);
	}

}