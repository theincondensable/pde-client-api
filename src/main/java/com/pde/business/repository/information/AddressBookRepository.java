package com.pde.business.repository.information;

import com.pde.business.model.auth.User;
import com.pde.business.model.information.AddressBook;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @author abbas
 */
@Repository
public interface AddressBookRepository extends JpaRepository<AddressBook, Long> {

//    AddressBook findByUsers(Set<User> user);

//    List<AddressBook> findAllByOrderByCreatedAtDesc(Pageable pageable);

    List<AddressBook> findAllByUsersOrderByCreatedAtDesc(Set<User> user, Pageable pageable);

}
