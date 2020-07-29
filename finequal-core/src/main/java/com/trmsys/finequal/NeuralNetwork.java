package com.trmsys.finequal;

import static org.nd4j.shade.protobuf.common.collect.Lists.newArrayList;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.deeplearning4j.datasets.iterator.impl.ListDataSetIterator;
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
import org.nd4j.shade.protobuf.common.collect.Lists;

import com.trmsys.finequal.openapi.model.Profile;
import com.trmsys.finequal.openapi.model.Profile.ApplicantEthnicityEnum;
import com.trmsys.finequal.openapi.model.Profile.ApplicantGenderEnum;
import com.trmsys.finequal.openapi.model.Profile.CoApplicantEthnicityEnum;
import com.trmsys.finequal.openapi.model.Profile.CoApplicantGenderEnum;
import com.trmsys.finequal.openapi.model.Profile.LoanTypeEnum;
import com.trmsys.finequal.openapi.model.Profile.PurposeEnum;

public class NeuralNetwork {

	private static final int HIDDEN_LAYER_SIZE = 128;
	private static final int TRAINING_EPOCH_COUNT = 5;
	
	private MultiLayerNetwork network;

	public NeuralNetwork(List<ExtendedProfile> profiles) throws Exception {

		final double[] sample = inputForProfile(profiles.get(0));
		final int inputLength = sample.length;

		// Load Training data
		final DataSetIterator trainingSet = createDataset(profiles);

		// Define DNN
        this.network = new MultiLayerNetwork(new NeuralNetConfiguration.Builder()
                .seed(1234)
                .weightInit(WeightInit.XAVIER)
                .updater(new Nesterovs(0.01, 0.9))
                .list()
                .layer(0, new DenseLayer.Builder().nIn(inputLength).nOut(HIDDEN_LAYER_SIZE)
                        .activation(Activation.RELU) //Change this to RELU and you will see the net learns very well very quickly
                        .build())
                .layer(1, new OutputLayer.Builder(LossFunctions.LossFunction.MSE)
                        .activation(Activation.IDENTITY)
                        .nIn(HIDDEN_LAYER_SIZE).nOut(1).build())
                .build()
        );
        network.init();

		// Train DNN
		network.addListeners(new ScoreIterationListener(100));
		network.fit(trainingSet, TRAINING_EPOCH_COUNT);
	}
	
	public double infer (Profile p) {
		final double[] input = inputForProfile(p);
		return network.output(Nd4j.create(input, 1, input.length), false).getDouble(0,0);
	}

	private static double[] inputForProfile(Profile profile) {
		List<Double> data = Lists.newArrayList();
		data.add(profile.getIncome().doubleValue() / 100000); // Normalizing
		data.add(profile.getLoanAmount().doubleValue() / 100000); // Normalizing
		data.addAll(inputsForEnum(profile, p -> p.getLoanType(), LoanTypeEnum.class));
		data.addAll(inputsForEnum(profile, p -> p.getPurpose(), PurposeEnum.class));
		data.addAll(inputsForEnum(profile, p -> p.getApplicantEthnicity(), ApplicantEthnicityEnum.class));
//		data.addAll(inputsForEnum(profile, p -> p.getCoApplicantEthnicity(), CoApplicantEthnicityEnum.class));
		data.addAll(inputsForEnum(profile, p -> p.getApplicantGender(), ApplicantGenderEnum.class));
//		data.addAll(inputsForEnum(profile, p -> p.getCoApplicantGender(), CoApplicantGenderEnum.class));
//		data.add(profile.getIncomeToMedianIncomeRatio());
//		data.add(profile.getMinorityPercentage());
//		data.add((double) profile.getPopulation());
		return data.stream().mapToDouble(d -> d.doubleValue()).toArray();

	}

	private static DataSetIterator createDataset(List<ExtendedProfile> profiles) {
		// DNN inputs
		final List<double[]> inputs = profiles.stream().map(p -> inputForProfile(p)).collect(Collectors.toList());
		final int nbFields = inputs.get(0).length;
		final List<INDArray> INDInputs = newArrayList();
		for (int i = 0; i < nbFields; ++i) {
			final int index = i;
			final double[] row = inputs.stream().mapToDouble(e -> e[index]).toArray();
			INDInputs.add(Nd4j.create(row, inputs.size(), 1));
		}
		final INDArray inputNDArray = Nd4j.hstack(INDInputs);
		
		// DNN output
		final double[] rates = profiles.stream().mapToDouble(p -> p.getRateSpread()).toArray();
		final INDArray outPutNDArray = Nd4j.create(rates, profiles.size(), 1);
		final DataSet dataSet = new DataSet(inputNDArray, outPutNDArray);
		return new ListDataSetIterator<>(dataSet.asList(), 128);
	}
	
	private static <T extends Enum<?>> List<Double> inputsForEnum(Profile p, Function<Profile, T> f,
			Class<T> cls) {
		final List<Double> result = newArrayList();
		for (T e : cls.getEnumConstants()) {
			result.add(f.apply(p) == e ? 1.0 : 0.0);
		}
		return result;
	}
}
