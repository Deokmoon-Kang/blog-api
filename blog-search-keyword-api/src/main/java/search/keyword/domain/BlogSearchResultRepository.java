package search.keyword.domain;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogSearchResultRepository extends JpaRepository<SearchKeyword, String> {

    @Query("SELECT e FROM SearchKeyword e ORDER BY e.searchCount DESC")
    List<SearchKeyword> findAllOrderBySearchCountDescWithLimit(int limit);
}
