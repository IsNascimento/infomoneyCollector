package com.isnascimento.infocollector.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Service
public class EndpointUtil implements Serializable {

    public ResponseEntity<?> returnObjectOrNotFound(final Optional object) {
        return object.isPresent() ? new ResponseEntity<>(object, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public <T> ResponseEntity<List<T>> returnListOrNotFound(final List<T> list) {
        return list == null || list.isEmpty() ? new ResponseEntity<List<T>>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(list, HttpStatus.OK);
    }

}
