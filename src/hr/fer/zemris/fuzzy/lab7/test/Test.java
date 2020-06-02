package hr.fer.zemris.fuzzy.lab7.test;

import java.util.List;

import hr.fer.zemris.fuzzy.lab7.INeuron;
import hr.fer.zemris.fuzzy.lab7.NNLayer;
import hr.fer.zemris.fuzzy.lab7.NeuralNetwork;
import hr.fer.zemris.fuzzy.lab7.Sample;
import hr.fer.zemris.fuzzy.lab7.genetic.Chromosome;
import hr.fer.zemris.fuzzy.lab7.genetic.EvolutionElimination;
import hr.fer.zemris.fuzzy.lab7.genetic.EvolutionGeneration;
import hr.fer.zemris.fuzzy.lab7.genetic.GeneticAlgorithm;

import java.util.ArrayList;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Test {
	public static void main(String[] args) throws IOException {

		int maxIterations = 2000;
		float[] pMutation = { 0.6f, 0.6f, 0.6f };
		float[] tMutation = { 1, 2, 1 };
		float[] sMutation = { 0.01f, 0.06f, 0.06f };
		int numberOfChromosomes = 100;
		boolean elitism = true;
		boolean eliminationAlgorithm = false;
		int[] numberOfNeurons = { 2, 8, 4, 3 };
		int numberOfLayers = 4;
		int cnt = 0;

		Path path = Paths.get("./files/datasets/lab7/zad7-dataset.txt");

		List<Sample> samples = new ArrayList<>();
		for (String line : Files.readAllLines(path)) {
			String[] parts = line.split("\t");

			float[] x = { Float.parseFloat(parts[0]), Float.parseFloat(parts[1]) };
			float[] y = { Float.parseFloat(parts[2]), Float.parseFloat(parts[3]), Float.parseFloat(parts[4]) };

			Sample sample = new Sample(x, y);
			samples.add(sample);
		}

		NeuralNetwork neuralNetwork = new NeuralNetwork(numberOfLayers, numberOfNeurons);
		GeneticAlgorithm geneticAlgorithm;
		if (eliminationAlgorithm) {
			geneticAlgorithm = new GeneticAlgorithm(numberOfChromosomes, maxIterations,
					new EvolutionElimination(pMutation, tMutation, sMutation), samples, neuralNetwork);
		} else {
			geneticAlgorithm = new GeneticAlgorithm(numberOfChromosomes, maxIterations,
					new EvolutionGeneration(elitism, pMutation, tMutation, sMutation), samples, neuralNetwork);
		}

		Chromosome result = (Chromosome) geneticAlgorithm.bestChromosome();
		System.out.println("Error: " + result.error());

		List<Float> tmpValues = new ArrayList<>();
		float[] tmp = result.getParameters();
		for (int i = 0; i < tmp.length; i++) {
			tmpValues.add(tmp[i]);
		}
		neuralNetwork.setValues(tmpValues);

		for (Sample sample : samples) {
			List<Float> tmpTwoValues = new ArrayList<>();
			for (int i = 0; i < result.getParameters().length; i++) {
				tmpTwoValues.add(result.getParameters()[i]);
			}
			neuralNetwork.setValues(tmpTwoValues);
			float[] resultValues = neuralNetwork.result(sample.x);
			float[] roundedResult = new float[resultValues.length];

			for (int i = 0; i < resultValues.length; i++) {
				if (resultValues[i] < 0.5) {
					roundedResult[i] = 0;
				} else {
					roundedResult[i] = 1;
				}
			}
			System.out.print("A= {" + sample.y[0] + ", " + sample.y[1] + ", " + sample.y[2] + "}");
			System.out.print("\tB= {" + roundedResult[0] + ", " + roundedResult[1] + ", " + roundedResult[2] + "}");
			if (roundedResult[0] == sample.y[0] && roundedResult[1] == sample.y[1] && roundedResult[2] == sample.y[2]) {
				System.out.println("\tSuccesfully clasified");
				cnt++;
			} else {
				System.out.println("\tUnsuccesfully clasified");
			}
		}

		System.out.println("Succesfully clasified = " + cnt);

		NNLayer layer = neuralNetwork.getLayers().get(0);
		for (INeuron neuron : layer.getNeurons()) {
			neuron.printValues();
		}

		layer = neuralNetwork.getLayers().get(1);
		for (INeuron neuron : layer.getNeurons()) {
			neuron.printValues();
		}
	}
}