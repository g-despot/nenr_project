package hr.fer.zemris.fuzzy.lab6;

import java.util.List;
import java.util.ArrayList;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;
import java.nio.charset.Charset;

public class TestWithOutput {

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

		numberOfRulesData(samples);
		errorData(samples);
		epohErrorData(samples);
		learningRateData(samples);
	}

	private static void numberOfRulesData(List<Sample> samples) throws IOException {
		Anfis anfisOne = new Anfis(1, samples, LEARNING_RATE, MAX_ERROR_TRESHOLD, NUMBER_OF_ITERATIONS);

		StringBuilder oneRuleFunctionGR = new StringBuilder();
		for (int i = 0; i < samples.size(); i++) {
			Sample sample = samples.get(i);
			oneRuleFunctionGR.append(anfisOne.calculate(sample.getX(), sample.getY()));
			oneRuleFunctionGR.append("\n");
		}
		Files.write(Paths.get("./src/hr/fer/zemris/fuzzy/lab6/output/oneRuleFunctionGR.txt"),
				oneRuleFunctionGR.toString().getBytes(Charset.forName("UTF-8")));
		Files.write(Paths.get("./src/hr/fer/zemris/fuzzy/lab6/output/oneRuleErrorGR.txt"),
				anfisOne.getErrors().getBytes(Charset.forName("UTF-8")));

		Anfis anfisTwo = new Anfis(2, samples, LEARNING_RATE, MAX_ERROR_TRESHOLD, NUMBER_OF_ITERATIONS);
		StringBuilder twoRuleFunctionGR = new StringBuilder();
		for (int i = 0; i < samples.size(); i++) {
			Sample sample = samples.get(i);
			twoRuleFunctionGR.append(anfisTwo.calculate(sample.getX(), sample.getY()));
			twoRuleFunctionGR.append("\n");
		}
		Files.write(Paths.get("./src/hr/fer/zemris/fuzzy/lab6/output/twoRuleFunctionGR.txt"),
				twoRuleFunctionGR.toString().getBytes(Charset.forName("UTF-8")));
		Files.write(Paths.get("./src/hr/fer/zemris/fuzzy/lab6/output/twoRuleErrorGR.txt"),
				anfisTwo.getErrors().getBytes(Charset.forName("UTF-8")));

		Anfis anfisThree = new Anfis(NUMBER_OF_RULES, samples, LEARNING_RATE, MAX_ERROR_TRESHOLD, NUMBER_OF_ITERATIONS);
		StringBuilder allRulesFunctionGR = new StringBuilder();
		for (int i = 0; i < samples.size(); i++) {
			Sample sample = samples.get(i);
			allRulesFunctionGR.append(anfisThree.calculate(sample.getX(), sample.getY()));
			allRulesFunctionGR.append("\n");
		}
		Files.write(Paths.get("./src/hr/fer/zemris/fuzzy/lab6/output/allRulesFunctionGR.txt"),
				allRulesFunctionGR.toString().getBytes(Charset.forName("UTF-8")));
		Files.write(Paths.get("./src/hr/fer/zemris/fuzzy/lab6/output/allRulesErrorGR.txt"),
				anfisThree.getErrors().getBytes(Charset.forName("UTF-8")));
	}

	private static void errorData(List<Sample> samples) throws IOException {
		Anfis anfisGR = new Anfis(NUMBER_OF_RULES, samples, LEARNING_RATE, MAX_ERROR_TRESHOLD, NUMBER_OF_ITERATIONS);

		StringBuilder stringBuilder = new StringBuilder();
		for (Rule rule : anfisGR.getRules()) {
			stringBuilder.append(rule.toString() + "\n");
		}
		Files.write(Paths.get("./src/hr/fer/zemris/fuzzy/lab6/output/rulesGR.txt"),
				stringBuilder.toString().getBytes(Charset.forName("UTF-8")));

		StringBuilder errorsGR = new StringBuilder();
		for (int i = 0; i < samples.size(); i++) {
			Sample sample = samples.get(i);
			double error = anfisGR.calculate(sample.getX(), sample.getY()) - sample.getZ();
			errorsGR.append(error);
			errorsGR.append("\n");
		}
		Files.write(Paths.get("./src/hr/fer/zemris/fuzzy/lab6/output/eGR.txt"),
				errorsGR.toString().getBytes(Charset.forName("UTF-8")));
	}

	private static void epohErrorData(List<Sample> samples) throws IOException {
		Anfis anfisTwo = new Anfis(NUMBER_OF_RULES, samples, LEARNING_RATE, MAX_ERROR_TRESHOLD, NUMBER_OF_ITERATIONS);
		Files.write(Paths.get("./src/hr/fer/zemris/fuzzy/lab6/output/errorGR.txt"),
				anfisTwo.getErrors().getBytes(Charset.forName("UTF-8")));
	}

	private static void learningRateData(List<Sample> samples) throws IOException {
		Anfis anfisOne = new Anfis(NUMBER_OF_RULES, samples, 0.05, MAX_ERROR_TRESHOLD, NUMBER_OF_ITERATIONS);
		Files.write(Paths.get("./src/hr/fer/zemris/fuzzy/lab6/output/learningRateOne.txt"),
				anfisOne.getErrors().getBytes(Charset.forName("UTF-8")));

		Anfis anfisTwo = new Anfis(NUMBER_OF_RULES, samples, 0.005, MAX_ERROR_TRESHOLD, NUMBER_OF_ITERATIONS);
		Files.write(Paths.get("./src/hr/fer/zemris/fuzzy/lab6/output/learningRateTwo.txt"),
				anfisTwo.getErrors().getBytes(Charset.forName("UTF-8")));

		Anfis anfisThree = new Anfis(NUMBER_OF_RULES, samples, 0.0005, MAX_ERROR_TRESHOLD, NUMBER_OF_ITERATIONS);
		Files.write(Paths.get("./src/hr/fer/zemris/fuzzy/lab6/output/learningRateThree.txt"),
				anfisThree.getErrors().getBytes(Charset.forName("UTF-8")));
	}
}