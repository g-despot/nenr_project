package hr.fer.zemris.fuzzy.lab1.fuzzySet;

public class StandardFuzzySets {

	public StandardFuzzySets() {
	}

	public static IIntUnaryFunction lFunction(int alpha, int beta) {
		return (x) -> {
			if (x < alpha)
				return 1;
			if (x >= alpha && x < beta)
				return (double) (beta - x) / (beta - alpha);
			return 0;
		};
	}

	public static IIntUnaryFunction gammaFunction(int alpha, int beta) {
		return (x) -> {
			if (x < alpha)
				return 0;
			if (x >= alpha && x < beta)
				return (double) (x - alpha) / (beta - alpha);
			return 1;
		};
	}

	public static IIntUnaryFunction lambdaFunction(int alpha, int beta, int gamma) {
		return (x) -> {
			if (x < alpha)
				return 0;
			if (x >= alpha && x < beta)
				return (double) (x - alpha) / (beta - alpha);
			if (x >= beta && x < gamma)
				return (double) (gamma - x) / (gamma - beta);
			return 0;
		};
	}

}
