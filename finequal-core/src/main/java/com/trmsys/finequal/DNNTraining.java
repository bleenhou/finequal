package com.trmsys.finequal;

import java.util.List;

import org.deeplearning4j.datasets.iterator.impl.EmnistDataSetIterator;
import org.deeplearning4j.datasets.iterator.impl.EmnistDataSetIterator.Set;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.evaluation.classification.Evaluation;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.lossfunctions.LossFunctions;

public class DNNTraining {

	public static void main (String[] args) throws Exception {
		
		int batchSize = 128; // how many examples to simultaneously train in the network
		Set emnistSet = EmnistDataSetIterator.Set.BALANCED;
		EmnistDataSetIterator emnistTrain = new EmnistDataSetIterator(emnistSet, batchSize, true);
		EmnistDataSetIterator emnistTest = new EmnistDataSetIterator(emnistSet, batchSize, false);
		
		int outputNum = EmnistDataSetIterator.numLabels(emnistSet); // total output classes
		int rngSeed = 123; // integer for reproducability of a random number generator
		int numRows = 28; // number of "pixel rows" in an mnist digit
		int numColumns = 28;

		MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
            .seed(rngSeed)
            .updater(new Adam())
            .l2(1e-4)
            .list()
            .layer(new DenseLayer.Builder()
                .nIn(numRows * numColumns) // Number of input datapoints.
                .nOut(1000) // Number of output datapoints.
                .activation(Activation.RELU) // Activation function.
                .weightInit(WeightInit.XAVIER) // Weight initialization.
                .build())
            .layer(new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                .nIn(1000)
                .nOut(outputNum)
                .activation(Activation.SOFTMAX)
                .weightInit(WeightInit.XAVIER)
                .build())
            .build();
		
		MultiLayerNetwork network = new MultiLayerNetwork(conf);
		network.init();
		
		int eachIterations = 10;
		network.addListeners(new ScoreIterationListener(eachIterations));
		
		network.fit(emnistTrain, 2);
		
		// evaluate basic performance
		Evaluation eval = network.evaluate(emnistTest);
		System.out.println(eval.accuracy());
		System.out.println(eval.precision());
		System.out.println(eval.recall());

	}
	
	public static void train (List<ExtendedProfile> profiles) throws Exception {
		
		int batchSize = 128; // how many examples to simultaneously train in the network
		Set emnistSet = EmnistDataSetIterator.Set.BALANCED;
		EmnistDataSetIterator emnistTrain = new EmnistDataSetIterator(emnistSet, batchSize, true);
		EmnistDataSetIterator emnistTest = new EmnistDataSetIterator(emnistSet, batchSize, false);
		
		int outputNum = EmnistDataSetIterator.numLabels(emnistSet); // total output classes
		int rngSeed = 123; // integer for reproducability of a random number generator
		int numRows = 28; // number of "pixel rows" in an mnist digit
		int numColumns = 28;

		MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
            .seed(rngSeed)
            .updater(new Adam())
            .l2(1e-4)
            .list()
            .layer(new DenseLayer.Builder()
                .nIn(numRows * numColumns) // Number of input datapoints.
                .nOut(1000) // Number of output datapoints.
                .activation(Activation.RELU) // Activation function.
                .weightInit(WeightInit.XAVIER) // Weight initialization.
                .build())
            .layer(new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                .nIn(1000)
                .nOut(outputNum)
                .activation(Activation.SOFTMAX)
                .weightInit(WeightInit.XAVIER)
                .build())
            .build();
		
		MultiLayerNetwork network = new MultiLayerNetwork(conf);
		network.init();
		
		int eachIterations = 10;
		network.addListeners(new ScoreIterationListener(eachIterations));
		
		network.fit(emnistTrain, 2);
		
		// evaluate basic performance
		Evaluation eval = network.evaluate(emnistTest);
		System.out.println(eval.accuracy());
		System.out.println(eval.precision());
		System.out.println(eval.recall());

	}
	
}
