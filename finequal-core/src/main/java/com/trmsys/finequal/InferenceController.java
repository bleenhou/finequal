package com.trmsys.finequal;

import java.math.BigDecimal;

import org.springframework.http.ResponseEntity;

import com.trmsys.finequal.openapi.InferenceApi;
import com.trmsys.finequal.openapi.model.Profile;
import com.trmsys.finequal.openapi.model.Rate;

public class InferenceController implements InferenceApi {

    public ResponseEntity<Rate> inference(Profile profile) {
    	 return ResponseEntity.ok(new Rate().rate(BigDecimal.valueOf(Finequal.network.infer(profile))));
    }
    
}
