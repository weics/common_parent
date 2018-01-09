package com.itheima.bos.dao.system;

import com.itheima.bos.domain.system.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Integer> {

    public User findByUsername(String username);

}
