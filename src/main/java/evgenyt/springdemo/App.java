package evgenyt.springdemo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.persistence.criteria.*;
import java.util.List;

/**
 * Hibernate CriteriaQuery demo: select, order by, like, or...
 * Aug 2019 EvgenyT
 */

public class App {
    public static void main( String[] args ) {
        // Get application context from file
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "applicationContext.xml");
        // Get factory bean and create session
        SessionFactory sessionFactory = context.getBean("sessionFactory",
                SessionFactory.class);
        Session session = sessionFactory.openSession();
        // Create builder
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        // Create criteria query
        CriteriaQuery<PersonEntity> criteriaQuery =
                criteriaBuilder.createQuery(PersonEntity.class);
        // Specify select root table "person"
        Root<PersonEntity> from = criteriaQuery.from(PersonEntity.class);
        criteriaQuery.select(from);
        // ORDER BY person.person_id
        criteriaQuery.orderBy(criteriaBuilder.asc(from.get("id")));
        // WHERE person_name LIKE "Julia%"
        Path expression = from.get("name");
        Predicate predicateLike = criteriaBuilder.like((Expression<String>) expression , "Julia%");
        criteriaQuery.where(predicateLike);
        // WHERE person_id = 1
        Path pathId = from.get("id");
        Predicate predicateEq = criteriaBuilder.equal((Expression<String>) pathId, 1);
        // person_name LIKE "Julia%" OR person_id = 1
        Predicate finalPredicate = criteriaBuilder.or(predicateLike, predicateEq);
        criteriaQuery.where(finalPredicate);
        // Execute and show query result
        List<PersonEntity> resultList = session.createQuery(criteriaQuery).getResultList();
        for (PersonEntity personEntity : resultList)
            System.out.println(personEntity);
        session.close();
    }
}
