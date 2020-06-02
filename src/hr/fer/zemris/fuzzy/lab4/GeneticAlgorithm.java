package hr.fer.zemris.fuzzy.lab4;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class GeneticAlgorithm {
	private int numberOfChromosomes;
	private List<IChromosome> population;
	private IEvolution evolution;
	private List<List<Float>> samples;

	public GeneticAlgorithm(int numberOfChromosomes, int maxIterations, IEvolution evolution,
			List<List<Float>> samples) {
		population = new ArrayList<>(numberOfChromosomes);
		this.evolution = evolution;
		this.numberOfChromosomes = numberOfChromosomes;
		this.samples = samples;
		this.evolve(maxIterations);
	}

	public void evolve(int iterations) {
		for (int i = 0; i < numberOfChromosomes; i++) {
			population.add((new Chromosome()).random(samples));
		}

		for (int i = 0; i < iterations; i++) {
			population = evolution.evolve(population);
			if (i % 100 == 0) {
				System.out.println("Iteration: " + i + ", Fitness:  " + 1 / bestChromosome().getFitness());
			}
		}
	}

	public IChromosome bestChromosome() {
		Collections.sort(population);
		return population.get(0);
	}

	public int getNumberOfChromosomes() {
		return numberOfChromosomes;
	}

	public void setNumberOfChromosomes(int numberOfChromosomes) {
		this.numberOfChromosomes = numberOfChromosomes;
	}

	public List<IChromosome> getPopulation() {
		return population;
	}

	public void setPopulation(List<IChromosome> population) {
		this.population = population;
	}

	public IEvolution getEvolution() {
		return evolution;
	}

	public void setEvolution(IEvolution evolution) {
		this.evolution = evolution;
	}

	public List<List<Float>> getSamples() {
		return samples;
	}

	public void setSamples(List<List<Float>> samples) {
		this.samples = samples;
	}
}
