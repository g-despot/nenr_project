package hr.fer.zemris.fuzzy.lab7.genetic;

public interface IChromosome extends Comparable<IChromosome> {

	IChromosome crossover(IChromosome chromosome);

	IChromosome mutation(float[] pMutation, float[] tMutation, float[] sMutation);

	IChromosome random();

	float[] getParameters();

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