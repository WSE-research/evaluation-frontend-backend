package org.wseresearch.backend.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;
import org.wseresearch.backend.helper.ExperimentsDTO;

@EnableMongoRepositories
public interface ExperimentsRepository extends MongoRepository<ExperimentsDTO,String> {

}
