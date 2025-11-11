package com.cnh.booking.service;

import com.cnh.booking.dto.UserLogin;
import com.cnh.booking.model.Event;
import com.cnh.booking.model.User;
import com.cnh.booking.repository.EventRepository;
import com.cnh.booking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    //All events
    public List<Event> getAllEvents(){
        return eventRepository.findAll();
    }

    //One Event by id
    public Optional<Event> getEventById(long id){
        return eventRepository.findById(id);
    }

    //Attend an event
    public String attendEvent(long id, UserLogin login){
        Optional<Event> currentEvent = eventRepository.findById(id);
        if(currentEvent.isEmpty()){
            return "Event not available";
        }
        Optional<User> currentUser = userRepository.findByEmail(login.getEmail());
        if(currentUser.isEmpty()){
            return "User invalid";
        }
        LocalDateTime time = LocalDateTime.now();
        if(currentEvent.get().getDate().isBefore(time)){
            return "Event is already done";
        }

        HashSet<Event> eventAttending = currentUser.get().getEvent();
        HashSet<User> userAttending = currentEvent.get().getUsers();
        for(Event event : eventAttending){
            if(event.getId() == id){
                return "User already attending the event";
            }
        }

        for(User user : userAttending){
            if(user.getEmail().equals(login.getEmail())){
                return "User already registered for event";
            }
        }
        eventAttending.add(currentEvent.get());
        userAttending.add(currentUser.get());
        userRepository.save(currentUser.get());
        eventRepository.save(currentEvent.get());
        double price = currentEvent.get().getPrice();
        if(currentUser.get().getGender().equalsIgnoreCase("f") ||
        currentUser.get().getGender().equalsIgnoreCase("female")){
            price -= (price * 5 / 100);
        }
        return "Slot Booked @ " + price;

    }

    //Unattended an event
    public String unattendedEvent(long id, UserLogin login){
        Optional<Event> currentEvent = eventRepository.findById(id);
        if(currentEvent.isEmpty()){
            return "Event not available";
        }
        Optional<User> currentUser = userRepository.findByEmail(login.getEmail());
        if(currentUser.isEmpty()){
            return "User invalid";
        }

        LocalDateTime time = LocalDateTime.now();
        if(currentEvent.get().getDate().isBefore(time)){
            return "Event is already done";
        }

        HashSet<Event> eventAttending = currentUser.get().getEvent();
        HashSet<User> userAttending = currentEvent.get().getUsers();

        eventAttending.remove(currentEvent.get());
        userAttending.remove(currentUser.get());
        userRepository.save(currentUser.get());
        eventRepository.save(currentEvent.get());
        double price = currentEvent.get().getPrice();
        if(currentUser.get().getGender().equalsIgnoreCase("f") ||
                currentUser.get().getGender().equalsIgnoreCase("female")){
            price -= (price * 5 / 100);
        }
        return "Refund Amount : " + price;
    }

    //All attendees
    public List<String> allAttendee(long id){
        Optional<Event> currentEvent = eventRepository.findById(id);
        if(currentEvent.isEmpty()){
            return null;
        }
        HashSet<User> users = currentEvent.get().getUsers();
        List<String> attendee = new ArrayList<>();
        for(User user : users){
            attendee.add(user.getName());
        }
        return attendee;
    }
}
