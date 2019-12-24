/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dao.sms;

import com.cbs.dao.GenericDAO;
import javax.persistence.EntityManager;

/**
 *
 * @author root
 */
public class MbSmsLogDAO extends GenericDAO {

    public MbSmsLogDAO(EntityManager entityManager) {
        super(entityManager);
    }
}
