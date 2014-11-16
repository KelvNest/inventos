/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pw.jpa;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import pw.entity.Estudio;
import pw.entity.Elenco;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import pw.entity.Pelicula;
import pw.jpa.exceptions.IllegalOrphanException;
import pw.jpa.exceptions.NonexistentEntityException;
import pw.jpa.exceptions.PreexistingEntityException;

/**
 *
 * @author seront
 */
public class PeliculaJpaController implements Serializable {

    public PeliculaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pelicula pelicula) throws PreexistingEntityException, Exception {
        if (pelicula.getElencoList() == null) {
            pelicula.setElencoList(new ArrayList<Elenco>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estudio idEstudio = pelicula.getIdEstudio();
            if (idEstudio != null) {
                idEstudio = em.getReference(idEstudio.getClass(), idEstudio.getIdEstudio());
                pelicula.setIdEstudio(idEstudio);
            }
            List<Elenco> attachedElencoList = new ArrayList<Elenco>();
            for (Elenco elencoListElencoToAttach : pelicula.getElencoList()) {
                elencoListElencoToAttach = em.getReference(elencoListElencoToAttach.getClass(), elencoListElencoToAttach.getElencoPK());
                attachedElencoList.add(elencoListElencoToAttach);
            }
            pelicula.setElencoList(attachedElencoList);
            em.persist(pelicula);
            if (idEstudio != null) {
                idEstudio.getPeliculaList().add(pelicula);
                idEstudio = em.merge(idEstudio);
            }
            for (Elenco elencoListElenco : pelicula.getElencoList()) {
                Pelicula oldPeliculaOfElencoListElenco = elencoListElenco.getPelicula();
                elencoListElenco.setPelicula(pelicula);
                elencoListElenco = em.merge(elencoListElenco);
                if (oldPeliculaOfElencoListElenco != null) {
                    oldPeliculaOfElencoListElenco.getElencoList().remove(elencoListElenco);
                    oldPeliculaOfElencoListElenco = em.merge(oldPeliculaOfElencoListElenco);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPelicula(pelicula.getIdPelicula()) != null) {
                throw new PreexistingEntityException("Pelicula " + pelicula + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pelicula pelicula) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pelicula persistentPelicula = em.find(Pelicula.class, pelicula.getIdPelicula());
            Estudio idEstudioOld = persistentPelicula.getIdEstudio();
            Estudio idEstudioNew = pelicula.getIdEstudio();
            List<Elenco> elencoListOld = persistentPelicula.getElencoList();
            List<Elenco> elencoListNew = pelicula.getElencoList();
            List<String> illegalOrphanMessages = null;
            for (Elenco elencoListOldElenco : elencoListOld) {
                if (!elencoListNew.contains(elencoListOldElenco)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Elenco " + elencoListOldElenco + " since its pelicula field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idEstudioNew != null) {
                idEstudioNew = em.getReference(idEstudioNew.getClass(), idEstudioNew.getIdEstudio());
                pelicula.setIdEstudio(idEstudioNew);
            }
            List<Elenco> attachedElencoListNew = new ArrayList<Elenco>();
            for (Elenco elencoListNewElencoToAttach : elencoListNew) {
                elencoListNewElencoToAttach = em.getReference(elencoListNewElencoToAttach.getClass(), elencoListNewElencoToAttach.getElencoPK());
                attachedElencoListNew.add(elencoListNewElencoToAttach);
            }
            elencoListNew = attachedElencoListNew;
            pelicula.setElencoList(elencoListNew);
            pelicula = em.merge(pelicula);
            if (idEstudioOld != null && !idEstudioOld.equals(idEstudioNew)) {
                idEstudioOld.getPeliculaList().remove(pelicula);
                idEstudioOld = em.merge(idEstudioOld);
            }
            if (idEstudioNew != null && !idEstudioNew.equals(idEstudioOld)) {
                idEstudioNew.getPeliculaList().add(pelicula);
                idEstudioNew = em.merge(idEstudioNew);
            }
            for (Elenco elencoListNewElenco : elencoListNew) {
                if (!elencoListOld.contains(elencoListNewElenco)) {
                    Pelicula oldPeliculaOfElencoListNewElenco = elencoListNewElenco.getPelicula();
                    elencoListNewElenco.setPelicula(pelicula);
                    elencoListNewElenco = em.merge(elencoListNewElenco);
                    if (oldPeliculaOfElencoListNewElenco != null && !oldPeliculaOfElencoListNewElenco.equals(pelicula)) {
                        oldPeliculaOfElencoListNewElenco.getElencoList().remove(elencoListNewElenco);
                        oldPeliculaOfElencoListNewElenco = em.merge(oldPeliculaOfElencoListNewElenco);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = pelicula.getIdPelicula();
                if (findPelicula(id) == null) {
                    throw new NonexistentEntityException("The pelicula with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pelicula pelicula;
            try {
                pelicula = em.getReference(Pelicula.class, id);
                pelicula.getIdPelicula();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pelicula with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Elenco> elencoListOrphanCheck = pelicula.getElencoList();
            for (Elenco elencoListOrphanCheckElenco : elencoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pelicula (" + pelicula + ") cannot be destroyed since the Elenco " + elencoListOrphanCheckElenco + " in its elencoList field has a non-nullable pelicula field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Estudio idEstudio = pelicula.getIdEstudio();
            if (idEstudio != null) {
                idEstudio.getPeliculaList().remove(pelicula);
                idEstudio = em.merge(idEstudio);
            }
            em.remove(pelicula);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pelicula> findPeliculaEntities() {
        return findPeliculaEntities(true, -1, -1);
    }

    public List<Pelicula> findPeliculaEntities(int maxResults, int firstResult) {
        return findPeliculaEntities(false, maxResults, firstResult);
    }

    private List<Pelicula> findPeliculaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pelicula.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Pelicula findPelicula(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pelicula.class, id);
        } finally {
            em.close();
        }
    }

    public int getPeliculaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pelicula> rt = cq.from(Pelicula.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
