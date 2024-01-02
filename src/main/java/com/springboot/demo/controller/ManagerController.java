package com.springboot.demo.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.springboot.demo.entities.Rooms;
import com.springboot.demo.entities.User;
import com.springboot.demo.helper.Message;
import com.springboot.demo.repository.ManagerRepository;
import com.springboot.demo.repository.UserRepository;
import com.springboot.demo.service.ManagerServcie;
import com.springboot.demo.service.RoomService;
import com.springboot.demo.service.UserService;

@Controller
public class ManagerController {
	
	@Autowired
	private ManagerRepository managerRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;

	@Autowired
	private RoomService roomService;

	@RequestMapping(value = { "/manager" })
	public String adminMain(Model model) {
		List<User> listEmployees = userService.getUserByRole("ROLE_MANAGER");
		model.addAttribute("listEmployees", listEmployees);
		return "admin1";
	}

	@RequestMapping(value = { "/manager/manageBooking" })
	public String adminManage(Model model) {
		List<Rooms> listOfRooms = roomService.getAllRooms();
		model.addAttribute("listOfRooms", listOfRooms);
		return "edit_user";
	}

	@RequestMapping(value = { "/manager/manageBooking/edit/{rId}" }, method = RequestMethod.GET)
	public String editRoom(@PathVariable("rId") Integer rId, Model model) {
		Optional<Rooms> optionalRoom = roomService.findRoomById(rId);
		if (optionalRoom.isPresent()) {
			Rooms room = optionalRoom.get();
			model.addAttribute("room", room);
			return "update";
		} else {
			return "redirect:edit_user";
		}
	}

	
	@RequestMapping(value = { "/manager/manageBooking/update" }, method = RequestMethod.POST)
	public String updateRoom(@ModelAttribute("room") Rooms updatedRoom, HttpSession session) {
		// Fetch the existing room from the database
		Optional<Rooms> optionalExistingRoom = roomService.findRoomById(updatedRoom.getrId());

		if (optionalExistingRoom.isPresent()) {
			Rooms existingRoom = optionalExistingRoom.get();

			// Update the existing room with the new data
			existingRoom.setFirst_name(updatedRoom.getFirst_name());
			existingRoom.setLast_name(updatedRoom.getLast_name());
			existingRoom.setEmail(updatedRoom.getEmail());
			// Set other properties accordingly...

			// Save the updated room
			roomService.saveRoom(existingRoom);

			session.setAttribute("message", new Message("Room updated successfully...", "success"));
			return "redirect:/manager/manageBooking";
		} else {
			session.setAttribute("message", new Message("Room not found...", "danger"));
			return  "redirect:/managerBooking/manage";
		}
	}

	@RequestMapping(value = { "/manager/manageBooking/delete/{rId}" }, method = RequestMethod.GET)
	public String deleteRoom(@PathVariable("rId") Integer rId) {
		roomService.deleteRoom(rId);
		return "redirect:/manager/manageBooking";
	}

}
