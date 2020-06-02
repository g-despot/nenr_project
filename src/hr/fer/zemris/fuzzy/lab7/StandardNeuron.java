package hr.fer.zemris.fuzzy.lab7;

import java.util.List;
import java.util.Random;

public class StandardNeuron implements INeuron {

	private float[] w;
	private int size;

	public StandardNeuron() {
	}

	public StandardNeuron(float[] w) {
		this.w = w;
		this.size = w.length;
	}

	public void random(int size) {
		Random random = new Random();

		float[] w = new float[size + 1];

		for (int i = 0; i < size + 1; i++) {
			w[i] = random.nextFloat();
		}

		this.w = w;
		this.size = size;
	}

	public float result(float[] x) {
		float sum = w[0];
		float functionResult;

		for (int i = 1; i < this.size; i++) {
			sum = sum + w[i] * x[i - 1];
		}
		functionResult = f(sum);

		return functionResult;
	}

	public float f(float net) {
		return (float) (1 / (1 + Math.exp(net * -1)));
	}

	public void setValues(List<Float> values) {
		setW(values);
	}

	public void printValues() {
		System.out.print("w: ");
		for (int i = 0; i < w.length; i++) {
			System.out.print(w[i] + " ");
		}
		System.out.print("\n");
	}

	public int getSize() {
		return this.size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public float[] getW() {
		return w;
	}

	public void setW(List<Float> values) {
		for (int i = 0; i < values.size(); i++) {
			this.w[i] = values.get(i);
		}
	}
}