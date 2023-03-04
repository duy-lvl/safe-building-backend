package com.safepass.safebuilding.bill_item.service.impl;

import com.safepass.safebuilding.bill_item.dto.BillItemDTO;
import com.safepass.safebuilding.bill_item.entity.BillItem;
import com.safepass.safebuilding.bill_item.repository.BillItemRepository;
import com.safepass.safebuilding.bill_item.service.BillItemService;
import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.common.utils.ModelMapperCustom;
import com.safepass.safebuilding.service.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BillItemServiceImpl implements BillItemService {

    @Autowired
    private BillItemRepository billItemRepository;
    @Autowired
    private ServiceRepository serviceRepository;
    private ModelMapperCustom modelMapper = new ModelMapperCustom();
    @Override
    public ResponseEntity<ResponseObject> getBillItem(String billId) {
        List<BillItem> billItems = billItemRepository.findByBillId(UUID.fromString(billId));
        List<BillItemDTO> billItemDTOS = modelMapper.mapList(billItems, BillItemDTO.class);
        int size = billItemDTOS.size();
        for (int i = 0; i < size; i++) {
            com.safepass.safebuilding.service.entity.Service service = serviceRepository.findServiceById(billItems.get(i).getService().getId()).get();
            billItemDTOS.get(i).setPrice(service.getPrice());
            billItemDTOS.get(i).setServiceName(service.getName());
        }
//        List<BillItemDTO> bi
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK.toString(), "Successfully", null, billItemDTOS));
    }
}
