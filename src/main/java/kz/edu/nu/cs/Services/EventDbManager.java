package kz.edu.nu.cs.Services;

import kz.edu.nu.cs.Model.Event;
import kz.edu.nu.cs.Model.UserGroup;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

class EventDbManager {
    private EntityManagerFactory emfactory;
    private EntityManager em;

    public EventDbManager() {
        emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        em = emfactory.createEntityManager();
        em.getTransaction().begin();
    }

    public void createEvent(Event event) {
        UserGroup ug = new UserGroup();
        ug.setEmail(event.getAdmin());
        ug.setName(event.getName());
        em.persist(ug);
        em.persist(event);
        em.getTransaction().commit();
        closeConnection();
    }
//    public void mergeEvent(Event event) {
//        em.merge(event);
//        em.getTransaction().commit();
//        closeConnection();
//    }

    public List getMyEvents(String email) {
        List events = em.createNamedQuery("Event.getEventsByEmail").setParameter("email", email).getResultList();
        closeConnection();
        return events;
    }

    public void join(String email, int groupId) {
        String name = (String)em.createNamedQuery("Event.getNameById").setParameter("id", groupId).getSingleResult();
        System.out.println("\n\n\n!!!!!!!!!!!!!!!!!111123123132123" + name);
        UserGroup ug = new UserGroup();
        ug.setEmail(email);
        ug.setName(name);
        em.persist(ug);
        em.getTransaction().commit();
        closeConnection();
    }

    public void leaveGroup(String email, int groupId) {
        String name = (String)em.createNamedQuery("Event.getNameById").setParameter("id", groupId).getSingleResult();
        em.createNamedQuery("UserGroup.deleteRow").setParameter("name", name).setParameter("email", email).getSingleResult();
        closeConnection();
    }

    public boolean deactivateGroup(String email, int groupId) {
        Event event = (Event) em.createNamedQuery("Event.getEventById").setParameter("id", groupId).getSingleResult();
        if (!event.getAdmin().equals(email)) {
            return false;
        }
        event.setIsactive(0);
        em.merge(event);
        em.getTransaction().commit();
        return true;
    }

    public void closeConnection() {
        em.close();
        emfactory.close();
    }

    public List getList() {
        List result = em.createNamedQuery("Event.findAll").getResultList();
        closeConnection();
        return result;
    }
}
