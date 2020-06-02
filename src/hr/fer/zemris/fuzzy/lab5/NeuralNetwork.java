package hr.fer.zemris.fuzzy.lab5;

import java.util.List;
import java.util.ArrayList;

public class NeuralNetwork {

	private List<NNLayer> layers;
	private int numberOfLayers;
	private int[] numberOfNeurons;
	private int maxIterations;
	private double errorTreshold;
	private double learningRate;
	private double error;

	public NeuralNetwork(int numberOfLayers, double learningRate, int[] numberOfNeurons, int maxIterations,
			double errorTreshold) {
		this.numberOfLayers = numberOfLayers;
		this.learningRate = learningRate;
		this.numberOfNeurons = numberOfNeurons;
		this.errorTreshold = errorTreshold;
		this.maxIterations = maxIterations;
		initNeuralNetwork();
	}

	public void initNeuralNetwork() {
		this.layers = new ArrayList<>(numberOfLayers - 1);
		for (int i = 0; i < numberOfLayers - 1; i++) {
			this.layers.add(new NNLayer(numberOfNeurons[i + 1], numberOfNeurons[i]));
		}
	}

	public void backpropagationTrainNN(List<Frame.Sample> trainingSamples) {
		for (int i = 0; i < maxIterations; i++) {
			for (Frame.Sample sample : trainingSamples) {
				double[] result = result(sample.x);
				double[] d = new double[result.length];
				NNLayer end = layers.get(this.numberOfLayers - 2);

				for (int l = 0; l < d.length; l++) {
					d[l] = (sample.y[l] - result[l]) * fDerivation(result[l]);
				}
				end.setD(d);

				NNLayer a, b;
				int j = layers.size() - 2;
				while (j > 0) {
					a = layers.get(j - 1);
					b = layers.get(j);
					a.setD(b);
					j--;
				}

				j = 0;
				while (j < layers.size()) {
					a = layers.get(j);
					if (j == 0) {
						double[] x = sample.x;
						a.setDW(x);
					} else {
						double[] x = layers.get(j - 1).getResult();
						a.setDW(x);
					}
					j++;
				}
			}
			for (NNLayer layer : layers) {
				layer.setW(learningRate);
			}
			error(trainingSamples);
			if (error < errorTreshold) {
				break;
			}
			System.out.println("Iteration: " + i + "\tError: " + this.error);
		}
	}

	public void stohasticTrainNN(List<Frame.Sample> trainingSamples) {
		for (int i = 0; i < maxIterations; i++) {
			for (Frame.Sample sample : trainingSamples) {
				double[] result = result(sample.x);
				double[] d = new double[result.length];
				NNLayer end = layers.get(this.numberOfLayers - 2);

				for (int l = 0; l < d.length; l++) {
					d[l] = (sample.y[l] - result[l]) * fDerivation(result[l]);
				}
				end.setD(d);

				NNLayer a, b;
				int j = layers.size() - 2;
				while (j > 0) {
					a = layers.get(j - 1);
					b = layers.get(j);
					a.setD(b);
					j--;
				}

				j = 0;
				while (j < layers.size()) {
					a = layers.get(j);
					if (j == 0) {
						double[] x = sample.x;
						a.setDW(x);
					} else {
						double[] x = layers.get(j - 1).getResult();
						a.setDW(x);
					}
					j++;
				}
				for (NNLayer layer : layers) {
					layer.setW(learningRate);
				}
			}
			error(trainingSamples);
			if (error < errorTreshold) {
				break;
			}
			System.out.println("Iteration: " + i + "\tError: " + this.error);
		}
	}

	public void miniBatchTrainNN(List<Frame.Sample> trainingSamples) {

		for (int i = 0; i < maxIterations; i++) {
			for (Frame.Sample sample : trainingSamples) {
				double[] result = result(sample.x);
				double[] d = new double[result.length];

				NNLayer end = layers.get(this.numberOfLayers - 2);

				for (int l = 0; l < d.length; l++) {
					d[l] = (sample.y[l] - result[l]) * fDerivation(result[l]);
				}
				end.setD(d);

				NNLayer a, b;
				int j = 0;
				while (j < layers.size() - 2) {
					a = layers.get(j + layers.size() - 2);
					b = layers.get(j + layers.size() - 1);
					a.setD(b);
					j++;
				}

				j = 0;
				while (j < layers.size()) {
					a = layers.get(j);
					if (j == 0) {
						double[] x = sample.x;
						a.setDW(x);
					} else {
						double[] x = layers.get(j - 1).getResult();
						a.setDW(x);
					}
					j++;
				}
				for (NNLayer layer : layers) {
					layer.setW(learningRate);
				}
			}
			error(trainingSamples);
			if (error < errorTreshold) {
				break;
			}
			System.out.println("Iteration: " + i + "\tError: " + this.error);
		}
	}

	public void error(List<Frame.Sample> samples) {
		double sum = 0;

		for (Frame.Sample sample : samples) {
			double[] result = this.result(sample.x);

			for (int i = 0; i < sample.y.length; i++) {
				sum = sum + Math.pow(sample.y[i] - result[i], 2);
			}
		}
		this.error = sum / samples.size();
		return;
	}

	public double fDerivation(double net) {
		return net * (1 - net);
	}

	public double[] result(double[] x) {
		double[] tmpResult;
		double[] result = x;

		for (int i = 0; i < numberOfLayers - 1; i++) {
			tmpResult = layers.get(i).result(result);
			result = tmpResult;
		}
		return result;
	}

	public int resultSymbol(double[] x) {
		double[] tmpResult;
		double[] result = x;
		double max = 0;
		int location = 0;

		for (int i = 0; i < numberOfLayers - 1; i++) {
			tmpResult = layers.get(i).result(result);
			result = tmpResult;
		}

		int i = 0;
		while (i < result.length) {
			if (result[i] > max) {
				max = result[i];
				location = i;
			}
			i++;
		}
		System.out.println("Result symbol: " + location);
		return location;
	}

	public List<NNLayer> getLayers() {
		return layers;
	}

	public void setLayers(List<NNLayer> layers) {
		this.layers = layers;
	}

	public int getNumberOfLayers() {
		return numberOfLayers;
	}

	public void setNumberOfLayers(int numberOfLayers) {
		this.numberOfLayers = numberOfLayers;
	}

	public int[] getNumberOfNeurons() {
		return numberOfNeurons;
	}

	public void setNumberOfNeurons(int[] numberOfNeurons) {
		this.numberOfNeurons = numberOfNeurons;
	}

	public int getMaxIterations() {
		return maxIterations;
	}

	public void setMaxIterations(int maxIterations) {
		this.maxIterations = maxIterations;
	}

	public double getErrorTreshold() {
		return errorTreshold;
	}

	public void setErrorTreshold(double errorTreshold) {
		this.errorTreshold = errorTreshold;
	}

	public double getLearningRate() {
		return learningRate;
	}

	public void setLearningRate(double learningRate) {
		this.learningRate = learningRate;
	}

	public double getError() {
		return error;
	}

	public void setError(double error) {
		this.error = error;
	}
}