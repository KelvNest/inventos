/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pw.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author seront
 */
@Entity
@Table(name = "actor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Actor.findAll", query = "SELECT a FROM Actor a"),
    @NamedQuery(name = "Actor.findByIdActor", query = "SELECT a FROM Actor a WHERE a.idActor = :idActor"),
    @NamedQuery(name = "Actor.findByNombre", query = "SELECT a FROM Actor a WHERE a.nombre LIKE :nombre"),
    @NamedQuery(name = "Actor.findByPais", query = "SELECT a FROM Actor a WHERE a.pais = :pais"),
    @NamedQuery(name = "Actor.findBySexo", query = "SELECT a FROM Actor a WHERE a.sexo = :sexo"),
    @NamedQuery(name = "Actor.findByFechaNacimiento", query = "SELECT a FROM Actor a WHERE a.fechaNacimiento = :fechaNacimiento"),
    @NamedQuery(name = "Actor.findByComision", query = "SELECT a FROM Actor a WHERE a.comision = :comision")})
public class Actor implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_actor")
    private String idActor;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "pais")
    private String pais;
    @Basic(optional = false)
    @Column(name = "sexo")
    private String sexo;
    @Basic(optional = false)
    @Column(name = "fecha_nacimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    @Basic(optional = false)
    @Column(name = "comision")
    private double comision;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "actor")
    private List<Elenco> elencoList;

    public Actor() {
    }

    public Actor(String idActor) {
        this.idActor = idActor;
    }

    public Actor(String idActor, String nombre, String pais, String sexo, Date fechaNacimiento, double comision) {
        this.idActor = idActor;
        this.nombre = nombre;
        this.pais = pais;
        this.sexo = sexo;
        this.fechaNacimiento = fechaNacimiento;
        this.comision = comision;
    }

    public String getIdActor() {
        return idActor;
    }

    public void setIdActor(String idActor) {
        this.idActor = idActor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public double getComision() {
        return comision;
    }

    public void setComision(double comision) {
        this.comision = comision;
    }

    @XmlTransient
    public List<Elenco> getElencoList() {
        return elencoList;
    }

    public void setElencoList(List<Elenco> elencoList) {
        this.elencoList = elencoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idActor != null ? idActor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Actor)) {
            return false;
        }
        Actor other = (Actor) object;
        if ((this.idActor == null && other.idActor != null) || (this.idActor != null && !this.idActor.equals(other.idActor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pw.entity.Actor[ idActor=" + idActor + " ]";
    }
    
}
