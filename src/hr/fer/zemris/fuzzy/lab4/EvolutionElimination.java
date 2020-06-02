package hr.fer.zemris.fuzzy.lab4;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class EvolutionElimination implements IEvolution {

	private float mutation;

	public EvolutionElimination(float mutation) {
		this.mutation = mutation;
	}

	public List<IChromosome> evolve(List<IChromosome> chromosomes) {
		List<IChromosome> selectedParents = new ArrayList<>();
		selectedParents = selectParents(chromosomes);

		IChromosome child = selectedParents.get(0).crossover(selectedParents.get(1));
		chromosomes.remove(selectedParents.get(2));

		child.mutation(mutation);
		chromosomes.add(child);
		return chromosomes;
	}

	public List<IChromosome> selectParents(List<IChromosome> chromosomes) {
		List<IChromosome> selectedParents = new ArrayList<>();
		List<IChromosome> randomSelection = new ArrayList<>();
		Random random = new Random();
		for (int i = 0; i < 3; i++) {
			randomSelection.add(chromosomes.get(random.nextInt(chromosomes.size())));
		}

		Collections.sort(randomSelection);
		selectedParents.add(randomSelection.get(0));
		selectedParents.add(randomSelection.get(1));
		selectedParents.add(randomSelection.get(2));
		return selectedParents;
	}

	public float getMutation() {
		return mutation;
	}

	public void setMutation(float mutation) {
		this.mutation = mutation;
	}
}