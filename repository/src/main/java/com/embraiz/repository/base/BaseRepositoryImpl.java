package com.embraiz.repository.base;

import com.embraiz.entity.base.*;
import org.hibernate.Session;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Repository
public abstract class BaseRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements BaseRepository<T, ID> {

    private final EntityManager entityManager;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public BaseRepositoryImpl(Class<T> domainClass, EntityManager entityManager) {
        super(domainClass, entityManager);
        this.entityManager = entityManager;
    }

    private Session getSession() {
        return entityManager.unwrap(Session.class);
    }

    public Object findObject(SearchMap searchMap) {
        return this.findObject(getDomainClass(), searchMap);
    }

    public Object findObject(Class<?> cls, SearchMap searchMap) {
        List list = this.findObjects(cls, searchMap);
        return list != null && list.size() > 0 ? list.get(0) : null;
    }

    public List findObjects(SearchMap searchMap) {
        return this.findObjects(getDomainClass(), searchMap);
    }

    public List findObjects(Class<?> cls, SearchMap searchMap) {
        return this.findObjects(cls, searchMap, -1, -1);
    }

    public List findObjects(SearchMap searchMap, int pageNum, int pageSize) {
        return this.findObjects(getDomainClass(), searchMap, pageNum, pageSize);
    }

    public List findObjects(Class<?> cls, SearchMap searchMap, int pageNum, int pageSize) {
        TypedQuery<?> typedQuery = this.getTypedQuery(cls, searchMap);
        return pageNum == -1 && pageSize == -1 ? typedQuery.getResultList() : typedQuery.setFirstResult((pageNum - 1) * pageSize).setMaxResults(pageSize).getResultList();
    }

    public PageResult findObjectsForPage(SearchMap searchMap, int pageNum, int pageSize) {
        return this.findObjectsForPage(getDomainClass(), searchMap, pageNum, pageSize);
    }

    public PageResult findObjectsForPage(Class<?> cls, SearchMap searchMap, int pageNum, int pageSize) {
        long totalCount = this.getCount(cls, searchMap);
        List list = this.findObjects(cls, searchMap, DefaultPageResult.adjustPageNum(totalCount, pageNum, pageSize), pageSize);
        return new DefaultPageResult(list, totalCount, DefaultPageResult.pageNumToStartIndex(totalCount, pageNum, pageSize), pageSize);
    }

    public long getCount(SearchMap searchMap) {
        return this.getCount(getDomainClass(), searchMap);
    }

    public long getCount(Class<?> cls, SearchMap searchMap) {
        TypedQuery<?> typedQuery = this.getTypedQuery(cls, searchMap);
        List list = typedQuery.getResultList();
        return list != null ? list.size() : 0L;
    }

    public int updateHandle(UpdateMap updateMap) {
        Query query = this.getUpdateQuery(updateMap);
        return query.executeUpdate();
    }

