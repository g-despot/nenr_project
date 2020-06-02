package hr.fer.zemris.fuzzy.lab7.genetic;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;

public class EvolutionGeneration implements IEvolution {
	private boolean elitism;
	private float[] pMutation;
	private float[] tMutation;
	private float[] sMutation;

	public EvolutionGeneration(boolean elitism, float[] pMutation, float[] tMutation, float[] sMutation) {
		this.elitism = elitism;
		this.pMutation = pMutation;
		this.tMutation = tMutation;
		this.sMutation = sMutation;
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
			child.mutation(pMutation, tMutation, sMutation);

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

	public boolean getElitism() {
		return elitism;
	}

	public void setElitism(boolean elitism) {
		this.elitism = elitism;
	}

	public float[] getpMutation() {
		return pMutation;
	}

	public void setpMutation(float[] pMutation) {
		this.pMutation = pMutation;
	}

	public float[] gettMutation() {
		return tMutation;
	}

	public void settMutation(float[] tMutation) {
		this.tMutation = tMutation;
	}

	public float[] getsMutation() {
		return sMutation;
	}

	public void setsMutation(float[] sMutation) {
		this.sMutation = sMutation;
	}
}
