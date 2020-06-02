package hr.fer.zemris.fuzzy.lab4;

import java.util.List;

public interface IChromosome extends Comparable<IChromosome> {

	IChromosome crossover(IChromosome chromosome);

	IChromosome mutation(float mutation);

	IChromosome random(List<List<Float>> samples);

	float getFitness();

	default int compareTo(IChromosome chromosome) {
		if (this.getFitness() > chromosome.getFitness()) {
			return -1;
		} else if (this.getFitness() < chromosome.getFitness()) {
			return 1;
		} else {
			return 0;
		}
	}

}