package search.utils;

import com.google.common.base.CaseFormat;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class DatabaseCleaner implements InitializingBean {

    @PersistenceContext
    private EntityManager entityManager;

    private List<String> tableNames;

    @Override
    public void afterPropertiesSet() {
        tableNames = entityManager.getMetamodel().getEntities().stream()
                .filter(e -> e.getJavaType().getAnnotation(Entity.class) != null)
                .map(e -> {
                    if(e.getName().equalsIgnoreCase("order")) {
                        return "orders";
                    }

                    return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, e.getName());
                })
                .collect(toList());
    }

    @Transactional
    public void execute() {
        entityManager.flush();
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();

        for (String tableName : tableNames) {
            entityManager.createNativeQuery("TRUNCATE TABLE " + tableName).executeUpdate();
        }

        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
    }

    private boolean isTableContainedSeqColumn(String tableName) {
        return StringUtils.equals(tableName, "order_line_item") ||
                StringUtils.equals(tableName, "menu_product");
    }
}
