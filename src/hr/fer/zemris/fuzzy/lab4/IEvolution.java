package hr.fer.zemris.fuzzy.lab4;

import java.util.List;

public interface IEvolution {

	List<IChromosome> evolve(List<IChromosome> chromosomes);

}