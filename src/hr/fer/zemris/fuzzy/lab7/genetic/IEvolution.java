package hr.fer.zemris.fuzzy.lab7.genetic;

import java.util.List;

public interface IEvolution {

	List<IChromosome> evolve(List<IChromosome> chromosomes);

}