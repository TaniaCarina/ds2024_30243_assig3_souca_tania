package ro.tuc.ds2020.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.tuc.ds2020.entity.Message;

import java.util.UUID;

public interface ChatRepository extends JpaRepository<Message, UUID> {

}