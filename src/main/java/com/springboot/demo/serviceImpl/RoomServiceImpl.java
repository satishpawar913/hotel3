package com.springboot.demo.serviceImpl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.demo.entities.Rooms;
import com.springboot.demo.entities.User;
import com.springboot.demo.repository.RoomRepository;
import com.springboot.demo.service.RoomService;

@Service
public class RoomServiceImpl implements RoomService {

	@Autowired
	private RoomRepository roomRepository;

	@Override
	public List<Rooms> getRoomsByCId(int cId) {
		return roomRepository.findByCId(cId);
	}

	@Override
	public Rooms saveRoom(Rooms rooms) {
		return roomRepository.save(rooms);
	}

	@Override
	public List<Rooms> getAllRooms() {
		return roomRepository.findAll();
	}

	@Override
	public Optional<Rooms> findRoomById(Integer rId) {
		return roomRepository.findById(rId);
	}

	@Override
	@Transactional
	public void deleteRoom(int rId) {
		Optional<Rooms> optionalRooms = roomRepository.findById(rId);
		optionalRooms.ifPresent(rooms -> {
			User user = rooms.getUser();
			if (user != null) {
				user.getRooms().remove(rooms);
				rooms.setUser(null);
			}
			roomRepository.delete(rooms);
		});
	}
}
