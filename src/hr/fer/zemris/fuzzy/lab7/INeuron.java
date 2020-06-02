package hr.fer.zemris.fuzzy.lab7;

import java.util.List;

public interface INeuron {

	void random(int size);

	void setValues(List<Float> params);

	float result(float[] input);

	int getSize();

	void printValues();
	
}