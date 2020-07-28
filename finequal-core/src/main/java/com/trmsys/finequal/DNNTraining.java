package com.trmsys.finequal;

import java.util.List;

import org.deeplearning4j.datasets.iterator.impl.ListDataSetIterator;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.learning.config.Nesterovs;
import org.nd4j.linalg.lossfunctions.LossFunctions;

public class DNNTraining {

	public static void main (String[] args) throws Exception {
		train(Finequal.loadData());
	}
	
	public static void train (List<ExtendedProfile> profiles) throws Exception {
		
		final DataSetIterator dsIt = getTrainingData(profiles);
		final MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder().seed(1234)
            .weightInit(WeightInit.XAVIER)
		    .updater(new Nesterovs(0.1, 0.9)) 
            .list()
            .layer(0, new DenseLayer.Builder()
                    .nIn(1).nOut(50)
                    .activation(Activation.RELU) 
                    .build())
            .layer(1, new OutputLayer.Builder(LossFunctions.LossFunction.MSE)
                    .nIn(50).nOut(1)
                    .activation(Activation.IDENTITY) 
                    .build())
            .build();
		
		MultiLayerNetwork network = new MultiLayerNetwork(conf);
		network.init();
		network.addListeners(new ScoreIterationListener(100));
		network.fit(dsIt, 10);
		
		final double income = profiles.get(0).getIncome().doubleValue();
		final INDArray testInput = Nd4j.create(new double[] { income }, 1, 1);
        INDArray out = network.output(testInput, false);
        System.out.println(out);
        System.out.println(profiles.get(0).getRateSpread());
	}
	
	 public static DataSetIterator getTrainingData(List<ExtendedProfile> profiles){
	 	final double [] incomes = profiles.stream().mapToDouble(p -> p.getIncome().doubleValue()).toArray();
	 	final double [] rates = profiles.stream().mapToDouble(p -> p.getRateSpread()).toArray();
	 	final INDArray inputArray = Nd4j.create(incomes, profiles.size(), 1);
	 	final INDArray outPut = Nd4j.create(rates, profiles.size(), 1);
	 	final DataSet dataSet = new DataSet(inputArray, outPut);
        return new ListDataSetIterator<>(dataSet.asList(), 128);
    }
}
