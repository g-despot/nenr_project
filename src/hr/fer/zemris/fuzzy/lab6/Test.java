package hr.fer.zemris.fuzzy.lab6;

import java.util.List;
import java.util.ArrayList;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;

public class Test {

	private static int NUMBER_OF_RULES = 10;
	private static int NUMBER_OF_ITERATIONS = 100000;
	private static double MAX_ERROR_TRESHOLD = 0.000001;
	private static double LEARNING_RATE = 0.0001;

	public static void main(String[] args) throws IOException {
		List<Sample> samples = new ArrayList<>();
		Path path = Paths.get("./files/datasets/lab6/data");
		List<String> lines = Files.readAllLines(path);
		for (String line : lines) {
			String[] element = line.split("\t");
			samples.add(new Sample(Double.parseDouble(element[0]), Double.parseDouble(element[1]),
					Double.parseDouble(element[2])));
		}

		Anfis anfisGR = new Anfis(NUMBER_OF_RULES, samples, LEARNING_RATE, MAX_ERROR_TRESHOLD, NUMBER_OF_ITERATIONS);

		StringBuilder errorsGR = new StringBuilder();
		for (int i = 0; i < samples.size(); i++) {
			Sample sample = samples.get(i);
			double error = anfisGR.calculate(sample.getX(), sample.getY()) - sample.getZ();
			errorsGR.append(error);
			errorsGR.append("\n");
		}
		System.out.println(errorsGR.toString());
	}
}