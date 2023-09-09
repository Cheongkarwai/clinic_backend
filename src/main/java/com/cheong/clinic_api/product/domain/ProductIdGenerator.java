package com.cheong.clinic_api.product.domain;

import com.cheong.clinic_api.common.constant.DatabaseConstant;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.stereotype.Component;

public class ProductIdGenerator implements IdentifierGenerator {

    private final String PREFIX = "P-";

    @Override
    public Object generate(SharedSessionContractImplementor session, Object object) {
        Long count = session.createQuery("SELECT COUNT(id) FROM Product",Long.class).getSingleResult();
        return PREFIX + (count + 1);
    }
}
