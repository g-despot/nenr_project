package hr.fer.zemris.fuzzy.lab4;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class EvolutionGeneration implements IEvolution {
	private float mutation;
	private boolean elitism;

	public EvolutionGeneration(float mutation, boolean elitism) {
		this.mutation = mutation;
		this.elitism = elitism;
	}

	public List<IChromosome> evolve(List<IChromosome> chromosomes) {
		int numberOfChromosomes = chromosomes.size();
		List<IChromosome> evolvedPopulation = new ArrayList<>(numberOfChromosomes);

		Collections.sort(chromosomes);

		if (elitism) {
			evolvedPopulation.add(chromosomes.get(0));
		}

		while (evolvedPopulation.size() < numberOfChromosomes) {
			List<IChromosome> selectedParents = selectParents(chromosomes);
			IChromosome parentOne = selectedParents.get(0);
			IChromosome parentTwo = selectedParents.get(1);

			IChromosome child = parentOne.crossover(parentTwo);
			child.mutation(mutation);

			evolvedPopulation.add(child);
		}

		return evolvedPopulation;
	}

	public List<IChromosome> selectParents(List<IChromosome> chromosomes) {
		List<IChromosome> selectedParents = new ArrayList<>();
		int fitness = 0;
		Random random = new Random();

		for (IChromosome chromosome : chromosomes) {
			fitness = (int) (fitness + chromosome.getFitness());
		}

		for (int i = 0; i < 2; i++) {
			float inverted = (float) 1 / random.nextInt(Math.round(fitness));
			float sum = 0;

			for (IChromosome chromosome : chromosomes) {
				sum = sum + 1 / chromosome.getFitness();
				if (sum >= inverted) {
					selectedParents.add(chromosome);
					break;
				}
			}
		}

		while (selectedParents.size() < 2) {
			selectedParents.add(chromosomes.get(random.nextInt(chromosomes.size())));
		}

		return selectedParents;
	}

	public float getMutation() {
		return mutation;
	}

	public void setMutation(float mutation) {
		this.mutation = mutation;
	}

	public boolean isElitism() {
		return elitism;
	}

	public void setElitism(boolean elitism) {
		this.elitism = elitism;
	}
}
