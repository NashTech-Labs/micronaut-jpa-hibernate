package example.micronaut.repository.impl;

import example.micronaut.entity.User;
import example.micronaut.repository.UserRepository;
import io.micronaut.transaction.annotation.ReadOnly;
import io.micronaut.transaction.annotation.TransactionalAdvice;
import jakarta.inject.Singleton;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Singleton
public class UserRepositoryImpl implements UserRepository {
    private final EntityManager entityManager;

    public UserRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;

    }

    @Override
    @ReadOnly
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Override
    @TransactionalAdvice
    @Transactional
    public User save(User user) {
        entityManager.persist(user);
        return user;

    }

    @Override
    @TransactionalAdvice
    @Transactional
    public void deleteById(Long id) {
        findById(id).ifPresent(entityManager::remove);
    }

    @ReadOnly
    public List<User> findAll() {
        return entityManager.
                createQuery("SELECT c FROM User c").
                getResultList();
    }

    @Override
    @TransactionalAdvice
    @Transactional
    public int update(Long id, User user) {
        return entityManager.createQuery("UPDATE User g SET firstName = :firstName where id = :id")
                .setParameter("firstName", user.getFirstName())
                .setParameter("id", id)
                .executeUpdate();
    }
}