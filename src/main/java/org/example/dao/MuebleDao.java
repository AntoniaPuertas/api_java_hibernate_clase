package org.example.dao;

import org.example.entidades.Mueble;
import org.example.util.HibernateUtil;
import org.hibernate.Session;

import javax.persistence.PersistenceException;
import java.util.List;

/*
DAO: Data Access Object
Patrón de diseño en el cual una clase se encarga de las operaciones de persistencia contra
una tabla de la base de datos
Implementa la lógica de acceso a los datos utilizando Hibernate
 */
public class MuebleDao implements MuebleDAOInterface{
    @Override
    public List<Mueble> devolverTodos() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        //consulta HQL
        List<Mueble> todos = session.createQuery("from Mueble", Mueble.class).list();
        session.close();
        return todos;
    }

    @Override
    public Mueble buscarPorId(long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        //consulta HQL
        //TODO comprobar si el mueble existe
        Mueble mueble = session.find(Mueble.class, id);
        session.close();
        return mueble;
    }

    @Override
    public Mueble create(Mueble mueble) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try{
            session.beginTransaction();
            session.save(mueble);
            session.getTransaction().commit();
        }catch (PersistenceException e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }

        session.close();
        return mueble;
    }

    @Override
    public Mueble actualizar(Mueble mueble) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try{
            session.beginTransaction();
            session.update(mueble);
            session.getTransaction().commit();
        }catch (PersistenceException e){
            session.getTransaction().rollback();
        }
        session.close();
        return mueble;
    }

    @Override
    public boolean deleteById(long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            session.beginTransaction();
            Mueble mueble = this.buscarPorId(id);
            if(mueble != null){
                session.delete(mueble);

            }else{
                return false;
            }
            session.getTransaction().commit();
        }catch (PersistenceException e){
            e.printStackTrace();
            session.getTransaction().rollback();
            return false;
        }finally {
            session.close();
        }
        return true;
    }
}
