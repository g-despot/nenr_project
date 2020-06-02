package hr.fer.zemris.fuzzy.lab7;

import java.util.List;
import java.util.Random;

public class DistanceNeuron implements INeuron {

	private float[] w;
	private float[] s;
	private int size;

	public DistanceNeuron() {
	}

	public DistanceNeuron(float[] w, float[] s) {
		this.w = w;
		this.s = s;
		this.size = w.length + s.length;
	}

	public void random(int size) {
		Random random = new Random();

		float[] w = new float[size];
		float[] s = new float[size];

		for (int i = 0; i < size; i++) {
			w[i] = random.nextFloat();
			s[i] = (float) ((random.nextFloat()) * 3 - 1);
		}

		this.w = w;
		this.s = s;
		this.size = w.length + s.length;
	}

	public float result(float[] x) {
		float net = 0;
		for (int i = 0; i < x.length; i++) {
			net = net + Math.abs(w[i] - x[i]) / Math.abs(s[i]);
		}
		float y = 1 / (1 + net);
		return y;
	}

	public void setValues(List<Float> values) {
		List<Float> wValues = values.subList(0, values.size() / 2);
		setW(wValues);

		List<Float> sValues = values.subList(values.size() / 2, values.size());
		setS(sValues);
	}

	public void printValues() {
		System.out.print("w: ");
		for (int i = 0; i < w.length; i++) {
			System.out.print(w[i] + " ");
		}

		System.out.print(" s: ");
		for (int i = 0; i < s.length; i++) {
			System.out.print(s[i] + " ");
		}

		System.out.print("\n");
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public float[] getS() {
		return s;
	}

	public void setS(List<Float> sValues) {
		for (int i = 0; i < sValues.size(); i++) {
			this.s[i] = sValues.get(i);
		}
	}

	public float[] getW() {
		return w;
	}

	public void setW(List<Float> wValues) {
		for (int i = 0; i < wValues.size(); i++) {
			this.w[i] = wValues.get(i);
		}
	}
}