package com.good.platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.good.platform.entity.UserAddressDetails;
import com.good.platform.model.AddressDetailsModel;

@Repository
public interface UserAddressDetailsRepository extends JpaRepository<UserAddressDetails, String>{

	@Query("SELECT new com.good.platform.model.AddressDetailsModel(mapping.user.id, mapping.addressLine1"
			+ ",mapping.streetName, mapping.city, mapping.state, mapping.pincode, mapping.latitude, mapping.longitude) "
			+ "FROM #{#entityName} mapping "
			+ "WHERE mapping.user.id = :userId")
	AddressDetailsModel getUserAddress(@Param("userId") String userId);
	
	@Query("SELECT add FROM #{#entityName} add"
			+ " WHERE add.user.id = :userId")
	UserAddressDetails getUserAddressEntity(@Param("userId") String userId);
	
}
