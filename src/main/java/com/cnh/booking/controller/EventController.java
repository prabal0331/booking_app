package com.cnh.booking.controller;

import com.cnh.booking.dto.UserLogin;
import com.cnh.booking.model.Event;
import com.cnh.booking.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//Event booking, checking and updating
@RestController
@RequestMapping("event")
public class EventController {

    @Autowired
    private EventService eventService;

    //All events
    @GetMapping("/all")
    public ResponseEntity<List<Event>> getAllEvents(){
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    //A particular event
    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable long id){
        Optional<Event> event = eventService.getEventById(id);
        if(event.isEmpty()){
            return ResponseEntity.status(404).body(event.get());
        }
        return ResponseEntity.status(200).body(event.get());
    }

    //Attending an event
    @PostMapping("/{id}/attend")
    public ResponseEntity<String> attendEvent(@PathVariable long id, @RequestBody UserLogin login){
        String message = eventService.attendEvent(id,login);
        if(message.startsWith("Slot Booked @")){
            return ResponseEntity.ok().body(message);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    //Unattending an event
    @PostMapping("/{id}/unattended")
    public ResponseEntity<String> unattendedEvent(@PathVariable long id, @RequestBody UserLogin login){
        String message = eventService.unattendedEvent(id,login);
        if(message.startsWith("Refund Amount")){
            return ResponseEntity.ok().body(message);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    //Users who are attending an event
    @GetMapping("/{id}/all")
    public ResponseEntity<List<String>> allAttendee(@PathVariable long id){
        List<String> attendee = eventService.allAttendee(id);
        if(attendee == null){
            List<String> list = new ArrayList<>();
            list.add("Event does not exist");
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(list);
        }

        if(attendee != null && attendee.isEmpty()){
            List<String> list = new ArrayList<>();
            list.add("No attendee");
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(list);
        }
        return ResponseEntity.ok().body(eventService.allAttendee(id));
    }
}
