package hr.fer.zemris.fuzzy.lab6;

import java.util.List;
import java.util.ArrayList;

public class Anfis {

	private List<Rule> rules = new ArrayList<>();
	private int numberOfRules;
	private List<Sample> samples;
	private double learningRate;
	private int numberOfIterations;
	private double maxErrorTreshold;
	private String errors = "";

	public Anfis(int numberOfRules, List<Sample> samples, double learningRate, double threshold, int maxIter) {
		this.numberOfRules = numberOfRules;
		this.samples = samples;
		this.learningRate = learningRate;
		this.maxErrorTreshold = threshold;
		this.numberOfIterations = maxIter;
		initRules();
		learn();
	}

	public void initRules() {
		for (int i = 0; i < numberOfRules; i++) {
			Rule rule = new Rule();
			rule.random();
			rules.add(rule);
		}
	}

	public void learn() {
		StringBuilder stringBuilder = new StringBuilder();
		double error = maxErrorTreshold + 1;

		int i = 0;
		while (error > maxErrorTreshold && i < numberOfIterations) {
			for (Sample sample : samples) {
				gradient(sample);
			}

			error = error();
			System.out.println("Iteration: " + i + " Error: " + error);
			stringBuilder.append(error + "\n");
			i++;
		}
		errors = stringBuilder.toString();
	}

	public double error() {
		double error = 0;
		for (Sample sample : samples) {
			double o = calculate(sample.getX(), sample.getY());
			error = error + Math.pow(o - sample.getZ(), 2);
		}
		error = error / (samples.size());
		return error;
	}

	public double calculate(double x, double y) {
		List<Components> components = new ArrayList<>(rules.size());
		double alpha, beta, gamma, z, o, sumOfGamma, sumOfGammaTimesZ;

		sumOfGamma = 0;
		for (Rule rule : rules) {
			alpha = sigmoid(rule.getA(), rule.getB(), x);
			beta = sigmoid(rule.getC(), rule.getD(), y);
			gamma = alpha * beta;
			z = rule.getP() * x + rule.getQ() * y + rule.getR();
			sumOfGamma = sumOfGamma + gamma;
			components.add(new Components(alpha, beta, gamma, z));
		}

		sumOfGammaTimesZ = 0;
		for (Components component : components) {
			sumOfGammaTimesZ = sumOfGammaTimesZ + component.getGamma() * component.getZ();
		}
		o = sumOfGammaTimesZ / sumOfGamma;
		return o;
	}

	public double sigmoid(double a, double b, double xy) {
		return 1 / (1 + Math.exp(b * (xy - a)));
	}

	private void gradient(Sample sample) {
		List<Components> components = new ArrayList<>(rules.size());
		double alpha, beta, gamma, z, o, sumOfGamma, sumOfGammaTimesZ, oMinusZ;

		sumOfGamma = 0;
		for (Rule rule : rules) {
			alpha = sigmoid(rule.getA(), rule.getB(), sample.getX());
			beta = sigmoid(rule.getC(), rule.getD(), sample.getY());
			gamma = alpha * beta;
			z = rule.getP() * sample.getX() + rule.getQ() * sample.getY() + rule.getR();
			sumOfGamma = sumOfGamma + gamma;
			components.add(new Components(alpha, beta, gamma, z));
		}

		sumOfGammaTimesZ = 0;
		for (Components component : components) {
			sumOfGammaTimesZ = sumOfGammaTimesZ + component.getGamma() * component.getZ();
		}
		o = sumOfGammaTimesZ / sumOfGamma;
		oMinusZ = o - sample.getZ();

		for (int i = 0; i < rules.size(); i++) {
			Components componentI = components.get(i);

			double fsum = 0;
			for (int j = 0; j < rules.size(); j++) {
				Components other = components.get(j);
				fsum += other.getGamma() * (componentI.getZ() - other.getZ());
			}

			Rule rule = rules.get(i);

			rule.setA(rule.getA() - learningRate * oMinusZ * (fsum / Math.pow(sumOfGamma, 2)) * componentI.getBeta()
					* rule.getB() * componentI.getAlpha() * (1 - componentI.getAlpha()));

			rule.setB(rule.getB() - learningRate * oMinusZ * (fsum / Math.pow(sumOfGamma, 2)) * componentI.getBeta()
					* (rule.getA() - sample.getX()) * componentI.getAlpha() * (1 - componentI.getAlpha()));

			rule.setC(rule.getC() - learningRate * oMinusZ * (fsum / Math.pow(sumOfGamma, 2)) * componentI.getAlpha()
					* rule.getD() * componentI.getBeta() * (1 - componentI.getBeta()));

			rule.setD(rule.getD() - learningRate * oMinusZ * (fsum / Math.pow(sumOfGamma, 2)) * componentI.getAlpha()
					* (rule.getC() - sample.getY()) * componentI.getBeta() * (1 - componentI.getBeta()));

			rule.setP(rule.getP() - learningRate * oMinusZ * (componentI.getGamma() / sumOfGamma) * sample.getX());
			rule.setQ(rule.getQ() - learningRate * oMinusZ * (componentI.getGamma() / sumOfGamma) * sample.getY());
			rule.setR(rule.getR() - learningRate * oMinusZ * (componentI.getGamma() / sumOfGamma));
		}
	}

	public List<Sample> getSamples() {
		return samples;
	}

	public void setSamples(List<Sample> samples) {
		this.samples = samples;
	}

	public List<Rule> getRules() {
		return rules;
	}

	public void setRules(List<Rule> rules) {
		this.rules = rules;
	}

	public String getErrors() {
		return errors;
	}

	public void setErrors(String errors) {
		this.errors = errors;
	}
}