package com.andreypaavlov.cardregistry.repositories;

import com.andreypaavlov.cardregistry.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room,String> {
}