    private List<Predicate> getPredicate(Root<?> root, List<SearchParameter> parameters, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (parameters != null && parameters.size() > 0) {
            for (SearchParameter parameter : parameters) {
                if (parameter.getType().equals(SearchMap.TYPE_EQ)) {
                    if (isNotNullOrEmpty(parameter.getValue())) {
                        predicates.add(criteriaBuilder.equal(root.get(parameter.getName()), parameter.getValue()));
                    }
                }
                if (parameter.getType().equals(SearchMap.TYPE_NOT_EQ)) {
                    if (isNotNullOrEmpty(parameter.getValue())) {
                        predicates.add(criteriaBuilder.notEqual(root.get(parameter.getName()), parameter.getValue()));
                    }
                }
                if (parameter.getType().equals(SearchMap.TYPE_GT)) {
                    if (isNotNullOrEmpty(parameter.getValue())) {
                        if (parameter.getValue() instanceof Number) {
                            predicates.add(criteriaBuilder.gt(root.get(parameter.getName()), (Number) parameter.getValue()));
                        }
                        if (parameter.getValue() instanceof Date) {
                            predicates.add(criteriaBuilder.greaterThan(root.get(parameter.getName()), (Date) parameter.getValue()));
                        }
                        if (parameter.getValue() instanceof Calendar) {
                            predicates.add(criteriaBuilder.greaterThan(root.get(parameter.getName()), (Calendar) parameter.getValue()));
                        }
                    }
                }
                if (parameter.getType().equals(SearchMap.TYPE_GE)) {
                    if (isNotNullOrEmpty(parameter.getValue())) {
                        if (parameter.getValue() instanceof Number) {
                            predicates.add(criteriaBuilder.ge(root.get(parameter.getName()), (Number) parameter.getValue()));
                        }
                        if (parameter.getValue() instanceof Date) {
                            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(parameter.getName()), (Date) parameter.getValue()));
                        }
                        if (parameter.getValue() instanceof Calendar) {
                            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(parameter.getName()), (Calendar) parameter.getValue()));
                        }
                    }
                }
                if (parameter.getType().equals(SearchMap.TYPE_LT)) {
                    if (isNotNullOrEmpty(parameter.getValue())) {
                        if (parameter.getValue() instanceof Number) {
                            predicates.add(criteriaBuilder.lt(root.get(parameter.getName()), (Number) parameter.getValue()));
                        }
                        if (parameter.getValue() instanceof Date) {
                            predicates.add(criteriaBuilder.lessThan(root.get(parameter.getName()), (Date) parameter.getValue()));
                        }
                        if (parameter.getValue() instanceof Calendar) {
                            predicates.add(criteriaBuilder.lessThan(root.get(parameter.getName()), (Calendar) parameter.getValue()));
                        }
                    }
                }
                if (parameter.getType().equals(SearchMap.TYPE_LE)) {
                    if (isNotNullOrEmpty(parameter.getValue())) {
                        if (parameter.getValue() instanceof Number) {
                            predicates.add(criteriaBuilder.le(root.get(parameter.getName()), (Number) parameter.getValue()));
                        }
                        if (parameter.getValue() instanceof Date) {
                            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(parameter.getName()), (Date) parameter.getValue()));
                        }
                        if (parameter.getValue() instanceof Calendar) {
                            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(parameter.getName()), (Calendar) parameter.getValue()));
                        }
                    }
                }
                if (parameter.getType().equals(SearchMap.TYPE_BETWEEN)) {
                    if (isNotNullOrEmpty(parameter.getValue())) {
                        if (parameter.getValue() instanceof Number[]) {
                            predicates.add(criteriaBuilder.ge(root.get(parameter.getName()), ((Number[]) parameter.getValue())[0]));
                            predicates.add(criteriaBuilder.le(root.get(parameter.getName()), ((Number[]) parameter.getValue())[1]));
                        }
                        if (parameter.getValue() instanceof Date[]) {
                            predicates.add(criteriaBuilder.between(root.get(parameter.getName()), ((Date[]) parameter.getValue())[0], ((Date[]) parameter.getValue())[1]));
                        }
                        if (parameter.getValue() instanceof Calendar[]) {
                            predicates.add(criteriaBuilder.between(root.get(parameter.getName()), ((Calendar[]) parameter.getValue())[0], ((Calendar[]) parameter.getValue())[1]));
                        }
                    }
                }
                if (parameter.getType().equals(SearchMap.TYPE_LIKE)) {
                    if (isNotNullOrEmpty(parameter.getValue())) {
                        predicates.add(criteriaBuilder.like(root.get(parameter.getName()), "%" + parameter.getValue() + "%"));
                    }
                }
                if (parameter.getType().equals(SearchMap.TYPE_NOT_LIKE)) {
                    if (isNotNullOrEmpty(parameter.getValue())) {
                        predicates.add(criteriaBuilder.notLike(root.get(parameter.getName()), "%" + parameter.getValue() + "%"));
                    }
                }
                if (parameter.getType().equals(SearchMap.TYPE_IN)) {
                    if (isNotNullOrEmpty(parameter.getValue())) {
                        Object[] values = ((Object[]) parameter.getValue());
                        CriteriaBuilder.In in = criteriaBuilder.in(root.get(parameter.getName()));
                        for (Object value : values) {
                            in.value(value);
                        }
                        predicates.add(in);
                    }
                }
                if (parameter.getType().equals(SearchMap.TYPE_NOT_IN)) {
                    if (isNotNullOrEmpty(parameter.getValue())) {
                        Object[] values = ((Object[]) parameter.getValue());
                        CriteriaBuilder.In in = criteriaBuilder.in(root.get(parameter.getName()));
                        for (Object value : values) {
                            in.value(value);
                        }
                        predicates.add(criteriaBuilder.not(in));
                    }
                }
                if (parameter.getType().equals(SearchMap.TYPE_NULL)) {
                    predicates.add(criteriaBuilder.isNull(root.get(parameter.getName())));
                }
                if (parameter.getType().equals(SearchMap.TYPE_NOT_NULL)) {
                    predicates.add(criteriaBuilder.isNotNull(root.get(parameter.getName())));
                }
                if (parameter.getType().equals(SearchMap.TYPE_OR_EQ)) {
                    if (isNotNullOrEmpty(parameter.getValue())) {
                        Object[] values = ((Object[]) parameter.getValue());
                        Predicate p = null;
                        for (Object value : values) {
                            if (p != null) {
                                p = criteriaBuilder.or(p, criteriaBuilder.equal(root.get(parameter.getName()), value));
                            } else {
                                p = criteriaBuilder.or(criteriaBuilder.equal(root.get(parameter.getName()), value));
                            }
                        }
                        predicates.add(p);
                    }
                }
                if (parameter.getType().equals(SearchMap.TYPE_OR_LIKE)) {
                    if (isNotNullOrEmpty(parameter.getValue())) {
                        Object[] values = ((Object[]) parameter.getValue());
                        Predicate p = null;
                        for (Object value : values) {
                            if (p != null) {
                                p = criteriaBuilder.or(p, criteriaBuilder.like(root.get(parameter.getName()), (String)value));
                            } else {
                                p = criteriaBuilder.or(criteriaBuilder.like(root.get(parameter.getName()), (String)value));
                            }
                        }
                        predicates.add(p);
                    }
                }
            }
        }
        return predicates;
    }

    private TypedQuery<?> getTypedQuery(Class<?> cls, SearchMap searchMap) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<?> criteriaQuery = criteriaBuilder.createQuery(cls);
        if (searchMap != null) {
            Root<?> root = criteriaQuery.from(cls);
            List<SearchParameter> parameters = searchMap.getParameters();
            List<String> selectParameters = searchMap.getSelects();
            List<Max> maxes = searchMap.getMaxes();
            List<String> groupParameters = searchMap.getGroups();
            List<SortCondition> sortConditions = searchMap.getSorts();
            List<Selection<?>> selects = new ArrayList<>();
            List<Expression<?>> groups = new ArrayList<>();
            List<Order> orders = new ArrayList<>();
            List<Predicate> predicates = this.getPredicate(root, parameters, criteriaBuilder);
            if (predicates.size() > 0) {
                criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[]{})));
            }
            if (selectParameters != null && selectParameters.size() > 0) {
                for (String selectParameter : selectParameters) {
                    selects.add(root.get(selectParameter));
                }
            }
            if (maxes != null && maxes.size() > 0) {
                for (Max max : maxes) {
                    selects.add(criteriaBuilder.max(root.get(max.getName())).alias(max.getAsName()));
                }
            }
            if (groupParameters != null && groupParameters.size() > 0) {
                for (String groupParameter : groupParameters) {
                    groups.add(root.get(groupParameter));
                }
            }

            if (sortConditions != null && sortConditions.size() > 0) {
                for (SortCondition sortCondition : sortConditions) {
                    if (sortCondition.getSortType().equals(SortCondition.ASC)) {
                        orders.add(criteriaBuilder.asc(root.get(sortCondition.getSortName())));
                    } else {
                        orders.add(criteriaBuilder.desc(root.get(sortCondition.getSortName())));
                    }
                }
            }

            if (selects.size() > 0) {
                criteriaQuery.multiselect(selects);
            }
            if (groups.size() > 0) {
                criteriaQuery.groupBy(groups);
            }
            if (orders.size() > 0) {
                criteriaQuery.orderBy(orders);
            }
        }
        return entityManager.createQuery(criteriaQuery);
    }

    private Query getUpdateQuery(UpdateMap updateMap) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<T> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(getDomainClass());
        if (updateMap != null) {
            Root<T> root = criteriaUpdate.from(getDomainClass());
            List<SearchParameter> parameters = updateMap.getParameters();
            List<DefaultParameter> updateParameters = updateMap.getUpdateParameters();
            List<Predicate> predicates = this.getPredicate(root, parameters, criteriaBuilder);
            if (predicates.size() > 0) {
                criteriaUpdate.where(criteriaBuilder.and(predicates.toArray(new Predicate[]{})));
            }
            for (DefaultParameter updateParameter : updateParameters) {
                if (isNotNullOrEmpty(updateParameter.getValue())) {
                    criteriaUpdate.set(updateParameter.getName(), updateParameter.getValue());
                }
            }
        }
        return entityManager.createQuery(criteriaUpdate);
    }

    private boolean isNotNullOrEmpty(Object value) {
        if (value == null) {
            return false;
        }
        if (value instanceof String) {
            return !"".equals(value);
        }
        return true;
    }
}
