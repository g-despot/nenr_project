package hr.fer.zemris.fuzzy.lab5;

import java.util.List;
import java.util.ArrayList;

public class NNLayer {

	private List<Neuron> neurons;
	private int numberOfNeurons;
	private double[] result;

	public NNLayer(int numberOfNeurons, int numberOfWeights) {

		this.neurons = new ArrayList<>(numberOfNeurons);
		this.numberOfNeurons = numberOfNeurons;
		int i = 0;
		while (numberOfNeurons > 0 && numberOfWeights > 0 && i < numberOfNeurons) {
			Neuron neuron = new Neuron();
			neuron.random(numberOfWeights);
			this.neurons.add(neuron);
			i++;
		}
	}

	public double[] result(double[] x) {
		double[] result = new double[neurons.size()];
		int i = 0;
		while (i < this.numberOfNeurons) {
			result[i] = neurons.get(i).result(x);
			i++;
		}
		this.result = result;
		return this.result;
	}

	public void setD(double[] d) {
		int i = 0;
		while (i < this.numberOfNeurons) {
			neurons.get(i).setD(d[i]);
			i++;
		}
	}

	public void setD(NNLayer layer) {
		double[] d = new double[neurons.size()];
		double y;
		for (int i = 0; i < this.numberOfNeurons; i++) {
			y = 0;
			for (Neuron neuron : layer.neurons) {
				y = y + neuron.getW()[i] * neuron.getD();
			}
			d[i] = y * fDerivation(neurons.get(i).getResult());
		}
		int i = 0;
		while (i < this.numberOfNeurons) {
			neurons.get(i).setD(d[i]);
			i++;
		}
	}

	public void setW(double learningRate) {
		for (Neuron neuron : neurons) {
			neuron.setW(learningRate);
		}
	}

	public void setDW(double[] x) {
		for (Neuron neuron : neurons) {
			neuron.setNextDW(x);
		}
	}

	public double fDerivation(double net) {
		return net * (1 - net);
	}

	public List<Neuron> getNeurons() {
		return this.neurons;
	}

	public void setNeurons(List<Neuron> neurons) {
		for (int i = 0; i < this.numberOfNeurons; i++) {
			this.neurons.set(i, neurons.get(i));
		}
	}

	public int getNumberOfNeurons() {
		return this.numberOfNeurons;
	}

	public void setNumberOfNeurons(int numberOfNeurons) {
		this.numberOfNeurons = numberOfNeurons;
	}

	public double[] getResult() {
		return this.result;
	}

	public void setResult(double[] result) {
		this.result = result;
	}
}
