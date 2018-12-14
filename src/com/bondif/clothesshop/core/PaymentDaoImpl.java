package com.bondif.clothesshop.core;

import com.bondif.clothesshop.models.Payment;

import java.sql.Connection;
import java.util.Collection;

public class PaymentDaoImpl extends AbstractDao implements Dao<Payment> {
    @Override
    public Connection getConnection() {
        return Database.getInstance().getConnection();
    }

    @Override
    public Collection<Payment> findAll() {
        return null;
    }

    @Override
    public Payment findOne(long id) {
        return null;
    }

    @Override
    public void create(Payment payment) {

    }

    @Override
    public void update(Payment entity) {

    }

    @Override
    public void delete(Payment entity) {

    }
}
