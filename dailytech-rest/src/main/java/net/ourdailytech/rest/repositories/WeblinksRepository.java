package net.ourdailytech.rest.repositories;

import net.ourdailytech.rest.models.Weblink;
import net.ourdailytech.rest.models.PostEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(collectionResourceRel="weblink", path="weblink")
public interface WeblinksRepository extends JpaRepository<Weblink, Long> {
  List<Weblink> findByPostEntity(PostEntity postEntity);

}