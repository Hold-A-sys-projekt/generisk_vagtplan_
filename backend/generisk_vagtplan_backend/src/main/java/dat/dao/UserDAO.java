package dat.dao;

import dat.config.HibernateConfig;
import dat.dto.UserDTO;
import dat.dto.UserInfoDTO;
import dat.exception.AuthorizationException;
import dat.model.Role;
import dat.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;

import java.util.Optional;

public class UserDAO extends DAO<User> {

    private static UserDAO INSTANCE;

    private final DAO<Role> ROLE_DAO = new DAO<>(Role.class, emf);

    private UserDAO(EntityManagerFactory emf) {
        super(User.class, emf);
    }

    public static UserDAO getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserDAO(HibernateConfig.getEntityManagerFactory());
        }

        return INSTANCE;
    }

    public User getVerifiedUser(UserInfoDTO userInfo) throws AuthorizationException {
        return getVerifiedUser(userInfo.email(), userInfo.password());
    }

    public User getVerifiedUser(String email, String password) throws AuthorizationException {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            User user = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                    .setParameter("email", email)
                    .getSingleResult();
            if (user == null || !user.checkPassword(password)) {
                throw new AuthorizationException(401, "Invalid email or password");
            }

            em.getTransaction().commit();
            return user;
        } catch (NoResultException e) {
            throw new AuthorizationException(401, "Invalid email or password");
        }
    }

    public User registerUser(UserInfoDTO userInfo) throws AuthorizationException {
        return registerUser(userInfo.email(), userInfo.username(), userInfo.password(), "USER");
    }

    public User registerUser(String email, String username, String password, String userRole) throws AuthorizationException {
        User user = new User(email, username, password);
        Optional<Role> role = ROLE_DAO.readById(userRole);
        user.setRole(role.or(() -> Optional.of(createRole(userRole))).get());
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
            return user;
        } catch (Exception e) {
            throw new AuthorizationException(400, "Email already exists");
        }
    }

    private Role createRole(String role) {
        Role newRole = new Role(role);
        return ROLE_DAO.create(newRole);
    }

    public User update(UserDTO userDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            User user = em.find(User.class, userDTO.getId());
            user.setUsername(userDTO.getUsername());
            em.getTransaction().commit();
            return user;
        }
    }

    @Override
    public User create(User user) {
        throw new UnsupportedOperationException("Use register instead");
    }
}