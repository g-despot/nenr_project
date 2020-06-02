package hr.fer.zemris.fuzzy.lab7.genetic;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;

public class EvolutionElimination implements IEvolution {
	private float[] pMutation;
	private float[] tMutation;
	private float[] sMutation;

	public EvolutionElimination(float[] pMutation, float[] tMutation, float[] sMutation) {
		this.pMutation = pMutation;
		this.tMutation = tMutation;
		this.sMutation = sMutation;
	}

	public List<IChromosome> evolve(List<IChromosome> chromosomes) {
		List<IChromosome> selectedParents = new ArrayList<>();
		selectedParents = selectParents(chromosomes);

		IChromosome child = selectedParents.get(0).crossover(selectedParents.get(1));
		chromosomes.remove(selectedParents.get(2));

		child.mutation(pMutation, tMutation, sMutation);
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