/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.*;
import java.util.*;

/**
 *
 * @author peje
 */


public interface Dao<T, K> {
    
    T getOne(K key) throws SQLException;
    
    List<T> getAll() throws SQLException;
    
    T create(T object) throws SQLException;
    
    boolean delete(K key) throws SQLException;
    
    boolean setDone(K key) throws SQLException;
    
}
