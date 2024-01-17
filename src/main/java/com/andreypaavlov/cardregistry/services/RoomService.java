package com.andreypaavlov.cardregistry.services;

import com.andreypaavlov.cardregistry.entities.Room;
import com.andreypaavlov.cardregistry.repositories.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;

    @Cacheable(value = "rooms",key = "'r'")
    public List<Room> getAllRooms() {
        List<Room> rooms = roomRepository.findAll();
//        Comparator<Room> idComparator = Comparator.comparing(room -> Integer.parseInt(room.getId()));
//        Collections.sort(rooms, idComparator);
        return rooms;
    }
}

