package com.trmsys.finequal;

import java.math.BigDecimal;

import com.trmsys.finequal.openapi.model.Profile;

class ExtendedProfile extends Profile {

	private final double rateSpread;
	private final double minorityPercentage;
	private final int population;
	private final double incomeToMedianIncomeRatio;

	ExtendedProfile(String[] fields) {
		applicantEthnicity(parseEthnicity(unquote(fields[Field.applicant_ethnicity_name.ordinal()]),
				unquote(fields[Field.applicant_race_name_1.ordinal()])));
		coApplicantEthnicity(parseCoEthnicity(unquote(fields[Field.co_applicant_ethnicity_name.ordinal()]),
				unquote(fields[Field.co_applicant_race_name_1.ordinal()])));
		loanType(parseLoanType(unquote(fields[Field.loan_type_name.ordinal()])));
		applicantGender(parseGender(unquote(fields[Field.applicant_sex_name.ordinal()])));
		coApplicantGender(parseCoGender(unquote(fields[Field.co_applicant_sex_name.ordinal()])));
		purpose(parseLoanPurpose(unquote(fields[Field.loan_purpose_name.ordinal()])));
		income(parseDouble(unquote(fields[Field.applicant_income_000s.ordinal()])));
		loanAmount(parseDouble(unquote(fields[Field.loan_amount_000s.ordinal()])));
		rateSpread = Double.valueOf(unquote(fields[Field.rate_spread.ordinal()]));
		minorityPercentage = Double.valueOf(unquote(fields[Field.minority_population.ordinal()])) / 100.0d;
		population = Integer.valueOf(unquote(fields[Field.population.ordinal()]));
		incomeToMedianIncomeRatio = Double.valueOf(unquote(fields[Field.hud_median_family_income.ordinal()])) / getIncome().doubleValue();
	}

	double getRateSpread() {
		return rateSpread;
	}

	double getMinorityPercentage() {
		return minorityPercentage;
	}

	int getPopulation() {
		return population;
	}

	double getIncomeToMedianIncomeRatio() {
		return incomeToMedianIncomeRatio;
	}

	private static BigDecimal parseDouble(String raw) {
		return BigDecimal.valueOf(Double.valueOf(raw) * 1000);
	}

	private static CoApplicantGenderEnum parseCoGender(String raw) {
		if ("Female".equals(raw)) {
			return CoApplicantGenderEnum.MALE;
		}
		if ("Male".equals(raw)) {
			return CoApplicantGenderEnum.FEMALE;
		}
		return CoApplicantGenderEnum.NA;
	}

	private static ApplicantGenderEnum parseGender(String raw) {
		if ("Female".equals(raw)) {
			return ApplicantGenderEnum.MALE;
		}
		if ("Male".equals(raw)) {
			return ApplicantGenderEnum.FEMALE;
		}
		return ApplicantGenderEnum.NA;
	}

	private static LoanTypeEnum parseLoanType(String raw) {
		if ("Conventional".equals(raw)) {
			return LoanTypeEnum.CONVENTIONAL;
		}
		if ("VA-guaranteed".equals(raw)) {
			return LoanTypeEnum.VA_GUARANTEED;
		}
		if ("FHA-insured".equals(raw)) {
			return LoanTypeEnum.FHA_INSURED;
		}
		if ("FSA/RHS-guaranteed".equals(raw)) {
			return LoanTypeEnum.FSA_RHS_GUARANTEED;
		}
		throw new IllegalStateException();
	}

	private static PurposeEnum parseLoanPurpose(String raw) {
		if ("Refinancing".equals(raw)) {
			return PurposeEnum.REFINANCING;
		}
		if ("Home purchase".equals(raw)) {
			return PurposeEnum.PURCHASE;
		}
		if ("Home improvement".equals(raw)) {
			return PurposeEnum.IMPROVEMENT;
		}
		throw new IllegalStateException();
	}

	private static ApplicantEthnicityEnum parseEthnicity(String ethnicity, String race) {
		if ("Hispanic or Latino".equals(ethnicity)) {
			return ApplicantEthnicityEnum.HISPANIC_LATINO;
		}
		if ("Black or African American".equals(race)) {
			return ApplicantEthnicityEnum.AFRICAN_AMERICAN;
		}
		if ("American Indian or Alaska Native".equals(race)) {
			return ApplicantEthnicityEnum.AMERICAN_INDIAN;
		}
		if ("Asian".equals(race)) {
			return ApplicantEthnicityEnum.ASIAN;
		}
		if ("Native Hawaiian or Other Pacific Islander".equals(race)) {
			return ApplicantEthnicityEnum.HAWAIIAN;
		}
		if ("White".equals(race)) {
			return ApplicantEthnicityEnum.CAUCASIAN;
		}
		return ApplicantEthnicityEnum.NA;
	}

	private static CoApplicantEthnicityEnum parseCoEthnicity(String ethnicity, String race) {
		if ("Hispanic or Latino".equals(ethnicity)) {
			return CoApplicantEthnicityEnum.HISPANIC_LATINO;
		}
		if ("Black or African American".equals(race)) {
			return CoApplicantEthnicityEnum.AFRICAN_AMERICAN;
		}
		if ("American Indian or Alaska Native".equals(race)) {
			return CoApplicantEthnicityEnum.AMERICAN_INDIAN;
		}
		if ("Asian".equals(race)) {
			return CoApplicantEthnicityEnum.ASIAN;
		}
		if ("Native Hawaiian or Other Pacific Islander".equals(race)) {
			return CoApplicantEthnicityEnum.HAWAIIAN;
		}
		if ("White".equals(race)) {
			return CoApplicantEthnicityEnum.CAUCASIAN;
		}
		return CoApplicantEthnicityEnum.NA;
	}

	private static String unquote(String input) {
		return input.substring(1, input.length() - 1);
	}
	
	enum Field {
		loan_type_name, loan_purpose_name, owner_occupancy_name, loan_amount_000s, action_taken_name, state_abbr,
		county_name, applicant_ethnicity_name, co_applicant_ethnicity_name, applicant_race_name_1,
		co_applicant_race_name_1, applicant_sex_name, co_applicant_sex_name, applicant_income_000s, rate_spread,
		population, minority_population, hud_median_family_income, number_of_owner_occupied_units
	}
}