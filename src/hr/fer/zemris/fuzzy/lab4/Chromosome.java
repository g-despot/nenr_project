package hr.fer.zemris.fuzzy.lab4;

import java.util.List;
import java.util.Random;

public class Chromosome implements IChromosome {

	private float[] b;
	private float fitness;
	private List<List<Float>> samples;

	public Chromosome() {
	}

	public Chromosome(float[] b, List<List<Float>> samples) {
		this.b = b;
		this.samples = samples;
		this.fitness = fitness();
	}

	public IChromosome crossover(IChromosome parentTwo) {
		float[] bTmp = new float[5];

		for (int i = 0; i < 5; i++) {
			bTmp[i] = (b[i] + ((Chromosome) parentTwo).b[i]) / 2;
		}
		return new Chromosome(bTmp, samples);
	}

	public IChromosome mutation(float mutation) {
		Random random = new Random();
		for (int i = 0; i < 5; i++) {
			if (random.nextFloat() <= mutation) {
				float newBeta = b[i] + (float) random.nextGaussian();
				if (newBeta < -4) {
					newBeta = -4;
				}
				if (newBeta > 4) {
					newBeta = 4;
				}
				b[i] = newBeta;
			}
		}
		fitness = fitness();
		return this;
	}

	private float fitness() {
		float error = error();
		if (error == 0) {
			return 1000000000;
		}
		return 1 / error;
	}

	public float error() {
		float sum = 0;
		float function;
		for (List<Float> sample : this.samples) {
			function = (float) Math.pow(sample.get(sample.size() - 1) - Math.sin(b[0] + b[1] * sample.get(0))
					+ b[2] * Math.cos(sample.get(0) * (b[3] + sample.get(1)))
							* (1 / (1 + Math.exp(Math.pow(sample.get(0) - b[4], 2)))),
					2);
			sum = sum + function;
		}
		sum = sum / samples.size();
		return sum;
	}

	public IChromosome random(List<List<Float>> samples) {
		this.samples = samples;
		// System.out.println(samples);
		Random random = new Random();
		float[] randomB = new float[5];
		for (int i = 0; i < 5; i++) {
			randomB[i] = (random.nextFloat() * 8) - 4;
		}
		this.b = randomB;
		this.fitness = fitness();
		return this;
	}

	public float[] getB() {
		return b;
	}

	public void setB(float[] b) {
		this.b = b;
	}

	public List<List<Float>> getSamples() {
		return samples;
	}

	public void setSamples(List<List<Float>> samples) {
		this.samples = samples;
	}

	public float getFitness() {
		return fitness;
	}

	public void setFitness(float fitness) {
		this.fitness = fitness;
	}
}