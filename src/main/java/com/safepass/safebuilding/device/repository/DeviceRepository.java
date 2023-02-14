package com.safepass.safebuilding.device.repository;

import com.safepass.safebuilding.device.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DeviceRepository extends JpaRepository<Device, UUID> {

    @Query(
            value = "SELECT * FROM device WHERE customer_id = ?1 AND token = ?2",
            nativeQuery = true
    )
    Device getByCustomerIdAndToken(String customerId, String token);

    Device findByCustomerIdAndToken(UUID customerId, String token);
    @Query(
            value = "INSERT INTO device(id, token, customer_id) VALUES(?#{#device.id}, ?#{#device.token}, ?#{#device.customerId.toString()})",
            nativeQuery = true
    )
    Device addDevice(@Param("device") Device device);


}
