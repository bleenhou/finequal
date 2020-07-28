package com.trmsys.finequal;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

/**
 * A set of utility methods to sanitize the data
 */
public class FinequalDataSanitizing {

	/**
	 * Computes the various possible values for a given column
	 */
	private static void computeEnumerations() throws IOException {
		final LineIterator it = FileUtils.lineIterator(new File("src/test/resources/curated.csv"), "UTF-8");
		final Map<String, Set<String>> enumerations = new HashMap<>();
		final String[] headers = it.next().split(",");
		while (it.hasNext()) {
			final String loanData = it.next();
			final String[] fields = loanData.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"); // Split by , unless in quotes
			for (int i = 0; i < fields.length; ++i) {
				enumerations.computeIfAbsent(headers[i], h -> new HashSet<>()).add(fields[i]);
			}
		}
		enumerations.entrySet().forEach(e -> System.out.println(e));
	}
	
	/**
	 * Cleans up the data, keeping only relevant fields for the DNN
	 */
	private static void dataCleanup() throws Exception {
		final LineIterator it = FileUtils.lineIterator(new File(
				"src/test/resources/hmda_2017_nationwide_first-lien-owner-occupied-1-4-family-records_labels.csv"),
				"UTF-8");
		final BufferedWriter writer = Files.newWriter(new File("src/test/resources/curated.csv"), Charsets.UTF_8);
		while (it.hasNext()) {
			final String[] fields = it.next().split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"); // Split by , unless in quotes
			final String rateSpread = fields[63];
			if (rateSpread.length() > 2) {
				writer.append(fields[5]).append(","); // loanType
				writer.append(fields[9]).append(","); // loanPurpose
				writer.append(fields[11]).append(","); // ownerOccupancy
				writer.append(fields[13]).append(","); // loanAmount
				writer.append(fields[16]).append(","); // actionTaken
				writer.append(fields[21]).append(","); // state
				writer.append(fields[23]).append(","); // county
				writer.append(fields[26]).append(","); // ethnicity
				writer.append(fields[28]).append(","); // ethnicity2
				writer.append(fields[30]).append(","); // race
				writer.append(fields[40]).append(","); // race2
				writer.append(fields[50]).append(","); // gender
				writer.append(fields[52]).append(","); // gender2
				writer.append(fields[54]).append(","); // income
				writer.append(rateSpread).append(","); // rateSpread
				writer.append(fields[71]).append(","); // popultion
				writer.append(fields[72]).append(","); // minorityPopultion
				writer.append(fields[73]).append(","); // medianIncome
				writer.append(fields[75]);
				writer.newLine();
			}
		}
		writer.close();
	}
}
