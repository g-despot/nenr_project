package hr.fer.zemris.fuzzy.lab7;

import java.util.List;

public class NNLayer {

	private List<INeuron> neurons;

	public NNLayer(List<INeuron> neurons) {
		this.neurons = neurons;
	}

	public float[] result(float[] x) {
		float[] result = new float[neurons.size()];
		int i = 0;
		for (INeuron neuron : neurons) {
			result[i] = neuron.result(x);
			i++;
		}

		return result;
	}

	public List<INeuron> getNeurons() {
		return neurons;
	}

	public void setNeurons(List<INeuron> neurons) {
		this.neurons = neurons;
	}
}