/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pw.entity;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author seront
 */
@Entity
@Table(name = "estudio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Estudio.findAll", query = "SELECT e FROM Estudio e"),
    @NamedQuery(name = "Estudio.findByIdEstudio", query = "SELECT e FROM Estudio e WHERE e.idEstudio = :idEstudio"),
    @NamedQuery(name = "Estudio.findByNombre", query = "SELECT e FROM Estudio e WHERE e.nombre = :nombre"),
    @NamedQuery(name = "Estudio.findByPais", query = "SELECT e FROM Estudio e WHERE e.pais = :pais")})
public class Estudio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_estudio")
    private String idEstudio;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "pais")
    private String pais;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEstudio")
    private List<Pelicula> peliculaList;

    public Estudio() {
    }

    public Estudio(String idEstudio) {
        this.idEstudio = idEstudio;
    }

    public Estudio(String idEstudio, String nombre, String pais) {
        this.idEstudio = idEstudio;
        this.nombre = nombre;
        this.pais = pais;
    }

    public String getIdEstudio() {
        return idEstudio;
    }

    public void setIdEstudio(String idEstudio) {
        this.idEstudio = idEstudio;
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

    @XmlTransient
    public List<Pelicula> getPeliculaList() {
        return peliculaList;
    }

    public void setPeliculaList(List<Pelicula> peliculaList) {
        this.peliculaList = peliculaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEstudio != null ? idEstudio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Estudio)) {
            return false;
        }
        Estudio other = (Estudio) object;
        if ((this.idEstudio == null && other.idEstudio != null) || (this.idEstudio != null && !this.idEstudio.equals(other.idEstudio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pw.entity.Estudio[ idEstudio=" + idEstudio + " ]";
    }
    
}
