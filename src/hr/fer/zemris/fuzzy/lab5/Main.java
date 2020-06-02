package hr.fer.zemris.fuzzy.lab5;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.SwingUtilities;

public class Main {
	private static Map<String, List<double[]>> symbolsMap;
	private static List<String> symbols = List.of("alpha", "beta", "gamma", "delta", "epsilon");
	private static List<double[]> symbolCodes;

	public static void main(String[] args) {

		float learningRate = 0.1f;
		int maxIterations = 1000000;
		float errorTreshold = 0.0001f;
		int symbolInstances = 3;
		int numberOfLayers = 4;

		int[] numberOfNeurons = { 20, 8, 8, 5 };
		int numberOfGestures = 10;

		if (numberOfNeurons[0] != 2 * numberOfGestures) {
			throw new IllegalArgumentException("First layer must have " + numberOfGestures * 2 + " neurons.");
		}
		if (numberOfNeurons[numberOfNeurons.length - 1] != 5) {
			throw new IllegalArgumentException("Last layer must have 5 neurons.");
		}

		initSymbolsMap();
		initSymbolCodes();

		NeuralNetwork neuralNetwork = new NeuralNetwork(numberOfLayers, learningRate, numberOfNeurons, maxIterations,
				errorTreshold);

		SwingUtilities.invokeLater(
				() -> new Frame(symbolsMap, symbols, symbolCodes, numberOfGestures, symbolInstances, neuralNetwork)
						.setVisible(true));
	}

	private static void initSymbolsMap() {
		symbolsMap = new HashMap<>();
		for (String symbol : symbols) {
			symbolsMap.put(symbol, new ArrayList<>());
		}
	}

	private static void initSymbolCodes() {
		symbolCodes = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			double code[] = new double[5];
			code[i] = 1;
			symbolCodes.add(code);
		}
	}
}
