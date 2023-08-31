package com.cheong.clinic_api.entity;

import java.io.Serializable;
import java.sql.Connection;
import java.util.stream.Stream;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class ProductIdGenerator implements IdentifierGenerator{
	
	private final String prefix = "P";

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		
		String query = String.format("select %s from %s", 
	            session.getEntityPersister(object.getClass().getName(), object)
	              .getIdentifierPropertyName(),
	            object.getClass().getSimpleName());
		
		Stream<String> ids = session.createQuery(query).stream();

	
        Long max = ids.map(o -> o.replace(prefix + "-", ""))
          .mapToLong(Long::parseLong)
          .max()
          .orElse(0L);
        
        return prefix + "-" + (max + 1);
	}

}
