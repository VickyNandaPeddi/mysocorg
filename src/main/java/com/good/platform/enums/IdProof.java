package com.good.platform.enums;

import com.good.platform.exception.ErrorCode;
import com.good.platform.exception.SOException;
import com.good.platform.utility.Constants;

/**
 * IdProofEnum is used to handle the enumeration data types with respect to the ID proofs
 * @author Mohamedsuhail S
 *
 */
public enum IdProof {

	PAN_CARD("Pan card"), AADHAR("Aadhar"), PASSPORT("Passport"), OTHER_ID_PROOF("Other ID proof");

	public final String value;

	private IdProof(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value;
	}
	
	public static IdProof getByName(String name){
	    for(IdProof proof : values()){
	      if(proof.toString().equals(name)){
	        return proof;
	      }
	    }
	    throw new SOException(ErrorCode.NOT_FOUND, Constants.INVALID_ID_PROOF_SELECTION);
	  }
	
}
