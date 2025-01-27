package org.example.entidades;

import javax.persistence.*;

@Entity
@Table(name="muebles")
public class Mueble {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", length = 100, unique = true)
    private String nombre;

    @Column(name = "company", length = 30, nullable = false)
    private String marca;

    @Column(name = "image")
    private String imagen;

    @Column(name = "price", nullable = false)
    private double precio;

    public Mueble() {
    }

    public Mueble(Long id, double precio, String imagen, String marca, String nombre) {
        this.id = id;
        this.precio = precio;
        this.imagen = imagen;
        this.marca = marca;
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Mueble{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", marca='" + marca + '\'' +
                ", imagen='" + imagen + '\'' +
                ", precio=" + precio +
                '}';
    }
}
