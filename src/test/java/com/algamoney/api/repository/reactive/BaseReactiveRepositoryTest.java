package com.algamoney.api.repository.reactive;


import com.algamoney.api.repository.CategoriaRepository;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan(basePackageClasses = CategoriaRepository.class)
public class BaseReactiveRepositoryTest {
}
