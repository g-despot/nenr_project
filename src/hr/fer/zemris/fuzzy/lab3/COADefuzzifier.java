package hr.fer.zemris.fuzzy.lab3;

import hr.fer.zemris.fuzzy.lab1.domain.DomainElement;
import hr.fer.zemris.fuzzy.lab1.fuzzySet.IFuzzySet;

public class COADefuzzifier {

	public COADefuzzifier() {
	}

	public int defuzzify(IFuzzySet fuzzySet) {
		double sumMuTimesX = 0;
		double sumMu = 0;
		double coa;
		for (DomainElement domainElement : fuzzySet.getDomain()) {
			sumMuTimesX = sumMuTimesX + fuzzySet.getValueAt(domainElement) * domainElement.getComponentValue(0);
			sumMu = sumMu + fuzzySet.getValueAt(domainElement);
		}

		if (sumMu == 0) {
			return -1;
		}

		coa = sumMuTimesX / sumMu;
		return (int) coa;
	}
}