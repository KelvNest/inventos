/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pw.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author seront
 */
@Entity
@Table(name = "elenco")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Elenco.findAll", query = "SELECT e FROM Elenco e"),
    @NamedQuery(name = "Elenco.findByIdActor", query = "SELECT e FROM Elenco e WHERE e.elencoPK.idActor = :idActor"),
    @NamedQuery(name = "Elenco.findByIdPelicula", query = "SELECT e FROM Elenco e WHERE e.elencoPK.idPelicula = :idPelicula"),
    @NamedQuery(name = "Elenco.findByPapel", query = "SELECT e FROM Elenco e WHERE e.papel = :papel"),
    @NamedQuery(name = "Elenco.findBySalario", query = "SELECT e FROM Elenco e WHERE e.salario = :salario")})
public class Elenco implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ElencoPK elencoPK;
    @Basic(optional = false)
    @Column(name = "papel")
    private String papel;
    @Basic(optional = false)
    @Column(name = "salario")
    private double salario;
    @JoinColumn(name = "id_pelicula", referencedColumnName = "id_pelicula", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Pelicula pelicula;
    @JoinColumn(name = "id_actor", referencedColumnName = "id_actor", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Actor actor;

    public Elenco() {
    }

    public Elenco(ElencoPK elencoPK) {
        this.elencoPK = elencoPK;
    }

    public Elenco(ElencoPK elencoPK, String papel, double salario) {
        this.elencoPK = elencoPK;
        this.papel = papel;
        this.salario = salario;
    }

    public Elenco(String idActor, String idPelicula) {
        this.elencoPK = new ElencoPK(idActor, idPelicula);
    }

    public ElencoPK getElencoPK() {
        return elencoPK;
    }

    public void setElencoPK(ElencoPK elencoPK) {
        this.elencoPK = elencoPK;
    }

    public String getPapel() {
        return papel;
    }

    public void setPapel(String papel) {
        this.papel = papel;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (elencoPK != null ? elencoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Elenco)) {
            return false;
        }
        Elenco other = (Elenco) object;
        if ((this.elencoPK == null && other.elencoPK != null) || (this.elencoPK != null && !this.elencoPK.equals(other.elencoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pw.entity.Elenco[ elencoPK=" + elencoPK + " ]";
    }
    
}
