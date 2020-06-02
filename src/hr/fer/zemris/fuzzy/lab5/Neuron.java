package hr.fer.zemris.fuzzy.lab5;

import java.util.Random;

public class Neuron {

	private double[] w;
	private double[] dw;
	private double d;
	private double o;
	private int size;

	public Neuron() {
	}

	public Neuron(double[] w) {
		this.w = w;
		this.size = w.length;
		this.dw = new double[size];
	}

	public void random(int size) {
		Random random = new Random();

		this.w = new double[size];
		for (int i = 0; i < size; i++) {
			this.w[i] = random.nextDouble() * 4 - 2;
		}

		this.size = size;
		this.dw = new double[size];
	}

	public double f(double net) {
		return 1 / (1 + Math.exp(net * (-1)));
	}

	public double[] setW(double learningRate) {
		int i = 0;
		while (i < this.size) {
			this.w[i] = this.w[i] + learningRate * dw[i];
			i++;
		}
		this.dw = new double[this.size];
		return this.w;
	}

	public void setNextDW(double[] x) {
		int i = 0;
		while (i < this.size) {
			this.dw[i] = this.dw[i] + this.d * x[i];
			i++;
		}
	}

	public double result(double[] x) {
		double net = 0;
		int i = 0;
		while (i < this.size) {
			net = net + this.w[i] * x[i];
			i++;
		}
		double activationResult = f(net);
		this.o = activationResult;
		return this.o;
	}

	public double[] getW() {
		return this.w;
	}

	public void setW(double[] w) {
		this.w = w;
	}

	public int getSize() {
		return this.size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public double getD() {
		return this.d;
	}

	public void setD(double d) {
		this.d = d;
	}

	public double getResult() {
		return this.o;
	}

	public void setResult(double o) {
		this.o = o;
	}

	public double[] getDW() {
		return this.dw;
	}

	public void setDW(double[] dw) {
		this.dw = dw;
	}
}