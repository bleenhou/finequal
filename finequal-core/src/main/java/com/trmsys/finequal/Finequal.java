package com.trmsys.finequal;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import com.trmsys.fabric.chassis.ChassisApplication;

@ChassisApplication
public class Finequal {

	public static final String BASE_PATH = "/finequal/v1/";

	public static void main(String[] args) throws Exception {
		final List<ExtendedProfile> profiles = loadData();
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
