package com.trmsys.finequal;

import java.math.BigDecimal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.trmsys.finequal.openapi.InferenceApi;
import com.trmsys.finequal.openapi.model.Profile;
import com.trmsys.finequal.openapi.model.Rate;

@RestController
@CrossOrigin
public class InferenceController implements InferenceApi {
	
    public ResponseEntity<Rate> inference(Profile profile) {
    	return ResponseEntity.ok(new Rate().rate(BigDecimal.valueOf(Finequal.network.infer(profile))));
    }
    
}
