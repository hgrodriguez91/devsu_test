package com.test.devsu.srvclientmanager.util;

import com.test.devsu.srvclientmanager.dto.ClientDTO;
import com.test.devsu.srvclientmanager.model.Client;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

    public static Date getDateFromQueryParams(String date)  {
        try {
            return  new SimpleDateFormat("dd-MM-yyyy").parse(date);
        }catch (ParseException e){
            return null;
        }
    }

    public static void mapfromDTO(Client client, ClientDTO clientDTO) {
        client.setName(clientDTO.getName() != null ? clientDTO.getName() : client.getName());
        client.setDni(clientDTO.getDni() != null ? clientDTO.getDni() : client.getDni());
        client.setAddress(clientDTO.getAddress() != null ? clientDTO.getAddress() : client.getAddress());
        client.setAge(clientDTO.getAge() != null ? clientDTO.getAge() : client.getAge());
        client.setGender(clientDTO.getGender() != null ? clientDTO.getGender() : client.getGender());
        client.setPhoneNumber(clientDTO.getPassword() != null ? clientDTO.getPassword() : client.getPassword());
        client.setPhoneNumber(clientDTO.getPhoneNumber() != null ? clientDTO.getPhoneNumber() : client.getPhoneNumber());
        client.setStatus(clientDTO.getStatus() != null ? clientDTO.getStatus() : client.getStatus());
    }
}
