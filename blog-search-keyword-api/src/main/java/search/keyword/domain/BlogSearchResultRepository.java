package search.keyword.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogSearchResultRepository extends JpaRepository<SearchKeyword, String> {
}
