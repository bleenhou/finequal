package com.trmsys.finequal;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;

import com.trmsys.fabric.chassis.ChassisApplication;
import com.trmsys.finequal.openapi.model.Profile.ApplicantEthnicityEnum;

@ChassisApplication
public class Finequal {

	public static final Logger LOG = LoggerFactory.getLogger(Finequal.class);
	public static NeuralNetwork network;
	
	public static void main(String[] args) throws Exception {
		final List<ExtendedProfile> profiles = loadData();
		network = new NeuralNetwork(profiles.subList(0, profiles.size()));
		
		// Testing block
		for (ExtendedProfile p : profiles.subList(0, 100)) {
			LOG.info("Initial Rate : {} %", String.format("%.2f", p.getRateSpread() * 100));
			double r1 = network.infer(p);
			LOG.info("System rate : {} %", String.format("%.2f", r1 * 100));
			
			p.setApplicantEthnicity(ApplicantEthnicityEnum.CAUCASIAN);
			double r2 = network.infer(p);
			LOG.info("Unbiased rate : {} %", String.format("%.2f", r2 * 100));
			LOG.info("");
		}
		
		// Run application
		SpringApplication.run(Finequal.class, args);
		Thread.sleep(100000000000L);
	}

	static List<ExtendedProfile> loadData() throws Exception {
		final LineIterator it = FileUtils.lineIterator(new File("src/test/resources/curated.csv"), "UTF-8");
		it.next(); // skip headers
		final List<ExtendedProfile> profiles = new ArrayList<>();
		while (it.hasNext()) {
			final String loanData = it.next();
			try {
				profiles.add(new ExtendedProfile(loanData.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)")));
			} catch (Exception e) {
				// Discard entry if data cannot be parsed
			}
		}
		return profiles;
	}
}
