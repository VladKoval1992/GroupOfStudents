import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class App {
    public static void main( String[] args ) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPATest");
        EntityManager em = emf.createEntityManager();
        Random ran = new Random(System.currentTimeMillis());
        try {

            em.getTransaction().begin();
            try {

                List<Course> courses = new ArrayList<>();
                courses.add(new Course("Java Start"));
                courses.add(new Course("Java OOP"));
                courses.add(new Course("Java Pro"));

                List<Group> groups = new ArrayList<>();
                groups.add(new Group("Group A"));
                groups.add(new Group("Group B"));
                groups.add(new Group("Group C"));


                System.out.println("------------------ #1 ------------------");

                for (int j = 0; j < groups.size(); j++)
                    for (int i = 0; i < 30; i++) {
                        Client client = new Client("Name" + i, 50 - ran.nextInt(30));
                        groups.get(j).addClient(client);
                        courses.get(j).addClient(client);
                    }

                for (Group c : groups)
                    em.persist(c);

                for (Course c : courses)
                    em.persist(c);

                em.getTransaction().commit();
            } catch (Exception ex) {
                em.getTransaction().rollback();
                return;
            }
           System.out.println("------------------ #2 ------------------");


            Query query = em.createNamedQuery("Course.findAll", Course.class);
            List<Course> courseList = query.getResultList();

            for (Course course : courseList) {
                for (Client client : course.getClients()) {
                    System.out.println(client.getName() +  " age = " + client.getAge() +  " goes to " + course.getName());
                }
            }

            System.out.println("------------------ #3 ------------------");

            query = em.createQuery("SELECT c FROM Group c ", Group.class);
            List<Group> groupList = query.getResultList();

            for (Group group : groupList) {
                System.out.println(group.getName() + " size group = " + group.getSize());

            }

            System.out.println("------------------ #4 ------------------");
            try {
                query = em.createNamedQuery("Course.findByName", Course.class);

                query.setParameter("name", "Java Start");
                Course course = (Course) query.getSingleResult();

                for (Client c : course.getClients()) {
                    System.out.println(c);
                }

            } catch (NoResultException ex) {
                System.out.println("Course not found!");
                return;
            } catch (NonUniqueResultException ex) {
                System.out.println("Non unique course found!");
                return;
            }




        } finally {
            em.close();
            emf.close();
        }
    }
}

