package com.springboot.demo.service;

import java.util.List;
import java.util.Optional;

import com.springboot.demo.entities.Rooms;



public interface RoomService {
	
	List<Rooms> getRoomsByCId(int cId);

	Rooms saveRoom(Rooms rooms);

	List<Rooms> getAllRooms();

	void deleteRoom(int rId);

	Optional<Rooms> findRoomById(Integer rId);

}
