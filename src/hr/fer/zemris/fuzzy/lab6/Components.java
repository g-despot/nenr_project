package hr.fer.zemris.fuzzy.lab6;

public class Components {
	private double alpha;
	private double beta;
	private double gamma;
	private double z;

	public Components(double alpha, double beta, double gamma, double z) {
		this.alpha = alpha;
		this.beta = beta;
		this.gamma = gamma;
		this.z = z;
	}

	public double getAlpha() {
		return alpha;
	}

	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}

	public double getBeta() {
		return beta;
	}

	public void setBeta(double beta) {
		this.beta = beta;
	}

	public double getGamma() {
		return gamma;
	}

	public void setGamma(double gamma) {
		this.gamma = gamma;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}
}