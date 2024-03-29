package com.meetingroomreservation.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.query.criteria.JpaCriteriaQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.meetingroomreservation.entity.MeetingRoom;

import jakarta.persistence.*;
import jakarta.persistence.criteria.Root;


@Repository
public class MeetingRoomDAOImpl implements MeetingRoomDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<MeetingRoom> getMeetingRooms() {
        Session session = sessionFactory.getCurrentSession();
        HibernateCriteriaBuilder cb = session.getCriteriaBuilder();
        JpaCriteriaQuery<MeetingRoom> cq = cb.createQuery(MeetingRoom.class);
        Root <MeetingRoom> root = cq.from(MeetingRoom.class);
        cq.select(root);
        Query query = session.createQuery(cq);
        return query.getResultList();
    }

    @Override
    public void deleteMeetingRoom(int id) {
        Session session = sessionFactory.getCurrentSession();
        MeetingRoom book = session.byId(MeetingRoom.class).load(id);
        session.delete(book);
    }

    @Override
    public void saveMeetingRoom(MeetingRoom theMeetingRoom) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.saveOrUpdate(theMeetingRoom);
    }

    @Override
    public MeetingRoom getMeetingRoom(int theId) {
        Session currentSession = sessionFactory.getCurrentSession();
        MeetingRoom theMeetingRoom = currentSession.get(MeetingRoom.class, theId);
        return theMeetingRoom;
    }
}
