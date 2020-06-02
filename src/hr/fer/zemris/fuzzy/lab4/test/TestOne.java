package hr.fer.zemris.fuzzy.lab4.test;

import java.util.List;

import hr.fer.zemris.fuzzy.lab4.Chromosome;
import hr.fer.zemris.fuzzy.lab4.EvolutionElimination;
import hr.fer.zemris.fuzzy.lab4.EvolutionGeneration;
import hr.fer.zemris.fuzzy.lab4.GeneticAlgorithm;

import java.util.ArrayList;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
//import java.util.Scanner;

public class TestOne {
	public static void main(String[] args) {

		Path path = Paths.get("./files/datasets/lab4/zad4-dataset1.txt");

		List<List<Float>> samples = new ArrayList<>();
		List<String> lines = null;
		try {
			lines = Files.readAllLines(path);
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (String line : lines) {
			String[] parts = line.split("\t");
			List<Float> numbers = new ArrayList<Float>();
			for (int i = 0; i < 3; ++i) {
				numbers.add(Float.parseFloat(parts[i]));
			}
			samples.add(numbers);
		}

		int maxIterations = 100000;
		float mutation = (float) 0.3;
		int numberOfChromosomes = 40;
		boolean elitism = true;
		boolean eliminationAlgorithm = true;

		GeneticAlgorithm geneticAlgorithm;
		if (eliminationAlgorithm) {
			geneticAlgorithm = new GeneticAlgorithm(numberOfChromosomes, maxIterations,
					new EvolutionElimination(mutation), samples);
		} else {
			geneticAlgorithm = new GeneticAlgorithm(numberOfChromosomes, maxIterations,
					new EvolutionGeneration(mutation, elitism), samples);
		}

		Chromosome result = (Chromosome) geneticAlgorithm.bestChromosome();
		System.out.println("Final error: " + result.error());

		// Alternate testing
		/*
		 * int maxIterations; float mutation; int numberOfChromosomes; boolean elitism =
		 * false; boolean eliminationAlgorithm;
		 * 
		 * Scanner input = new Scanner(System.in);
		 * 
		 * System.out.print("Enter number of iterations: "); maxIterations =
		 * input.nextInt(); System.out.print("Enter mutation rate: "); mutation =
		 * input.nextFloat(); System.out.print("Enter number of chromosomes: ");
		 * numberOfChromosomes = input.nextInt();
		 * System.out.print("Enter if elimination algoithm: "); eliminationAlgorithm =
		 * input.nextBoolean(); if (!eliminationAlgorithm) {
		 * System.out.print("Enter elitism: "); elitism = input.nextBoolean(); }
		 * 
		 * input.close();
		 */
	}
}