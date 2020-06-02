package hr.fer.zemris.fuzzy.lab6;

import java.util.Random;

public class Rule {

	private double a, b, c, d, p, q, r;

	public Rule() {
	}

	public void random() {
		Random rand = new Random();

		a = rand.nextDouble();
		b = rand.nextDouble();
		c = rand.nextDouble();
		d = rand.nextDouble();
		p = rand.nextDouble();
		q = rand.nextDouble();
		r = rand.nextDouble();
	}
	
	public String toString() {
		return a + " " + b + " " + c + " " + d + " " + p + " " + q + " " + r;
	}

	public double getA() {
		return a;
	}

	public void setA(double a) {
		this.a = a;
	}

	public double getB() {
		return b;
	}

	public void setB(double b) {
		this.b = b;
	}

	public double getC() {
		return c;
	}

	public void setC(double c) {
		this.c = c;
	}

	public double getD() {
		return d;
	}

	public void setD(double d) {
		this.d = d;
	}

	public double getP() {
		return p;
	}

	public void setP(double p) {
		this.p = p;
	}

	public double getQ() {
		return q;
	}

	public void setQ(double q) {
		this.q = q;
	}

	public double getR() {
		return r;
	}

	public void setR(double r) {
		this.r = r;
	}
}