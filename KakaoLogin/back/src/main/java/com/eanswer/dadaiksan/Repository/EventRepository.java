package com.eanswer.dadaiksan.Repository;

import com.eanswer.dadaiksan.Entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
