package hello;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ResultRepository extends CrudRepository<Result, Long> {

    List<Result> findByCode(String code);

}