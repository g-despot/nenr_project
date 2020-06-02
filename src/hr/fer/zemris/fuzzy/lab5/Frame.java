package hr.fer.zemris.fuzzy.lab5;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public class Frame extends JFrame {

	private Canvas canvas;
	private JLabel label;

	private Button submitButton;
	private Button finishedButton;
	private Button clearButton;
	private Button loadButton;

	private JPanel buttonPane;
	private JPanel buttonPaneTwo;
	private JPanel inputPane;

	private Container contentPane;

	private int cnt = 0;
	private int symbolNumber = 0;

	private List<Sample> samples;
	private Map<String, List<double[]>> symbolsMap;
	private List<String> symbols;
	private List<double[]> symbolCodes;
	private int M;

	public Frame(Map<String, List<double[]>> symbolsMap, List<String> symbols, List<double[]> symbolCodes, int M,
			int symbolInstances, NeuralNetwork neuralNetwork) {

		this.symbolsMap = symbolsMap;
		this.symbols = symbols;
		this.symbolCodes = symbolCodes;
		this.M = M;

		this.pack();
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setSize(new Dimension(900, 900));

		canvas = new Canvas();
		canvas.setBackground(new Color(255, 255, 255));
		inputPane = new JPanel();
		inputPane.setLayout(new BoxLayout(inputPane, BoxLayout.PAGE_AXIS));
		inputPane.add(canvas);
		inputPane.add(Box.createRigidArea(new Dimension(0, 5)));
		inputPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		clearButton = new Button("Clear");
		submitButton = new Button("Submit");
		finishedButton = new Button("Finished");
		loadButton = new Button("Load");
		label = new JLabel();
		Border border = BorderFactory.createLineBorder(Color.DARK_GRAY, 3);
		label.setBorder(border);
		label.setText(symbols.get(symbolNumber) + " " + (cnt + 1));
		buttonPane = new JPanel();
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
		buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		buttonPane.add(Box.createHorizontalGlue());
		buttonPane.add(submitButton);
		buttonPane.add(Box.createRigidArea(new Dimension(10, 0)));
		buttonPane.add(clearButton);
		buttonPane.add(Box.createRigidArea(new Dimension(10, 0)));
		buttonPane.add(finishedButton);
		buttonPane.add(Box.createRigidArea(new Dimension(10, 0)));
		buttonPane.add(loadButton);
		buttonPane.add(Box.createRigidArea(new Dimension(10, 0)));
		buttonPane.add(label);

		contentPane = getContentPane();
		contentPane.add(inputPane, BorderLayout.CENTER);
		contentPane.add(buttonPane, BorderLayout.PAGE_END);

		clearButton.addActionListener(e -> {
			label.setText("");
			canvas.reset();
		});
		submitButton.addActionListener(e -> {
			symbolsMap.get(symbols.get(symbolNumber)).add(getSymbols(canvas.getPoints()));

			if (cnt == symbolInstances - 1) {
				cnt = 0;
				symbolNumber++;
			} else {
				cnt++;
			}

			if (symbolNumber < symbols.size()) {
				label.setText(symbols.get(symbolNumber) + " " + (cnt + 1));
			}
			canvas.reset();
		});
		finishedButton.addActionListener(e -> {
			neuralNetwork.stohasticTrainNN(neuralNetworkInput());
			// neuralNetwork.backpropagationTrainNN(neuralNetworkInput());
			try {
				saveGestures();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			contentPane.remove(buttonPane);

			Button submitButtonTwo = new Button("Submit");

			submitButtonTwo.addActionListener(f -> {
				label.setText(symbols.get(neuralNetwork.resultSymbol(getSymbols(canvas.getPoints()))));
			});

			buttonPaneTwo = new JPanel();
			buttonPaneTwo.setLayout(new BoxLayout(buttonPaneTwo, BoxLayout.LINE_AXIS));
			buttonPaneTwo.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
			buttonPaneTwo.add(Box.createHorizontalGlue());
			buttonPaneTwo.add(submitButtonTwo);
			buttonPaneTwo.add(Box.createRigidArea(new Dimension(10, 0)));
			buttonPaneTwo.add(clearButton);
			buttonPaneTwo.add(Box.createRigidArea(new Dimension(10, 0)));
			buttonPaneTwo.add(label);

			contentPane = getContentPane();
			contentPane.add(buttonPaneTwo, BorderLayout.PAGE_END);

			canvas.reset();
			return;
		});
		loadButton.addActionListener(e -> {
			loadGestures();
			neuralNetwork.stohasticTrainNN(neuralNetworkInput());
			// neuralNetwork.backpropagationTrainNN(neuralNetworkInput());

			contentPane.remove(buttonPane);

			Button submitButtonTwo = new Button("Submit");

			submitButtonTwo.addActionListener(f -> {
				label.setText(symbols.get(neuralNetwork.resultSymbol(getSymbols(canvas.getPoints()))));
			});

			buttonPaneTwo = new JPanel();
			buttonPaneTwo.setLayout(new BoxLayout(buttonPaneTwo, BoxLayout.LINE_AXIS));
			buttonPaneTwo.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
			buttonPaneTwo.add(Box.createHorizontalGlue());
			buttonPaneTwo.add(submitButtonTwo);
			buttonPaneTwo.add(Box.createRigidArea(new Dimension(10, 0)));
			buttonPaneTwo.add(clearButton);
			buttonPaneTwo.add(Box.createRigidArea(new Dimension(10, 0)));
			buttonPaneTwo.add(label);

			contentPane = getContentPane();
			contentPane.add(buttonPaneTwo, BorderLayout.PAGE_END);
			canvas.reset();

			this.setSize(new Dimension(900, 900));
			return;
		});
	}

	private List<Sample> neuralNetworkInput() {
		samples = new ArrayList<>();

		for (@SuppressWarnings("rawtypes")
		Map.Entry me : symbolsMap.entrySet()) {
			@SuppressWarnings("unchecked")
			List<double[]> g = (ArrayList<double[]>) me.getValue();
			for (double[] gesture : g) {
				samples.add(new Sample(gesture, symbolCodes.get(symbols.indexOf(me.getKey()))));
			}
		}
		return samples;
	}

	private void saveGestures() throws IOException {
		BufferedWriter writer = new BufferedWriter(
				new FileWriter("./files/datasets/lab5/symbols.txt"));
		for (@SuppressWarnings("rawtypes")
		Map.Entry me : symbolsMap.entrySet()) {
			@SuppressWarnings("unchecked")
			List<double[]> g = (ArrayList<double[]>) me.getValue();
			for (double[] gesture : g) {
				writer.write(toString(gesture) + "," + toString(symbolCodes.get(symbols.indexOf(me.getKey()))) + '\n');
			}
		}
		writer.close();
	}

	private void loadGestures() {
		Path path = Paths.get("./files/datasets/lab5/symbols.txt");
		List<String> lines = null;
		try {
			lines = Files.readAllLines(path);
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (String line : lines) {
			String[] parts = line.split(",");
			double[] numbers = new double[M * 2];
			double[] code = new double[5];
			for (int i = 0; i < M * 2; i++) {
				numbers[i] = (Double.parseDouble(parts[i]));
			}
			for (int i = 0; i < 5; i++) {
				code[i] = (Double.parseDouble(parts[M * 2 + i]));
			}
			System.out.println(symbolCode(code));
			symbolsMap.get(symbols.get(symbolCode(code))).add(Arrays.copyOf(numbers, numbers.length));
			System.out.println(symbolsMap);
		}
	}

	private int symbolCode(double[] code) {
		for (int i = 0; i < 5; i++) {
			if (code[i] == 1) {
				return i;
			}
		}
		return 0;
	}

	private String toString(double[] numbers) {
		String result = "";
		for (int i = 0; i < numbers.length; i++) {
			result = result + numbers[i];
			if (i != numbers.length - 1) {
				result = result + ",";
			}
		}
		return result;
	}

	private double[] getSymbols(List<Point> points) {
		double xSum = 0;
		double ySum = 0;
		double length = 0;
		List<Point> pointsOne = new ArrayList<>();
		List<Point> pointsTwo = new ArrayList<>();
		List<Point> pointsThree = new ArrayList<>();
		double xyMaximum = 0;

		for (Point point : points) {
			xSum = xSum + point.getX();
			ySum = ySum + point.getY();
		}

		double xAverage = xSum / points.size();
		double yAverage = ySum / points.size();

		for (int i = 0; i < points.size(); i++) {
			Point point = points.get(i);
			double xNew = point.getX() - xAverage;
			double yNew = point.getY() - yAverage;
			pointsOne.add(new Point((float) xNew, (float) yNew));
		}

		for (Point point : pointsOne) {
			if (Math.abs(point.getX()) > xyMaximum) {
				xyMaximum = Math.abs(point.getX());
			} else if (Math.abs(point.getY()) > xyMaximum) {
				xyMaximum = Math.abs(point.getY());
			}
		}

		for (Point point : pointsOne) {
			double xNew = point.getX() / xyMaximum;
			double yNew = point.getY() / xyMaximum;
			pointsTwo.add(new Point((float) xNew, (float) yNew));
		}

		for (int i = 0; i < pointsTwo.size() - 1; i++) {
			Point a = pointsTwo.get(i);
			Point b = pointsTwo.get(i + 1);
			length = length + distance(a, b);
		}

		double minLength = length / (M);
		double sum = 0;
		int count = 0;

		Point a = pointsTwo.get(0);
		for (Point point : pointsTwo) {
			sum = sum + distance(a, point);
			a = point;
			if (sum > minLength) {
				pointsThree.add(point);
				sum = 0;
				count++;
			}
		}

		while (count < M) {
			pointsThree.add(pointsTwo.get(pointsTwo.size() - 1));
			count++;
		}

		double[] symbols = new double[pointsThree.size() * 2];

		int j = 0;
		for (int i = 0; i < pointsThree.size(); i = i + 1) {
			symbols[j] = pointsThree.get(i).getX();
			symbols[j + 1] = pointsThree.get(i).getY();
			j = j + 2;
		}

		return symbols;
	}

	public float distance(Point pointOne, Point pointTwo) {
		float distance = (float) Math
				.sqrt(Math.pow(pointOne.getX() - pointTwo.getX(), 2) + Math.pow(pointOne.getY() - pointTwo.getY(), 2));
		return distance;
	}

	public class Sample {
		public double[] x;
		public double[] y;

		public Sample(double[] x, double[] y) {
			this.x = x;
			this.y = y;
		}
	}
}
