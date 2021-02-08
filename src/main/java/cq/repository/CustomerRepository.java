package cq.repository;

import cq.entity.Customer;

public interface CustomerRepository extends BaseAppUserRepositoryImp<Customer>{
	boolean existsByEmail(String email);
}
