package com.favendo.user.service.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.favendo.commons.domain.User;

@Repository
public interface UserDao extends JpaRepository<User, Long> {

    @Query("select user from User user where user.username = :username")
    User findByUsername(@Param("username") String username);

    @Query("select user from User as user join user.roles as role where role.roleName = 'ROLE_MERCHANT' ORDER BY user.firstName ASC")
    List<User> findAll();

    @Query("select user from User user where user.username = :username")
    User findByAccountNo(@Param("username") String username);

    @Query("select user from User as user join user.roles as role where user.user_id =:userId and role.roleName = :role")
    User findByUserIdAndRole(@Param("userId") Long userId,
                             @Param("role") String role);

    @Query("select user " +
            "from User user join fetch user.customer as customer " +
            "where  user.customer.customerId = customer.customerId AND  " +
            "upper(user.username) = upper(:username) OR " +
            "upper(user.firstName) = upper(:firstName) OR " +
            "upper(user.customer.name) = upper(:name) ")
    List<User> findByUsernameOrFirstNameOrCustomerName(@Param("username") String username,
                                                       @Param("firstName") String firstName,
                                                       @Param("name") String name);

    @Query("select user " +
            "from User user join fetch user.merchant as merchant " +
            "where  user.merchant.merchantId = merchant.merchantId AND  " +
            "upper(user.username) = upper(:username) OR " +
            "upper(user.firstName) = upper(:firstName) OR " +
            "upper(merchant.accountName) = upper(:accountName) AND " +
            "user.merchant.merchantId <> :merchantId")
    List<User> findByUsernameFirstNameOrAccountNameAndMerchantId(@Param("username") String username,
                                                                 @Param("firstName") String firstName,
                                                                 @Param("accountName") String accountName,
                                                                 @Param("merchantId") Long merchantId);
    @Query("select user " +
            "from User user join fetch user.customer as customer " +
            "where  user.customer.customerId = customer.customerId AND  " +
            "upper(user.username) = upper(:username) OR " +
            "upper(customer.name) = upper(:name) AND " +
            "user.customer.customerId <> :customerId")
    List<User> findByUsernameOrNameAndCustomerId(@Param("username") String username,
                                                 @Param("name") String name,
                                                 @Param("customerId") Long customerId);

    @Query("select user " +
            "from User user  " +
            "where  upper(user.username) = upper(:username) AND " +
            "user.user_id <> :userId")
    List<User> findByUsernameAndUserId(@Param("username") String username,
                                       @Param("userId") Long userId);
}
