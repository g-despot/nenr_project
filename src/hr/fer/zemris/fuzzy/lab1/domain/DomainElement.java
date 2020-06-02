package hr.fer.zemris.fuzzy.lab1.domain;

import java.util.Arrays;
import java.util.Objects;

public class DomainElement {

	private int[] values;

	public DomainElement(int... values) {
		this.values = values;
	}

	public int getNumberOfComponents() {
		return this.values.length;
	}

	public int getComponentValue(int location) {
		return this.values[location];
	}
	
	public int hashCode() {
		return Objects.hash(this.values);
	}

	public boolean equals(Object object) {
		// System.out.println("tu sam");
		if (this == object)
			return true;
		if (object == null)
			return false;
		if (getClass() != object.getClass())
			return false;
		DomainElement domainElement = (DomainElement) object;
		return Arrays.equals(this.values, domainElement.values);
	}

	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("(");

		int count = 0;
		for (int i : values) {
			stringBuilder.append(i);
			if (count != this.values.length - 1) {
				stringBuilder.append(", ");
			}
			count++;
		}
		stringBuilder.append(")");
		return stringBuilder.toString();
	}

	public static DomainElement of(int... values) {
		return new DomainElement(values);
	}

	public static DomainElement combine(DomainElement left, DomainElement right) {
		int[] leftValues = left.getValues();
		int[] rightValues = right.getValues();

		int length = leftValues.length + rightValues.length;
		int[] result = new int[length];
		System.arraycopy(leftValues, 0, result, 0, leftValues.length);
		System.arraycopy(rightValues, 0, result, leftValues.length, rightValues.length);

		return new DomainElement(result);
	}

	public int[] getValues() {
		return values;
	}

	public void setValues(int[] values) {
		this.values = values;
	}
}
