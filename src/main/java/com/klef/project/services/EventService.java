package com.klef.project.services;

import java.util.List;

import javax.ejb.Remote;

import com.klef.project.models.Event;
import com.klef.project.models.Faculty;

@Remote
public interface EventService {
	public String addEvent(Event event);
	public String updateEvent(Event event);
	public String deleteEvent(int eid);
	public List<Event> viewallEvent();
	public Event viewEventById(int id);
}
