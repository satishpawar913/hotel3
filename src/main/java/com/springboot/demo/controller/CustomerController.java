package com.springboot.demo.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.springboot.demo.entities.Rooms;
import com.springboot.demo.entities.User;
import com.springboot.demo.helper.Message;
import com.springboot.demo.service.RoomService;
import com.springboot.demo.service.UserService;

@Controller
public class CustomerController {

	@Autowired
	private UserService userService;

	@Autowired
	private RoomService roomService;

//	@RequestMapping(value = { "/process_booking" }, method = RequestMethod.POST)
//	public String reserveRoom(@ModelAttribute("rooms") Rooms rooms, Model model, HttpSession session,
//			Principal principal) {
//		if (principal != null) {
//			String name = principal.getName();
//			User user = userService.getUserByUserName(name);
//			int cId = user.getId();
//			rooms.setcId(cId);
//			user.getRooms().add(rooms);
//		}
//
//		// Set the default price to 0
//		rooms.setPrice(0);
//
//		if ("Single Bed Room".equals(rooms.getCategory())) {
//			rooms.setPrice(1100);
//		} else if ("Double Bed Room".equals(rooms.getCategory())) {
//			rooms.setPrice(21000);
//		} else if ("New Triple Bed Room".equals(rooms.getCategory())) {
//			rooms.setPrice(25000);
//		}
//
//		// Calculate total days and total price...
//		System.out.println("Rooms " + rooms);
//		String checkIn = rooms.getCheckinDate();
//		String checkOut = rooms.getCheckoutDate();
//
//		try {
//			Date checkInDate = new SimpleDateFormat("yyyy-MM-dd").parse(checkIn);
//			System.out.println("The check-in date in date format: " + checkInDate);
//			Date checkOutDate = new SimpleDateFormat("yyyy-MM-dd").parse(checkOut);
//			System.out.println("The check-out date in date format: " + checkOutDate);
//
//			long diff = checkOutDate.getTime() - checkInDate.getTime();
//			TimeUnit time = TimeUnit.DAYS;
//			long difference = time.convert(diff, TimeUnit.MILLISECONDS);
//			System.out.println("The difference in days is: " + difference);
//
//			rooms.setTotal_days(difference);
//
//			// Calculate total price using the correct price
//			long totalPrice = rooms.getPrice() * difference;
//			rooms.setTotal_price(totalPrice);
//
//		} catch (java.text.ParseException e) {
//			e.printStackTrace();
//		}
//
//		Rooms reservation = roomService.saveRoom(rooms);
//
//		model.addAttribute("rooms", new Rooms());
//		session.setAttribute("message", new Message("Successfully Registered", "alert-success"));
//		return "room_booking";
//	}
	
	@RequestMapping(value= {"/process_booking"},method=RequestMethod.POST)
	public String reserveRoom(@ModelAttribute("rooms") Rooms rooms,Model model,HttpSession session,Principal principal) {
		
		String name = principal.getName();
		User user=this.userService.getUserByUserName(name);
		
		int cId=user.getId();
		rooms.setcId(cId);
		user.getRooms().add(rooms);
		
		
		
		if(rooms.getCategory().equals("Luxury Room")) {
			rooms.setPrice(16950);
		}
		else if(rooms.getCategory().equals("Luxury Grande Room City View")) {
			rooms.setPrice(18700);
		}
		
		else if(rooms.getCategory().equals("Luxury Grande Room Sea View")) {
			rooms.setPrice(20825);
		}else {
			rooms.setPrice(0);
		}
		
		System.out.println("Rooms "+rooms);
		String checkIn = rooms.getCheckinDate();
		String checkOut = rooms.getCheckoutDate();
		try {
			Date checkInDate = new SimpleDateFormat("yyyy-MM-dd").parse(checkIn);
			System.out.println("The check in date in date format: "+checkInDate);
			Date checkOutDate = new SimpleDateFormat("yyyy-MM-dd").parse(checkOut);
			System.out.println("The check out date in date format: "+checkOutDate);
			long diff = checkOutDate.getTime() - checkInDate.getTime();
//			System.out.println("The difference between the two dates is: "+diff);
			TimeUnit time = TimeUnit.DAYS; 
	        long difference = time.convert(diff, TimeUnit.MILLISECONDS);
	        System.out.println("The difference in days is : "+difference);
	        rooms.setTotal_days(difference);
//			System.out.println(checkOutDate-checkInDate);
	        long a=rooms.getPrice();
	        long b=a*difference;
	        rooms.setTotal_price(b);
	        
		}catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Rooms reservation=this.roomService.saveRoom(rooms);
		  
		
		model.addAttribute("rooms", new Rooms());
		session.setAttribute("message", new Message("Successfully Registered","alert-success"));
		return "room_booking";
	}


	@RequestMapping(value = { "/billing" })
	public String userBillinginfo(@ModelAttribute("rooms") Rooms rooms, Model model, HttpSession session,
			Principal principal) {
		String name = principal.getName();
		User user = userService.getUserByUserName(name);
		int cId = user.getId();
		List<Rooms> roomReservations = roomService.getRoomsByCId(cId);
		model.addAttribute("user", user);
		model.addAttribute("userRooms", roomReservations);
		return "billing";
	}
}
