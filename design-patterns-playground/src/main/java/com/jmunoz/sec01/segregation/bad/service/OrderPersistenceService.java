package com.jmunoz.sec01.segregation.bad.service;

import com.jmunoz.sec01.segregation.bad.entity.Order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//Stores Order entities
public class OrderPersistenceService implements PersistenceService<Order> {

    private static final Map<Long, Order> ORDERS = new HashMap<>();

    @Override
    public void save(Order entity) {
        synchronized (ORDERS) {
            ORDERS.put(entity.getId(), entity);
        }
    }

    @Override
    public void delete(Order entity) {
        synchronized (ORDERS) {
            ORDERS.remove(entity.getId());
        }
    }

    @Override
    public Order findById(Long id) {
        synchronized (ORDERS) {
            return ORDERS.get(id);
        }
    }

    // Este méto-do no tiene sentido para esta clase.
    // No cumple el principio de segregación de interfaces.
    @Override
    public List<Order> findByName(String name) {
        throw new UnsupportedOperationException("Find by name is not supported.");
    }

}
