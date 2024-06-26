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

import java.util.List;
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

    // I've changed credential from email to username - Mikkel
    public User getVerifiedUser(UserInfoDTO userInfo) throws AuthorizationException {
        return getVerifiedUser(userInfo.username(), userInfo.password());
    }

    public User getVerifiedUser(String username, String password) throws AuthorizationException {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            //TODO: using email parameter as those are always unique in workplaces or should this be name? - Yusuf
            // We've chosen to use username to create the signup/login functionality - group I
            User user = em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
            if (user == null || !user.checkPassword(password)) {
                throw new AuthorizationException(401, "Invalid username or password");
            }
            em.getTransaction().commit();
            return user;
        } catch (NoResultException e) {
            throw new AuthorizationException(401, "Invalid username or password");
        }
    }

    public User getUser(String username) throws AuthorizationException {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            User user = em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
            em.getTransaction().commit();
            return user;
        } catch (NoResultException e) {
            throw new AuthorizationException(401, "Invalid username or password");
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

    public List<User> getUsersByRole(String roleName) {
        EntityManager em = emf.createEntityManager();
        try {
            System.out.println("Executing query with roleName: " + roleName);
            return em.createQuery("SELECT u FROM User u WHERE u.role.name = :roleName", User.class)
                     .setParameter("roleName", roleName)
                     .getResultList();
        } catch (Exception e) {
            System.err.println("Error executing query with roleName: " + roleName + " - " + e.getMessage());
            throw e;
        } finally {
            em.close();
        }
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