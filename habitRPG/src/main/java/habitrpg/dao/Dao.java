/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package habitrpg.dao;

import java.sql.*;
import java.util.*;

/**
 *
 * @author peje
 */


public interface Dao<T, K> {
    
    T getOne(K key);
    
    List<T> getAll();
    
    T create(T object);
    
    boolean delete(K key);
    
    boolean setDone(K key);
    
}
