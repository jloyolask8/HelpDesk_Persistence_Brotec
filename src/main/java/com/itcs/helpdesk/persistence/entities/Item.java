/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itcs.helpdesk.persistence.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jonathan
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Item.findAll", query = "SELECT c FROM Item c"),
    @NamedQuery(name = "Item.findByIdItem", query = "SELECT c FROM Item c WHERE c.idItem = :idItem"),
    @NamedQuery(name = "Item.findByNombre", query = "SELECT c FROM Item c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "Item.findByOrden", query = "SELECT c FROM Item c WHERE c.orden = :orden"),
    @NamedQuery(name = "Item.findTODOS", query = "SELECT c FROM Item c WHERE c.idItemPadre is null"),
    @NamedQuery(name = "Item.findByNombreLike", query = "SELECT c FROM Item c WHERE c.nombre like :nombre")
})
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_item")
    private Integer idItem;
    @Size(max = 40)
    private String nombre;
    @NotNull
    private boolean editable;

    private Integer orden;

    @OneToMany(mappedBy = "idItemPadre")
    private List<Item> itemList;

    @JoinColumn(name = "id_item_padre", referencedColumnName = "id_item")
    @ManyToOne
    private Item idItemPadre;

    @OneToMany(mappedBy = "idItem")
    private List<Caso> casoList;

    @JoinColumn(name = "id_area", referencedColumnName = "id_area")
    @ManyToOne(optional = false)
    private Area idArea;

    public Item() {
    }

    public Item(Integer idItem, String nombre, boolean editable, Integer orden, Area idArea) {
        this.idItem = idItem;
        this.nombre = nombre;
        this.editable = editable;
        this.orden = orden;
        this.idArea = idArea;
    }

    public Item(Integer idItem) {
        this.idItem = idItem;
    }

    public Integer getIdItem() {
        return idItem;
    }

    public void setIdItem(Integer idItem) {
        this.idItem = idItem;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    @XmlTransient
    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public Item getIdItemPadre() {
        return idItemPadre;
    }

    public void setIdItemPadre(Item idItemPadre) {
        this.idItemPadre = idItemPadre;
    }

    public Area getIdArea() {
        return idArea;
    }

    public void setIdArea(Area idArea) {
        this.idArea = idArea;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idItem != null ? idItem.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Item)) {
            return false;
        }
        Item other = (Item) object;
        if ((this.idItem == null && other.idItem != null) || (this.idItem != null && !this.idItem.equals(other.idItem))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        if (this.getIdItemPadre() == null) {
            return getNombre();
        } else {
            return getIdItemPadre().toString() + " /" + getNombre();
        }
    }

    /**
     * @return the editable
     */
    public boolean isEditable() {
        return editable;
    }

    /**
     * @param editable the editable to set
     */
    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    @XmlTransient
    public List<Caso> getCasoList() {
        return casoList;
    }

    public void setCasoList(List<Caso> casoList) {
        this.casoList = casoList;
    }
}
