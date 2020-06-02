package hr.fer.zemris.fuzzy.lab7.genetic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.fuzzy.lab7.NeuralNetwork;
import hr.fer.zemris.fuzzy.lab7.Sample;

public class Chromosome implements IChromosome {

	private float[] parameters;
	private float fitness;
	private List<Sample> samples;
	private NeuralNetwork neuralNetwork;

	public Chromosome() {
	}

	public Chromosome(List<Sample> samples, NeuralNetwork neuralNetwork) {
		this.samples = samples;
		this.neuralNetwork = neuralNetwork;
	}

	public Chromosome(float[] parameters, List<Sample> samples, NeuralNetwork neuralNetwork) {
		this.parameters = parameters;
		this.samples = samples;
		this.neuralNetwork = neuralNetwork;
		this.fitness = fitness();
	}

	public IChromosome crossover(IChromosome parentTwo) {
		Random random = new Random();
		float rand = random.nextFloat();
		float[] crossoverParameters = new float[parameters.length];
		float[] randomCrossover = { 0.33f, 0.66f, 1 };

		if (rand < randomCrossover[0]) {
			for (int i = 0; i < this.parameters.length; i++) {
				crossoverParameters[i] = (this.parameters[i] + parentTwo.getParameters()[i]) / 2;
			}
		} else if (rand < randomCrossover[1]) {
			for (int i = 0; i < this.parameters.length; i++) {
				crossoverParameters[i] = 2 / ((1 / this.parameters[i]) + (1 / parentTwo.getParameters()[i]));
			}
		} else if (rand <= randomCrossover[2]) {
			for (int i = 0; i < this.parameters.length; i++) {
				if (rand < 0.5) {
					crossoverParameters[i] = this.parameters[i];
				} else {
					crossoverParameters[i] = parentTwo.getParameters()[i];
				}
			}
		}
		return new Chromosome(crossoverParameters, samples, neuralNetwork);
	}

	public IChromosome mutation(float[] pMutation, float[] tMutation, float[] sMutation) {
		Random random = new Random();
		float rand = random.nextFloat();

		float[] v = new float[3];
		float sum = 0;

		for (int i = 0; i < 3; i++) {
			sum = sum + tMutation[i];
		}

		for (int i = 0; i < 3; i++) {
			v[i] = tMutation[i] / sum;
		}

		for (int i = 0; i < parameters.length; i++) {
			if (rand < v[0]) {
				if (rand < pMutation[0]) {
					parameters[i] = (float) (parameters[i] + random.nextGaussian() * sMutation[0]);
				}
			} else if (rand < (v[0] + v[1])) {
				if (rand < pMutation[1]) {
					parameters[i] = (float) (parameters[i] + random.nextGaussian() * sMutation[1]);
				}
			} else {
				if (rand < pMutation[2]) {
					parameters[i] = (float) (random.nextGaussian() * sMutation[2]);
				}
			}
			rand = random.nextFloat();
		}
		this.fitness = fitness();
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
		float error;
		for (Sample sample : samples) {
			List<Float> tmpValues = new ArrayList<>();
			for (int i = 0; i < parameters.length; i++) {
				tmpValues.add(parameters[i]);
			}

			neuralNetwork.setValues(tmpValues);
			float[] result = neuralNetwork.result(sample.x);

			for (int i = 0; i < sample.y.length; i++) {
				sum = (float) (sum + Math.pow(sample.y[i] - result[i], 2));
			}
		}

		error = sum / samples.size();
		return error;
	}

	public IChromosome random() {
		Random random = new Random();
		float[] parameters = new float[neuralNetwork.getNumberOfValues()];
		for (int i = 0; i < parameters.length; i++) {
			parameters[i] = (float) random.nextGaussian();
		}
		this.parameters = parameters;
		this.fitness = fitness();
		return this;
	}

	public float[] getParameters() {
		return parameters;
	}

	public void setParameters(float[] parameters) {
		this.parameters = parameters;
	}

	public List<Sample> getSamples() {
		return samples;
	}

	public void setSamples(List<Sample> samples) {
		this.samples = samples;
	}

	public float getFitness() {
		return fitness;
	}

	public void setFitness(float fitness) {
		this.fitness = fitness;
	}

	public NeuralNetwork getNeuralNetwork() {
		return neuralNetwork;
	}

	public void setNeuralNetwork(NeuralNetwork neuralNetwork) {
		this.neuralNetwork = neuralNetwork;
	}
}