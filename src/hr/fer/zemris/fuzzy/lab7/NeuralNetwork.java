package hr.fer.zemris.fuzzy.lab7;

import java.util.List;
import java.util.ArrayList;

public class NeuralNetwork {

	private List<NNLayer> layers;
	private int numberOfLayers;
	private int[] numberOfNeurons;
	private int numberOfValues;

	public NeuralNetwork(int numberOfLayers, int[] numberOfNeurons) {
		this.numberOfLayers = numberOfLayers;
		this.numberOfNeurons = numberOfNeurons;
		this.layers = new ArrayList<>(numberOfLayers - 1);
		initLayers();
	}

	public void initLayers() {
		List<INeuron> neuronsOne = new ArrayList<>();
		for (int j = 0; j < numberOfNeurons[1]; j++) {
			INeuron distanceNeuron = new DistanceNeuron();
			distanceNeuron.random(numberOfNeurons[0]);
			neuronsOne.add(distanceNeuron);
		}
		layers.add(new NNLayer(neuronsOne));

		for (int i = 2; i < numberOfLayers; i++) {
			List<INeuron> neuronsTwo = new ArrayList<>();

			int size = this.layers.get(this.layers.size() - 1).getNeurons().size();

			for (int j = 0; j < numberOfNeurons[i]; j++) {
				INeuron standardNeuron = new StandardNeuron();
				standardNeuron.random(size);
				neuronsTwo.add((standardNeuron));
			}

			this.layers.add(new NNLayer(neuronsTwo));
		}

		int numberOfValues = 0;
		for (NNLayer layer : this.layers) {
			for (INeuron neuron : layer.getNeurons()) {
				numberOfValues = numberOfValues + neuron.getSize();
			}
		}
		this.numberOfValues = numberOfValues;
	}

	public float[] result(float[] x) {
		float[] result = x;
		for (NNLayer layer : layers) {
			result = layer.result(result);
		}
		return result;
	}

	public void setValues(List<Float> values) {
		int index = 0;
		for (NNLayer layer : layers) {
			for (INeuron neuron : layer.getNeurons()) {
				List<Float> tmpValues = values.subList(index, index + neuron.getSize());
				neuron.setValues(tmpValues);
				index = index + neuron.getSize();
			}
		}
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

	public int getNumberOfValues() {
		return numberOfValues;
	}

	public void setNumberOfValues(int numberOfValues) {
		this.numberOfValues = numberOfValues;
	}

	public List<NNLayer> getLayers() {
		return layers;
	}

	public void setLayers(List<NNLayer> layers) {
		this.layers = layers;
	}
}